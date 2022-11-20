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

### 6、Model、ModelMap、Map的关系()

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

### 4、具体功能：查询所有员工数据

##### a>控制器方法

```java
/域对象共享数据:model
@GetMapping(value = "/employee")
public String getEmpList(Model model) {
    Collection<Employee> empList = employeeDao.getAll();
    model.addAttribute("empList", empList);
    return "employee_list";
}
```

##### b>创建employee_list.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Employee Info</title>
</head>
<body>
    <table border="1" cellpadding="0" cellspacing="0" style="text-align: center;">
        <tr>
            <th colspan="4">Employee Info</th>
        </tr>
        <tr>
            <th>id</th>
            <th>lastname</th>
            <th>email</th>
            <th>gender</th>
        </tr>
        <!-- 类似django的template语法 for-->
        <tr th:each="emp:${empList}">
            <td th:text="${emp.id}"></td>
            <td th:text="${emp.lastName}"></td>
            <td th:text="${emp.email}"></td>
            <td th:text="${emp.gender}"></td>
        </tr>
    </table>
    <a th:href="@{/}">Index</a>
</body>
</html>
```

### 5、具体功能：删除

##### a>创建处理delete请求方式的表单

```html
 <!--  发送/employee/$emp.id           -->
                <!--  错误写法 /employee/$%7emp.id%7          -->
                <a th:href="@{/employee/${emp.id}}">error delete</a>
                <!--  正确写法1           -->
                <a th:href="@{/employee/}+${emp.id}">delete</a>
                <!--  正确写法2           -->
                <a th:href="@{'/employee/'+${emp.id}}">delete</a>
    <form id="deleteForm" method="post">
        <input type="hidden" name="_method" value="delete">
    </form>
```

##### b>删除超链接绑定点击事件

引入jquery

```html
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
```

删除超链接

```html
<a class="get2post" th:href="@{/employee/}+${emp.id}">delete</a>
```

通过jquery处理点击事件

```html
<script>
    $(document).ready(function(){
        $(".get2post").click(function(event){
            //阻止超链接的默认跳转行为（先请求后发送表单）
            event.preventDefault(); 
            var deleteForm = document.getElementById("deleteForm");
            //修改deleteForm的action
            deleteForm.action = event.target.href;
            deleteForm.submit();
        });
    });
</script>
```

### 6、具体功能：跳转到添加数据页面

##### a>配置view-controller

```xml
<mvc:view-controller path="/toAdd" view-name="employee_add"></mvc:view-controller>
```



##### b>创建employee_add.html

```html
<form th:action="@{/employee}" method="post"></br>
    lastname:<input type="text" name="lastName"></br>
    email:<input type="text" name="email"></br>
    gender:<input type="radio" name="gender" value="1">male
    <input type="radio" name="gender" value="0">female</br>
    <input type="submit" value="add"></br>
</form>
```

### 7、具体功能：执行保存

##### a>控制器方法

```java
@PostMapping(value = "/employee")
public String addEmp(Employee emp){
    employeeDao.save(emp);
    return "redirect:/employee";
}
```

### 8、具体功能：跳转到更新数据页面

##### a>修改超链接

```html
<a th:href="@{'/employee/'+${emp.id}}">update</a>
```

##### b>控制器方法

```java
@PutMapping(value = "/employee/{id}")
public String updateEmp(@PathVariable("id") Integer id, Employee emp) {
    Employee employee = employeeDao.get(id);
    if (employee.getId().equals(id)){
        employeeDao.save(emp);
    }
    return "redirect:/employee";
}
```

##### c>创建employee_update.html

```html
    <form method="post"></br>
        <input type="hidden" name="_method" value="put">
        lastname:<input type="text" name="lastName" th:value="${emp.lastName}"></br>
        email:<input type="text" name="email" th:value="${emp.email}"></br>
        <!-- th:field 自动选中gender的属性       -->
        gender:<input type="radio" name="gender" value="1" th:field="${emp.gender}">male
        <input type="radio" name="gender" value="0" th:field="${emp.gender}">female</br>
        <input type="submit" value="update"></br>
    </form>
