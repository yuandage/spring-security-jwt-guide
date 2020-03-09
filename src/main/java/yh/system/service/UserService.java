package yh.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import yh.system.entity.User;
import yh.system.dao.UserDao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser(User user) {
        User user1  = userDao.findByUsername(user.getUsername());
        if (user1 != null) {
            throw new RuntimeException("用户名已存在!");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("DEV,PM");
        userDao.save(user);
    }

    public User findUserByUserName(String name) {
        return userDao.findByUsername(name);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteUserByUserName(String name) {
        userDao.deleteByUsername(name);
    }

    public List<User> getAllUser() {
        return userDao.findAll();
    }
}
