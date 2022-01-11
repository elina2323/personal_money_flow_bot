package com.hattori.personal_money_flow_bot;

import com.hattori.personal_money_flow_bot.bot.PersonalMoneyFlowBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class PersonalMoneyFlowBotApplication {

    public static void main(String[] args) {

//        ApiContextInitializer.init();

        SpringApplication.run(PersonalMoneyFlowBot.class, args);
    }

}
