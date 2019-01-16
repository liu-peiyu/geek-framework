# Geek-Framework 微服务快速开发脚手架

### 平台简介

Geek-Framework是基于多个优秀的开源项目，高度整合封装而成的高效，高性能，强安全性的**开源**Java微服务快速开发框架。

Geek-Framework是在SpringBoot基础上搭建的一个Java基础开发框架，以Spring MVC为模型视图控制器，MyBatis为数据访问层，
Apache Shiro和Spring-Security为权限授权层，redis进行缓存。

Geek-Framework主要定位于微应用的开发，已内置后台系统的基础功能，用户管理、角色管理、权限管理、会员管理、日志管理等；前台已经实现用户登录，注册等基础功能。
同时前后台会员实现分表管理，可扩展多角色系统、多权限系统。
采用分层设计、双重验证、提交数据安全编码、密码加密、访问验证、数据权限验证。
使用Maven做项目管理，提高项目的易开发性、扩展性。

---

###更新日期2019-01-16

* 升级springboot版本为2.1.2.RELEASE application.yml增加以下配置，兼容最新的springboot版本
  > spring:main:allow-bean-definition-overriding: true
* 移除j2cache支持，移除原因为简化新手用户部署系统配置
* 更改后台登录为单realm，不再支持多realm
* 优化日期时间工具类，使用Instant、LocalDateTime、LocalDate实现线性安全
* 修复Java开发规约中级警告及部分低级警告
* 增加debug日志输出开关

###更新日期2018-12-28

* 项目增加健康检查暴露配置
* 根据JAVA开发手册对项目部分不符合开发手册的代码进行了修正，已修复高级警告、中级警告，由于低级警告较多，尚未修复，后续将持续修复
* 给前期已经使用项目的同学，可以使用【阿里巴巴Java开发规约插件p3c】进行修正，造成不便深表歉意


###更新日期2018-10-08

* 最近学习了远程过程调用协议RPC（Remote Procedure Call Protocol),将本框架与dubbo做了一个集成，详见dubbo分支，
* 为了方便大学家习dubbo的运行机制，本框架将dubbo的provider和customer作了一个整合，将官方demo里的方多应用整合成了一个，即在同一应用内启动消费端和服务端
* 注：如有实际业务需要请将服务端与消费端分离，此处整合仅供学习dubbo的运行机制和思想

###更新日期2018-09-19
* 升级mybatis包为mybatis-spring-boot-starter，移除原有mybatis包
* 升级mapper包为mapper-spring-boot-starter
* 升级pagehelper包为pagehelper-spring-boot-starter增加pagehelper-spring-boot-autoconfigure包
* 更改mybatis、mapper和pagehelper为自动配置，配置方式详见application.yml
* 移除MyBatisConfig.java和MybatisMapperScannerConfig.java文件
* 更改升级pagehelper之后对排序方式的处理方式
* 增加事务测试样例，详见AdminController的save方法，此坑很深，爬了一天，由于没有对spring事务的深入了解，导致事务一直不成功，原因在于spring事务只能处理没有被捕获的异常信息，如果对方法增加了事务，请尽量避免用catch来获取异常，或进在cache里面增加抛出异常功能，使事务能够访问到

###更新日期2018-09-19
* 升级J2cache为2.7.0版本，主要修复channel获取次数过多导致的错误问题，另个j2cache后期可能会移除对jedis的支持，所以还是提前升级了吧
* 调整二级缓存redis为lettuce，lettuce为spring推荐的redis操作方式，另个j2cache也推荐使用
* 配置文件application.yml和j2cache配置文件都增加了对lettuce的支持
* 修改com.geekcattle.core.redis包下的RedisCacheManage为RedisShiroCacheManage,因为此文件名和spring源码中的一个文件重名，为了区分特此改名
* 优化RedisConfiguration的JedisConnectionFactory为LettuceConnctionFoctory


###更新日期2018-09-11
本次主要更新了后台两个常用功能组件
* 文件上传组件：上传组件支持本地上传、阿里云OSS上传、七牛上传，使用之前需先进行相关的配置
* 编辑器组件：本组件使用百度UEditor作为编辑器插件，基础功能已经配置开发完成（编辑器初始加载、编辑器内容获取、文件上传等）


