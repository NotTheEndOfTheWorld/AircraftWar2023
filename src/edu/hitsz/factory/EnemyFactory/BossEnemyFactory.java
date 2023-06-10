package edu.hitsz.factory.EnemyFactory;

import edu.hitsz.aircraft.EnemyAircraft.AbstractEnemy;
import edu.hitsz.aircraft.EnemyAircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements EnemyFactory {

    private int speedX = 2;
    private int speedY = 0;
    private int hp = 400;

    private int maxSpeedY = 0;
    private int maxhp = 1000;

    private static  AbstractEnemy bossEnemy = null;

    public static AbstractEnemy getBoss(){
        return bossEnemy;
    }


    @Override
    public void increaseProperty(int hpInc,int SpeedYInc) {
        if(hp<maxhp-hpInc){
            hp += hpInc;
        }else {
            hp = maxhp;
        }
//        System.out.println("boss 血量增加至"+hp+" ");
    }

    @Override
    public AbstractEnemy creatEnemy(){
        if(bossEnemy ==null || bossEnemy.notValid()){
            bossEnemy =  new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                speedX,
                speedY,
                hp);
            return bossEnemy;
        }
        else{
            return null;
        }
    }
}
