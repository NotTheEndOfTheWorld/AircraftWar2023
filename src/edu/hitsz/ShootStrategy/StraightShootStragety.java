package edu.hitsz.ShootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class StraightShootStragety implements BaseShootStragety{
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    public StraightShootStragety(int shootNum){
        if(0<shootNum &&shootNum<4){
            this.shootNum = shootNum;
        }
        else if(shootNum>=4){
            shootNum= 3;
        }
    }


    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot(AbstractAircraft aircraft, boolean isHero){
        List<BaseBullet> res = new LinkedList<>();
        /**
         * 子弹射击方向 (向上发射：1，向下发射：-1)
         */
        int direction = isHero? -1 : 1;

        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction*2;
        int speedX = 0;
        /**
         * 子弹相对飞机的速度（英雄机由鼠标控制，速度为0，而精英机本身有速度。
         * 因此英雄机子弹的相对速度要提高。
         */
        int revelentSpeedY = isHero? direction*13 : direction*8;
        int speedY = aircraft.getSpeedY() + revelentSpeedY ;


        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            if(isHero){
                bullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y + 4*Math.abs(2*i-(shootNum-1)), speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }else {
                bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }
        }

        return res;
    }
}