###更新日期2018-06-20
本次更新主要解决了前后台不能在同一浏览器登录的问题
* 本次更新引入的spring-security用于对前台会员进行登录控制，后台管理仍使用shiro框架，前后台独立登录，不会出现挤掉另一端的BUG
* spring-security已与JWT进行了完美的整合（不得不说spring家族的框架比shiro要好处理的太多了）
* 数据库更新，对前台会员表进行了更新，引入了spring-security最新的密码验证机制BCryptPasswordEncoder，使用随机密钥方式，移除了原有的密码盐方式
* 文件目录结构进行了优化调整
* 数据库备份文件至data/geekcattle.sql
* 修复上次提交时pom文件打包的错误
* 修复j2cache不同环境配置文件的配置
* 修复部署文件


###更新日期2018-06-14
更新说明：SpringBootAdmin已正式更名为Geek-Framework
* 升级springboot1.5为了2.0，2.0版本变动比较大，1.5版本不可直接更新，如有需要可以查看更新记录
* 已经建立1.0版本分支，如果需1.0版本代码，可下载v1.0分支
* 整合了J2Cache二级缓存功能，J2Cache具体功能详见<http://www.oschina.net/p/j2cache>
* 原有单redis缓存功能仍可使用，具体仍用方法见代码注释

PS:现有代码中有J2Cache的源代码，原因是因为发现了一个J2Cache的BUG，官方版本已经更新，但未同步至中央仓库，暂时用源码替代，待仓库更新后移除现在源代码

###更新日期2018-05-30

* 增加了JWT工具类
* 修复了jwt验证成功之后用户信息的传递，通过shiro的token登录传递，移除了以前的参数传递方法，此方法更为灵活好用
* 引入了refresh_token的概念


###更新日期2017-11-07

* 项目整合redis存储，shiro可使用redisSession可使用于集群访问
* 项目增加jwt模式
* 增加默认启动模式为开发模式
* 优化已知BUG

## 内置功能

1.	管理员管理：管理员是系统操作者，该功能主要完成系统管理员相关配置和角色授权。
2.	角色管理：角色的基础功能以及角色分配权限。
3.	菜单管理：配置系统菜单，操作权限，按钮权限标识等。
4. 会员管理：对前台注册会员的基础的管理。
5.	操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。

## 技术选型

1、后端

* 核心框架：SpringBoot 2.0.2.RELEASE
* 集成运行框架：Tomcat 8.5.31
* 安全框架：Apache Shiro 1.4 Spring-Security 5.0.5
* 视图框架：Spring MVC 5.0.6
* 服务端验证：Hibernate Validator 6.0.9
* 布局框架：Thymeleaf 3.0.9
* 持久层框架：MyBatis 3.3.1
* 数据处理框架：Mapper 3.3.9
* 数据库连接池：Alibaba Druid 1.0
* 缓存框架：Ehcache 3、Redis、J2Cache2.13
* 日志管理：Log4j
* TOKEN模式： jsonwebtoken 0.6
* 工具类：Apache Commons、Jackson 2.9.5

2、前端

* JQ框架：jQuery 2.2.4
* JQ兼容插件：jQuery-Migrate 1.4.1
* CSS框架：Twitter Bootstrap 3.3.7+AdminLte 2.3.7
* 客户端验证：jQuery Validate Plugin 1.15。
* 数据表格：BootStrap-Table 1.11
* 树数据列表：jQuery-Treegrid 0.2
* 树结构控件：BootStrap-Treeview 1.2
* 工具类框架：Layer 3.0

4、平台

* 服务器中间件：项目默认支持Tomcat8.5版本，如果需要打包部署到已有的Tomcat需做特殊处理后续会更新。
* 数据库支持：目前仅提供MySql数据库的支持，但不限于数据库，后续会增加其它数据库支持接口，
* 开发环境：Java1.8以上、IDEA、Maven 3.1以上、Git

## 安全考虑

