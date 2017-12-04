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

    登录地址 /api/oauth/token，此处登录验证与用户realm有关，可根据业务需要进行更改
    登录成功之后通过jwtUtil.createJWT生成token信息（用户身份信息，过期时间等可自行增加），将token返回给客户端，客户端存储该token
    
##4、客户端增加token

    客户端请求时在header里面添加authorization选项，值为Bearer+空格+token（可用postman进行测试）
    
##5、验证token

    token验证是通过filter完成的，filter验证成功之后将解析出的用户信息通过request的方式传递到需要执行的方法（此处如果能结合shiro实现为最佳，还在探索中，如有更好的方案请指正）
    
##6、业务处理

    在实际业务上通过request方法获取到用户信息进行用户数据的处理
        