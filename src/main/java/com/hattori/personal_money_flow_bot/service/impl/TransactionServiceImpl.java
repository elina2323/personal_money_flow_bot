package com.hattori.personal_money_flow_bot.service.impl;

import com.hattori.personal_money_flow_bot.model.Transaction;
import com.hattori.personal_money_flow_bot.model.User;
import com.hattori.personal_money_flow_bot.repo.TransactionRepo;
import com.hattori.personal_money_flow_bot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public List<Transaction> getAllTransactionsOfUser(User user) {
        return transactionRepo.findByUserAndEndDateIsNull(user);
    }

    @Override
    public Transaction getLastTransaction(User user) {
        return transactionRepo.findTopByUserAndEndDateIsNullOrderByIdDesc(user);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepo.saveAndFlush(transaction);
    }

    @Transactional
    @Override
    public void deleteAllTransactions(User user){
        transactionRepo.deleteAllByUser(user);
    }

    @Override
    public boolean existUserExpenses(User user) {

        return transactionRepo.existsByUser(user);
    }
}

