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
