package yh.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import yh.security.entity.SecurityUserDetails;
import yh.system.entity.User;
import yh.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = userService.findUserByUserName(name);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		String roles = user.getRoles();
		String[] allRoles = roles.split(",");
		for(String allRole:allRoles){
			authorities.add(new SimpleGrantedAuthority(allRole));
		}
		System.out.println("用户名:"+user.getUsername());
		System.out.println("角色:"+authorities);
		return new SecurityUserDetails(user.getUsername(), user.getPassword(), authorities);
	}

}
