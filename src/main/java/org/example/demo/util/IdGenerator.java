package org.example.demo.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger counter = new AtomicInteger(1);
    private static final String PREFIX = "C";
    private static final int ID_LENGTH = 3;

    public static String generateId() {
        int idNumber = counter.getAndIncrement();
        return String.format("%s%0" + ID_LENGTH + "d", PREFIX, idNumber);
    }
}