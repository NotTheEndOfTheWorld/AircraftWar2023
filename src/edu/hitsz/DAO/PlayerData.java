package edu.hitsz.DAO;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerData implements Serializable {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    public static PlayerData
    private String name;
    private int score;

    private Date dateTime;


    public PlayerData(String name, int score, Date dateTime){
        this.name = name;
        this.score = score;
        this.dateTime = dateTime;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public Date getTime(){
        return  dateTime;
    }

    public String toString(){
        return String.join(",",name,
                Integer.toString(score),
                format.format(dateTime));
    }




}

