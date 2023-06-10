package edu.hitsz.application.SwingDemo;

import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.*;

public class CardLayoutDemo {

    public static final CardLayout cardLayout = new CardLayout(0,0);
    public static final JPanel cardPanel = new JPanel(cardLayout);

    public static void main() {

//        JFrame frame = new JFrame("CardLyaout Demo");
        JFrame frame = new JFrame("Aircraft War");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - Main.WINDOW_WIDTH) / 2, 0,
                Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(cardPanel);

        GameMenu start = new GameMenu();
        cardPanel.add(start.getMainPanel());
        frame.setVisible(true);
    }
}
