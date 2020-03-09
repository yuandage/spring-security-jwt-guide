package yh.system.dao;

import yh.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Transactional
    void deleteByUsername( String username);

}
