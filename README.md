# 一、SpringMVC简介

## 1、什么是MVC

MVC是一种软件架构的思想，将软件按照模型、视图、控制器来划分

M：Model，模型层，指工程中的JavaBean，作用是处理数据

JavaBean分为两类：

- 一类称为实体类Bean：专门存储业务数据的，如 Student、User 等
- 一类称为业务处理 Bean：指 Service 或 Dao 对象，专门用于处理业务逻辑和数据访问。

V：View，视图层，指工程中的html或jsp等页面，作用是与用户进行交互，展示数据

C：Controller，控制层，指工程中的servlet，作用是接收请求和响应浏览器

MVC的工作流程：
用户通过视图层发送请求到服务器，在服务器中请求被Controller接收，Controller调用相应的Model层处理请求，处理完毕将结果返回到Controller，Controller再根据请求处理的结果找到相应的View视图，渲染数据后最终响应给浏览器

## 2、什么是SpringMVC

SpringMVC是Spring的一个后续产品，是Spring的一个子项目

SpringMVC 是 Spring 为表述层开发提供的一整套完备的解决方案。在表述层框架历经 Strust、WebWork、Strust2 等诸多产品的历代更迭之后，目前业界普遍选择了 SpringMVC 作为 Java EE 项目表述层开发的**首选方案**。

> 注：三层架构分为表述层（或表示层）、业务逻辑层、数据访问层，表述层表示前台页面和后台servlet

## 3、SpringMVC的特点

- **Spring 家族原生产品**，与 IOC 容器等基础设施无缝对接
- **基于原生的Servlet**，通过了功能强大的**前端控制器DispatcherServlet**，对请求和响应进行统一处理
- 表述层各细分领域需要解决的问题**全方位覆盖**，提供**全面解决方案**
- **代码清新简洁**，大幅度提升开发效率
- 内部组件化程度高，可插拔式组件**即插即用**，想要什么功能配置相应组件即可
- **性能卓著**，尤其适合现代大型、超大型互联网项目要求2

# 二、HelloWorld

### 1、开发环境

IDE：idea 2019.2

构建工具：maven3.5.4

**服务器：tomcat7/8.5(注意一定要这个版本)**

Spring版本：5.3.1

### 2、创建maven工程

##### a>添加web模块

##### b>打包方式：war

##### c>引入依赖

```xml
<!-- SpringMVC:核心模块       -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.3.1</version>
    </dependency>

    <!--    日志    -->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.2.2</version>
    </dependency>

    <!--    ServletAPI    -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <!--     打成war包之后由服务器提供这个依赖       -->
      <scope>provided</scope>
    </dependency>

    <!--   Spring5和thymeleaf整合包     -->
    <dependency>
      <groupId>org.thymeleaf</groupId>
      <artifactId>thymeleaf-spring5</artifactId>
      <version>3.0.12.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
```

### 3、配置web.xml

注册SpringMVC的前端控制器DispatcherServlet1

##### a>默认配置方式

此配置作用下，SpringMVC的配置文件默认位于WEB-INF下，默认名称为<servlet-name>-servlet.xml，例如，以下配置所对应SpringMVC的配置文件位于WEB-INF下，文件名为SpringMVCTest-servlet.xml

