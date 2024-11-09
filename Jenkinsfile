pipeline {
    agent any
    tools {
        jdk 'Java17'       
        maven 'Maven3'     
    }
    stages {
        stage('Clonar Repositorio') {
            steps {
                git url: 'https://github.com/BrunooRamos/prog_av_e2.git', branch: 'main'
            }
        }

        stage('Compilar Proyecto') {
            steps {
                sh 'mvn clean compile'
            }
        }

        // stage('Ejecutar Pruebas y Generar Cobertura') {
        //     steps {
        //         sh 'mvn test'
        //     }
        //     post {
        //         always {
        //             junit 'source/target/surefire-reports/*.xml'
        //             jacoco execPattern: 'source/target/jacoco.exec'
        //         }
        //     }
        // }
        stage('Generar Documentación') {
            steps {
                // Genera la documentación en 'docs/application'
                // Ajusta este comando si utilizas una herramienta específica para generar la documentación
                sh 'javadoc -d doc -sourcepath src/main/java -subpackages application'
            }
        }

        stage('Desplegar Documentación') {
            steps {
                sh 'sudo mv doc/application/OrderProcessingSystem.html /var/www/html/documentins/delivery-sys.html'
                sh 'sudo mv doc/stylesheet.css /var/www/html/stylesheet.css'
            }
        }
    }
}
