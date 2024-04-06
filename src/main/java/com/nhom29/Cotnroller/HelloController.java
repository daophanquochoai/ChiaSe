package com.nhom29.Cotnroller;

import com.nhom29.LoginRequest;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Controller
public class HelloController {
    @GetMapping("/login")
    public String hello(){
        return "login";
    }
    @GetMapping("/home")
    public String home(){
        return "home";
    }
    @GetMapping("404")
    public String denied(){
        return "404";
    }
//    @PostMapping("/sign-in")
//    public String authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
//        Authentication authentication = authe
//    }
}
