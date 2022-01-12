package com.hattori.personal_money_flow_bot.service;

import com.hattori.personal_money_flow_bot.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    void deleteUser(Long chatId);
    List<User> getAllUsers();
    User getUser(long chatId);

}
