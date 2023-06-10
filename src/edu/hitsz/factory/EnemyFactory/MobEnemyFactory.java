package edu.hitsz.factory.EnemyFactory;

import edu.hitsz.aircraft.EnemyAircraft.AbstractEnemy;
import edu.hitsz.aircraft.EnemyAircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory implements EnemyFactory {

    private int speedX = 0;
    private int speedY = 10;
    private int hp = 10;

    private int maxSpeedY = 15;
    private int maxhp = 90;

    @Override
    public void increaseProperty(int hpInc,int SpeedYInc) {
        if(hp<maxhp-hpInc){
            hp += hpInc;
        }else {
            hp = maxhp;
        }
        System.out.print("普通机血量增加至"+hp+" ");
        if(speedY < maxSpeedY - SpeedYInc){
            speedY+=SpeedYInc;
        }else {
            speedY = maxSpeedY;
        }
        System.out.println("速度增加至"+speedY);
    }

    @Override
    public AbstractEnemy creatEnemy(){
        return new MobEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                speedX,
                speedY,
                hp
        );
    }
}
