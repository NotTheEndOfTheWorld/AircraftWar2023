package edu.hitsz.aircraft;

import edu.hitsz.ShootStrategy.BaseShootStragety;
import edu.hitsz.ShootStrategy.DivergentShootStragety;
import edu.hitsz.ShootStrategy.StraightShootStragety;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    //使用双重检查锁定 （DCL，即 double-checked locking）
    private  volatile static HeroAircraft heroAircraft;

    private Thread bulletThread;

    private int fireItemCount = 0;

    private final int updateFirePerItem = 5;

    private int shootNum = 1;


    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        setStragety(new StraightShootStragety(1));
    }

    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft == null)
        {
            synchronized (HeroAircraft.class){
                if (heroAircraft == null){
                    heroAircraft = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 1000);
                }
            }
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res;
        res = shootStragety.shoot(heroAircraft,true);
        return res;
    }


    public void increaseHp(int increase){
        if(hp + increase <=maxHp ){
            hp +=increase;}
        else{ hp = maxHp;
        }
    }


    public void BulletItem() throws InterruptedException {

        //每吃updateFirePerItem次火力道具可以永久增加一颗子弹，最多增加2
        if(fireItemCount<updateFirePerItem*2){
            fireItemCount++;
        }else {
            fireItemCount = updateFirePerItem*2;
        }

        if(shootNum < fireItemCount/updateFirePerItem +1){
            shootNum =  fireItemCount/updateFirePerItem +1 ;
            System.out.println("英雄机子弹数量增加至"+shootNum);
        }



        //若正在使用活力道具，则中断线程并做更新
        if(Objects.nonNull(bulletThread)){
            bulletThread.interrupt();
        }
        //火力道具持续3秒
        bulletThread = new Thread(()->{
            setStragety(new DivergentShootStragety(shootNum+3));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
            }

            setStragety(new StraightShootStragety(shootNum ));
        });
        bulletThread.start();
    }
}
