package com.globallogic.creditcardpayment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.globallogic.creditcardpayment.entity.CreditCard;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, Long> {
}