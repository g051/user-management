package com.user.mngmnt.controller;

import com.user.mngmnt.constants.AppConstant;
import com.user.mngmnt.dto.Response;
import com.user.mngmnt.dto.SearchDTO;
import com.user.mngmnt.model.Password;
import com.user.mngmnt.model.RoleNames;
import com.user.mngmnt.model.User;
import com.user.mngmnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${max.result.per.page}")
    private int maxResults;

    @Value("${max.card.display.on.pagination.tray}")
    private int maxPaginationTraySize;



    @GetMapping("/")
    public ModelAndView home(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                             @RequestParam(value = "size", defaultValue = "4", required = false) Integer size,
                             HttpServletRequest request, HttpServletResponse response) {

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> list = new ArrayList<>();
        authorities.forEach(e -> {
            list.add(e.getAuthority());
        });

        ModelAndView modelAndView = new ModelAndView();
        if (list.contains(RoleNames.ADMIN.name())) {
            modelAndView.setViewName("home");
            Page<User> allUsers = userService.listUsers(PageRequest.of(page, size, Sort.by("userName")));
            modelAndView.addObject("allUsers", allUsers);
            modelAndView.addObject("maxTraySize", size);
            modelAndView.addObject("currentPage", page);
        } else {
            modelAndView.setViewName("user-home");
        }
        User user = userService.findUserByUserName(request.getUserPrincipal().getName());
        modelAndView.addObject("currentUser", user);
        request.getSession().setAttribute("currentUser", user);

        return modelAndView;
    }


    @GetMapping("/searchBox")
    public ModelAndView searchByTerm(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                     @RequestParam(value = "size", defaultValue = "4", required = false) Integer size,
                                     @RequestParam(value = "searchTerm", required = false) String searchTerm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        Page<User> allUsers = userService.searchByTerm(searchTerm.trim(), PageRequest.of(page, size, Sort.by("userName")));
        modelAndView.addObject("allUsers", allUsers);
        modelAndView.addObject("maxTraySize", size);
        modelAndView.addObject("currentPage", page);
        return modelAndView;
    }


    @GetMapping("/search")
    public ModelAndView search() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search");
        return modelAndView;
    }


    @PostMapping("/searchSubmit")
    public ModelAndView searchSubmit(@ModelAttribute SearchDTO searchDto) {
        List<User> result = userService.searchBy(searchDto.getSearchKeyword(), searchDto.getCriteria());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search");
        modelAndView.addObject("result", result);
        return modelAndView;
    }


    @GetMapping("/addNewUser")
    public ModelAndView addNewUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("create-user");
        return modelAndView;
    }


    @ResponseBody
    @PostMapping("/save")
    public Response update(@RequestBody User user) {
        User dbUser = userService.findById(user.getId());
        //dbUser.setUserName(user.getUserName());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setEmail(user.getEmail());
        userService.saveUser(dbUser);
        return new Response(302, AppConstant.SUCCESS, "/");
    }



    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        String result = "redirect:/";
        User dbUser = userService.findUserByUserName(user.getUserName());
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            result = "redirect:/addNewUser?error=Enter valid username";
        } else if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            result = "redirect:/addNewUser?error=Enter valid email";
        } else if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            result = "redirect:/addNewUser?error=Enter valid password";
        } else if (StringUtils.isEmpty(user.getRoleName())) {
            result = "redirect:/addNewUser?error=Select a valid Role";
        }
        if (dbUser == null) {
            userService.saveUser(user);
        } else {
            result = "redirect:/addNewUser?error=User Already Exists!";
        }

        return result;
    }


    @GetMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId) {
        userService.removeById(userId);
        return "redirect:/";
    }


    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable Long userId) {
        userService.resetPassword(userId);
        return "redirect:/";
    }

    @GetMapping("/updatePwd")
    public ModelAndView updatePassword() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update-password");
        return modelAndView;
    }

    @PostMapping("/setNewPassword")
    public String setNewPassword(@ModelAttribute Password pwdObj) {
        String result = "redirect:/";

        String userName = pwdObj.getUserName();
        String oldPwd = pwdObj.getOldPwd();
        String newPwd = pwdObj.getNewPwd();
        System.out.println("setNewPassword: userName="+userName+", oldPwd="+oldPwd+", newPwd="+newPwd);

        if (isNullOrEmpty(userName)) {
            result = "redirect:/updatePwd?error=Enter valid username";
        } else if (isNullOrEmpty(oldPwd)) {
            result = "redirect:/updatePwd?error=Enter valid existing password";
        } else if (isNullOrEmpty(newPwd)) {
            result = "redirect:/updatePwd?error=Enter valid new password";
        } else {
            userService.updatePassword(userName, oldPwd, newPwd);
        }

        return result;
    }

    @ResponseBody
    @GetMapping("/removeAll")
    public Boolean removeAll() {
        return userService.removeAll();
    }


    @GetMapping("/403")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("403");
        return modelAndView;
    }


    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }

    private boolean isNullOrEmpty(String in) {
        return (in==null || in.trim().isEmpty());
    }
}