```xml
<!---配置SpringMVC的前端控制器,对浏览器发送的请求统一进行处理-->
<servlet>
    <servlet-name>springMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>springMVC</servlet-name>
    <!--
		设置springMVC的核心控制器所能处理的请求的请求路径
		/所匹配的请求可以是/login或.html或.js或.css方式请求路径
		但是/不能匹配.jsp请求路径的请求
	-->
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

##### b>扩展配置方式

可通过init-param标签设置SpringMVC配置文件的位置和名称，通过load-on-startup标签设置SpringMVC前端控制器DispatcherServlet的初始化时间()

```xml
<!--web.xml:从resources下读取配置-->
<!---配置SpringMVC的前端控制器,对浏览器发送的请求统一进行处理:类似django的settings.py-->
    <servlet>
        <servlet-name>SpringMVCTest</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--通过初始化参数指定SpringMVC配置文件的位置和名称-->
        <init-param>
            <!--contextConfigLocation为固定值-->
            <param-name>contextConfigLocation</param-name>

            <!--WEB-INF下只放静态文件,将配置设置文件转移都resource下-->
            <!--使用classpath:表示从类路径中查找配置文件,例如maven工程中的src/main/resources-->
            <param-value>classpath:springMVC.xml</param-value>
        </init-param>
        <!--
            作为框架的核心组件,在启动过程中有大量的初始化操作要做
            而且这些操作第一次请求时才执行会严重影响访问速度
            因此需要通过此标签将启动控件DispatcherServlet的初始化时间提前到服务器启动时
        -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVCTest</servlet-name>
        <!--
           设置springMVC的核心控制器所能处理的请求的请求路径
           /所匹配的请求可以是/login或.html或.js或.css方式请求路径
           但是/不能匹配.jsp请求路径的请求
       -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```



### 4、创建请求控制器

由于前端控制器对浏览器发送的请求进行了统一的处理，但是具体的请求有不同的处理过程，因此需要创建处理具体请求的类，即请求控制器

请求控制器中每一个处理请求的方法成为控制器方法

因为SpringMVC的控制器由一个POJO（普通的Java类）担任，因此需要通过@Controller注解将其标识为一个控制层组件，交给Spring的IoC容器管理，此时SpringMVC才能够识别控制器的存在

```java
//之前用的修饰符是@Component;不过目前是@Controller
@Controller
public class HelloController {
}

```

### 5、创建springMVC的配置文件

```xml
<!--resources/springMVC-->    
<!-- 自动扫描包   -->
    <context:component-scan base-package="com.kali.controllerDemo"></context:component-scan>

    <!-- 配置thymeleaf视图解析器 -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                        
                        <!-- 视图前缀 -->
                        <property name="prefix" value="/WEB-INF/templates/"/>

                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>
```

### 6、测试HelloWorld

##### a>实现对首页的访问

在请求控制器中创建处理请求的方法

```java
//之前用的修饰符是@Component;不过目前是@Controller
@Controller
public class HelloController {
    //"/"-->/WEB-INF/templates/index.html

    //请求映射器,也就是路由
    @RequestMapping(value = "/")
    private String index() {
        //返回视图名称
        System.out.println("welcome to index!");

        //结果返回给视图解析器 springMVC.xml,加上前缀("/WEB-INF/templates/")和 后缀(".html")
        return "index";
    }
}
```

##### b>通过超链接跳转到指定页面

在主页index.html中设置超链接

```html
<!DOCTYPE html>
<!--thymeleaf namespace-->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>SpringMVC Index</title>
</head>
<body>
    <h1>SpringMVC Index</h1>
    <!--  thymeleaf语法,调用控制器 /hello  -->
    <a th:href="@{/hello}">Hello</a>
</body>
</html>
```



在请求控制器中创建处理请求的方法

```java
@RequestMapping("/hello")
    private String hello() {
        //被解析器解析后转发到    $prefix+hello+$suffix
        return "hello";
    }
```

### 7、总结(重点)

浏览器发送请求，若请求地址符合前端控制器的url-pattern，该请求就会被前端控制器DispatcherServlet处理。前端控制器会读取SpringMVC的核心配置文件，通过扫描组件找到控制器，将请求地址和控制器中@RequestMapping注解的value属性值进行匹配，若匹配成功，该注解所标识的控制器方法就是处理请求的方法。处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会被视图解析器解析，加上前缀和后缀组成视图的路径，通过Thymeleaf对视图进行渲染，最终转发到视图所对应页面

# 三、@RequestMapping注解

### 1、@RequestMapping注解的功能

从注解名称上我们可以看到，@RequestMapping注解的作用就是将请求和处理请求的控制器方法关联起来，建立映射关系。

SpringMVC 接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求。

### 2、@RequestMapping注解的位置

@RequestMapping标识一个类：设置映射请求的请求路径的初始信息

@RequestMapping标识一个方法：设置映射请求请求路径的具体信息

```java
@Controller
@RequestMapping(value = "/classReqMapping")
public class ClassReqMappingTest1 {

    //此时请求映射所映射的请求的路径为: /classReqMapping/test1
    @RequestMapping("/test1")
    public String test1() {
        return "test1";
    }
}

```

```html5
<body>
    <h1>Welcome to ReqMapping Index</h1>
    <a th:href="@{/classReqMapping/test1}">Go to ClassReqMappingTest1</a>
