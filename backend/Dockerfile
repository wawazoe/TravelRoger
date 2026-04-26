FROM eclipse-temurin:17-jdk
WORKDIR /backend
COPY src /backend/src
RUN javac src/*.java
CMD ["java","-cp","src","db.Login"]