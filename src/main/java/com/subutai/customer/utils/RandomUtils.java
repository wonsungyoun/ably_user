package com.subutai.customer.utils;

public class RandomUtils {

    /**
     * 무작위 패스워드 설정
     * @param length
     * @return
     */
    public static String randomPassword(int length) {
        if(length < 1) {
            return "";
        }

        char[] tmp = new char[length];

        for(int i=0; i<tmp.length; i++) {
            int randomCnt = (int) Math.floor(Math.random() * 3); // 0,1,2 반환

            if(randomCnt == 0) { // 0이 걸릴시 숫자
                tmp[i] = (char)(Math.random() * 10 + '0');
            }else if(randomCnt == 1) { // 1이 걸릴시 알파벳
                tmp[i] = (char)(Math.random() * 26 + 'A');
            }else { // 2가 걸릴시 특수문자(!,",#,$,%,&)
                tmp[i] = (char)(Math.random() * 6 + '!');
            }
        }

        return new String(tmp);
    }
}
