# Challenge
Challenge para W2M

Se expone una api rest para gestion de superheroes.

# Requerimientos
Java 17

Maven 3.8.1

Docker

# compile
#clone project

git clone https://github.com/leo-vilte/challengew2m

cd challengew2m

mvn clean install

# deploy
mvn spring-boot:run 

# run dockerfile
cd docker

docker build -t superhero-api .

docker run -d -p 8080:8080 --name superhero superhero-api

# pagina de swagger

http://localhost:8080/swagger-ui/index.html
