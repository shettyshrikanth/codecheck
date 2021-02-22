./gradlew clean build

docker build -f deploy-pipeline/Dockerfile -t codecheck-app .

docker run -p 8080:8080 codecheck-app
