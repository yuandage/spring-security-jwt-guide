package yh.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import yh.common.Result;
import yh.common.StatusCode;
import yh.system.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('DEV','ADMIN','USER')")
    public Result getAllUser() {
        return new Result(true, StatusCode.SUCCESS, "查询成功",  userService.getAllUser());
    }

    @DeleteMapping("/user")
    @PreAuthorize("hasAnyAuthority('DEV','ADMIN')")
    public Result deleteUserById(@RequestParam("username") String username) {
        userService.deleteUserByUserName(username);
        return new Result(true, StatusCode.SUCCESS, "删除成功");
    }
}
