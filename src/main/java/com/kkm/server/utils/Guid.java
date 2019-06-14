package com.kkm.server.utils;

import java.util.UUID;

public class Guid {
    public static String guid(){
        return UUID.randomUUID().toString();
    }
}
