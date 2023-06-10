package edu.hitsz.DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface PlayerDao {
    public List<PlayerData> getAllPlayers();
    public void updatePlayer(PlayerData player);
    public void deletePlayer(int rank);
    public void addPlayer(PlayerData player);

    public void clear();

    public void save()throws IOException;
    public int size();

    public String ShowData();
}
