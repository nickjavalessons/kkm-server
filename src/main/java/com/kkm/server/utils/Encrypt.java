package com.kkm.server.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encrypt {
    private static final String KEY = "5peqCGD_OYjXWgEoR7J6x0z3vtBwsaTZrnc1.NHAil,FyfdPL2mIu9U-QV?hKbk!8M4S";
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static byte[] code(byte[] text)  {
        byte[] key = KEY.getBytes(CHARSET);
        byte[] res = new byte[text.length];
        for (int i = 0; i < text.length; i++) {
             res[i] =(byte) (text[i] ^ key[i% key.length]);
        }
        return res;
    }
    public static String encode(String str){
        if(str == null) return null;
        return Base64.getEncoder().encodeToString(code(str.getBytes(CHARSET)));
    }
    public static String decode(String str) {
        if(str == null) return null;
        return new String(code(Base64.getDecoder().decode(str)), CHARSET);
    }
    private Encrypt(){}
    public static void main(String[] args) {
           // System.out.println(encrypt("С"));
        String enc = encode("Съешь ещё этих мягких французских булок, да выпей чаю.");
        String dec = decode(enc);
        System.out.println(enc);
        System.out.println(dec);
    }
}
