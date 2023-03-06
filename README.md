# Challenge
Challenge para W2M

Se expone una api rest para gestion de superheroes.

# Requerimientos
Java 17

Maven 3.8.1

Docker

#Compile
#Clone project

git clone https://github.com/leo-vilte/challengew2m

cd challengew2m

mvn clean install

# Deploy
mvn spring-boot:run 

# Run docker image
cd docker

docker build -t superhero-api .

docker run -d -p 8080:8080 --name superhero superhero-api

# Pagina de swagger

http://localhost:8080/swagger-ui/index.html

# Credencial de Prueba
username=leo_vilte
password=prueba01
