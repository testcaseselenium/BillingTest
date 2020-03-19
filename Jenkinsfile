pipeline {
   agent any

   stages {
      stage('Build') {
         steps {
            // Run Maven on a Unix agent.
            bat "mvn test -f BilligTest/pom.xml"
            // To run Maven on a Windows agent, use
            // bat "mvn -Dmaven.test.failure.ignore=true clean package"
         }
         post {
            // If Maven was able to run the tests, even if some of the test
            // failed, record the test results and archive the jar file.
               always{
                junit "**/target/surefire-reports/*.xml"
               }
         }
      }
   }
}
