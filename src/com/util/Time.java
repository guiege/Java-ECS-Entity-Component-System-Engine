package com.util;

public class Time {

    public static double timeStarted = System.nanoTime();

    public static double getTime(){
        return (System.nanoTime() - timeStarted) * 1E-9;// 1E-9 Returns time in seconds to make it easier to work with
    }
}
