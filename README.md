### api-vc-spring-boot-starter
在用 SpringBoot 开发 RESTful API 时，如果你想为你的接口加上版本管理，那么现在你可以尝试使用@ApiVersion注解,通过在请求头加版本号帮你完成这项工作。

### 项目使用
在项目中pom.xml中添加依赖
```
    <dependency>
        <groupId>com.anthonyzero</groupId>
        <artifactId>api-vc-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
    
    
    <repositories>
        <repository>
            <id>anthonyzero-maven-repo</id>
            <url>https://raw.githubusercontent.com/AnthonyZero/maven-repo/main/repository</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
```
> 如果依赖下载失败,可能是GitHub的raw.githubusercontent.com连接失败，请通过IPAddress.com首页,输入raw.githubusercontent.com查询到真实IP地址, 并保存到/etc/hosts。
然后项目下进行mvn -U compile 强制让Maven检查所有依赖更新

### 自动配置
在项目配置文件中进行配置,同时也提供了默认配置值（default-version=1.0.0; param-name = x-api-version）
```
    api-version:
        default-version: 1.0.0
        param-name: x-api-version
```
说明：default-version代表版本的默认值（最低值），在未添加@ApiVersion注解的Controller里默认使用此版本;
param-name的值代表通过拿到请求头的x-api-version的值 来获取请求版本值

> 版本格式：x.y.z格式

### 特性
@ApiVersion 功能特性
* 使用方便，只需要一个注解即可在代码层面上区别版本
* 支持在类或者方法上使用
  1. 优先级：方法 > 类
  2. 如果同时在类和方法上都加上了@ApiVersion,那么方法上指定的版本号会生效
* 配置默认版本、自定义请求头参数
  1. 默认前缀是1.0.0，可以通过配置项 api-version.default-version 进行修改。
  2. 默认请求头参数名称为x-api-version, 可以通过配置项 api-version.param-name 自定义参数名称


### 演示
现在有一个接口/index 有两个版本分别为1.0.0(默认) 和2.0.0
```
@RestController
@RequestMapping("/")
public class AppController {

    @GetMapping("/index")
    public String index() {
        return "v1.0.0";
    }
}

@RestController("appController-v2")
@RequestMapping("/")
@ApiVersion("2.0.0")
public class AppController {

    @GetMapping("/index")
    public String index() {
        return "v2.0.0";
    }
}
```
curl 调用接口测试，调用结果如下
```
curl  http://127.0.0.1:8080/index
不加请求头，返回默认版本： v1.0.0

curl  "http://127.0.0.1:8080/index"  -H "x-api-version: 1.0.0"
返回v1.0.0

curl  "http://127.0.0.1:8080/index"  -H "x-api-version: 2.0.0"
返回v2.0.0

curl  "http://127.0.0.1:8080/index"  -H "x-api-version: 2.2.0"
返回v2.0.0 最靠近2.2.0的最高版本为2.0.0

curl  "http://127.0.0.1:8080/index"  -H "x-api-version: 1.2.0"
返回v1.0.0 在所有版本中找最靠近1.2.0的版本并且版本要小于等于1.2.0, 所以就只有1.0.0版本的合适

```
