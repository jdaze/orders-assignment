package dev.jdsoft.ordersassignment.facade;

import dev.jdsoft.ordersassignment.persistence.entity.User;
import dev.jdsoft.ordersassignment.persistence.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
