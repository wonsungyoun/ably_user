package com.subutai.customer.domain.sms.repository;

import com.subutai.customer.domain.sms.entity.SmsSendHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SmsSendHistoryRepository extends JpaRepository<SmsSendHistory, Long> {

    List<SmsSendHistory> findAllByEmailAndPhoneNumber(String email, String phoneNumber);
    SmsSendHistory findAllById(Long id);

}
