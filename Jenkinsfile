pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                git url: 'https://github.com/newshqyang/My-Diary.git', branch: 'master'
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'chmod +x ./gradlew'
                sh './gradlew assembleRelease'
            }
        }
        stage('Archive APK') {
            steps {
                echo 'Archive...'
                archiveArtifacts artifacts: '**app/build/outputs/apk/release/*.apk', fingerprint: true
            }
        }
    }

    post {
        always {
            echo 'This will always run after the stages.'
        }
    }
}