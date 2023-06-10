package edu.hitsz.application.SwingDemo;

import edu.hitsz.DAO.PlayerDao;
import edu.hitsz.DAO.PlayerDaoImpl;
import edu.hitsz.DAO.PlayerData;
import edu.hitsz.application.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

public class ScoreTable {
    private JPanel mainPanel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JScrollPane tableScrollPane;
    private JLabel rankLabel;
    private JPanel topPanel;
    private JPanel buttomPanel;
    private JTextField GameDifficulty;

    private final PlayerDao playerDao;

    public JPanel getMainPanel(){
        return mainPanel;
    }

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int DefaltHeight = Main.WINDOW_HEIGHT/4+Main.WINDOW_HEIGHT/2;

    private Dimension presizetop = new Dimension(Main.WINDOW_WIDTH,DefaltHeight);

    private Dimension buttomPanelSize = new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT-DefaltHeight);

    private Dimension buttomSize = new Dimension(Main.WINDOW_WIDTH/2,Main.WINDOW_HEIGHT/8);

    public ScoreTable(int score,int gamemode) throws IOException {
        //设置大小
        topPanel.setPreferredSize(presizetop);
        buttomPanel.setPreferredSize(buttomPanelSize);
        deleteButton.setPreferredSize(buttomSize);


        //根据游戏难度读取相应的游戏数据
        playerDao = new PlayerDaoImpl(gamemode);

        //玩家输入用户名以记录数据
        String inputname = JOptionPane.showInputDialog(null,"游戏结束，你的分数为："+ score+ "\n请输入名字记录得分：");
        if(inputname!= null){
            PlayerData player = new PlayerData(inputname,score,new Date());
            playerDao.addPlayer(player);
            try {
                playerDao.save();
//               System.out.println("save success");
            } catch (IOException e) {
             e.printStackTrace();
         }
         }
        //显示游戏难度
        String showMode = "难度：";
        if(gamemode == 1){
            showMode += "普通";
        }else if(gamemode == 2){
            showMode += "困难";
        }else {
            showMode += "简单";
        }
        GameDifficulty.setText(showMode);

        //展示用户数据
        String[] columnName = {"排名","玩家名字","分数","时间"};
        int num = playerDao.size();
        String[][]tableData = new String[num][4];
        List<PlayerData>  players = playerDao.getAllPlayers();
        for (int i = 0; i < num; i++) {
            PlayerData player = players.get(i);
            tableData[i][0] = Integer.toString(i+1);
            tableData[i][1] = player.getName();
            tableData[i][2] = Integer.toString(player.getScore());
            tableData[i][3] = format.format(player.getTime());
        }
        DefaultTableModel model = new DefaultTableModel(tableData,columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        scoreTable.setModel(model);
        tableScrollPane.setViewportView(scoreTable);

    deleteButton.addActionListener(new ActionListener() {
        //删除按钮
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = scoreTable.getSelectedRow();
            System.out.println(row);
            int result = JOptionPane.showConfirmDialog(deleteButton,
                    "是否确定中删除？");
            if (JOptionPane.YES_OPTION == result && row != -1) {
                model.removeRow(row);
                //删除dataDao对应行的数据并且保存
                playerDao.deletePlayer(row);
                try {
                    playerDao.save();
                } catch (IOException eio){
                    eio.printStackTrace();
                }
            }
        }
    });

}

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("HardModeTable");
//        frame.setContentPane(new ScoreTable().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }
}
