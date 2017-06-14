package leix.lebean.sweb.auth;

/**
 * Name:AuthServiceImpl
 * Description:用户谁业务接口
 * Author:leix
 * Time: 2017/6/12 09:43
 */
public interface IAuthService {
    String login(String name,String password);
    String refresh(String oldToken);
}
