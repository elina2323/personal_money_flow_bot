package com.hattori.personal_money_flow_bot.repo;

import com.hattori.personal_money_flow_bot.model.BalanceAmountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceAmountRepo extends JpaRepository<BalanceAmountHistory, Long> {

    BalanceAmountRepo findBalanceAmountHistoryByAction(String action);
}
