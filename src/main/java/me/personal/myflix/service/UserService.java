package me.personal.myflix.service;


import me.personal.myflix.entity.User;
import me.personal.myflix.service.base.BaseService;

import java.util.Collection;

public interface UserService extends BaseService<User, Long> {
    User findOne(String email);

    Collection<User> findByRole(String role);
}