</body>
```

### 3、@RequestMapping注解的value属性

@RequestMapping注解的value属性通过请求的请求地址匹配请求映射

@RequestMapping注解的value属性是一个字符串类型的数组，表示该请求映射能够匹配多个请求地址所对应的请求

@RequestMapping注解的value属性必须设置，至少通过请求地址匹配请求映射

```html
 <a th:href="@{/reqMappingValueTest1}">测试@RequestMapping的value属性--->reqMappingValueTest1</a></br>
    <a th:href="@{/reqMappingValueTest2}">测试@RequestMapping的value属性--->reqMappingValueTest2</a></br>
```

```java
@RequestMapping(
            value = {"/reqMappingValueTest1", "/reqMappingValueTest2"}
    )
    public String reqMappingValueTest1() {
        return "valuetest1";
    }
```

### 4、@RequestMapping注解的method属性

@RequestMapping注解的method属性通过请求的请求方式（get或post）匹配请求映射

@RequestMapping注解的method属性是一个RequestMethod类型的数组，表示该请求映射能够匹配多种请求方式的请求

若当前请求的请求地址满足请求映射的value属性，但是请求方式不满足method属性，则浏览器报错405：Request method ‘POST’ not supported

```html
<form th:action="@{/reqMappingMethodTest1}" method="post">
        <input type="submit" value="测试@RequestMapping的method属性">
    </form>
```

```java
@Controller
public class ReqMappingMethodTest1 {

    @RequestMapping(
            value = "/reqMappingMethodTest1",
            //  配置post方法
            method = {RequestMethod.POST}
    )
    public String reqMappingMethodTest1() {
        return "methodtest1";
    }
}
```

注：

1、对于处理指定请求方式的控制器方法，SpringMVC中提供了@RequestMapping的派生注解

处理get请求的映射–>@GetMapping

处理post请求的映射–>@PostMapping

处理put请求的映射–>@PutMapping

处理delete请求的映射–>@DeleteMapping

2、常用的请求方式有get，post，put，delete

但是目前浏览器只支持get和post，若在form表单提交时，为method设置了其他请求方式的字符串（put或delete），则按照默认的请求方式get处理

若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMethodFilter，在RESTful部分会讲到

```java
//也可以用@PostMapping来设置请求方法
    @PostMapping(value = "/reqMappingMethodTest1")
    public String reqMappingMethodTest1() {
        return "methodtest1";
    }
```

### 5、@RequestMapping注解的params属性（了解）

@RequestMapping注解的params属性通过请求的请求参数匹配请求映射

@RequestMapping注解的params属性是一个字符串类型的数组，可以通过四种表达式设置请求参数和请求映射的匹配关系

“param”：要求请求映射所匹配的请求必须携带param请求参数

“!param”：要求请求映射所匹配的请求必须不能携带param请求参数

“param=value”：要求请求映射所匹配的请求必须携带param请求参数且param=value

“param!=value”：要求请求映射所匹配的请求必须携带param请求参数但是param!=value

```java
@Controller
public class ReqMappingParamsTest1 {
    @RequestMapping(
            value = "/reqMappingParamsTest1",
            //params参数必须全部符合才能访问
            params = {"username", "password=123456"}
    )
    public String reqMappingParamsTest1() {
        return "paramstest1";
    }
}
```

```html
<!-- 括号内携带参数   -->
    <a th:href="@{reqMappingParamsTest1('username'='admin','password'=123456)}">测试@RequestMapping的params属性</a>
```

### 6、@RequestMapping注解的headers属性（了解）

@RequestMapping注解的headers属性通过请求的请求头信息匹配请求映射

@RequestMapping注解的headers属性是一个字符串类型的数组，可以通过四种表达式设置请求头信息和请求映射的匹配关系

“header”：要求请求映射所匹配的请求必须携带header请求头信息

“!header”：要求请求映射所匹配的请求必须不能携带header请求头信息

“header=value”：要求请求映射所匹配的请求必须携带header请求头信息且header=value

“header!=value”：要求请求映射所匹配的请求必须携带header请求头信息且header!=value

若当前请求满足@RequestMapping注解的value和method属性，但是不满足headers属性，此时页面显示404错误，即资源未找到

```html
    <!--  测试@RequestMapping的headers属性Host=localhost:28080  -->
    <a th:href="@{/reqMappingHeadersTest1}">测试@RequestMapping的headers属性</a>

```

```java
@Controller
public class ReqMappingHeadersTest1 {

