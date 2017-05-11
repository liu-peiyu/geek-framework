# SpringBootAdmin 微服务快速开发脚手架

## 平台简介

SpringBootAdmin是基于多个优秀的开源项目，高度整合封装而成的高效，高性能，强安全性的**开源**Java微服务快速开发框架。

SpringBootAdmin是在SpringBoot基础上搭建的一个Java基础开发框架，以Spring MVC为模型视图控制器，MyBatis为数据访问层，
Apache Shiro为权限授权层，Ehcahe对常用数据进行缓存。

SpringBootAdmin主要定位于微应用的开发，已内置后台系统的基础功能，用户管理、角色管理、权限管理、会员管理、日志管理等；前台已经实现用户登录，注册等基础功能。
同时前后台会员实现分表管理，可扩展多角色系统、多权限系统。
采用分层设计、双重验证、提交数据安全编码、密码加密、访问验证、数据权限验证。
使用Maven做项目管理，提高项目的易开发性、扩展性。

## 内置功能

1.	管理员管理：管理员是系统操作者，该功能主要完成系统管理员相关配置和角色授权。
2.	角色管理：角色的基础功能以及角色分配权限。
3.	菜单管理：配置系统菜单，操作权限，按钮权限标识等。
4. 会员管理：对前台注册会员的基础的管理。
5.	操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。

## 技术选型

1、后端

* 核心框架：SpringBoot 1.5.2.RELEASE
* 集成运行框架：Tomcat 8.5.11
* 安全框架：Apache Shiro 1.2
* 视图框架：Spring MVC 4.1
* 服务端验证：Hibernate Validator 5.3.4
* 布局框架：Thymeleaf 1.5.2
* 持久层框架：MyBatis 3.3.1
* 数据处理框架：Mapper 3.3.9
* 数据库连接池：Alibaba Druid 1.0
* 缓存框架：Ehcache 2.6、Redis
* 日志管理：SLF4J 1.7、Log4j
* 工具类：Apache Commons、Jackson 2.8.5、Junit 4.12

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
* 开发环境：Java1.7以上、IDEA、Maven 3.1以上、Git

## 安全考虑

1. 开发语言：系统采用Java 语言开发，具有卓越的通用性、高效性、平台移植性和安全性。
2. 分层设计：（数据库层，数据访问层，业务逻辑层，展示层）层次清楚，低耦合，各层必须通过接口才能接入并进行参数校验（如：在展示层不可直接操作数据库），保证数据操作的安全。
3. 双重验证：用户表单提交双验证：包括服务器端验证及客户端验证，防止用户通过浏览器恶意修改（如不可写文本域、隐藏变量篡改、上传非法文件等），跳过客户端验证操作数据库。
4. 安全编码：用户表单提交所有数据，在服务器端都进行安全编码，防止用户提交非法脚本及SQL注入获取敏感数据等，确保数据安全。
5. 密码加密：登录用户密码进行SHA1散列加密，此加密方法是不可逆的。保证密文泄露后的安全问题。
6. 强制访问：系统对所有管理端链接都进行用户身份权限验证，防止用户直接填写url进行访问。

## 演示地址

* <http://demo.geekcattle.cc/>  &nbsp; 用户名：admin &nbsp; 密码：admin 
PS：为了方便大家查看DEMO和功能使用并没有对admin账户做特殊限制，为了方便其他人查看DEMO，请大家不要随意更改admin的授权，可以针对普通和理员的账号更改不同的角色来测试,测试数据库会不定期的尽弄恢复。

## 快速体验

1. 具备运行环境：JDK1.7+、Maven3.0+、MySql5+。
2. 修改src\main\resources\application.properties、application-dev.properties、application-pro.properties文件中的数据库设置参数(application-dev.properties为开发环境的相应参数，application-pro.properties为部署环境的相应参数)。
3. 根据修改参数创建对应MySql数据库用户和参数。
4. 运行mvn package脚本，即可创建项目jar文件，同时也可以通过java -jar *.jar --spring.profiles.active=dev 即可本地预览
5. 将src\main\resources\geekcattle.sql导入本地数据库即可
6. 最高管理员账号，用户名：admin 密码：admin
7. 由于项目只是基础功能实现，可能还有一些没有优化到的时候，后续会持续优化和改进

## 如何交流、反馈、参与贡献？

* E-mail：l_iupeiyu@qq.com
* GitHub：<https://github.com/liu-peiyu/SpringBootAdmin>
* 开源中国：<http://git.oschina.net/liupeiyu/springbootadmin>
* 支持项目发展：（加我好友）支付宝：l_iupeiyu@qq.com &nbsp; 微信：l_iupeiyu

一个人的个人能力再强，也无法战胜一个团队，希望兄弟姐妹的支持，能够贡献出自己的部分代码，参与进来共同完善它(^_^)。

怎么共享我的代码：[手把手教你如何加入到github的开源世界！](http://www.cnblogs.com/wenber/p/3630921.html)

## 版权声明

本软件使用 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) 协议，请严格遵照协议内容：

1. 需要给代码的用户一份Apache Licence。
2. 如果你修改了代码，需要在被修改的文件中说明。
3. **在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议，商标，专利声明和其他原来作者规定需要包含的说明。**
4. 如果再发布的产品中包含一个Notice文件，则在Notice文件中需要带有Apache Licence。你可以在Notice中增加自己的许可，但不可以表现为对Apache Licence构成更改。
5. Apache Licence也是对商业应用友好的许可。使用者也可以在需要的时候修改代码来满足需要并作为开源或商业产品发布/销售
6. 你可以二次包装出售，**但还请保留文件中的版权和作者信息**，并在你的产品说明中注明SpringBootAdmin。
7. 你可以以任何方式获得，你可以修改包名或类名，**但还请保留文件中的版权和作者信息**。

## 项目起因

本人是一个全栈开发人员，精通前后台开发，以前一直用SSH、SSM框架开发，大多数时候也都用已有项目进行二次开发或用核心功能来做业务功能开发，一直想自己做一套属于自己的东西项目，
很多时候感觉自己有很强的强迫证，都说大多数程序员都有，不知道是不是这样的，所以经过自己开发项目经验不断积累现在已经有了能够架构基础项目，
后来又接触到了SpringBoot，喜欢SpringBoot的原因是SpringBoot省掉了很多的XML的配置，使我们能够更多关注我们业务的开发。

在和很多同事和朋友交流的时候发现大多数人都是采用别人开发好的东西，很少有人去研究底层框架的搭建，由于SSH和SSM框架已经很多，所以就萌生了开发一套属于自己的底层的基础框架，
不涉及任何业务逻辑，大多数二次开发时候我们可以需一个干净的框架让我们来做系统的业务开发，这也正是SpingBootAdmin诞生的原因，希望这个项目能够给一些人带来一些帮助。

## 个人技能

JAVA、PHP、HTML、CSS、JQ、BootStrap、Vue、Mysql、Oracle、Redis、SVN、GIT、Apache、Nginx、Tomcat、Weblogic、Jboss、WindowServer、Ubuntu
