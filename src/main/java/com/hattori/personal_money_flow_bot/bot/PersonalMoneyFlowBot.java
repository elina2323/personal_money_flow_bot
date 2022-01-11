package com.hattori.personal_money_flow_bot.bot;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonalMoneyFlowBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    String botUsername;

    @Value("${bot.token}")
    String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        String command=update.getMessage().getText();

        SendMessage message = new SendMessage();

        if(command.equals("income")){

            message.setText(update.getMessage().getText());
        }

        if(command.equals("expense")){

            message.setText(update.getMessage().getText());
        }

        if(command.equals("balance")){

            message.setText(update.getMessage().getText());
        }

        message.setChatId(String.valueOf(update.getMessage().getChatId()));


        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
