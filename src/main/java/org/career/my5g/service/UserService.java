package org.career.my5g.service;

import java.util.Optional;

import org.career.my5g.domain.entity.UserDetail;
import org.career.my5g.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDetail> viewByAccount(String account) {
        return userRepository.findByAccount(account);
    }
    
}
