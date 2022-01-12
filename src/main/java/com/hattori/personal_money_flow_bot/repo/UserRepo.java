package com.hattori.personal_money_flow_bot.repo;

import com.hattori.personal_money_flow_bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByChatId(long chatId);
    void deleteUserByChatId(long chatId);
}
