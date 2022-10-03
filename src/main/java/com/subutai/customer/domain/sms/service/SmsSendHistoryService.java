package com.subutai.customer.domain.sms.service;

import com.subutai.customer.domain.customer.service.CustomerService;
import com.subutai.customer.domain.sms.entity.SmsSendHistory;
import com.subutai.customer.domain.sms.repository.SmsSendHistoryRepository;
import com.subutai.customer.dto.AccountKey;
import com.subutai.customer.dto.RegenerationKey;
import com.subutai.customer.dto.CertificationNumberData;
import com.subutai.customer.dto.CustomerData;
import com.subutai.customer.exception.CertificationNumberNotMatchException;
import com.subutai.customer.exception.PasswordNotMatchException;
import com.subutai.customer.exception.SmsSendHistoryNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SmsSendHistoryService {

    @Autowired
    private SmsSendHistoryRepository smsSendHistoryRepository;

    /**
     * 인증 번호 생성 ( 원래 문자로 보내야하지만 그건 실무에서 하고 여기선 생략한다 )
     * @param accountKey
     * @return
     */
    public CertificationNumberData createNumber(AccountKey accountKey) {
        // (Math.random() * (max-min+1)) + min
        int number = (int)(Math.random() * (99999 - 10000 +1)) + 10000;

        // db 저장.
        SmsSendHistory smsSendHistory = SmsSendHistory.insertRow(accountKey, number);
        smsSendHistoryRepository.save(smsSendHistory);

        return new CertificationNumberData(number);
    }


    /**
     * 인증번호 일치 여부 확인
     * @param key
     * @return
     */
    public boolean certification(RegenerationKey key) {

        List<SmsSendHistory> smsSendHistories = smsSendHistoryRepository.findAllByEmailAndPhoneNumber(key.getEmail(), key.getPhoneNumber());

        if(CollectionUtils.isEmpty(smsSendHistories)) {
            throw new SmsSendHistoryNotExistException("전송 이력이 없습니다.");
        }

        if(smsSendHistories.get(0).getCertificationNumber() != key.getCertificationNumber()) {
            throw new CertificationNumberNotMatchException("인증번호가 맞지 않습니다.");
        }

        SmsSendHistory smsSendHistory = smsSendHistories.get(0);

        Calendar cal = Calendar.getInstance();
        cal.setTime(smsSendHistory.getSendDate());
        cal.add(Calendar.MINUTE, 5);

        Date expireDate = cal.getTime();
        Date nowDate = new Date();

        // 만료 여부 체크.
        return nowDate.before(expireDate);
    }

}
