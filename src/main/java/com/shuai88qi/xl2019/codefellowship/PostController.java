package com.shuai88qi.xl2019.codefellowship;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    AppUserRepository appUserRepository;


     @GetMapping("/posts/add")
    public String getDinosaurCreator() {
        return "createPost";
    }


    @PostMapping("/posts")
    public RedirectView addPost(String body, Principal p){
       Post newpost = new Post();
       newpost.body=body;
       newpost.createAt=new Timestamp(System.currentTimeMillis());
       AppUser user= appUserRepository.findByUsername(p.getName());
       newpost.appUser=user;
       postRepository.save(newpost);
       return new RedirectView("/myprofile");
    }

    @GetMapping("/posts/{id}")
    public String showDinosaur(@PathVariable long id, Model m, Principal p) {
       Post post = postRepository.findById(id).get();
        // check if that dinosaur belongs to the currently logged in user
        if (post.getAppUser().username.equals(p.getName())) {
            // if so, do the nice things
            m.addAttribute("onepost", post);
            return "post";
        } else {
            // otherwise, tell them no
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
