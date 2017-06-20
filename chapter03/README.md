​	用SprintBoot做了RESTful风格的基础框架，但发现了个问题，如果服务在处理业务时发现了异常怎么处理呢，举个栗子，在获取一个城市的详细信息时发现传和的城市编号不存在，我是返回200在返回的数据里说传入的编号不存在呢，还是直接抛出异常给前端？对于纯RESTful风格的服务，我更倾向于抛出异常到前端。这里就来讲讲怎么来抛出这个异常，让前端能获取到有用的信息，而不只是一个500错误。

​	首先我把异常分为两种，一种是可控制的，或者是由我们发现条件不正确主动抛出的异常，就像前城市编号不存在那个粟子；另一种是不可控制的，或者说是程序存在bug引起的异常，但这种异常也不想变态的就直接给前端抛出个500异常。

实现步骤如下：

### 第1步，新建一个Exception类

​	新建一个RESTException类，在主动招聘异常时，就抛出一个RESTException类实例。它包含两个属性，code和message。code是要抛出的异常代码用http状态码来表示，message是想要告诉前端的信息，如“城市编号不存在”之类的。

```
package leix.lebean.sweb.common.core;

/**
 * Name:RESTException
 * Description: 异常信息
 * Author:leix
 * Time: 2017/6/20 13:58
 */
public class RESTException extends Exception {
    int code;//状态代码
    String message;//异常提示信息

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

```

### 第2步，建一个异常拼接器

创建一个异常处理器，它有两个异常处理方法，一个处理主动抛出的异常，一个处理非主动异常。

```
package leix.lebean.sweb.common.config;

import leix.lebean.sweb.common.core.RESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Name:ExtExceptionHandler
 * Description:
 * Author:leix
 * Time: 2017/6/20 14:00
 */
@ControllerAdvice
public class ExtExceptionHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理业务发现问题主动抛出的异常
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = RESTException.class)
    @ResponseBody
    public ResponseEntity<RESTException> baseErrorHandler(HttpServletRequest request, RESTException e) throws Exception {
        //把错误输出到日志
        logger.error("RESTfulException Handler---Host: {} invokes url: {} ERROR: {}", request.getRemoteHost(), request.getRequestURL(), e.getMessage());
        return new ResponseEntity<RESTException>(e, HttpStatus.valueOf(e.getCode()));
    }

    /**
     * 系统抛出的没有处理过的异常
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Exception> defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        //把错误输出到日志
        logger.error("DefaultException Handler---Host: {} invokes url: {} ERROR: {}", request.getRemoteHost(), request.getRequestURL(), e.getMessage());
        return new ResponseEntity<Exception>(new Exception("Soory, 服务器好像抽风了, 程序员小伙伴正在疯狂抢救！"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


```

​	主异常处理方法将传递业务处理中的异常提示信息给前端，非主动异常处理方法将统一返回一种异常提示信息到前端。

### 第3步，在业务中抛出异常

```
thow new RESTException(HttpStatus.INTERNAL_SERVER_ERROR.value(),"城市编号不存在");
```











