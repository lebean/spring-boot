package leix.lebean.sweb.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import leix.lebean.sweb.common.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name:UserController
 * Description:
 * Author:leix
 * Time: 2017/6/12 10:29
 */
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@Api(value = "用户服务", description = "与用户相关的服务", position = 2)
public class UserController extends BaseController {

    @Autowired
    IUserService userService;

    /**
     * 注册新的用户
     *
     * @param user
     * @return
     * @throws AuthenticationException
     */
    @PostMapping("/users")
    @ApiOperation(value = "注册用户", notes = "注册一个新的用户")
    @ApiImplicitParam(name = "user", value = "用户数据实体", dataType = "User")
    public User register(@RequestBody User user) throws AuthenticationException {
        return userService.register(user);
    }

}
