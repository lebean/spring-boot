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
