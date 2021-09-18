package com.didkovskiy.shitsite.repositories;

import com.didkovskiy.shitsite.domains.userstore.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAll();

}
