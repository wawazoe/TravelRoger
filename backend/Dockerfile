FROM eclipse-temurin:17-jdk
WORKDIR /backend
COPY src /backend/src
RUN javac src/main.java
CMD ["java","-cp","src","main"]