# JWT（Json Web Token）使用方法
##1、pxm.xml引入依赖

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.6.0</version>
    </dependency>
    
##2、配置JWT相关参数 配置文件application.properties

    JWT配置
    认证名称
    jwt.header=authorization
    加密密钥  可自定义
    jwt.secret=1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
    过期时间一天(秒)
    jwt.expiration=86400
    Tokenu前缀
    jwt.token.head=Bearer
    
##3、获取token

    登录地址 /member/login
    登录成功之后在AjaxAuthSuccessHandler通过jwtUtil生成token信息（用户身份信息，过期时间等可自行增加），将token返回给客户端，客户端存储该token
    
##4、客户端增加token

    客户端请求时在header里面添加authorization选项，值为Bearer+空格+token（可用postman进行测试）
    
##5、验证token

    token验证是通过JwtAuthenticationFilter完成的，验证成功之后将解析出的用户信息提交给spring-security进行处理登录，以方便在后续的操作中使用
    
        