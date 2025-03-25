
# CPU State Monitor

## 简介

这是一个用于监控 CPU C 状态的网页应用程序。它可以通过图表展示 CPU 各个 C 状态的使用情况。应用程序提供了柱状图和饼图两种视图，用户可以切换不同的图表类型，并且支持根据客户端的浏览器语言自动切换界面语言。

该应用程序使用 **Java** 开发，并通过 **Docker** 容器化部署。

## 功能特点

- **实时监控**：展示每个 CPU 的 C 状态使用情况（从 C0 到 C8）。
- **图表切换**：支持柱状图和饼图两种显示方式，可以根据需要切换。
- **自动语言切换**：根据浏览器的语言自动切换界面语言，支持英文和中文。
- **直观显示**：通过色彩渐变表示不同 C 状态的使用率，C0 越偏红色，C8 越偏绿色。

## 使用方法

### 1. 克隆代码库

首先，克隆该项目的代码库到本地：

```bash
git clone https://github.com/your-repository/cpu-state-monitor.git
cd cpu-state-monitor
```

### 2. 运行前端界面

1. 打开 `index.html` 文件，您可以直接在浏览器中打开它来查看界面。或者将其部署到 Web 服务器上进行托管。

2. 启动服务后，访问 `http://localhost:8123` 即可查看应用程序的实时图表。

### 3. Docker 构建与部署

#### 构建 Docker 镜像

该项目包含了一个 `Dockerfile`，您可以使用 Docker 来构建并运行应用程序。

1. 使用以下命令构建 Docker 镊像：

```bash
docker build -t cpu-state-monitor .
```

2. 构建成功后，您可以运行该 Docker 镜像：

```bash
docker run -d -p 8123:8123 cpu-state-monitor
```

该命令将容器启动并映射端口 `8123`，然后您可以在浏览器中访问 `http://localhost:8123` 来查看应用程序。

### 4. 配置环境变量

您可以通过环境变量来配置 SSH 连接的相关信息。以下是默认的配置项：

- `SSH_HOST`：SSH 主机地址，默认值为 `192.168.50.61`
- `SSH_PORT`：SSH 端口，默认值为 `22`
- `SSH_USERNAME`：SSH 登录用户名，默认值为 `root`
- `SSH_PASSWORD`：SSH 登录密码，默认值为 `123456`
- `CONNECT_TIMEOUT`：连接超时，默认值为 `5000` 毫秒

在启动 Docker 容器时，您可以通过设置环境变量来覆盖这些默认值：

```bash
docker run -d -p 8123:8123   -e SSH_HOST=your-ssh-host   -e SSH_PORT=your-ssh-port   -e SSH_USERNAME=your-ssh-username   -e SSH_PASSWORD=your-ssh-password   -e CONNECT_TIMEOUT=5000   cpu-state-monitor
```

## Dockerfile

### 多阶段构建 Dockerfile

以下是该项目的 Dockerfile，用于将 Java 项目容器化部署：

```dockerfile
# 使用 eclipse-temurin 作为构建基础镜像
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app
COPY . .
RUN ./mvnw clean package

# 使用 eclipse-temurin 作为运行时基础镜像
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar

# 设置默认的 SSH 连接配置
ENV SSH_HOST=192.168.50.61
ENV SSH_PORT=22
ENV SSH_USERNAME=root
ENV SSH_PASSWORD=123456
ENV CONNECT_TIMEOUT=5000

EXPOSE 8123
CMD ["java", "-jar", "app.jar"]
```

### 说明

- **多阶段构建**：首先使用 `eclipse-temurin:17-jdk-jammy` 镜像构建应用程序的 JAR 包，然后将 JAR 包复制到 `eclipse-temurin:17-jre-jammy` 镜像中进行运行。
- **环境变量**：通过设置默认的环境变量来配置 SSH 连接信息（您可以根据实际需要进行修改）。
- **EXPOSE**：暴露端口 `8123`，供外部访问应用程序。

### 5. 访问应用程序

启动 Docker 容器后，可以通过浏览器访问 `http://localhost:8123` 来查看图表。

## 技术栈

- **前端**：HTML, JavaScript, Chart.js, Tailwind CSS
- **后端**：Java (Spring Boot)
- **容器化**：Docker

## 联系方式

如果你在使用过程中有任何问题，欢迎通过以下方式联系我：

- GitHub Issues: https://github.com/your-repository/cpu-state-monitor/issues
- Email: your-email@example.com
