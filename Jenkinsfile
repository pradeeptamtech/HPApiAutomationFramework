pipeline {

    agent any
    tools {
        maven 'Maven_3.6.3' 
    }
    
    environment {
            EMAIL_INFORM = 'pradeeptamtech@gmail.com;pradeepta.panigrahi@hp.com'
        }
    
    stages {
        stage('Compile stage') {
            steps {
                bat "mvn clean compile" 
        }
    }

         stage('testing stage') {
             steps {
                bat "mvn test"
        }
    }

       //   stage('deployment stage') {
       //       steps {
       //         bat "mvn deploy"
      //  }
   // }
        
        stage("Email") {
            steps{
                       emailext body: 'Check console output at $BUILD_URL to view the results.', 
                        to: "${EMAIL_INFORM}", 
                        subject: 'Jenkins - Released $PROJECT_NAME - #$BUILD_NUMBER'
            }         
       }
        
        // stage("Email") {
       //     steps{
       //             emailext( to:'pradeepta.panigrahi@hp.com' ,
       //                       replyTo:'pradeeptamtech@gmail.com',
       //                       subject: "Email Report from- '${env.JOB_NAME}'",
      //                        body: readFile("target/reports/"),
      //                        mimeType: 'text/html' );
      //          }
      //   }    

  }

}
