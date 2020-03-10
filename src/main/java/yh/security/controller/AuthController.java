package yh.security.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import yh.common.Result;
import yh.common.StatusCode;
import yh.security.service.UserDetailsServiceImpl;
import yh.utils.JwtTokenUtils;
import yh.system.entity.User;
import yh.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService userService;
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	AuthenticationManager authenticationManager;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public Result registerUser(@RequestBody User user) {
		userService.saveUser(user);
		return new Result(true, StatusCode.SUCCESS, "注册成功", user);
	}

	@PostMapping("/login")
	public Result login(@RequestBody User user) {
		// 执行登录认证过程
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				user.getUsername(), user.getPassword());
		Authentication authentication = authenticationManager.authenticate(authRequest);
		System.out.println(authentication.getAuthorities().toString());
		String token = JwtTokenUtils.createToken(authentication.getName(), authentication.getAuthorities());
		return new Result(true, StatusCode.SUCCESS, "登录成功", token);
	}

}
