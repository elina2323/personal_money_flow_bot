package com.hattori.personal_money_flow_bot.bot;

import com.hattori.personal_money_flow_bot.model.Transaction;
import com.hattori.personal_money_flow_bot.model.User;
import com.hattori.personal_money_flow_bot.service.TransactionService;
import com.hattori.personal_money_flow_bot.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonalMoneyFlowBot extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(PersonalMoneyFlowBot.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Override
    public String getBotUsername() {
        return "@personal_money_flow_bot";
    }

    @Override
    public String getBotToken() {
        return "5085248917:AAHbDvPN_1xqKTt-tHHcyfO4ZCY2HCQ9ImE";
    }

    @Override
    public void onUpdateReceived(Update update) {


        Long chatId = update.getMessage().getChatId();

        User user = userService.getUser(chatId);
        SendMessage messageForUser = new SendMessage();
        StringBuilder stringBuilder = new StringBuilder();

        if (Objects.isNull(user)){
            user = new User();
            user.setChatId(chatId);
            user.setName(update.getMessage().getFrom().getUserName());
            user.setBalanceAmount(0L);
            user = userService.saveUser(user);
            stringBuilder.append("Hi\t").append(update.getMessage().getFrom().getUserName())
                    .append("!\n")
                    .append("Budget to get balance\n")
                    .append("Budget + {amount} to top up balance\n")
                    .append("Expenses to get total expenses\n")
                    .append("Add expense format [item]-[price]\n")
                    .append("Delete last- to remove last added expense\n")
                    .append("Clear all - to remove all actions");
        }else {
            String command=update.getMessage().getText().trim();

            if (command.startsWith("Budget")){
                if (command.equalsIgnoreCase("Budget")){
                    stringBuilder.append("Balance left:"+user.getBalanceAmount());
                }else if (command.matches("Budget\\s\\+\\s\\d+")) {
                    command = command.replaceAll("[^\\d.]", "");
                    Long topUp = Long.parseLong(command);
                    if (topUp < 1) {
                        stringBuilder.append("Add balance greater than 1");
                    } else {
                        Long userBalance = user.getBalanceAmount();
                        user.setBalanceAmount(userBalance + topUp);
                        user = userService.saveUser(user);
                        stringBuilder.append("Budget updated from\t")
                                .append(userBalance)
                                .append("\tto\t")
                                .append(user.getBalanceAmount());
                    }
                }else {
                    stringBuilder.append("does not match [budget] + [amount]");
                }
                }else if (command.startsWith("Expenses")){
                    if (command.equalsIgnoreCase("Expenses")) {
                        List<Transaction> transactionList = transactionService.getAllTransactionsOfUser(user);
                        if (Objects.isNull(transactionList) || transactionList.isEmpty()) {
                            stringBuilder.append("Expenses do not exist");
                        } else {
                            Long totalExpense = transactionList.stream().mapToLong(x -> x.getExpense()).sum();
                            stringBuilder.append("Total expenses = ")
                                    .append(totalExpense);
                        }
                    }
                }else if (command.matches("^\\w+\\s-\\s\\d+")) {
                Transaction transaction = new Transaction();
                int nameIndex = command.indexOf("-");
                String name = command.substring(0, nameIndex - 1);
                transaction.setTransactionName(name);
                command = command.replaceAll("[^\\d.]", "");
                Long expenseAmount = Long.parseLong(command);
                if (expenseAmount < 1) {
                    stringBuilder.append("Expense must be greater than or equal 1");
                }else if (user.getBalanceAmount()<expenseAmount){
                    stringBuilder.append("Not enough money on the balance\t"+user.getBalanceAmount()+"<"+expenseAmount);
                }else {
                    transaction.setExpense(expenseAmount);
                    user.setBalanceAmount(user.getBalanceAmount() - expenseAmount);
                    user = userService.saveUser(user);
                    transaction.setUser(user);
                    transaction = transactionService.save(transaction);
                    stringBuilder.append("successfully added expense");
                }
            }else  if (command.equalsIgnoreCase("Delete last")){

                Transaction transaction = transactionService.getLastTransaction(user);
                if (Objects.isNull(transaction)){
                    stringBuilder.append("There is no last expense");
                }else {
                    transaction.setEndDate(LocalDateTime.now());
                    user.setBalanceAmount(user.getBalanceAmount()+transaction.getExpense());
                    userService.saveUser(user);
                    transaction = transactionService.save(transaction);
                    stringBuilder.append("Deleting:\t")
                            .append(transaction.getTransactionName())
                            .append("," + transaction.getExpense());
                }

            }else if (command.equalsIgnoreCase("Clear all")){
                boolean existExpenses = transactionService.existUserExpenses(user);
                if (!existExpenses){
                    stringBuilder.append("Not exist expenses");
                }else {
                    transactionService.deleteAllTransactions(user);
                    stringBuilder.append("Expenses cleared");
                    user.setBalanceAmount(0L);
                    userService.saveUser(user);
                }
            }
            else {
                stringBuilder.append("Write commands below\n")
                        .append("Budget to get balance\n")
                        .append("Budget + {amount} to top up balance\n")
                        .append("Expenses get total expenses\n")
                        .append("Add expense format [item]-[price]\n")
                        .append("Delete last - to remove last added expense\n")
                        .append("Clear all - to remove all actions");
            }
            }
        messageForUser.setChatId(String.valueOf(chatId));
        messageForUser.setText(stringBuilder.toString());
        try {
            execute(messageForUser);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }




    }

