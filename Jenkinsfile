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
                sh 'mvn javadoc:javadoc'
                // Mover la documentación generada a 'docs/application'
                sh 'mkdir -p docs/application'
                sh 'cp -r target/site/apidocs/* docs/application/'
            }
        }

        stage('Desplegar Documentación') {
            steps {
                // Copia la documentación al directorio de destino
                sh 'cp -r docs/application/* /var/www/html/documentins/'
                // Renombra el archivo 'OrderProcessingSystem.html' a 'usql-traductor.html'
                sh 'mv /var/www/html/documentins/OrderProcessingSystem.html /var/www/html/documentins/usql-traductor.html'
            }
        }
    }
}
