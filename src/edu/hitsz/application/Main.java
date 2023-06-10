package edu.hitsz.application;

import edu.hitsz.application.Game.Game;
import edu.hitsz.application.SwingDemo.CardLayoutDemo;

import java.io.IOException;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static Game game;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello Aircraft War");
        /*
            lab5-6
            CardLayoutDemo 将会创建菜单  StartMenu
            StartMenu 用户点击不同难度button后，button监听器将创建对应难度的游戏game
            Game 游戏结束后 自动创建分数表格ScoreTable
            ScoreTable分数表格在展示前会先 调用JOptionPane.showInputDialog要求用户输入用户名 数据
            输入完数据后ScoreTable将展示相应难度的游戏数据
         */
        CardLayoutDemo.main();
    }
}
