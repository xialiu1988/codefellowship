package com.shuai88qi.xl2019.codefellowship;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class AppUserController  {
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
        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        m.addAttribute("principal",currentUser);
        return "myprofile";
    }


    @GetMapping("/explore")
    public String getOtherUsers(Principal p,Model m){
         //get current user
        AppUser curr =appUserRepository.findByUsername(p.getName());
        //display all the users that are not followed by logged user
      Iterable<AppUser> allUsers = appUserRepository.findAll();
      //convert iterable->list
        List<AppUser> myList = toList(allUsers);
        Set<AppUser> followingUsers = curr.followingUsers;
      for(AppUser a: followingUsers){
          myList.remove(a);
      }
        //remove myself from the list
        myList.remove(curr);
        //add the myList to the modle
        m.addAttribute("potential",myList);
        return "explore";
    }

//convert iterable=>list

   private List<AppUser> toList(Iterable<AppUser> iterable) {
        ArrayList<AppUser> list = new ArrayList<AppUser>();
        if(iterable != null) {
            for(AppUser e: iterable) {
                list.add(e);
            }
        }
        return list;
    }


    @GetMapping("/explore/{id}")
    public String getTheUserPage(@PathVariable Long id,Model m){
        AppUser theUser = appUserRepository.findById(id).get();
        m.addAttribute("theUser",theUser);
        m.addAttribute("request",false);
        return "publicProfile";
    }

  @PostMapping("/explore/{id}")
    public String followTheUser(@PathVariable Long id,Principal p,Model m){

        //get the user i want to follow
      AppUser theUser = appUserRepository.findById(id).get();
      //current logged in user
      AppUser currUser = appUserRepository.findByUsername(p.getName());

      //add theUser to the following list
      currUser.followingUsers.add(theUser);
      appUserRepository.save(currUser);
      m.addAttribute("theUser",theUser);
      m.addAttribute("request",true);
      return "publicProfile";

  }


  @GetMapping("/feed")
    public String getFeed(Model m,Principal p){
      //current logged in user
      AppUser currUser = appUserRepository.findByUsername(p.getName());
        Set<AppUser> followingUsers = currUser.followingUsers;
        m.addAttribute("following",followingUsers);
        return "feed";
  }


}
