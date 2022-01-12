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
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "expense")
    Long expense;

    @Column(name = "transaction_name")
    String transactionName;

    @ManyToOne
    User user;

    @CreationTimestamp
    LocalDateTime addDate;

    @UpdateTimestamp
    LocalDateTime editDate;

    @UpdateTimestamp
    LocalDateTime endDate;


}
