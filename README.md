# SpringCloudeStudy

Spring Cloud 微服务学习项目。项目以一个简单的图书借阅系统为例，拆分为用户、图书和借阅服务，并通过不同 Git 分支演示服务注册与发现、配置中心、网关、容错、限流、分布式事务和 OAuth2 等能力。

> **重要：不同分支是不同功能的实验版本。**
>
> 请先切换到目标分支，再按该分支的配置启动。不要把不同分支中的注册中心、配置中心或业务服务混合使用。

## 分支说明

| 分支 | 功能重点 | 需要额外准备的组件 |
| --- | --- | --- |
| `main` | 基础版本：用户、图书、借阅三个 Spring Boot 服务，MySQL + MyBatis | MySQL |
| `eureka`（远程分支 `origin/eureka`） | Eureka 服务注册与发现、OpenFeign 服务间调用；包含双节点 Eureka 示例 | MySQL、两个 Eureka 节点 |
| `nacos` | 使用 Nacos 完成服务注册、发现和配置管理，并使用 OpenFeign 调用服务 | MySQL、Nacos |
| `gateway` | 在 Eureka 体系上增加 Spring Cloud Gateway、路由和全局过滤器 | MySQL、Eureka |
| `hystrix` | 在 Feign 调用上增加 Hystrix 熔断与 fallback 降级 | MySQL、Eureka |
| `config` | 增加 Spring Cloud Config Server，同时保留 Eureka、Gateway 和 Hystrix 示例 | MySQL、配置仓库、Eureka |
| `sentinel` | 在 Nacos 体系上增加 Sentinel 流量控制/熔断，以及 Feign 整合 | MySQL、Nacos、Sentinel Dashboard（默认 `8858`） |
| `seata` | 在 Nacos 体系上增加 Seata 分布式事务 | MySQL、Nacos、Seata Server；业务库需准备 `undo_log` |
| `oAuth` | OAuth2 授权服务和资源服务保护；包含 `auth-service` | MySQL、Nacos（按配置启用）；授权服务默认 `8500`、上下文路径 `/sso` |
| `boot-2.6` | Spring Boot 2.6 / Java 8 兼容性基础版本 | MySQL |
| `springcloudBus` | Spring Cloud Config + Spring Cloud Bus，通过 AMQP 广播配置刷新 | MySQL、配置仓库、RabbitMQ |

分支名称以仓库实际 Git 引用为准，可先查看：

```bash
git branch -a
```

本地没有 `eureka` 分支时，使用远程分支创建本地跟踪分支：

```bash
git switch -c eureka --track origin/eureka
```

其余分支可以直接切换，例如：

```bash
git switch springcloudBus
```

## 项目结构

```text
SpringCloudeStudy/
├── user-service       # 用户服务，默认端口 8380
├── book-service       # 图书服务，默认端口 8180
├── borrow-service     # 借阅服务，默认端口 8280
├── commons            # 公共实体和依赖
├── eureka-server      # eureka/config/gateway 等分支中的注册中心
├── gateway-server     # gateway/config 等分支中的网关
├── config-server      # config/springcloudBus 分支中的配置中心
├── auth-service       # oAuth 分支中的 OAuth2 授权服务
├── db                 # 数据库脚本
└── pom.xml            # Maven 父工程
```

基础接口（端口以当前分支配置为准）：

| 服务 | 示例接口 | 说明 |
| --- | --- | --- |
| user-service | `GET /user/{uid}` | 查询用户及借阅信息中的用户数据 |
| book-service | `GET /book/{bid}` | 查询图书 |
| book-service | `GET /test` | `config`/`springcloudBus` 等配置中心分支的动态配置示例接口 |
| borrow-service | `GET /borrow/{uid}` | 查询用户借阅详情 |

## 环境要求

- JDK：以目标分支根目录 `pom.xml` 的 `java.version` 为准；`boot-2.6`、`oAuth` 和 `springcloudBus` 使用 Java 8 配置。
- Maven 3.8+。
- MySQL 8.x（或兼容版本），数据库名默认为 `cloudstudy`。
- 需要注册中心、配置中心、消息队列或事务协调器的分支，按上方分支表准备对应组件。

## 快速开始

1. 创建数据库并执行 [`db`](./db) 目录中的脚本。
2. 切换到一个目标分支。
3. 修改该分支各服务 `src/main/resources/application.yml`（以及 `bootstrap.yml`）中的数据库、注册中心、配置仓库和中间件地址。仓库中的部分路径和连接信息是开发机示例，不能直接照搬到其他环境。
4. 在项目根目录构建：

   ```bash
   mvn clean package -DskipTests
   ```

5. 按依赖顺序启动基础设施和服务：

   - `eureka`/`gateway`/`hystrix`/`config`：先启动 Eureka，再启动业务服务；使用网关的分支再启动 `gateway-server`。
   - `nacos`/`sentinel`/`seata`：先启动 Nacos；`sentinel` 还需启动 Dashboard，`seata` 还需启动 Seata Server。
   - `config`：先启动 `config-server`（默认 `8700`），再启动配置客户端。
   - `springcloudBus`：先启动 Config Server 和 RabbitMQ，再启动业务服务；配置变更后可通过 Actuator 的刷新端点验证广播刷新。
   - `oAuth`：先启动 `auth-service`，再访问受保护的资源服务。

   单独启动某个模块的示例：

   ```bash
   mvn -pl user-service spring-boot:run
   mvn -pl book-service spring-boot:run
   mvn -pl borrow-service spring-boot:run
   ```

## 配置注意事项

- 不同分支的配置格式和服务名称可能不同：Eureka 使用 `eureka.client`，Nacos 使用 `spring.cloud.nacos`，Config/Bus 使用 `bootstrap.yml` 与 `spring.cloud.config`。
- `config-service` 中的 Git 仓库地址是本地路径示例；使用远程配置仓库时请替换为可访问的 URI，并确认 `default-label` 与实际分支一致。
- RabbitMQ、Nacos、Seata、数据库等账号密码仅用于本地示例。部署到其他环境时请修改配置，避免提交真实凭据。
- 同一端口只能启动一个实例；需要验证注册中心高可用时，再按 Eureka 配置启动 `8801` 和 `8802` 两个节点。

## 学习顺序建议

```text
main
  └─ eureka
      ├─ gateway
      └─ hystrix
          └─ config
              └─ springcloudBus

nacos
  ├─ sentinel
  └─ seata

oAuth / boot-2.6  # 独立主题分支
```

上图表示推荐的阅读顺序和功能关联，不表示所有分支都能直接合并。每次实验完成后，建议记录所需基础设施、关键配置和调用链路，再切换到下一个分支。