```



### 9、具体功能：执行更新

##### a>控制器方法

```java
@PutMapping(value = "/employee/{id}")
public String updateEmp(@PathVariable("id") Integer id, Employee emp) {
    Employee employee = employeeDao.get(id);
    if (employee.getId().equals(id)){
        employeeDao.save(emp);
    }
    return "redirect:/employee";
}
```

### ex:处理静态请求的过程

tomcat的web.xml的配置

```xml
#servlet映射器
<servlet>
    <servlet-name>default</servlet-name>
    <!--底层是调用默认的servlet处理-->
    <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
    ...
    <load-on-startup>1</load-on-startup>
</servlet>
 <servlet>
        <servlet-name>jsp</servlet-name>
        <servlet-class>org.apache.jasper.servlet.JspServlet</servlet-class>
      	...
        <load-on-startup>3</load-on-startup>
    </servlet>
<servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <servlet-mapping>
        <servlet-name>jsp</servlet-name>
        <url-pattern>*.jsp</url-pattern>
        <url-pattern>*.jspx</url-pattern>
    </servlet-mapping>
	...
#mime-type映射器
    <mime-mapping>
        <extension>tar</extension>
        <mime-type>application/x-tar</mime-type>
    </mime-mapping>
	...
```

**原则1:请求就近处理**

请求优先给工程的web.xml(dispatchservlet)处理,然后才轮到tomcat的web.xml

需要做如下配置

```xml
<!--开放对静态资源的访问-->
<!--项目的dispatch-servlet处理不了的交给default-servlet处理(仅仅是静态资源)-->
<mvc:default-servlet-handler/>

<!--开启mvc注解驱动-->
<!--除了静态资源,其他处理不了的也交给default-servlet处理-->
<mvc:annotation-driven />
```

# 九、HttpMessageConverter

HttpMessageConverter，报文信息转换器，将请求报文转换为Java对象，或将Java对象转换为响应报文

HttpMessageConverter提供了两个注解和两个类型：@RequestBody，@ResponseBody，RequestEntity，

ResponseEntity

### 1、@RequestBody

@RequestBody可以获取请求体，需要在控制器方法设置一个形参，使用@RequestBody进行标识，当前请求的请求体就会为当前注解所标识的形参赋值

```html
    <form th:action="@{/reqBodyDemo1}" method="post"></br>
        lastname:<input type="text" name="lastName"></br>
        email:<input type="text" name="email"></br>
        <input type="submit" value="submit"></br>
    </form>
```

```java
@Controller
public class ReqBodyDemo1 {
    @PostMapping("/reqBodyDemo1")
    public ModelAndView reqBodyDemo1(@RequestBody String reqBody) {
        System.out.println(reqBody);
        ModelAndView mav = new ModelAndView();
        mav.addObject("reqBody", reqBody);
        mav.setViewName("success");
        return mav;
    }
}
```

输出结果：

lastName=admin&email=admin%40a.com

### 2、RequestEntity

RequestEntity封装请求报文的一种类型，需要在控制器方法的形参中设置该类型的形参，当前请求的请求报文就会赋值给该形参，可以通过getHeaders()获取请求头信息，通过getBody()获取请求体信息

```java
@Controller
public class ReqEttDemo1 {
    @PostMapping(value = "/reqEttDemo1")
    public ModelAndView reqEttDemo1(RequestEntity<String> reqEtt){
        ModelAndView mav = new ModelAndView();
        mav.addObject("reqBody",reqEtt.getBody());
        mav.addObject("reqHeader",reqEtt.getHeaders());
        mav.setViewName("success");
        return mav;
    }
}
```

输出结果：
requestHeader:[host:“localhost:8080”, connection:“keep-alive”, content-length:“27”, cache-control:“max-age=0”, sec-ch-ua:"" Not A;Brand";v=“99”, “Chromium”;v=“90”, “Google Chrome”;v=“90"”, sec-ch-ua-mobile:"?0", upgrade-insecure-requests:“1”, origin:“http://localhost:8080”, user-agent:“Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36”]

### 3、@ResponseBody

@ResponseBody用于标识一个控制器方法，可以将该方法的返回值直接作为响应报文的响应体响应到浏览器

```java
@Controller
public class RspBodyDemo1 {
    //添加@ResponseBody后不再先向View做解析后返回给前端,而是直接输出内容到前端
    @GetMapping("/rspBodyDemo1")
    @ResponseBody
    public String rspBodyDemo1(){
        return "Hello rspBodyDemo1!</br><a href='/SpringMVCTest'>Index</a>";
    }
}
```

### 4、SpringMVC处理json

@ResponseBody处理json的步骤：

a>导入jackson的依赖

```xml
<!--导入json处理器-->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.1</version>
</dependency>
```

b>在SpringMVC的核心配置文件中开启mvc的注解驱动，此时在HandlerAdaptor中会自动装配一个消息转换器：MappingJackson2HttpMessageConverter，可以将响应到浏览器的Java对象转换为Json格式的字符串

```xml
<mvc:annotation-driven />
```

c>在处理器方法上使用@ResponseBody注解进行标识

d>将Java对象直接作为控制器方法的返回值返回，就会自动转换为Json格式的字符串

```java
@Controller
public class RspJsonDemo1 {
    @GetMapping("/rspJsonDemo1")
    @ResponseBody
    public User rspJsonDemo1(){
        return new User(1,"Hello Json");
    }
}
```

浏览器的页面中展示的结果：

```html
{"id":1,"name":"Hello Json"}
```

### 5、SpringMVC处理ajax

a>请求超链接：

```html
<a class="testajax" th:href="@{/ajaxDemo1}">Ajax</a>
```

b>通过jquery处理点击事件：

```html
<script>
    $(document).ready(function(){
        $(".testajax").click(function(event){
            event.preventDefault();
            $.post(event.target.href,
             {
                username:"admin",
                password:"password"
             },
            function(data,status){
			alert(data);
		    });
        });
    });
