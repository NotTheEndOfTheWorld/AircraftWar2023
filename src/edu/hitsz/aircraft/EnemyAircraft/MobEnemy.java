package edu.hitsz.aircraft.EnemyAircraft;

import edu.hitsz.item.ObserverDemo.BombSubscriber;
import edu.hitsz.application.Game.Game;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.item.AbstractItem;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */


public class MobEnemy extends AbstractEnemy implements BombSubscriber {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 分数
     */
    private static int score = 10;

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

    public int getScore(){
        return score;
    }

    @Override
    public List<AbstractItem>  getItem(){
        return null;
    }

    @Override
    public void bombUpdate() {
        Game.addScore(score);
        vanish();
    }

    @Override
    public void vanish(){
        super.vanish();
    }
}
