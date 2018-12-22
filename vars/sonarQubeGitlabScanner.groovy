#!/usr/bin/groovy
def call(accessToken, gitlabProject, sonarHost, branchName, commitHash) {
    def serviceName = "sonarqube";
    def port = "9000";
    def scannerVersion = "3.2.0.1227"
    def runSonarScanner = "true"

    if (runSonarScanner) {
        try {
            def srcDirectory = pwd();
            def tmpDir = pwd(tmp: true)

            //work in tmpDir - as sonar scanner will download files from the server
            dir(tmpDir) {
                def jobName = "${env.JOB_NAME}".tokenize('/')[0]

                def localScanner = "scanner-cli.jar"

                def scannerURL = "http://central.maven.org/maven2/org/sonarsource/scanner/cli/sonar-scanner-cli/${scannerVersion}/sonar-scanner-cli-${scannerVersion}.jar"

                echo "downloading scanner-cli"

                sh "curl -o ${localScanner}  ${scannerURL} "

                echo("executing sonar scanner ")

                sh "java -jar ${localScanner} -Dsonar.junit.reportsPath=target/surefire-reports -Dsonar.surefire.reportsPath=target/surefire-reports -Dsonar.java.coveragePlugin=jacoco -Dsonar.exclusions=**/test/**/**/.java -Dsonar.language=java -Dsonar.jacoco.reportPath=target/jacoco.exec -Dsonar.host.url=${sonarHost} -Dsonar.projectKey=${jobName} -Dsonar.branch=${branchName} -Dsonar.projectBaseDir=${srcDirectory} -Dsonar.java.binaries=${srcDirectory}/target/classes -Dsonar.sources=${srcDirectory} -Dsonar.analysis.mode=preview -Dsonar.gitlab.unique_issue_per_inline=true -Dsonar.gitlab.url=https://gitlab.com -Dsonar.gitlab.user_token=${accessToken} -Dsonar.gitlab.user_token=${accessToken} -Dsonar.gitlab.commit_sha=${commitHash} -Dsonar.gitlab.project_id=${gitlabProject} -Dsonar.gitlab.ref_name=${branchName} -Dsonar.login=admin -Dsonar.password=admin -X -Dsonar.verbose=true -Dsonar.log.level=DEBUG -Dsonar.showProfiling=true -Dsonar.gitlab.ping_user=true -Dsonar.gitlab.all_issues=true"
            }

        } catch (err) {
            echo "Failed to execute scanner:"
            echo "Exception: ${err}"
            throw err;
        }
    }

}
