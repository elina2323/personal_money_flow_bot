package com.hattori.personal_money_flow_bot.repo;

import com.hattori.personal_money_flow_bot.model.Transaction;
import com.hattori.personal_money_flow_bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {


    List<Transaction> findByUserAndEndDateIsNull(User user);

    Transaction findTopByUserAndEndDateIsNullOrderByIdDesc(User user);
}
