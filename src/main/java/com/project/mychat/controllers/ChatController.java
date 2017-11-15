package com.project.mychat.controllers;

import com.project.mychat.models.Message;
import com.project.mychat.models.User;
import com.project.mychat.models.data.MessageDao;
import com.project.mychat.models.data.UsersDao;
import org.apache.tomcat.jni.Time;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatController
{
    @Autowired
    private UsersDao usersDao;

    @Autowired
    private MessageDao messageDao;

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
    public Message responce(String message)
    {
        JSONObject jsonObject = new JSONObject(message);

        String text = jsonObject.get("message").toString();

        String userName = jsonObject.get("userName").toString();

        Message newMessage = new Message();

        newMessage.setUserName(userName);
        newMessage.setText(text);
        newMessage.setDate(Date.valueOf(LocalDate.now()));
        newMessage.setTime(java.sql.Time.valueOf(LocalTime.now()));

        messageDao.save(newMessage);

        jsonObject.put("time", newMessage.getTime());

        return newMessage;
    }

    @MessageMapping("/listMessages")
    @SendTo("/topic/listMessages")
    public List<Message> listMessages(String message)
    {
        List<Message> messages = new ArrayList<>();

        messageDao.findAll().forEach(messages::add);

        return messages;
    }
}
