package com.jscode.myBooks.Controller;

import com.jscode.myBooks.Entity.User;
import com.jscode.myBooks.Errors.ServiceError;
import com.jscode.myBooks.Service.UserSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserControl {
    @Autowired
    UserSV userSV;

    @GetMapping("/new_user")
    public String userForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new_user_form";
    }

    @PostMapping("/user")
    public String newUser(@ModelAttribute("user") User user) {
        try {
            userSV.saveUser(user);
            return "redirect:/user/search";
        } catch (ServiceError e) {
            return "error";
        }
    }

    @GetMapping("/user/search")
    public String userIndex(Model model, @Param("keyword") String keyword) {
        User user = userSV.searchByName(keyword);
        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);
        return "find_user";
    }
}