    //匹配Host
    @RequestMapping(value = "/reqMappingHeadersTest1", headers = "Host=localhost:28080")
    public String reqMappingHeadersTest1() {
        return "headerstest1";
    }
}
```

### 7、SpringMVC支持ant风格的路径

？：表示任意的单个字符

*：表示任意的0个或多个字符

**：表示任意的一层或多层目录

注意：在使用第三个时，只能使用/**/xxx的方式

```html
<a th:href="@{/aaab/a/a/a/reqMappingAntTest1}">测试@RequestMapping的Ant风格(**)路径</a>
    </br>
    <a th:href="@{singleStarstartreqMappingAntTest2}">测试@RequestMapping的Ant风格(*)路径</a>
    </br>
    <a th:href="@{query!reqMappingAntTest3}">测试@RequestMapping的Ant风格(?)路径</a>
    </br>
```

```java
@Controller
public class ReqMappingAntTest1 {

    // **表示任意的多级目录
    @RequestMapping(value = "/**/reqMappingAntTest1")
    public String reqMappingAntTest1() {
        return "anttest1";
    }

    //*:表示任意的0个或多个字符
    @RequestMapping(value = "/singleStar*reqMappingAntTest2")
    public String reqMappingAntTest2() {
        return "anttest1";
    }

    //？:表示任意的单个字符
    @RequestMapping(value = "/query?reqMappingAntTest3")
    public String reqMappingAntTest3() {
        return "anttest1";
    }
}

```

### 8、SpringMVC支持路径中的占位符（重点）

原始方式：/deleteUser?id=1

rest方式：/deleteUser/1

SpringMVC路径中的占位符常用于RESTful风格中，当请求路径中将某些数据通过路径的方式传输到服务器中，就可以在相应的@RequestMapping注解的value属性中通过占位符{xxx}表示传输的数据，在通过@PathVariable注解，将占位符所表示的数据赋值给控制器方法的形参

```html
<a th:href="@{/reqMappingRestParamTest1/username/password}">测试路径中的占位符(RestFul风格传参)</a>
```

```java
@Controller
public class ReqMappingRestParamTest1 {
    //RESTFUL传参,占位符
    @RequestMapping(value = "/reqMappingRestParamTest1/{username}/{password}")
    public String reqMappingRestParamTest1(@PathVariable("username") String username, @PathVariable("password") String password) {
        System.out.println("username=" + username + ";password=" + password);
        return "restfulparamstest1";

    }
}
```



# 四、SpringMVC获取请求参数

### 1、通过ServletAPI获取

将HttpServletRequest作为控制器方法的形参，此时HttpServletRequest类型的参数表示封装了当前请求的请求报文的对象

```html
<a th:href="@{/servletApiTest1('username'='admin','password'=123456)}">通过ServletAPI获取参数</a>
```

```java
@Controller
public class ServletApiTest1 {

    @RequestMapping("/servletApiTest1")
    public String servletApiTest1(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username=" + username + "\tpassword=" + password);
        return "servletapitest1";
    }
}

```

### 2、通过控制器方法的形参获取请求参数

在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，匹配到请求映射时，在DispatcherServlet中就会将请求参数赋值给相应的形参

```html
<form th:action="@{/controllerParams1}" method="post">
        <label>通过控制器方法的形参获取请求参数</label></br>
        username:<input type="text" name="username"></br>
        password:<input type="password" name="password"></br>
        hobby:<input type="checkbox" name="hobby" value="a">a
            <input type="checkbox" name="hobby" value="a">b
            <input type="checkbox" name="hobby" value="a">c</br>
        <input type="submit" value="submit">
    </form>
