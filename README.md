# realtime-service

`realtime-service` 是实时数据处理框架的业务服务工程，基于 `realtime-starter` 提供的 API/SPI 实现具体业务订阅、取消订阅、事件监听和消息推送。业务侧原则上只依赖 starter 的公开接口，不直接引用框架内部实现类。

## 设计思路

业务服务通过 `MessageProcessor` 处理客户端消息，通过 `SubscriptionManager` 维护订阅关系，通过 `EventBus` 触发业务初始化或异步处理，通过 `MessageSender` 将业务数据推送到指定会话或订阅目标。

当前业务示例围绕“单线路故障列表初始化”展开：客户端订阅指定 topic 后，服务发布初始化事件，监听器模拟获取故障列表并通过 WebSocket 单播给当前 session。

## 项目结构

```text
src/main/java/com/realtimes/service
├── RealtimeServiceApplication.java  # Spring Boot 启动类
├── constant/                        # 常量与实时主题
├── event/                           # 业务事件与事件上下文
├── listener/                        # 事件监听器
├── processor/                       # 订阅/取消订阅消息处理器
└── subscription/                    # 业务订阅 Key

src/main/resources
├── application.yml                  # 服务与实时框架配置
└── logback-spring.xml               # 日志配置
```

## 技术栈

- Java 17
- Spring Boot 3.2.4
- Spring Web
- realtime-starter
- fastjson2
- Lombok
- JUnit 5

## 关键类说明

- `RealtimeServiceApplication`：业务服务启动入口。
- `BusinessSubscribeMessageProcessor`：处理订阅消息，消息类型为 `1024`。
- `BusinessUnsubscribeMessageProcessor`：处理取消订阅消息，消息类型为 `1025`。
- `BusinessSubscriptionKey`：业务订阅维度，包含 topic、线路和车次等信息。
- `InitSingleLineTroubleEvent`：单线路故障列表初始化事件。
- `BusinessEventContext`：业务事件上下文，保存 session 与订阅信息。
- `SingleLineInitTroubleListListener`：监听初始化事件并向指定 session 推送故障列表。
- `RealtimeTopics`：集中定义业务 topic。

## 配置与运行

当前默认配置位于 `src/main/resources/application.yml`：

- 服务端口：`8094`
- 应用上下文：`/realtime`
- WebSocket 路径：由 `realtime.websocket.path` 配置，当前默认 `/websocket/subscribe`
- WebSocket 完整路径示例：`/realtime/websocket/subscribe`，其中 `/realtime` 来自 `server.servlet.context-path`
- 事件总线：`memory`
- 存储类型：`memory`
- 线程池：`core-pool-size=5`、`max-pool-size=20`、`queue-capacity=100`

常用命令：

```bash
mvn -f realtime-service/pom.xml compile
mvn -f realtime-service/pom.xml test
mvn -f realtime-service/pom.xml spring-boot:run
```

## 运维方式

本地运行可使用 `spring-boot:run`，部署时可通过 Maven 打包后使用 `java -jar` 启动。运行参数优先通过 `application.yml` 或外部配置覆盖。

日志配置在 `logback-spring.xml` 中维护，排查问题时重点关注 WebSocket 连接、订阅/取消订阅处理、事件发布和消息推送日志。当前存储和事件总线均为内存模式，服务重启后订阅关系会丢失，不适合作为多实例共享状态方案。

## 开发约束

业务代码只允许依赖 `com.realtimes.framework.api` 下的接口和模型，不直接依赖 `com.realtimes.framework.internal`。新增业务能力优先通过新增 `MessageProcessor`、`EventListener`、业务事件和订阅 Key 完成。修改代码后至少执行 `mvn -f realtime-service/pom.xml compile`。
