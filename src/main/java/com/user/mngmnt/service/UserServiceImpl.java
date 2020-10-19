package com.user.mngmnt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.mngmnt.model.User;
import com.user.mngmnt.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public void saveUser(User user) {
        if (user.getId() == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(Boolean.TRUE);
        }
        userRepository.save(user);
        System.out.println("Save user: "+user);
    }


    @Override
    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    public Page<User> searchByTerm(String name, Pageable pageable) {
        return userRepository.searchByTerm(name, pageable);
    }


    @Override
    public Boolean removeAll() {
        userRepository.deleteAll();
        return Boolean.TRUE;
    }


    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }


    @Override
    public List<User> searchBy(String keyword, String criteria) {
        if ("userName".equals(criteria)) {
            return userRepository.findByUserNameContaining(keyword);
        } else if ("firstName".equals(criteria)) {
            return userRepository.findByFirstNameIgnoreCaseContaining(keyword);
        } else if ("lastName".equals(criteria)) {
            return userRepository.findByLastNameIgnoreCaseContaining(keyword);
        } else if ("email".equals(criteria)) {
            return userRepository.findByEmailIgnoreCaseContaining(keyword);
        }
        return new ArrayList<>();

    }

}
