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

    post {
        success {
            // Envía un correo con el archivo trivia.html adjunto y un mensaje de éxito
            emailext(
                to: 'brunoramos.u@gmail.com',
                subject: 'Generación de Documentación Completada con Éxito',
                body: '''Hola Bruno,

La documentación ha sido generada exitosamente y el archivo trivia.html ha sido generado y movido a la ubicación correspondiente.

Puede ver la documentación correspondiente en este enlace: http://34.227.68.157/delivery-sys

Saludos,
Jenkins
                ''',
            )
        }
        
        failure {
            // Envía un correo en caso de que ocurra un fallo
            emailext(
                to: 'brunoramos.u@gmail.com',
                subject: 'Error en la Generación de Documentación',
                body: '''Hola Bruno,

Hubo un problema durante la generación de la documentación. Revisa el log de Jenkins para más detalles.

Puede ver la documentación anterior en este enlace: http://34.227.68.157/delivery-sys

Saludos,
Jenkins
                '''
            )
        }
    }
}
