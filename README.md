# commander

### 当前版本，基于内存管理实现的前后端可分离的oauth2授权


#### 授权服务器：[oauth2-authorization-server](oauth2-authorization-server)

#### 资源服务器：[gateway](gateway) 

#### 使用流程：
1、访问资源服务器： GET http://localhost:8080/need  
 响应： 401 -> 标识未授权，需要登录授权

2、访问授权服务器的登录接口： GET/POST http://localhost/login?username=user&password=password  
    响应： ture -> 登录成功，会种cookie:JSESSIONID（这是用户在授权服务器的cookie）

3、访问授权服务器的授权接口：GET http://localhost/oauth2/authorize?client_id=client1&response_type=code&redirect_uri=http://localhost/find/code&scope=read  
    响应： 302 -> 重定向到 http://localhost/find/code （接口会返回授权code，这里的重定向地址是为了方便获取code）

4、访问授权服务器获取token接口： POST http://localhost/oauth2/token  
请求头：(3.4.4版本的请求方式) content-type: multipart/form-data； Authorization 设置 Basic Auth: username: client1 password: secret  
grant_type：authorization_code
code： 上一步获取的code
redirect_uri： http://localhost/find/code
响应access_token 和 refresh_token

5、刷新token POST http://localhost/oauth2/token
请求头：(3.4.4版本的请求方式) content-type: multipart/form-data； Authorization 设置 Basic Auth: username: client1 password: secret  
grant_type： refresh_token
refresh_token： 上一步获取的refresh_token
响应access_token 和 refresh_token


6、访问资源服务器： GET http://localhost/need
请求头：Authorization设置Bearer Token : access_token

