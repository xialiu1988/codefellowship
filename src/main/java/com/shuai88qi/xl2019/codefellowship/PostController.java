package com.shuai88qi.xl2019.codefellowship;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    AppUserRepository appUserRepository;


     @GetMapping("/posts/add")
    public String getCreatePost(Model m) {
         m.addAttribute("newpost",new Post());
        return "createPost";
    }

 // trying to show the field error msg but not working. it will not show the error msg on html
    @PostMapping("/posts")
    public String addPost(@Valid Post newpost, BindingResult bindingResult, Principal p,Model m){
        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            m.addAttribute("newpost",new Post());
            return "createPost";
        }
       newpost.createAt=new Timestamp(System.currentTimeMillis());
       AppUser user= appUserRepository.findByUsername(p.getName());
       newpost.appUser=user;
       postRepository.save(newpost);
       return "redirect:/myprofile";
    }

    @GetMapping("/posts/{id}")
    public String showPost(@PathVariable long id, Model m, Principal p) {
       Post post = postRepository.findById(id).get();
        // check if that post belongs to the currently logged in user
        if (post.getAppUser().username.equals(p.getName())) {
            m.addAttribute("onepost", post);
            return "post";
        } else {
            throw new NotyourPostsException("Please create your own posts.");
        }


    }

    // came from https://stackoverflow.com/questions/2066946/trigger-404-in-spring-mvc-controller
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    class NotyourPostsException extends RuntimeException {
        public NotyourPostsException(String s) {
            super(s);
        }
}


}
