package edu.hitsz.DAO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 郑瑜杰
 */
public class PlayerDaoImpl implements PlayerDao{


    private List<PlayerData> players ;

    private Path path = Path.of("Datas\\playerDatas.dat");

    public PlayerDaoImpl(int gameMode) throws IOException {
        players = new LinkedList<>();
        if(gameMode == 0){
            path = Path.of("Datas\\playerDatasEasy.dat");
        }else if(gameMode == 1){
            path = Path.of("Datas\\playerDatasOrdinary.dat");
        }else if(gameMode == 2){
            path = Path.of("Datas\\playerDatasHard.dat");
        }
        if(Files.exists(path)){
            FileInputStream fip = new FileInputStream(path.toFile());
            ObjectInputStream ois = new ObjectInputStream(fip);
            try {
                do{
                    try {
//                        System.out.println("test");
                        PlayerData player = (PlayerData) ois.readObject();
                        players.add(player);
                    }catch (EOFException e){
                        System.out.println("InputError");
                        break;

                    }
                }while (fip.available()>0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<PlayerData> getAllPlayers(){
        return players;
    }

    @Override
    public void updatePlayer(PlayerData player){

    }

    @Override
    public void deletePlayer(int rank){
        if(0<=rank || rank< size()){
            players.remove(rank);
        }
    }

    @Override
    public void addPlayer(PlayerData player){
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getScore() < player.getScore()){
                players.add(i,player);
                return;
            }
        }
        players.add(player);
        System.out.println(player.toString());
        System.out.println(player.getTime());
    }
    @Override
    public void clear() {
        players.clear();
    }
    @Override
    public void save() throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()));
        for (PlayerData player: players) {
            oos.writeObject(player);
//            System.out.println("write");
        }
    }
    @Override
    public int size() {
        return players.size();
    }
    @Override
    public String ShowData() {
        StringBuffer datas = new StringBuffer() ;
        int i = 1;
//        System.out.println("ShowDataTest");
        for (PlayerData player: players) {
            datas.append("\nrank " + i++ +" " + player.toString());
        }
        return datas.toString();

    }


}
