package com.kkm.server.utils;

import java.math.BigDecimal;

public class RoundDouble {
    public static double roundDown(double value){
        int decimalsToConsider = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        BigDecimal roundedWithScale = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
        return roundedWithScale.doubleValue();
    }
}
