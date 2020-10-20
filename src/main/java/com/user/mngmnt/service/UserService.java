package com.user.mngmnt.service;

import com.user.mngmnt.model.User;
import com.user.mngmnt.model.VerificationToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User findUserByUserName(String userName);

    User findUserByEmail(String email);

    void saveUser(User user);

    void activateUser(String username, String password);

    void resetPassword(Long id);

    void updatePassword(String userName, String oldPwd, String newPwd);

    Boolean removeAll();

    void removeById(Long id);

    User findById(Long id);

    String findUserNameByID(Long id);

    Page<User> searchByTerm(String name, Pageable pageable);

    Page<User> listUsers(Pageable pageable);

    List<User> searchBy(String keyword, String criteria);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}
