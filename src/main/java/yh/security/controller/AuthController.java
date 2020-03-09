package yh.security.controller;

import org.springframework.security.core.userdetails.UserDetails;
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
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		String token = JwtTokenUtils.createToken(userDetails.getUsername(), userDetails.getAuthorities().toString());
		return new Result(true, StatusCode.SUCCESS, "登录成功", token);
	}

}