</script>
```

c>控制器方法：

```java
@Controller
public class AjaxDemo1 {
    @PostMapping("/ajaxDemo1")
    @ResponseBody
    public String ajaxDemo1(String username, String password) {
        System.out.println("username:" + username + "\npassword:" + password);
        return "Hello Ajax";
    }
}
```

### 6、@RestController注解(重点)

@RestController注解是springMVC提供的一个复合注解，标识在控制器的类上，就相当于为类添加了@Controller注解，并且为其中的**每个方法**添加了@ResponseBody注解

### 7、ResponseEntity

ResponseEntity用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文

使用ResponseEntity实现下载文件的功能

```java
@RequestMapping("/testDown")
public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
    //获取ServletContext对象
    ServletContext servletContext = session.getServletContext();
    //获取服务器中文件的真实路径
    String realPath = servletContext.getRealPath("/static/img/1.jpg");
    //创建输入流
    InputStream is = new FileInputStream(realPath);
    //创建字节数组
    byte[] bytes = new byte[is.available()];
    //将流读到字节数组中
    is.read(bytes);
    //创建HttpHeaders对象设置响应头信息
    MultiValueMap<String, String> headers = new HttpHeaders();
    //设置要下载方式以及下载文件的名字
    headers.add("Content-Disposition", "attachment;filename=1.jpg");
    //设置响应状态码
    HttpStatus statusCode = HttpStatus.OK;
    //创建ResponseEntity对象
    ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
    //关闭输入流
    is.close();
    return responseEntity;
}
```

### 

# 十、文件上传和下载

# 十一、拦截器(重点)

请求执行顺序:

filter->dispatcher-servlet->(preHandle)controller(postHandle)->ModelAndView(afterComplation)

### 1、拦截器的配置

SpringMVC中的拦截器用于拦截控制器方法的执行

SpringMVC中的拦截器需要实现HandlerInterceptor

SpringMVC的拦截器必须在SpringMVC的配置文件中进行配置：

```xml
<!--  配置拦截器  -->
<mvc:interceptors>
    <!--  方式1:指定某个bean为拦截器      -->
    <!--        <bean class="com.kali.handle.interceptor.InterceptorDemo1"></bean>-->
    <!--  方式2:根据beanid选择拦截器      -->
    <!--        <ref bean="interceptorDemo1"></ref>-->
    <!--  以上两种配置方式都是对DispatcherServlet所处理的所有请求进行拦截-->
    <mvc:interceptor>
        <!--
        仅对任意一级目录进行拦截
        <mvc:mapping path="/*"/>
        -->
        <!-- 对任意多级目录进行拦截  -->
        <mvc:mapping path="/**"/>
        <!-- 首页不进行拦截  -->
        <mvc:exclude-mapping path="/"/>
        <ref bean="interceptorDemo1"></ref>
    </mvc:interceptor>
