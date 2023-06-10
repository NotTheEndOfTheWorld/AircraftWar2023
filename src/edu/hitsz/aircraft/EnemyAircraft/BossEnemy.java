package edu.hitsz.aircraft.EnemyAircraft;

import edu.hitsz.ShootStrategy.DivergentShootStragety;
import edu.hitsz.application.Game.Game;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.ItemFactory.RandomItemGenerator;
import edu.hitsz.item.AbstractItem;
import edu.hitsz.item.ObserverDemo.BombSubscriber;

import java.util.LinkedList;
import java.util.List;

/**
 * Boss敌机
 * 可射击
 *
 * @author 郑瑜杰
 */
public class BossEnemy extends AbstractEnemy implements BombSubscriber {

    /**
     * 分数
     */
    private static int score = 100;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        setStragety(new DivergentShootStragety(7));
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }


    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        res = shootStragety.shoot(this,false);
        return res;
    }

    public int getScore(){
        return score;
    }

    public List<AbstractItem>  getItem(){
        List<AbstractItem>  itemList= new LinkedList<>();
        //随机生成3个道具，水平速度不一致
        RandomItemGenerator RIG = new RandomItemGenerator(0.5,0.2,0.3);
        AbstractItem item1 = RIG.creatItem(this.getLocationX(),this.getLocationY(),-2, this.getSpeedY()+5);
        itemList.add(item1);
        AbstractItem item2 = RIG.creatItem(this.getLocationX(),this.getLocationY()+10,0, this.getSpeedY()+5);
        itemList.add(item2);
        AbstractItem item3 = RIG.creatItem(this.getLocationX(),this.getLocationY()+20,2, this.getSpeedY()+5);
        itemList.add(item3);
        return  itemList;
    }

    @Override
    public void bombUpdate() {
        //扣除boss100血量，若boss血量小于0则死亡且加分
        decreaseHp(100);
        if(hp<=0){
            vanish();
            Game.addScore(score);
        }
        //无限循环炸会影响游戏平衡性，可以产生大量道具。最终决定被炸不产生道具
    }
}
