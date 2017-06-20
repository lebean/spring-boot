package leix.lebean.sweb.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import leix.lebean.sweb.auth.secruity.AuthenticationResponse;
import leix.lebean.sweb.common.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Name:AuthController
 * Description:用户认证接口
 * Author:leix
 * Time: 2017/6/12 09:42
 */
@RestController
@Api(value = "认证服务", description = "与用户认证相关的服务", position = 1)
public class AuthController extends BaseController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    IAuthService authService;

    @PostMapping("/auth")
    @ApiOperation(value = "用户认证", notes = "用户信息认证服务，用户登录名与密码，返回验证结果")
    @ApiImplicitParam(name = "authentication", value = "用户登录信息", dataType = "Authentication")
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody Authentication authentication) {
        String token = authService.login(authentication.getName(), authentication.getPassword());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @GetMapping("/auth")
    @ApiOperation(value = "刷新TOKEN", notes = "刷新用户Token服务")
    public ResponseEntity<AuthenticationResponse> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
        }
    }
}
