package com.hattori.personal_money_flow_bot.service;

import com.hattori.personal_money_flow_bot.model.Transaction;
import com.hattori.personal_money_flow_bot.model.User;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactionsOfUser(User user);
    Transaction getLastTransaction(User user);

    Transaction save(Transaction transaction);
}
