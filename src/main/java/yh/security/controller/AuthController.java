package yh.security.controller;

import org.springframework.security.core.userdetails.UserDetails;
import yh.common.Result;
import yh.common.StatusCode;
import yh.security.service.UserDetailsServiceImpl;
import yh.utils.JwtTokenUtils;
import yh.system.entity.User;
import yh.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	BCryptPasswordEncoder bCryptPasswordEncoder;
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
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		String token = JwtTokenUtils.createToken(userDetails.getUsername(), userDetails.getAuthorities().toString());
		return new Result(true, StatusCode.SUCCESS, "登录成功", token);
	}
//	@PostMapping("/login")
//	public Result login(@RequestBody User user) {
//		String username = user.getUsername();
//		String password = user.getPassword();
//		user = userService.findUserByUserName(username);
//		if (user == null) {
//			return new Result(false, StatusCode.LOGIN_ERROR, "用户名不存在");
//		}
//		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
//			System.out.println("密码错误");
//			return new Result(false, StatusCode.LOGIN_ERROR, "密码错误");
//		}
//		//系统登录认证
//		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//		Authentication authentication = authenticationManager.authenticate(authRequest);
//
//		// 认证成功存储认证信息到上下文
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		System.out.println("登录时的authentication:"+authentication);
//		// 生成令牌并返回给客户端
//		String token = JwtTokenUtils.createToken("id",user.getUsername(),user.getRoles());
//		// Http Response Header 中返回 Token
//		Map<String, Object> result = new HashMap<>();
//		result.put("user", user);
//		result.put("role", user.getRoles());
//		result.put("token", token);
//		return new Result(true, StatusCode.SUCCESS, "登录成功", result);
//	}
}