```

```java
@Controller
public class ControllerParams1 {
    @RequestMapping(value = "/controllerParams1")
    public String controllerParams1(String username, String password, String[] hobby) {
        System.out.println("username=" + username + "\tpassword=" + password + "\thobby=" + Arrays.toString(hobby));
        return "controllerparams";
    }

}
```



注：

若请求所传输的请求参数中有多个同名的请求参数，此时可以在控制器方法的形参中设置字符串数组或者字符串类型的形参接收此请求参数

若使用字符串数组类型的形参，此参数的数组中包含了每一个数据

若使用字符串类型的形参，此参数的值为每个数据中间使用逗号拼接的结果

### 3、@RequestParam

@RequestParam是将请求参数和控制器方法的形参创建映射关系

@RequestParam注解一共有三个属性：

value：指定为形参赋值的请求参数的参数名

required：设置是否必须传输此请求参数，默认值为true

若设置为true时，则当前请求必须传输value所指定的请求参数，若没有传输该请求参数，且没有设置defaultValue属性，则页面报错400：Required String parameter ‘xxx’ is not present；若设置为false，则当前请求不是必须传输value所指定的请求参数，若没有传输，则注解所标识的形参的值为null

defaultValue：不管required属性值为true或false，当value所指定的请求参数没有传输或传输的值为""时，则使用默认值为形参赋值

```html
<form th:action="@{/requestParamTest1}" method="post">
        <label>请求参数映射器</label></br>
        username:<input type="text" name="username"></br>
        password:<input type="password" name="pwd"></br>
        hobby:<input type="checkbox" name="hobby" value="a">a
        <input type="checkbox" name="hobby" value="a">b
        <input type="checkbox" name="hobby" value="a">c</br>
        <input type="submit" value="submit">
    </form>
```

```java
@Controller
public class RequestParamTest1 {
    //  @RequestParam(value = "pwd") 给形参赋别名
    @RequestMapping("/requestParamTest1")
    public String requestParamTest1(
            @RequestParam(defaultValue = "admin") String username,
            @RequestParam(value = "pwd") String password,
            @RequestParam(required = false) String[] hobby) {
        System.out.println("username=" + username + "\tpassword=" + password + "\thobby=" + Arrays.toString(hobby));
        return "controllerparams";
    }
}
```



### 4、@RequestHeader

@RequestHeader是将请求头信息和控制器方法的形参创建映射关系

@RequestHeader注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

```java
@Controller
public class ReqHeaderTest1 {
    @RequestMapping("/reqHeaderTest1")
    public String reqHeaderTest1(
            @RequestHeader(value = "hostheader",defaultValue = "null") String host){
        return "reqheaderparam";
    }
}
```

### 5、@CookieValue

@CookieValue是将cookie数据和控制器方法的形参创建映射关系

@CookieValue注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

```java
@Controller
public class ReqCookie1 {
    @RequestMapping("/reqCookie1")
    public String reqCookie1(@CookieValue("JSESSIONID") String JSESSIONID){
        System.out.println("JSESSIONID:"+JSESSIONID);
        return "cookie";
    }
}
```

### 6、通过POJO获取请求参数

可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实体类中的属性名一致，那么请求参数就会为此属性赋值

```html
<form th:action="@{/pojotest1}" method="post">
        <label>通过POJO请求参数</label></br>
        username:<input type="text" name="username"></br>
        password:<input type="password" name="password"></br>
        age:<input type="text" name="age"></br>
        sex:<input type="checkbox" name="sex" value="man">man
        <input type="checkbox" name="hobby" value="women">women
        <input type="submit" value="submit">
    </form></br>
```

```java
@Controller
public class POJOTest1 {
    @RequestMapping("/pojotest1")
    public String pOJOTest1(User user) {
        System.out.println(user);
        return "pojo";
        //注意User对象需要设置有参和无参构造,以及set和get方法(bean的相关知识)
        //User{username='admin', password='admin', sex='null', age=1}
    }
}

```

### 7、解决获取请求参数的乱码问题

解决get乱码问题:tomcat配置文件server.xml修改编码即可

```xml
<Connector port="28080" URIEncoding="UTF-8" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443" />
```

解决post乱码问题:

解决获取请求参数的乱码问题，可以使用SpringMVC提供的编码过滤器CharacterEncodingFilter，但是必须在web.xml中进行注册

```xml
    <!-- 配置SpringMVC的编码过滤器   -->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <!--   请求的编码格式UTF-8     -->
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <!--   返回编码也用utf-8     -->
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```



> 注：
>
> 必须在获取参数之前就进行转码,启动顺序是:**监听器->filter->servlet->DispatcherServlet->Controller**。所以在Controller修改转码没有用
>
> SpringMVC中处理编码的过滤器一定要配置到其他过滤器之前，否则无效

# 五、域对象共享数据

### 1、使用ServletAPI向request域对象共享数据

```java
@Controller
public class ServletAPItest1 {
    @RequestMapping("/servletAPItest1")
    public String servletAPItest1(HttpServletRequest request){
        request.setAttribute("testScope","Hello servletAPIctx");
        return "servletapi";
    }
}
```

```html
<!-- 通过servletAPI向request域对象共享数据   -->
    <p th:text="${testScope}"></p>
