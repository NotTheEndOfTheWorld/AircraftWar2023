package edu.hitsz.ThreadDemo;

import edu.hitsz.application.ThreadDemo.MusicThread;

public class ThreadTest {

    public static void main(String[] args) {

        new MusicThread("src/bgm.wav").start();

    }
}
