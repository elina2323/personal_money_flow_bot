package com.hattori.personal_money_flow_bot.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "balance_amount_history")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BalanceAmountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "amount")
    Long amount;

    @CreationTimestamp
    LocalDateTime addDate;

    @UpdateTimestamp
    LocalDateTime editDate;

    @Column(name = "action")
    String action;

    @ManyToOne
    User user;

    @ManyToOne
    Transaction transaction;
}