1. 开发语言：系统采用Java 语言开发，具有卓越的通用性、高效性、平台移植性和安全性。
2. 分层设计：（数据库层，数据访问层，业务逻辑层，展示层）层次清楚，低耦合，各层必须通过接口才能接入并进行参数校验（如：在展示层不可直接操作数据库），保证数据操作的安全。
3. 双重验证：用户表单提交双验证：包括服务器端验证及客户端验证，防止用户通过浏览器恶意修改（如不可写文本域、隐藏变量篡改、上传非法文件等），跳过客户端验证操作数据库。
4. 安全编码：用户表单提交所有数据，在服务器端都进行安全编码，防止用户提交非法脚本及SQL注入获取敏感数据等，确保数据安全。
5. 密码加密：登录用户密码进行SHA1散列加密，此加密方法是不可逆的。保证密文泄露后的安全问题。
6. 强制访问：系统对所有管理端链接都进行用户身份权限验证，防止用户直接填写url进行访问。

## 功能预览

![icon](https://github.com/liu-peiyu/Geek-Framework/blob/master/src/main/resources/static/img/pic1.png?raw=true)
![icon](https://github.com/liu-peiyu/Geek-Framework/blob/master/src/main/resources/static/img/upload1.gif?raw=true)
![icon](https://github.com/liu-peiyu/Geek-Framework/blob/master/src/main/resources/static/img/upload2.gif?raw=true)
![icon](https://github.com/liu-peiyu/Geek-Framework/blob/master/src/main/resources/static/img/pic2.png?raw=true)
![icon](https://github.com/liu-peiyu/Geek-Framework/blob/master/src/main/resources/static/img/pic3.png?raw=true)
![icon](https://github.com/liu-peiyu/Geek-Framework/blob/master/src/main/resources/static/img/pic4.png?raw=true)
![icon](https://github.com/liu-peiyu/Geek-Framework/blob/master/src/main/resources/static/img/pic5.png?raw=true)
![icon](https://github.com/liu-peiyu/Geek-Framework/blob/master/src/main/resources/static/img/pic6.png?raw=true)



## 账号信息

* 后台账号密码：admin ---- admin 
* 前台账号密码：guest ---- hao123

PS：测试数据库会不定期恢复。

## 快速体验

1. 具备运行环境：JDK1.8+、Maven3.0+、MySql5+。
2. 修改src\main\resources\application.properties、application-dev.properties、application-pro.properties文件中的数据库设置参数(application-dev.properties为开发环境的相应参数，application-pro.properties为部署环境的相应参数)。
3. 根据修改参数创建对应MySql数据库用户和参数。
4. 运行mvn package脚本，即可创建项目jar文件，同时也可以通过java -jar *.jar 即可本地预览
5. data\geekcattle.sql导入本地数据库即可
6. 最高管理员账号，用户名：admin 密码：admin

## 如何交流、反馈、参与贡献？

* 为方便大家讨论交流，请加QQ群：805442966 加群回答：GeekFramework
* GitHub：<https://github.com/liu-peiyu/Geek-Framework>
* 开源中国：<https://gitee.com/liupeiyu/springbootadmin>

## 版权声明

本软件使用 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) 协议，请严格遵照协议内容：

1. 需要给代码的用户一份Apache Licence。
2. 如果你修改了代码，需要在被修改的文件中说明。
3. **在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议，商标，专利声明和其他原来作者规定需要包含的说明。**
4. 如果再发布的产品中包含一个Notice文件，则在Notice文件中需要带有Apache Licence。你可以在Notice中增加自己的许可，但不可以表现为对Apache Licence构成更改。
5. Apache Licence也是对商业应用友好的许可。使用者也可以在需要的时候修改代码来满足需要并作为开源或商业产品发布/销售
6. 你可以二次包装出售，**但还请保留文件中的版权和作者信息**，并在你的产品说明中注明SpringBootAdmin。
7. 你可以以任何方式获得，你可以修改包名或类名，**但还请保留文件中的版权和作者信息**。

## 捐赠支持

![icon](https://github.com/liu-peiyu/Geek-Framework/blob/master/src/main/resources/static/img/pay.jpg?raw=true)
