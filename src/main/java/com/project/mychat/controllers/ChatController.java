package com.project.mychat.controllers;

import com.project.mychat.models.User;
import com.project.mychat.models.data.UsersDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController
{
    @Autowired
    private UsersDao usersDao;

    @MessageMapping("/listUsers")
    @SendTo("/topic/listUsers")
    public String listUsers(String message)
    {
        JSONObject jsonObject = new JSONObject();

        for (User user : usersDao.findAll())
        {
            jsonObject.put(user.getUsername(), user.isOnline());
        }

        return jsonObject.toString();
    }

    @MessageMapping("/visible")
    @SendTo("/topic/visible")
    public String setVisibility(String message, @ModelAttribute User user)
    {
        JSONObject jsonObject = new JSONObject(message);

        user = usersDao.findUserByUsername(jsonObject.get("userName").toString());

        user.setOnline((boolean)jsonObject.get("message"));

        usersDao.save(user);

        return "";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public String responce(String message)
    {
        return message;
    }
}
