package com.example.demo.controller;

import com.example.demo.domain.Board;
import com.example.demo.domain.User;
import com.example.demo.service.BoardService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired BoardService boardService;
    @Autowired UserService userService;
    @Autowired PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> list = boardService.selectBoardList();

        model.addAttribute("list", list);

        return "/list";
    }

    @GetMapping("/")
    public String home() {
        logger.debug("디버그입니다.");
        logger.info("인포입니다.");

        return "/index";
    }

    @GetMapping("/form")
    public String beforeSignup() {
        return "/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        //유저 데이터 세팅
        user.setPassword(encodedPassword);
        user.setAccountNonExpired(true);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER"));

        //유저 생성
        userService.createUser(user);
        //유저 권한 생성
        userService.createAuthorities(user);

        return "/login";
    }

    @GetMapping(value="/login")
    public String beforeLogin(Model model) {
        return "/login";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping(value="/admin")
    public String admin(Model model) {
        return "/admin";
    }

    @Secured({"ROLE_USER"})
    @GetMapping(value="/user/info")
    public String userInfo(Model model) {

        return "/user_info";
    }

    @GetMapping(value="/denied")
    public String denied(Model model) {
        return "/denied";
    }
}