</mvc:interceptors>
```

### 2、拦截器的三个抽象方法

SpringMVC中的拦截器有三个抽象方法：

preHandle：控制器方法执行之前执行preHandle()，其boolean类型的返回值表示是否拦截或放行，返回true为放行，即调用控制器方法；返回false表示拦截，即不调用控制器方法

postHandle：控制器方法执行之后执行postHandle()

afterComplation：处理完视图和模型数据，渲染视图完毕之后执行afterComplation()

### 3、多个拦截器的执行顺序

a>若每个拦截器的preHandle()都返回true

此时多个拦截器的执行顺序和拦截器在SpringMVC的配置文件的配置顺序有关：

preHandle()会按照配置的顺序执行，而postHandle()和afterComplation()会按照配置的反序执行

```html
InterceptorDemo1->preHandle
InterceptorDemo2->preHandle
InterceptorDemo2->postHandle
InterceptorDemo1->postHandle
InterceptorDemo2->afterCompletion
InterceptorDemo1->afterCompletion
```

![handle1](img/handle1.png)

![handle2](img/handle2.png)

![handle3](img/handle3.png)

![handle4](img/handle4.png)

b>若某个拦截器的preHandle()返回了false

preHandle()返回false和它之前的拦截器的preHandle()都会执行，postHandle()都不执行，返回false的拦截器之前的拦截器的afterComplation()会执行(参考上面的img/handle2.png,img/handle4.png)

```text
InterceptorDemo1->preHandle
InterceptorDemo2->preHandle
InterceptorDemo2->postHandle	x
InterceptorDemo1->postHandle	x
InterceptorDemo2->afterCompletion	x
InterceptorDemo1->afterCompletion
```



# 十二、异常处理器

### 1、基于配置的异常处理

SpringMVC提供了一个处理控制器方法执行过程中所出现的异常的接口：HandlerExceptionResolver

HandlerExceptionResolver接口的实现类有：DefaultHandlerExceptionResolver和SimpleMappingExceptionResolver

SpringMVC提供了自定义的异常处理器SimpleMappingExceptionResolver，使用方式：

```xml
    <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <property name="exceptionMappings">
            <props>
                <!--
                    properties的键标识处理器方法执行过程中出现+的异常
                    properties的值标识若出现指定异常时，设置一个新的视图名称(映射器)，跳转到指定页面 (这里跳转到error)
                -->
                <prop key="java.lang.ArithmeticException">error</prop>
            </props>
        </property>
        <!--  exceptionAttribute属性设置一个属性名,将出现的异常信息再请求域中进行共享(域共享异常)      -->
        <property name="exceptionAttribute" value="ex"></property>
    </bean>
```

```java
@Controller
public class ExceptHandleDemo1 {

    @RequestMapping(value = "/exceptHandleDemo1")
    public String exceptHandleDemo1() {
        System.out.println(1 / 0);
        //异常触发时直接找到ExceptionHandler bean
        return "success";
    }
}
```

```html
<p th:text="${ex}"></p>
```

### 2、基于注解的异常处理

```java
@Controller
public class ExceptHandleDemo1 {

