FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar

# 设置默认值
ENV SSH_HOST=192.168.50.61
ENV SSH_PORT=22
ENV SSH_USERNAME=root
ENV SSH_PASSWORD=123456
ENV CONNECT_TIMEOUT=5000

EXPOSE 8123
CMD ["java", "-jar", "app.jar"]