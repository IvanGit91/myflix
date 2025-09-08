package me.personal.myflix.service;

import me.personal.myflix.entity.User;
import me.personal.myflix.exception.AppException;
import me.personal.myflix.repository.base.BaseRepository;
import me.personal.myflix.repository.UserRepository;
import me.personal.myflix.service.base.impl.BaseServiceImpl;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@DependsOn("passwordEncoder")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(BaseRepository<User, Long> baseRepository,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository) {
        super(baseRepository, User.class);
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Collection<User> findByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Transactional
    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "User already exists");
        }

    }

    @Override
    @Transactional
    public User update(User user) {
        User oldUser = userRepository.findByEmail(user.getEmail());
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        oldUser.setName(user.getName());
        oldUser.setPhone(user.getPhone());
        oldUser.setAddress(user.getAddress());
        return save(oldUser);
    }
}