```

### 2、使用ModelAndView向request域对象共享数据(难点)

```java
@Controller
public class ModelAndViewTest1 {
    @RequestMapping(value = "/modelAndViewTest1")
    public ModelAndView modelAndViewTest1() {
        /**
         * ModelAndView有Model和View的功能被
         * Model主要用于请求域共享数据
         * View主要用于返回视图,实现页面跳转
         */
        ModelAndView mav = new ModelAndView();
        //向请求域共享数据
        mav.addObject("testScope", "Hello ModelAndViewCtx");
        //设置视图,实现页面跳转
        mav.setViewName("mav1");
        return mav;
    }
}

```

### 3、使用Model向request域对象共享数据

```java
@Controller
public class ModelTest1 {
    @RequestMapping("/modeltest1")
    public String modeltest(Model model) {
        model.addAttribute("testScope", "Hello Model");
        return "success";
    }
}
```

### 4、使用map向request域对象共享数据

```java
@Controller
public class MapTest1 {
    @RequestMapping(value = "/mapTest1")
    public String mapTest1(Map<String, Object> map) {
        map.put("testScope","Hello Map");
        return "success";
    }
}

```



### 5、使用ModelMap向request域对象共享数据

```java
@Controller
public class ModelMapTest1 {
    @RequestMapping("/modelMapTest1")
    public String modelMapTest1(ModelMap modelMap){
        modelMap.addAttribute("testScope","Hello ModelMap");
        return "success";
    }
}
```

### 6、Model、ModelMap、Map的关系

Model、ModelMap、Map类型的参数其实本质上都是 BindingAwareModelMap 类型的

```java
public interface Model{}
public class ModelMap extends LinkedHashMap<String, Object> {}
public class ExtendedModelMap extends ModelMap implements Model {}
public class BindingAwareModelMap extends ExtendedModelMap {}
```

### 7、向session域共享数据

```java
@Controller
public class SessionTest1 {
    @RequestMapping(value = "/sessionTest1")
    public String sessionTest1(HttpSession session){
        session.setAttribute("testScope","Hello Session");
        return "success";
    }
}

```

```html
<p th:text="${session.testScope}"></p>
```



### 8、向application域共享数据

```java
@Controller
public class ApplicationTest1 {
    @RequestMapping("/applicationTest1")
    public String applicationTest1(HttpSession session){
        ServletContext application = session.getServletContext();
        //整个项目都生效
        application.setAttribute("testScope","Hello Application");
        return "success";
    }
}
```



# 六、SpringMVC的视图(难点)

SpringMVC中的视图是View接口，视图的作用渲染数据，将模型Model中的数据展示给用户

SpringMVC视图的种类很多，默认有转发视图和重定向视图

当工程引入jstl的依赖，转发视图会自动转换为JstlView

若使用的视图技术为Thymeleaf，在SpringMVC的配置文件中配置了Thymeleaf的视图解析器，由此视图解析器解析之后所得到的是ThymeleafView

### 1、ThymeleafView

当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图后缀所得到的最终路径，会通过转发的方式实现跳转

### 2、转发视图

SpringMVC中默认的转发视图是InternalResourceView

SpringMVC中创建转发视图的情况：

当控制器方法中所设置的视图名称以"forward:"为前缀时，创建InternalResourceView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"forward:"去掉，剩余部分作为最终路径通过转发的方式实现跳转

例如"forward:/"，“forward:/employee”

```java
@Controller
public class ForwardViewTest1 {
    @RequestMapping("/forwardViewTest1")
    public String forwardViewTest1(){
        //转发给thymeleafViewTest1
        return "forward:/thymeleafViewTest1";
    }
}

