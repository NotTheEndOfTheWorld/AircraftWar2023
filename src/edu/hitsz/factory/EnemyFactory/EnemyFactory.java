package edu.hitsz.factory.EnemyFactory;

import edu.hitsz.aircraft.EnemyAircraft.AbstractEnemy;

public interface EnemyFactory {
//     int speedX = 2;
//     int speedY = 5;
//     int hp = 30;
//
//     int maxSpeedY = 20;
//     int maxhp = 100;
     void increaseProperty(int hpInc,int SpeedYInc);

     AbstractEnemy creatEnemy();
}
