package edu.hitsz.factory.EnemyFactory;

import edu.hitsz.aircraft.EnemyAircraft.AbstractEnemy;
import edu.hitsz.aircraft.EnemyAircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyFactory implements EnemyFactory {

    //控制初始产生时横向速度为左还是右
    private double leftOrRight;
    private int speedX = 2;
    private int speedY = 5;
    private int hp = 10;

    private int maxSpeedY = 10;
    private int maxhp = 90;

    @Override
    public void increaseProperty(int hpInc,int SpeedYInc) {
        if(hp<maxhp-hpInc){
            hp += hpInc;
        }else {
            hp = maxhp;
        }
        System.out.print("精英机血量增加至"+hp+" ");
        if(speedY < maxSpeedY - SpeedYInc){
            speedY+=SpeedYInc;
        }else {
            speedY = maxSpeedY;
        }
        System.out.println("速度增加至"+speedY);
    }
    @Override
    public AbstractEnemy creatEnemy(){
        leftOrRight = Math.random();
        if(leftOrRight<0.5){
            speedX = -speedX;
        }
        return new EliteEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                speedX,
                speedY,
                hp
        );
    }
}
