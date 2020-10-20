package com.user.mngmnt.service;

import java.util.ArrayList;
import java.util.List;

import com.user.mngmnt.model.VerificationToken;
import com.user.mngmnt.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Value("${user.password.default}")
    private String defPwd;

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
        if (user.getId() == null)
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        System.out.println("Save user: "+user);
    }

    @Override
    public void activateUser(String username, String password) {
        User user = userRepository.findByUserName(username);
        if(user!=null) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setActive(Boolean.TRUE);
            userRepository.save(user);
        }
    }

    @Override
    public void resetPassword(Long id) {
        User user = userRepository.findById(id).get();
        user.setPassword(bCryptPasswordEncoder.encode(defPwd));
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String userName, String oldPwd, String newPwd) {
        User user = userRepository.findByUserName(userName);
        if(user!=null && bCryptPasswordEncoder.matches(oldPwd, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPwd));
            userRepository.save(user);
        }
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
    public String findUserNameByID(Long id) {
        return findById(id).getUserName();
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

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
}
