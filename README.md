# Spring Security整合JWT,实现基础的,前后端分离的权限管理项目
## 参考项目: https://github.com/Snailclimb/spring-security-jwt-guide.git
## 参考项目作者:Snailclimb https://github.com/Snailclimb

## 相关文档

- [JWT 优缺点分析以及常见问题解决方案](https://github.com/Snailclimb/JavaGuide/blob/master/docs/system-design/authority-certification/JWT-advantages-and-disadvantages.md)
- [项目讲解/分析](./SpringSecurity介绍.md)

## 介绍

[Spring Security](https://spring.io/projects/spring-security ) 是 Spring 全家桶中非常强大的一个用来做身份验证以及权限控制的框架，我们可以轻松地扩展它来满足我们当前系统安全性这方面的需求。

## 下载配置

1. git clone https://github.com/yuandage/spring-security-jwt-guide.git
2. 打开项目并且等待 Maven 下载好相关依赖。建议使用 Intellij IDEA 打开，并确保你的 Intellij IDEA 下载了 lombok 插件。
3. 修改 `application.properties` 将数据库连接信息改成你自己的。
4. 运行项目（相关数据表会被自动创建，不了解的看一下 JPA）

## 示例

### 1.注册一个账号

![Register](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-7/1-register.png)

### 2.登录

登录的时候传的参数示例：

```json
{"username": "123456", "password": "123456","rememberMe":true}
```

![Login](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-7/2-login.png)

### 3.使用正确 Token 访问需要进行身份验证的资源

![Access resources that require authentication](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-7/3-visit-authenticated-resourse-have-token.png)

### 4.不带 Token 访问需要进行身份验证的资源

![Access resources that require authentication  without token](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-7/4-visit-authenticated-resourse-not-have-token.png)

### 5.使用不正确 Token 访问需要进行身份验证的资源

![Access resources that require authentication  with not correct token](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/2019-7/5-visit-authenticated-resourse-not-have-valid-token.png)

## 参考

- https://dev.to/keysh/spring-security-with-jwt-3j76
