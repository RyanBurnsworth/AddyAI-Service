package com.addyai.addyaiservice.services.user;

import com.addyai.addyaiservice.repos.user.UserAccountRepository;
import com.addyai.addyaiservice.repos.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    public UserServiceImpl(UserRepository userRepository, UserAccountRepository userAccountRepository) {
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
    }
}
