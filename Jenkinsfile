 pipeline {
tools {
maven "M3"
}
agent any
stages {
stage("Preparation") {
steps {
git 'https://github.com/oscardpg/AisTest.git'
}
}
stage("Test") {
steps {
script {
if(isUnix()) {
sh "cd oscardpg/AisTest ; mvn package"
} else {
bat(/cd oscardpg ; "${mvnHome}\bin\mvn" package/)
}
}
}
}
}
post {
always {
junit "oscardpg/AisTest/**/target/surefire-reports/TEST-*.xml"
}
success {
archive "oscardpg/AisTest/target/*.jar"
}
}
}