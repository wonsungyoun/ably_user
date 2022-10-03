package com.subutai.customer.utils;

import org.junit.Test;

/**
 * 랜덤 유틸 테스트
 */
public class RandomUtilsTest {

    @Test
    public void randomPasswordTest() {
        System.out.println(RandomUtils.randomPassword(10));
    }

}