# Instalando uma imagem do JDK 21
FROM openjdk:21

# Criando uma pasta para onde o docker irá copiar os arquivos do projeto
WORKDIR /app

# Copiando todos os arquivos do projeto para dentro desta pasta
COPY . /app

# Comando para fazer o DEPLOY do projeto
RUN ./mvnw -B clean package -DskipTests

# POrta em que o projeto será executado
EXPOSE 8081

# Script para executar o projeto
CMD ["java", "-jar", "target/apiPedidos-0.0.1-SNAPSHOT.jar"]
