# Ecode
Ecode(Error Code)一个简单的错误码管理工具，支持动态修改异常消息和集中管理错误码消息。

## 用途

当你在开发一个Java项目时（更多的时候时一个Web），你或许需要管理很多的`错误码`（Error Code)，如果其中大量的
错误码时属于面向用户的，常常需要修改错误码消息，这是一个繁琐的过程。当使用`Ecode`后，你只需要定义在一个枚举类或者
常量类中定义好你需要使用的错误码，同时在对应的Ecode数据源（例如JSON文件（或者数据库）中，定义相应的`Ecode`，之后你将不需要
在手动的去修改异常信息。此外`Ecode`时低入侵的工具，大多数在你的代码只会出现`Ecode`提供的工具类，只有在异常处理出口和配置类中出现
`Ecode`的其他组件。

## 扩展
`Ecode`的核心包中仅提供了API接口，和默认的实现类，你可以通过继承`AbstractEcodeFactory`来管理你最适合的`Ecode`数据源。

## 代码实例


### 简单方式
你抛出该异常对象（或其子类对象）的位置改写为如下方式：

```java
// 常规的方式
// throw new AppException("应用错误");

// Ecode方式
// 1000 -> "应用错误”
throw EcodeUtils.toThrow("1000");

```
在你的配置文件（假设为JSON）中应该时这样的
```json
[
  {
    "code": "1000",
    "message": "应用错误"
  }
]
```

再你的异常处理位置，你只需再次利用`EcodeUtils`读取对应的`Ecode`即可
```java
// 这里已SpringMVC中统一处理异常为例
@RestControllerAdvice
public class TestController {

    @ExceptionHandler(TestException.class)
    public HttpEntity<Ecode> handleTestException(TestException e) {
        Ecode body = EcodeUtils.getEcode(e);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

```
此外，通过`EcodeUtils`创建的异常类对象，内部已经注入了你配置文件中的异常信息。
如果你希望它打印到控制台，也是可以看到你定义的异常信息的。

### 推荐方式

在你的应用根异常类上实现接口`EcodeSupport`，同时增加属性`Ecode`，例如
```java
public class AppException extends RuntimeException implements EcodeSupport {
    protected Ecode ecode;

    public AppException() {
    }
    
    // 必须提供参数为 message 的构造方法
    public AppException(String message) {
        super(message);
    }
    
    @Override
    public void setEcode(Ecode ecode) {
        this.ecode = ecode;
    }

    @Override
    public Ecode getEcode() {
        return this.ecode;
    }
    
}
```

同时设置`ExceptionFactory`工厂类的属性`rootExceptionClass`为你应用的根异常类（如上面的`AppException`)
默认情况下，`ExceptionFactory`会常见`EcodeException`对象。

如果你是使用SpringBoot开发，你可以增加一个`Bean`来替换默认注入的`ExceptionFactory`,例如：
```java

@Configuration
public class MyConfigure {

    @Bean
    public ExceptionFactory exceptionFactory() {
        SimpleExceptionFactory simpleExceptionFactory = new SimpleExceptionFactory();
        simpleExceptionFactory.setExceptionRootClass(AppException.class);
        return simpleExceptionFactory;
    }
    
}
```






