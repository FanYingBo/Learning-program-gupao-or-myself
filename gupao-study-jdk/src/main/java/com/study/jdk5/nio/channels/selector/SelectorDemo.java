package com.study.jdk5.nio.channels.selector;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 *
 */
public class SelectorDemo {
    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
