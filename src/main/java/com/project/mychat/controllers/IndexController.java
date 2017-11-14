package com.project.mychat.controllers;

import com.project.mychat.models.User;
import com.project.mychat.models.data.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("")
public class IndexController
{
    @Autowired
    private UsersDao usersDao;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index()
    {
        return "index";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.POST)
    public String loginValidation(@RequestParam String username, String password, ModelMap modelMap)
    {
        String nextScreen = "";

        if (username.equals("") || password.equals(""))
        {
            modelMap.put("label", "Fill out all the fields !!!");

            return "index";
        }

        for (User usr : usersDao.findAll())
        {
            if (username.equals(usr.getUsername()) && password.equals(usr.getPassword()))
            {
                usr.setOnline(true);

                usersDao.save(usr);

                modelMap.put("userName", username);

                nextScreen = "chat";

                break;

            } else
            {
                modelMap.put("label", "Wrong username or password !!!");

                nextScreen = "index";
            }
        }
        return nextScreen;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister()
    {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute User newUser, ModelMap modelMap)
    {
        if (newUser.getUsername().equals("") || newUser.getPassword().equals(""))
        {
            modelMap.put("label", "Fill out all the fields !!!");

            return "register";
        }

        for (User usr : usersDao.findAll())
        {
            if(newUser.getUsername().equals(usr.getUsername()))
            {
                modelMap.put("label", "The username exist !!!");

                return "register";
            }
        }

        modelMap.put("userName", newUser.getUsername());

        usersDao.save(newUser);

        return "chat";
    }
}