    @RequestMapping(value = "/exceptHandleDemo1")
    public String exceptHandleDemo1() {
        System.out.println(1 / 0);
        //异常触发时直接找到@ExceptionHandler(ArithmeticException.class)
        return "success";
    }
}
```

```java
//@ControllerAdvice将当前类标识为异常处理的组件
@ControllerAdvice
public class HandlebyAnnotationDemo1 {
    //@ExceptionHandler用于设置所标识方法处理的异常，这里仅处理ArithmeticException触发的异常
    @ExceptionHandler(ArithmeticException.class)
    //ex标识当前请求处理中所出现的异常
    public String HandlebyAnnotation(Exception ex, Model model) {
        model.addAttribute("ex", ex);
        return "error";
    }
}
```



# 十三、注解配置SpringMVC

使用配置类和注解代替web.xml和SpringMVC配置文件的功能

### 1、创建初始化类，代替web.xml

在Servlet3.0环境中，容器会在类路径中查找实现javax.servlet.ServletContainerInitializer接口的类，如果找到的话就用它来配置Servlet容器。
Spring提供了这个接口的实现，名为SpringServletContainerInitializer，这个类反过来又会查找实现WebApplicationInitializer的类并将配置的任务交给它们来完成。Spring3.2引入了一个便利的WebApplicationInitializer基础实现，名为AbstractAnnotationConfigDispatcherServletInitializer，当我们的类扩展了AbstractAnnotationConfigDispatcherServletInitializer并将其部署到Servlet3.0容器的时候，容器会自动发现它，并用它来配置Servlet上下文。

```java
//Web工程的初始化类,用来代替web.xml
public class Webinit extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 指定Srping的配置类
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    /**
     * 指定SrpingMVC的配置类
     *
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * 指定DispatcherServlet的映射规则,即url-parttern
     *
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 注册过滤器
     *
     * @return
     */

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-");
        characterEncodingFilter.setForceResponseEncoding(true);
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{characterEncodingFilter, hiddenHttpMethodFilter};
    }
}

```

### 2、创建SpringConfig配置类，代替spring的配置文件

```java
@Configuration
public class SpringConfig {
    //SSM整合后,spring的配置信息写在这个类中
}
```

### 3、创建WebConfig配置类，代替SpringMVC的配置文件

```java
/**
 * 代替SpringMVC的配置文件:
 * 1.扫描组件   2.视图解析器 3.view-controller  4.default-servlet-handle
 * 5.mvc注解驱动    6.文件上传解析器   7.异常处理  8.拦截器
 */

//将当前类标识为一个配置类
@Configuration
//1.扫描组件
@ComponentScan("com.mvc.controller")
//5.mvc注解驱动 <mvc:annotation-driven/>
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //7.异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        //<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties prop = new Properties();
        //<property name="exceptionMappings">
        //<prop key="java.lang.ArithmeticException">error</prop>
        prop.setProperty("java.lang.ArithmeticException", "error");
        exceptionResolver.setExceptionMappings(prop);
        //<property name="exceptionAttribute" value="ex"></property>
        exceptionResolver.setExceptionAttribute("ex");
        resolvers.add(exceptionResolver);
    }

    //6.文件上传解析器
