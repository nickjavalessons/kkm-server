package com.kkm.server.utils;

import com.kkm.server.entity.CheckEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CheckMap extends ConcurrentHashMap<String, CheckEntity> {
}
