package com.subutai.customer.handler;

import com.subutai.customer.constants.ResponseConstants;
import com.subutai.customer.exception.*;
import com.subutai.customer.result.ResponseData;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 익셉션 헨들러
 */
@RestControllerAdvice
@Slf4j
public class CustomerExceptionHandler {

    /**
     * 누락되거나 유효하지 못하는 파라미터가 있음.
     * @param me
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData> validException(MethodArgumentNotValidException me) {
        log.error(me.getMessage());

        return this.createResponseEntity(400, ResponseConstants.VALIDATION_FAIL);
    }

    /**
     * 계정 중복
     * @param oe
     * @return
     */
    @ExceptionHandler(OverLapException.class)
    public ResponseEntity<ResponseData> overlapException(OverLapException oe) {
        log.error(oe.getMessage());

        return this.createResponseEntity(400, ResponseConstants.EXIST_ID);
    }

    /**
     * 계정이 존재하지 않음.
     * @param ue
     * @return
     */
    @ExceptionHandler(UserDetailsException.class)
    public ResponseEntity<ResponseData> userDetailsException(UserDetailsException ue) {
        log.error(ue.getMessage());

        return this.createResponseEntity(400, ResponseConstants.LOGIN_FAIL_01);
    }

    /**
     * 패스워드가 일치하지 않음.
     * @param pe
     * @return
     */
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ResponseData> passwordNotMatchException(PasswordNotMatchException pe) {
        log.error(pe.getMessage());

        return this.createResponseEntity(400, ResponseConstants.LOGIN_FAIL_02);
    }

    /**
     * 로그인 토큰이 정상적이지 않음.
     * @param te
     * @return
     */
    @ExceptionHandler(TokenNotAccessException.class)
    public ResponseEntity<ResponseData> tokenNotAccessException(TokenNotAccessException te) {
        log.error(te.getMessage());

        return this.createResponseEntity(403, ResponseConstants.TOKEN_NOT_ACCESS);
    }

    /**
     * 올바르게 구성되지 못한 토큰.
     * @param ee
     * @return
     */
    @ExceptionHandler({SecurityException.class, MalformedJwtException.class})
    public ResponseEntity<ResponseData> malformedJwtException(ExpiredJwtException ee) {
        log.error(ee.getMessage());

        return this.createResponseEntity(403, ResponseConstants.TOKEN_NOT_ACCESS);
    }

    /**
     * jwt 형식에 맞지 않음.
     * @param ee
     * @return
     */
    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ResponseData> unsupportedJwtException(UnsupportedJwtException ee) {
        log.error(ee.getMessage());

        return this.createResponseEntity(403, ResponseConstants.TOKEN_NOT_ACCESS);
    }

    /**
     * 타입에 맞지 않음.
     * @param ee
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseData> illegalArgumentException(IllegalArgumentException ee) {
        log.error(ee.getMessage());

        return this.createResponseEntity(403, ResponseConstants.TOKEN_NOT_ACCESS);
    }


    /**
     * 만료된 토큰.
     * @param ee
     * @return
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseData> expiredJwtException(ExpiredJwtException ee) {
        log.error(ee.getMessage());

        return this.createResponseEntity(403, ResponseConstants.TOKEN_NOT_ACCESS);
    }

    /**
     * 계정 미존재.
     * @param ae
     * @return
     */
    @ExceptionHandler(AccountNotExistException.class)
    public ResponseEntity<ResponseData> accountNotExistException(AccountNotExistException ae) {
        log.error(ae.getMessage());

        return this.createResponseEntity(400, ResponseConstants.NOT_EXIST_ACCOUNT);
    }

    /**
     * 번호 미일치.
     * @param pe
     * @return
     */
    @ExceptionHandler(PhoneNumberNotMatchException.class)
    public ResponseEntity<ResponseData> phoneNumberNotMatchException(PhoneNumberNotMatchException pe) {
        log.error(pe.getMessage());

        return this.createResponseEntity(400, ResponseConstants.PHONE_NUMBER_NOT_MATCH);
    }

    /**
     * 전송 이력이 없음.
     * @param se
     * @return
     */
    @ExceptionHandler(SmsSendHistoryNotExistException.class)
    public ResponseEntity<ResponseData> smsSendHistoryNotExistException(SmsSendHistoryNotExistException se){
        log.error(se.getMessage());

        return this.createResponseEntity(400, ResponseConstants.SMS_SEND_HISTORY_NOT_EXIST);
    }

    /**
     * 인증번호 미일치
     * @param ce
     * @return
     */
    @ExceptionHandler(CertificationNumberNotMatchException.class)
    public ResponseEntity<ResponseData> certificationNumberNotMatchException(CertificationNumberNotMatchException ce) {
        log.error(ce.getMessage());

        return this.createResponseEntity(400, ResponseConstants.CERTIFICATION_NUMBER_NOT_MATCH);
    }

    /**
     * 휴대폰 인증시간 만료
     * @param ce
     * @return
     */
    @ExceptionHandler(CertificationExpireException.class)
    public ResponseEntity<ResponseData> certificationExpireException(CertificationExpireException ce) {
        log.error(ce.getMessage());

        return this.createResponseEntity(400, ResponseConstants.CERTIFICATION_EXPIRE);
    }

    /**
     * response entity 생성
     * @param code
     * @param message
     * @return
     */
    private ResponseEntity<ResponseData> createResponseEntity(int code, String message) {
        return new ResponseEntity<>(
                ResponseData
                        .builder()
                        .code(code)
                        .message(message)
                        .build()
                , HttpStatus.BAD_REQUEST
        );
    }



}
