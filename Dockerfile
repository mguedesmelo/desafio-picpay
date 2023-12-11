# Usa a imagem base do Maven para compilar o projeto
FROM maven:latest AS build

# Diretório de trabalho dentro do contêiner
WORKDIR /build

# Copia apenas o arquivo POM e faça o download das dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte e compile o projeto
COPY src/ src/
RUN mvn package

# Usa a imagem base do OpenJDK para executar a aplicação
FROM openjdk:latest

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR do Spring Boot do estágio de compilação para o contêiner
COPY --from=build /build/target/simplepicpay-0.0.1-SNAPSHOT.jar /app/simplepicpay-0.0.1-SNAPSHOT.jar

# Executa a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "simplepicpay-0.0.1-SNAPSHOT.jar"]
