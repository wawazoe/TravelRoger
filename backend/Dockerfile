FROM eclipse-temurin:17-jdk
WORKDIR /backend
ADD https://jdbc.postgresql.org/download/postgresql-42.7.3.jar /backend/postgresql.jar
COPY src /backend/src
RUN javac -cp postgresql.jar src/*.java
CMD ["java","-cp","postgresql.jar:src","LoginServer"]