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
                dir('source') {
                    sh 'mvn javadoc:javadoc'
                    // Verificar que la documentación se generó
                    sh 'ls -la target/site/apidocs/'
                    // Crear directorio si no existe
                    sh 'mkdir -p docs/application'
                    // Copiar archivos generados
                    sh 'cp -r target/site/apidocs/* docs/application/'
                    // Listar archivos copiados
                    sh 'ls -la docs/application/'
                }
            }
        }
        stage('Desplegar Documentación') {
            steps {
                // Verificar si el archivo existe antes de copiar
                sh 'if [ -f source/docs/application/OrderProcessingSystem.html ]; then cp source/docs/application/OrderProcessingSystem.html /var/www/html/documentins/usql-traductor.html; else echo "Archivo no encontrado"; fi'
            }
        }
    }
    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }
}
