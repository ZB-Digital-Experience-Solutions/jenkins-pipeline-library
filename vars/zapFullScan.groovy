#!/usr/bin/groovy
def call( serviceName, DNS_NAME) {

    try {

        container('owasp-zap') {
            sh """
                  mkdir /zap/wrk
                  (/zap/zap-full-scan.py -r zapreport.html -t http://${serviceName}.${DNS_NAME} || true) >cat.txt
                  ls -al
                  cp /zap/wrk/zapreport.html .
                """
              def zaptest = sh(returnStdout: true, script: 'cat cat.txt').trim()
              print "${zaptest}"

              def zaptestresult = sh(returnStdout: true, script: 'cat cat.txt | tail -1').trim()
              print "${zaptestresult}"

              sh "rm cat.txt"
              return zaptestresult
            }

    } catch (err) {
        echo "Exception: ${err}"
        throw err
    }
}