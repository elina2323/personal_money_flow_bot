package com.hattori.personal_money_flow_bot;

import com.hattori.personal_money_flow_bot.bot.PersonalMoneyFlowBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class PersonalMoneyFlowBotApplication {

    public static void main(String[] args) {

//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(new PersonalMoneyFlowBot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }


        SpringApplication.run(PersonalMoneyFlowBotApplication.class, args);
    }

}
