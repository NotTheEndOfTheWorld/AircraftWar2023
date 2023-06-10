package edu.hitsz.application.SwingDemo;

import edu.hitsz.application.Game.EasyGame;
import edu.hitsz.application.Game.Game;
import edu.hitsz.application.Game.HardGame;
import edu.hitsz.application.Game.OrdinaryGame;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

public class GameMenu {
    private  JPanel mainPanel;
    private JPanel topPanel;
    private JButton easyModeButton;
    private JButton mediumModeButton;
    private JButton hardModeButton;
    private JPanel bottomPanel;
    private JComboBox MusicComboBox1;
    private JLabel musicLabel;

    private static final  int DEFAULT_WIDTH = 240;
    private static final int DEFAULT_HEIGHT = 120;

    public  JPanel getMainPanel() {
        return mainPanel;
    }

    public GameMenu() {

        Dimension preferredSize = new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        easyModeButton.setPreferredSize(preferredSize);
        mediumModeButton.setPreferredSize(preferredSize);
        hardModeButton.setPreferredSize(preferredSize);

        Dimension MusicLabelSize = new Dimension(DEFAULT_WIDTH/2,DEFAULT_HEIGHT/2);

        musicLabel.setPreferredSize(MusicLabelSize);
        MusicComboBox1.setPreferredSize(MusicLabelSize);

    easyModeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("选择简单模式");
            try {
                ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            }catch (IOException ex){
                ex.printStackTrace();
            }

            Main.game  = new EasyGame();
            Main.game.setGamemode(0);//简单
            CardLayoutDemo.cardPanel.add(Main.game);
            CardLayoutDemo.cardLayout.last(CardLayoutDemo.cardPanel);
            Main.game.action();
        }
    });
    mediumModeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("选择普通模式");
            try {
                ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            }catch (IOException ex){
                ex.printStackTrace();
            }

            Main.game  = new OrdinaryGame();
            Main.game.setGamemode(1);//普通
            CardLayoutDemo.cardPanel.add(Main.game);
            CardLayoutDemo.cardLayout.last(CardLayoutDemo.cardPanel);
            Main.game.action();
        }
    });

    hardModeButton.addActionListener(e -> {
        System.out.println("选择困难模式");
        try {
            ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
        }catch (IOException ex){
            ex.printStackTrace();
        }

        Main.game  = new HardGame();
        Main.game.setGamemode(2);//困难
        CardLayoutDemo.cardPanel.add(Main.game);
        CardLayoutDemo.cardLayout.last(CardLayoutDemo.cardPanel);
        Main.game.action();
    });
    MusicComboBox1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String music = (String) MusicComboBox1.getSelectedItem();
            if (music.equals("开启")){
                Game.setMusic(true);
                System.out.println("音乐开启");
            }else {
                Game.setMusic(false);
                System.out.println("音乐关闭");
            }
        }
    });
    }

//    public static void main() {
//        JFrame frame = new JFrame("Menu");
//        frame.setContentPane(new GameMenu().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }
}
