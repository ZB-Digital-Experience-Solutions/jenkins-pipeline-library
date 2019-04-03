#!/usr/bin/groovy
def call( zapReportName, serviceName, DNS_NAME ) {

    try {

            sh """
                  mkdir /zap/wrk
                  (/zap/zap-baseline.py -r baseline.html -t http://${serviceName}.${DNS_NAME} || true) >cat.txt
                  cp /zap/wrk/baseline.html .
                """
                def zaptest = sh(returnStdout: true, script: 'cat cat.txt').trim()
                  print "${zaptest}"

                  def zaptestresult = sh(returnStdout: true, script: 'cat cat.txt | tail -1').trim()
                  print "${zaptestresult}"

                  sh "rm cat.txt"

                  return zaptestresult

    } catch (err) {
        echo "Exception: ${err}"
        throw err
    }
}