package com.kkm.server.utils;

import com.kkm.server.cash.CashReceipt;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class CheckQueue extends LinkedBlockingQueue<CashReceipt> {

}
