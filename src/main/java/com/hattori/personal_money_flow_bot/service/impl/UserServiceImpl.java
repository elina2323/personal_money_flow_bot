package com.hattori.personal_money_flow_bot.service.impl;

import com.hattori.personal_money_flow_bot.model.User;
import com.hattori.personal_money_flow_bot.repo.UserRepo;
import com.hattori.personal_money_flow_bot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public void saveUser(User user) {
        userRepo.saveAndFlush(user);
    }

    @Override
    public void deleteUser(Long chatId) {
        userRepo.deleteUserByChatId(chatId);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUser(long chatId) {
        return userRepo.findByChatId(chatId);
    }
}
