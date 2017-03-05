package com.basic.utils;

public class Time {
    public static final long SECOND = 1_000_000_000;

    public static long get() {
        return System.nanoTime();
    }
}
