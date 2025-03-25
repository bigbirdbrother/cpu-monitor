# 构建阶段
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app
# 复制项目文件到容器
COPY . .
# 调试：列出 /app 目录中的文件，确认 mvnw 是否在其中
RUN ls -alh /app
# 如果 mvnw 文件存在且权限无问题，添加执行权限并执行构建
RUN chmod +x /app/mvnw && /app/mvnw clean package -X

# 运行阶段
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar

# 设置默认环境变量
ENV SSH_HOST=192.168.50.61
ENV SSH_PORT=22
ENV SSH_USERNAME=root
ENV SSH_PASSWORD=123456
ENV CONNECT_TIMEOUT=5000

EXPOSE 8123
CMD ["java", "-jar", "app.jar"]
