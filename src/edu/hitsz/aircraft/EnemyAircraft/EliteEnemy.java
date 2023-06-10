package edu.hitsz.aircraft.EnemyAircraft;

import edu.hitsz.item.ObserverDemo.BombSubscriber;
import edu.hitsz.ShootStrategy.StraightShootStragety;
import edu.hitsz.application.Game.Game;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.ItemFactory.RandomItemGenerator;
import edu.hitsz.item.AbstractItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Elite精英敌机
 * 可射击
 *
 * @author 郑瑜杰
 */
public class EliteEnemy extends AbstractEnemy implements BombSubscriber {

    /**
     * 分数
     */
    private static int score = 30;


    /**
     * @param locationX 精英机位置x坐标
     * @param locationY 精英机位置y坐标
     * @param speedX 精英机射出的子弹的基准速度
     * @param speedY 精英机射出的子弹的基准速度
     * @param hp    初始生命值
     */
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        setStragety(new StraightShootStragety(1));
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

    @Override
    public List<AbstractItem>  getItem(){
        RandomItemGenerator RIG = new RandomItemGenerator(0.4,0.15,0.35);
        AbstractItem item = RIG.creatItem(this.getLocationX(),this.getLocationY(),this.getSpeedX(), this.getSpeedY());
        List<AbstractItem>  itemList= new LinkedList<>();
        itemList.add(item);
        return  itemList;
    }

    @Override
    public void bombUpdate() {
        if(this.isValid){
            //添加分数
            Game.addScore(score);

            //无限循环炸会影响游戏平衡性，可以产生大量道具。最终决定精英机被炸不产生道具

            //此处有未知bug
            //已完成测试内容： addItems函数正常， getItem函数正常。
            // 两函数一起运行时也正常，excepion不在函数执行期间内（函数前后print语句均正常）
            //调用本机产生道具函数，会出现卡顿。未知在何处卡顿
//            Game.addItems(getItem());
            //用具体道具实例做测试，发现依旧有问题，未知在何处导致卡顿
//            List<AbstractItem> itemList = new LinkedList<>();
//            itemList.add(new BulletItem(100,100,5,5));
//            for (AbstractItem item: itemList
//                 ) {
//                System.out.println(item.getClass());
//            }
//            Game.addItems(itemList);
        }
        vanish();
    }
    @Override
    public void vanish(){
        super.vanish();
    }
}
