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



# 五、域对象共享数据

# 六、SpringMVC的视图

# 七、RESTful

# 八、RESTful案例

# 九、文件上传和下载

# 十、拦截器

# 十一、异常处理器

# 十二、注解配置SpringMVC

# 十三、SpringMVC执行流程