```

### 3、重定向视图

SpringMVC中默认的重定向视图是RedirectView

当控制器方法中所设置的视图名称以"redirect:"为前缀时，创建RedirectView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"redirect:"去掉，剩余部分作为最终路径通过重定向的方式实现跳转

例如"redirect:/"，“redirect:/employee”

```java
@Controller
public class RedirectViewTest1 {
    @RequestMapping(value = "/redirectViewTest1")
    public String redirectViewTest1(){
        return "redirect:/thymeleafViewTest1";
    }
}
```

> 注：
>
> 重定向/转发视图在解析时，会先将redirect:前缀去掉，然后会判断剩余部分是否以/开头，若是则会自动拼接上下文路径

### 转发和重定向的区别

- 浏览器:重定向是两次(第一次servlet,第二次重定向的地址)请求,转发是一次(第一次浏览器,第二次内部请求)请求
- 地址栏:重定向是的重定向后的地址,转发是原来的地址
- 域数据:重定向无法获得请求域的数据(两个request),转发可以(因为是同一个request对象)
- webinfo下的资源:重定向无法获取,转发可以(webinfo下的资源具有安全性,只能通过服务器内部资源访问,不能通过浏览器/外部访问)
- 跨域:重定向可以跨域(domin),转发不能

### 4、视图控制器view-controller

当控制器方法中，仅仅用来实现页面跳转(不需要进行业务处理)，即只需要设置视图名称时，可以将处理器方法使用view-controller标签进行表示

```xml
<beans>
    ...
    <!--
        path: 设置处理的请求地址
        view: 设置请求地址所对应的视图名称
     -->
    <mvc:view-controller path="/" view-name="index"></mvc:view-controller>
    <!--当SpringMVC中设置任何一个view-controller时，
    其他控制器中的请求映射将全部失效，
    此时需要在SpringMVC的核心配置文件中设置开启mvc注解驱动的标签：-->
    <!--  开启MVC的注解驱动  -->
    <mvc:annotation-driven />

</beans>
```

# 七、RESTful

### 1、RESTful简介

REST：**Re**presentational **S**tate **T**ransfer，表现层资源状态转移。

##### a>资源

资源是一种看待服务器的方式，即，将服务器看作是由很多离散的资源组成。每个资源是服务器上一个可命名的抽象概念。因为资源是一个抽象的概念，所以它不仅仅能代表服务器文件系统中的一个文件、数据库中的一张表等等具体的东西，可以将资源设计的要多抽象有多抽象，只要想象力允许而且客户端应用开发者能够理解。与面向对象设计类似，资源是以名词为核心来组织的，首先关注的是名词。一个资源可以由一个或多个URI来标识。URI既是资源的名称，也是资源在Web上的地址。对某个资源感兴趣的客户端应用，可以通过资源的URI与其进行交互。

##### b>资源的表述

资源的表述是一段对于资源在某个特定时刻的状态的描述。可以在客户端-服务器端之间转移（交换）。资源的表述可以有多种格式，例如HTML/XML/JSON/纯文本/图片/视频/音频等等。资源的表述格式可以通过协商机制来确定。请求-响应方向的表述通常使用不同的格式。

##### c>状态转移

状态转移说的是：在客户端和服务器端之间转移（transfer）代表资源状态的表述。通过转移和操作资源的表述，来间接实现操作资源的目的。

### 2、RESTful的实现

具体说，就是 HTTP 协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。

它们分别对应四种基本操作：GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源。

REST 风格提倡 URL 地址使用统一的风格设计，从前到后各个单词使用斜杠分开，不使用问号键值对方式携带请求参数，而是将要发送给服务器的数据作为 URL 地址的一部分，以保证整体风格的一致性。

| 操作     | 传统方式         | REST风格               |
| -------- | ---------------- | ---------------------- |
| 查询操作 | getUserById?id=1 | user/1–>get请求方式    |
| 保存操作 | saveUser         | user–>post请求方式     |
| 删除操作 | deleteUser?id=1  | user/1–>delete请求方式 |
| 更新操作 | updateUser       | user–>put请求方式      |

### 3、HiddenHttpMethodFilter

由于浏览器只支持发送get和post方式的请求，那么该如何发送put和delete请求呢？

SpringMVC 提供了 **HiddenHttpMethodFilter** 帮助我们**将 POST 请求转换为 DELETE 或 PUT 请求**

**HiddenHttpMethodFilter** 处理put和delete请求的条件：

a>当前请求的请求方式必须为post

b>当前请求必须传输请求参数_method

满足以上条件，**HiddenHttpMethodFilter** 过滤器就会将当前请求的请求方式转换为请求参数_method的值，因此请求参数_method的值才是最终的请求方式

在web.xml中注册**HiddenHttpMethodFilter**

```xml
<filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
> 修改方法的原理：
>
> 通过滤器时:
>
> ​	1.先匹配路径
>
> ​	2.将POST方法替换为传参_method的方法

