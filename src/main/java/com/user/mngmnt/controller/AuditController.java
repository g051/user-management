package com.user.mngmnt.controller;

import com.user.mngmnt.model.Audit;
import com.user.mngmnt.model.RoleNames;
import com.user.mngmnt.model.User;
import com.user.mngmnt.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class AuditController {

    @Autowired
    private AuditService auditService;

    @Value("${audit.result.per.page}")
    private int maxResults;

    @Value("${max.card.display.on.pagination.tray}")
    private int maxPaginationTraySize;

    @GetMapping("/audit")
    public ModelAndView listAudit(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                             @RequestParam(value = "size", defaultValue = "4", required = false) Integer size,
                             HttpServletRequest request, HttpServletResponse response) {

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> list = new ArrayList<>();
        authorities.forEach(e -> {
            list.add(e.getAuthority());
        });

        System.out.println("Inside AuditController.listAudit() called by: "+ request.getRequestURI());

        if(size<maxResults)
            size = maxResults;
        String userName = ((User)request.getSession().getAttribute("currentUser")).getUserName();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("transactions");
        modelAndView.addObject("maxTraySize", size);
        modelAndView.addObject("currentPage", page);

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp"));
        Page<Audit> audits = list.contains(RoleNames.ADMIN.name())?
                auditService.listAudits(pageable):
                auditService.findAuditByActor(userName, pageable);
        modelAndView.addObject("audits", audits);

        return modelAndView;
    }
}
