package com.shuai88qi.xl2019.codefellowship;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class AppUserController {
   @Autowired
    AppUserRepository appUserRepository;

   @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }






    @GetMapping("/signup")
    public String getSignupPage(Model m) {
        m.addAttribute("newuser",new AppUser());
        return "signup";
    }



    @PostMapping("/signup")
    public String getSignUp(@ModelAttribute AppUser newuser){
        AppUser res = new AppUser(newuser.username,passwordEncoder.encode(newuser.password),newuser.firstName,newuser.lastName,newuser.dateofBirth,newuser.bio);
        appUserRepository.save(res);

        AppUser user = appUserRepository.findByUsername(res.username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(res, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        long id = user.id;
        return "redirect:/users/"+id;
    }


    @GetMapping("/users/{id}")
    public String getMyProfile(Principal p, Model m){


        AppUser currentUser = (AppUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();

        m.addAttribute("principal",currentUser);
        return "myprofile";
    }



    @GetMapping("/myprofile")
    public String afterLogin(Principal p, Model m){

     //reference from Evan! yeah!
        AppUser currentUser = (AppUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();

        m.addAttribute("principal",currentUser);
        return "myprofile";
    }

}
