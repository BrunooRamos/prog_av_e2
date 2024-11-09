pipeline {
    agent any
    stages {
        stage('Clonar Repositorio') {
            steps {
                git url: 'https://github.com/BrunooRamos/prog_av_e3.git', branch: 'main'
            }
        }

        stage('Compilar Proyecto') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Ejecutar Pruebas y Generar Cobertura') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco execPattern: '**/target/jacoco.exec'
                }
            }
        }

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
    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }
}