//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        return new CommonsMultipartResolver();
//    }


    //4.default-servlet-handle
    //开放对静态资源的访问:<mvc:default-servlet-handler/>
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    //3.view-controller
    //<mvc:view-controller path="/" view-name="index"></mvc:view-controller>
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/view-controller").setViewName("view-controller");
    }


    //8.拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TestInterceptor testInterceptor = new TestInterceptor();
        registry.addInterceptor(testInterceptor).addPathPatterns("/**");
    }


    //2.视图解析器
    //配置生成模板解析器
    @Bean
    public ITemplateResolver templateResolver() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        //ServletContextTemplateResolver需要一个ServletContext作为构造参数,可通过WebApplicationContext的方法获得
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
                webApplicationContext.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    //生成模板引擎并为模板引擎注入模板解析器，通过autowire将上面的对象直接作为传参
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    //生成视图解析器并为解析器注入模板引擎，通过autowire将上面的对象直接作为传参
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
}
```

### 4、测试功能

```java
@Controller
public class TestController1 {
    @RequestMapping(value = "/")
    public String testController1() {
        return "index";
    }
}
```



# 十四、SpringMVC执行流程(难点)

### 1、SpringMVC常用组件

- DispatcherServlet：**前端控制器**，不需要工程师开发，由框架提供

作用：统一处理请求和响应，整个流程控制的中心，由它调用其它组件处理用户的请求

- HandlerMapping：**处理器映射器**，不需要工程师开发，由框架提供

作用：根据请求的url、method等信息查找Handler，即控制器方法

- Handler：**处理器**，需要工程师开发

作用：在DispatcherServlet的控制下Handler对具体的用户请求进行处理(就是controller)

- HandlerAdapter：**处理器适配器**，不需要工程师开发，由框架提供

作用：通过HandlerAdapter对处理器（控制器方法）进行执行(找到handle对应的方法,并执行)

- ViewResolver：**视图解析器**，不需要工程师开发，由框架提供

作用：进行视图解析，得到相应的视图，例如：ThymeleafView、InternalResourceView、RedirectView

- View：**视图**

作用：将模型数据通过页面展示给用户

### 2、DispatcherServlet初始化过程（难点）

**DispatcherServlet 本质上是一个 Servlet**，所以天然的遵循 Servlet 的生命周期。所以宏观上是 Servlet 生命周期来进行调度。

![init1](img/mvcinit1.png)

![init2](img/mvcinit2.png)

![init3](img/mvcinit3.png)


#### a>初始化WebApplicationContext
所在类：org.springframework.web.servlet.FrameworkServlet

```java
protected WebApplicationContext initWebApplicationContext() {
WebApplicationContext rootContext =
WebApplicationContextUtils.getWebApplicationContext(getServletContext());
WebApplicationContext wac = null;

    if (this.webApplicationContext != null) {
        // A context instance was injected at construction time -> use it
        wac = this.webApplicationContext;
        if (wac instanceof ConfigurableWebApplicationContext) {
            ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
            if (!cwac.isActive()) {
                // The context has not yet been refreshed -> provide services such as
                // setting the parent context, setting the application context id, etc
                if (cwac.getParent() == null) {
                    // The context instance was injected without an explicit parent -> set
                    // the root application context (if any; may be null) as the parent
                    cwac.setParent(rootContext);
                }
                configureAndRefreshWebApplicationContext(cwac);
            }
        }
    }
    if (wac == null) {
        // No context instance was injected at construction time -> see if one
        // has been registered in the servlet context. If one exists, it is assumed
        // that the parent context (if any) has already been set and that the
        // user has performed any initialization such as setting the context id
        wac = findWebApplicationContext();
    }
    if (wac == null) {
        // No context instance is defined for this servlet -> create a local one
        // 创建WebApplicationContext
        wac = createWebApplicationContext(rootContext);
    }

    if (!this.refreshEventReceived) {
        // Either the context is not a ConfigurableApplicationContext with refresh
        // support or the context injected at construction time had already been
        // refreshed -> trigger initial onRefresh manually here.
        synchronized (this.onRefreshMonitor) {
            // 刷新WebApplicationContext
            onRefresh(wac);
        }
    }

    if (this.publishContext) {
        // Publish the context as a servlet context attribute.
        // 将IOC容器在应用域共享
        String attrName = getServletContextAttributeName();
        getServletContext().setAttribute(attrName, wac);
    }

    return wac;
}
```

#### b>创建WebApplicationContext
所在类：org.springframework.web.servlet.FrameworkServlet

```java
protected WebApplicationContext createWebApplicationContext(@Nullable ApplicationContext parent) {
    Class<?> contextClass = getContextClass();
    if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
        throw new ApplicationContextException(
            "Fatal initialization error in servlet with name '" + getServletName() +
            "': custom WebApplicationContext class [" + contextClass.getName() +
            "] is not of type ConfigurableWebApplicationContext");
    }
    // 通过反射创建 IOC 容器对象
    ConfigurableWebApplicationContext wac =
        (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(contextClass);

    wac.setEnvironment(getEnvironment());
    // 设置父容器:spring和springMVC容器整合，配置文件相互独立,将springIOC设置为springMVCIOC的父容器
    wac.setParent(parent);
    String configLocation = getContextConfigLocation();
    if (configLocation != null) {
        wac.setConfigLocation(configLocation);
    }
    configureAndRefreshWebApplicationContext(wac);

    return wac;
}
```

#### c>DispatcherServlet初始化策略
FrameworkServlet创建WebApplicationContext后，刷新容器，调用onRefresh(wac)，此方法在DispatcherServlet中进行了重写，调用了initStrategies(context)方法，初始化策略，**即初始化DispatcherServlet的各个组件**

所在类：org.springframework.web.servlet.DispatcherServlet

```java
protected void initStrategies(ApplicationContext context) {
   initMultipartResolver(context);
   initLocaleResolver(context);
   initThemeResolver(context);
   initHandlerMappings(context);
   initHandlerAdapters(context);
   initHandlerExceptionResolvers(context);
   initRequestToViewNameTranslator(context);
   initViewResolvers(context);
   initFlashMapManager(context);
}

