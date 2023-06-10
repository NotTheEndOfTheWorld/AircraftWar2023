package edu.hitsz.bullet;

import edu.hitsz.item.ObserverDemo.BombSubscriber;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements BombSubscriber {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void bombUpdate(){
        vanish();
    }

    @Override
    public void vanish(){
        super.vanish();
    }


}
