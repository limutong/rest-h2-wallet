package com.mt.wallet.utils;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class WalletUtil {

    public static boolean isValidCoinStr(String coins) {
        if(Strings.isBlank(coins)){
            return false;
        }
        AtomicBoolean result = new AtomicBoolean(true);
        Arrays.stream(coins.split(",")).forEach(str->{
            if(!isPositiveInteger(str)){
                result.set(false);
            }
        });
        return result.get();
    }

    public static boolean isPositiveInteger(String s) {
        if(s.isEmpty()) return false;
        s = s.trim();
        for(int i = 0; i < s.length(); i++) {
            if(Character.digit(s.charAt(i),10) < 0) return false;
        }
        return true;
    }
}