> 注：
>
> 目前为止，SpringMVC中提供了两个过滤器：CharacterEncodingFilter和HiddenHttpMethodFilter
>
> 在web.xml中注册时，必须先注册CharacterEncodingFilter，再注册HiddenHttpMethodFilter
>
> 原因：
>
> - 在 CharacterEncodingFilter 中通过 request.setCharacterEncoding(encoding) 方法设置字符集的
>
> - request.setCharacterEncoding(encoding) 方法要求前面不能有任何获取请求参数的操作
>
> - 而 HiddenHttpMethodFilter 恰恰有一个获取请求方式的操作：
>
> - ```java
>   String paramValue = request.getParameter(this.methodParam);
>   ```

# 八、RESTful案例

### 1、准备工作

和传统 CRUD 一样，实现对员工信息的增删改查。

- 搭建环境

- 准备实体类

  ```java
  package com.kali.restfulDemo.bean;
  
  public class Employee {
  
     private Integer id;
     private String lastName;
  
     private String email;
     //1 male, 0 female
     private Integer gender;
     
     public Integer getId() {
        return id;
     }
  
     public void setId(Integer id) {
        this.id = id;
     }
  
     public String getLastName() {
        return lastName;
     }
  
     public void setLastName(String lastName) {
        this.lastName = lastName;
     }
  
     public String getEmail() {
        return email;
     }
  
     public void setEmail(String email) {
        this.email = email;
     }
  
     public Integer getGender() {
        return gender;
     }
  
     public void setGender(Integer gender) {
        this.gender = gender;
     }
  
     public Employee(Integer id, String lastName, String email, Integer gender) {
        super();
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
     }
  
     public Employee() {
     }
  }
  ```

- 准备dao模拟数据

  ```java
  package com.kali.restfulDemo.dao;
  
  import java.util.Collection;
  import java.util.HashMap;
  import java.util.Map;
  
  import com.kali.restfulDemo.bean.Employee;
  import org.springframework.stereotype.Repository;
  
  //标识为持久层组件
  @Repository
  public class EmployeeDao {
  
     private static Map<Integer, Employee> employees = null;
     
     static{
        employees = new HashMap<Integer, Employee>();
  
        employees.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1));
        employees.put(1002, new Employee(1002, "E-BB", "bb@163.com", 1));
        employees.put(1003, new Employee(1003, "E-CC", "cc@163.com", 0));
        employees.put(1004, new Employee(1004, "E-DD", "dd@163.com", 0));
        employees.put(1005, new Employee(1005, "E-EE", "ee@163.com", 1));
     }
     
     private static Integer initId = 1006;
     
     public void save(Employee employee){
        if(employee.getId() == null){
           employee.setId(initId++);
        }
        employees.put(employee.getId(), employee);
     }
     
     public Collection<Employee> getAll(){
        return employees.values();
     }
     
     public Employee get(Integer id){
        return employees.get(id);
     }
     
     public void delete(Integer id){
        employees.remove(id);
     }
  }
  ```

​	控制层

```java
package com.kali.restfulDemo.controller;

import com.kali.restfulDemo.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EmployeeController {

    //自动装配,默认根据bytype,否则根据byname
    @Autowired
    private EmployeeDao employeeDao;
}
```

### 2、功能清单

| 功能                | URL 地址    | 请求方式 |
| ------------------- | ----------- | -------- |
| 访问首页√           | /           | GET      |
| 查询全部数据√       | /employee   | GET      |
| 删除√               | /employee/2 | DELETE   |
| 跳转到添加数据页面√ | /toAdd      | GET      |
| 执行保存√           | /employee   | POST     |
| 跳转到更新数据页面√ | /employee/2 | GET      |
| 执行更新√           | /employee   | PUT      |

### 3、具体功能：访问首页

##### a>配置view-controller

```xml
    <!--
       配置视图控制器
     -->
    <mvc:view-controller path="/" view-name="index"></mvc:view-controller>

    <!--  开启MVC的注解驱动  -->
    <mvc:annotation-driven />
```

##### b>创建页面

```html
<a th:href="@{/employee}">employee information</a></br>
```

# 九、文件上传和下载

# 十、拦截器

# 十一、异常处理器

# 十二、注解配置SpringMVC

# 十三、SpringMVC执行流程
