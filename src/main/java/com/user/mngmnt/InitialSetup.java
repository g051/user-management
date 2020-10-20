package com.user.mngmnt;

import com.user.mngmnt.model.*;
import com.user.mngmnt.service.AuditService;
import com.user.mngmnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class InitialSetup {

    @Autowired
    private UserService userService;

    @Autowired
    private AuditService auditService;

    @Value("${admin.username}")
    private String userName;

    @Value("${admin.first.name}")
    private String firstName;

    @Value("${admin.last.name}")
    private String lastName;

    @Value("${admin.email.address}")
    private String emailAddress;

    @Value("${admin.password}")
    private String password;

    @PostConstruct
    public void initIt() throws Exception {

        //User dbUser = userService.findUserByEmail(emailAddress);
        User dbUser = userService.findUserByUserName(userName);

        if (dbUser == null) {
            User user = new User();
            user.setUserName(userName);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(emailAddress);
            user.setPassword(password);
            user.setActive(Boolean.TRUE);
            user.setRoleName(RoleNames.ADMIN.name());
            userService.saveUser(user);
        }
        loadUsers();
        //loadAudits();
    }



    private void loadUsers() {
        User user1 = new User("user1", "1", "User",
                "user1@yopmail.com", "123456", RoleNames.USER.name(), Boolean.TRUE);
        userService.saveUser(user1);

        User user2 = new User("user2", "2", "User",
                "user2@yopmail.com", "123456", RoleNames.USER.name(), Boolean.TRUE);
        userService.saveUser(user2);

        User user3 = new User("user3", "3", "User",
                "user3@yopmail.com", "123456", RoleNames.USER.name(), Boolean.TRUE);
        userService.saveUser(user3);
    }

    private void loadAudits() {
        Audit audit0 = new Audit(userName, Actions.LOGIN.name(), ActionStatus.SUCCESS.name(),
                userName,"Admin login", LocalDateTime.now());
        auditService.saveAudit(audit0);

        Audit audit1 = new Audit(userName, Actions.RESISTER.name(), ActionStatus.FAILED.name(),
                "user1","Create new user", LocalDateTime.now());
        auditService.saveAudit(audit1);

        Audit audit2 = new Audit("user1", Actions.ACTIVATE.name(), ActionStatus.SUCCESS.name(),
                "user1","Account is activated", LocalDateTime.now());
        auditService.saveAudit(audit2);

        Audit audit3 = new Audit("user2", Actions.FORGOT_PWD.name(), ActionStatus.FAILED.name(),
                "user2","Reset password", LocalDateTime.now());
        auditService.saveAudit(audit3);
    }
}
