FROM maven:3-amazoncorretto-8

WORKDIR /app

COPY pom.xml .

RUN mvn clean install

COPY . .

EXPOSE 9000

CMD ['mvn', 'tomcat7:run']