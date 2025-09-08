package me.personal.myflix.repository;


import me.personal.myflix.entity.User;
import me.personal.myflix.repository.base.BaseRepository;

import java.util.Collection;

public interface UserRepository extends BaseRepository<User, Long> {
    User findByEmail(String email);
    Collection<User> findAllByRole(String role);
}
