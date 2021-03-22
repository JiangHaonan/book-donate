package com.mutou.user.controller;

import com.mutou.auth.service.AuthService;
import com.mutou.auth.service.pojo.Account;
import com.mutou.auth.service.pojo.AuthCode;
import com.mutou.auth.service.pojo.AuthResponse;
import com.mutou.controller.BaseController;
import com.mutou.pojo.IMOOCJSONResult;
import com.mutou.user.pojo.Users;
import com.mutou.user.pojo.bo.UserBO;
import com.mutou.user.service.UserService;
import com.mutou.utils.CookieUtils;
import com.mutou.utils.JsonUtils;
import com.mutou.utils.MD5Utils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
@Slf4j
public class PassportController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {

        // 1. 判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }

        // 2. 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        // 3. 请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        // 如果是登录的请先退出
        if (isAlreadyLogin(request)) {
            return IMOOCJSONResult.errorMsg("请先退出当前用户再注册");
        }

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1. 查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        // 2. 密码长度不能少于6位
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能少于6");
        }

        // 3. 判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("两次密码输入不一致");
        }

        // 4. 实现注册
        Users userResult = userService.createUser(userBO);

        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        // TODO DONATE没有类似购物车的功能，未来可能会加入类似的功能

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    @HystrixCommand(
            commandKey = "loginFail", // 全局唯一的标识服务，默认函数名称
            fallbackMethod = "loginFail", //同一个类里，public private都可以
            // 线程有关的属性
            // 线程组, 多个服务可以共用一个线程组
            threadPoolKey = "threadPoolA",
            threadPoolProperties = {
                    // 核心线程数
                    @HystrixProperty(name = "coreSize", value = "10"),
                    // size > 0, LinkedBlockingQueue -> 请求等待队列
                    // 默认-1 , SynchronousQueue -> 不存储元素的阻塞队列
                    @HystrixProperty(name = "maxQueueSize", value = "40"),
                    // 在maxQueueSize=-1的时候无效，队列没有达到maxQueueSize依然拒绝
                    // queueSizeRejectionThreshold优先于maxQueueSize，队列10个，瞬发25个请求测试就可以得到想要的结果
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15")
            }

    )
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        if (isAlreadyLogin(request)) {
            return IMOOCJSONResult.errorMsg("请先退出当前用户再进行登录");
        }

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1. 实现登录
        Users userResult = userService.queryUserForLogin(username,
                    MD5Utils.getMD5Str(password));

        if (userResult == null) {
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }

        AuthResponse token = authService.tokenize(userResult.getId());
        if (!AuthCode.SUCCESS.getCode().equals(token.getCode())) {
            log.error("Token error - uid={}", userResult.getId());
            return IMOOCJSONResult.errorMsg("Token error");
        }

        // 将token添加到Header当中
        addAuth2Header(response, token.getAccount());

        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        return IMOOCJSONResult.ok(userResult);
    }

    private IMOOCJSONResult loginFail(UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 Throwable throwable) throws Exception {
        return IMOOCJSONResult.errorMsg("验证码输错了（模仿12306）" + throwable.getLocalizedMessage());
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreateTime(null);
        userResult.setUpdateTime(null);
        userResult.setBirthday(null);
        return userResult;
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        Account account = Account.builder()
                .token(request.getHeader(AUTH_HEADER))
                .refreshToken(request.getHeader(REFRESH_TOKEN_HEADER))
                .userId(userId)
                .build();
        AuthResponse auth = authService.delete(account);
        if (!AuthCode.SUCCESS.getCode().equals(auth.getCode())) {
            log.error("Token error - uid={}", userId);
            return IMOOCJSONResult.errorMsg("Token error");
        }

        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        return IMOOCJSONResult.ok();
    }

    // 前端每次请求服务，都把这几个参数带上
    private void addAuth2Header(HttpServletResponse response, Account token) {
        response.setHeader(UID_HEADER, token.getUserId());
        response.setHeader(AUTH_HEADER, token.getToken());
        response.setHeader(REFRESH_TOKEN_HEADER, token.getRefreshToken());

        // 让前端感知到，过期时间一天，这样可以在临近过期的时候refresh token
        Calendar expTime = Calendar.getInstance();
        expTime.add(Calendar.DAY_OF_MONTH, 1);
        response.setHeader("token-exp-time", expTime.getTimeInMillis() + "");
    }

    private boolean isAlreadyLogin(HttpServletRequest request) {
        String userId = request.getHeader(UID_HEADER);
        String token = request.getHeader(AUTH_HEADER);
        Account account = Account.builder()
                .userId(userId)
                .token(token).build();
        if (AuthCode.SUCCESS.getCode().equals(authService.verify(account).getCode())) {
            return true;
        }
        return false;
    }

}