```

### 3、DispatcherServlet调用组件处理请求

![req1](img/mvcreq1.png)
![req2](img/mvcreq2.png)
![req3](img/mvcreq3.png)
![req4](img/mvcreq4.png)

#### a>processRequest()

FrameworkServlet重写HttpServlet中的service()和doXxx()，这些方法中调用了processRequest(request, response)

所在类：org.springframework.web.servlet.FrameworkServlet

```java
protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    long startTime = System.currentTimeMillis();
    Throwable failureCause = null;

    LocaleContext previousLocaleContext = LocaleContextHolder.getLocaleContext();
    LocaleContext localeContext = buildLocaleContext(request);

    RequestAttributes previousAttributes = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes requestAttributes = buildRequestAttributes(request, response, previousAttributes);

    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
    asyncManager.registerCallableInterceptor(FrameworkServlet.class.getName(), new RequestBindingInterceptor());

    initContextHolders(request, localeContext, requestAttributes);

    try {
        // 执行服务，doService()是一个抽象方法，在DispatcherServlet中进行了重写
        doService(request, response);
    }
    catch (ServletException | IOException ex) {
        failureCause = ex;
        throw ex;
    }
    catch (Throwable ex) {
        failureCause = ex;
        throw new NestedServletException("Request processing failed", ex);
    }

    finally {
        resetContextHolders(request, previousLocaleContext, previousAttributes);
        if (requestAttributes != null) {
            requestAttributes.requestCompleted();
        }
        logResult(request, response, failureCause, asyncManager);
        publishRequestHandledEvent(request, response, startTime, failureCause);
    }
}
```

#### b>doService()

所在类：org.springframework.web.servlet.DispatcherServlet

```java
@Override
protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
    logRequest(request);

    // Keep a snapshot of the request attributes in case of an include,
    // to be able to restore the original attributes after the include.
    Map<String, Object> attributesSnapshot = null;
    if (WebUtils.isIncludeRequest(request)) {
        attributesSnapshot = new HashMap<>();
        Enumeration<?> attrNames = request.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String attrName = (String) attrNames.nextElement();
            if (this.cleanupAfterInclude || attrName.startsWith(DEFAULT_STRATEGIES_PREFIX)) {
                attributesSnapshot.put(attrName, request.getAttribute(attrName));
            }
        }
    }

    // Make framework objects available to handlers and view objects.
    request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());
    request.setAttribute(LOCALE_RESOLVER_ATTRIBUTE, this.localeResolver);
    request.setAttribute(THEME_RESOLVER_ATTRIBUTE, this.themeResolver);
    request.setAttribute(THEME_SOURCE_ATTRIBUTE, getThemeSource());

    if (this.flashMapManager != null) {
        FlashMap inputFlashMap = this.flashMapManager.retrieveAndUpdate(request, response);
        if (inputFlashMap != null) {
            request.setAttribute(INPUT_FLASH_MAP_ATTRIBUTE, Collections.unmodifiableMap(inputFlashMap));
        }
        request.setAttribute(OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap());
        request.setAttribute(FLASH_MAP_MANAGER_ATTRIBUTE, this.flashMapManager);
    }

    RequestPath requestPath = null;
    if (this.parseRequestPath && !ServletRequestPathUtils.hasParsedRequestPath(request)) {
        requestPath = ServletRequestPathUtils.parseAndCache(request);
    }

    try {
        // 处理请求和响应
        doDispatch(request, response);
    }
    finally {
        if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
            // Restore the original attribute snapshot, in case of an include.
            if (attributesSnapshot != null) {
                restoreAttributesAfterInclude(request, attributesSnapshot);
            }
        }
        if (requestPath != null) {
            ServletRequestPathUtils.clearParsedRequestPath(request);
        }
    }
}
```

#### c>doDispatch()

所在类：org.springframework.web.servlet.DispatcherServlet

```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpServletRequest processedRequest = request;
    HandlerExecutionChain mappedHandler = null;
    boolean multipartRequestParsed = false;

    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

    try {
        ModelAndView mv = null;
        Exception dispatchException = null;

        try {
            processedRequest = checkMultipart(request);
            multipartRequestParsed = (processedRequest != request);

            // Determine handler for the current request.
            /*
                mappedHandler：调用链
                包含handler、interceptorList、interceptorIndex
                handler：浏览器发送的请求所匹配的控制器方法
                interceptorList：处理控制器方法的所有拦截器集合
                interceptorIndex：拦截器索引，控制拦截器afterCompletion()的执行
            */
            mappedHandler = getHandler(processedRequest);
            if (mappedHandler == null) {
                noHandlerFound(processedRequest, response);
                return;
            }

            // Determine handler adapter for the current request.
               // 通过控制器方法创建相应的处理器适配器，调用所对应的控制器方法
            HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

            // Process last-modified header, if supported by the handler.
            String method = request.getMethod();
            boolean isGet = "GET".equals(method);
            if (isGet || "HEAD".equals(method)) {
                long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
                    return;
                }
            }
            
            // 调用拦截器的preHandle()
            if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                return;
            }

            // Actually invoke the handler.
            // 由处理器适配器调用具体的控制器方法，最终获得ModelAndView对象
            mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

            if (asyncManager.isConcurrentHandlingStarted()) {
                return;
            }

            applyDefaultViewName(processedRequest, mv);
            // 调用拦截器的postHandle()
            mappedHandler.applyPostHandle(processedRequest, response, mv);
        }
        catch (Exception ex) {
            dispatchException = ex;
        }
        catch (Throwable err) {
            // As of 4.3, we're processing Errors thrown from handler methods as well,
            // making them available for @ExceptionHandler methods and other scenarios.
            dispatchException = new NestedServletException("Handler dispatch failed", err);
        }
        // 后续处理：处理模型数据和渲染视图
        processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
    }
    catch (Exception ex) {
        triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
    }
    catch (Throwable err) {
        triggerAfterCompletion(processedRequest, response, mappedHandler,
                               new NestedServletException("Handler processing failed", err));
    }
    finally {
        if (asyncManager.isConcurrentHandlingStarted()) {
            // Instead of postHandle and afterCompletion
            if (mappedHandler != null) {
                mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
            }
        }
        else {
            // Clean up any resources used by a multipart request.
            if (multipartRequestParsed) {
                cleanupMultipart(processedRequest);
            }
        }
    }
}
```

#### d>processDispatchResult()

```java
private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
                                   @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv,
                                   @Nullable Exception exception) throws Exception {

    boolean errorView = false;

    if (exception != null) {
        if (exception instanceof ModelAndViewDefiningException) {
            logger.debug("ModelAndViewDefiningException encountered", exception);
            mv = ((ModelAndViewDefiningException) exception).getModelAndView();
        }
        else {
            Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);
            mv = processHandlerException(request, response, handler, exception);
            errorView = (mv != null);
        }
    }

    // Did the handler return a view to render?
    if (mv != null && !mv.wasCleared()) {
        // 处理模型数据和渲染视图
        render(mv, request, response);
        if (errorView) {
            WebUtils.clearErrorRequestAttributes(request);
        }
    }
    else {
        if (logger.isTraceEnabled()) {
            logger.trace("No view rendering, null ModelAndView returned.");
        }
    }

    if (WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
        // Concurrent handling started during a forward
        return;
    }

    if (mappedHandler != null) {
        // Exception (if any) is already handled..
        // 调用拦截器的afterCompletion()
        mappedHandler.triggerAfterCompletion(request, response, null);
    }
}
```

### 4、SpringMVC的执行流程

bp拓展

https://www.mad-coding.cn/2019/10/04/burpsuite%E5%B8%B8%E7%94%A8%E6%8F%92%E4%BB%B6%E6%8E%A8%E8%8D%90%E4%B8%8E%E4%BD%BF%E7%94%A8/
https://blog.csdn.net/qq_27446553/article/details/78951881
https://blog.csdn.net/weixin_50464560/article/details/120495249
https://cloud.tencent.com/developer/article/1015187
