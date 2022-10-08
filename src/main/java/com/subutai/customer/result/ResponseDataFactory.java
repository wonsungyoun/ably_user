package com.subutai.customer.result;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class ResponseDataFactory {

    /**
     * 성공시 클라이언트에게 반환할 데이터
     * @param data
     * @return
     */
    public ResponseEntity getSuccessResponseEntity(Object data) {
        return ResponseEntity.created(this.createURI()).body(data);
    }

    private URI createURI() {

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath().toUriString());

        return uri;
    }

}
