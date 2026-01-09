package com.main;

import com.tomo.Window;

public class Main {
    public static void main(String[] args) {

        Window window = Window.getWindow();

        window.init();
        window.run();

        Thread mainThread = new Thread(window);
    }
}
