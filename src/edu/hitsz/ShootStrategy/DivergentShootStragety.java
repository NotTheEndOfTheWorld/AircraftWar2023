package edu.hitsz.ShootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class DivergentShootStragety implements BaseShootStragety {
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 5;

    public DivergentShootStragety(int shootNum){
        if(shootNum>0 && shootNum<=7){
            this.shootNum = shootNum;
        }else if(shootNum>7){
            this.shootNum = 7;
        }
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot(AbstractAircraft aircraft, boolean isHero) {
        List<BaseBullet> res = new LinkedList<>();
        /**
         * 子弹射击方向 (向上发射：1，向下发射：-1)
         */
        int direction = isHero ? -1 : 1;

        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + direction * 15;


        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            if (isHero) {
                bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y + 4*Math.abs(2*i-(shootNum-1)),
                        speedX+ ((i*2 - shootNum + 1))/2,
                        speedY , aircraft.getPower());
                res.add(bullet);
            } else {
                bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y - 4*Math.abs(2*i-(shootNum-1)),
                        speedX+(i*2 - shootNum + 1),
                        speedY, aircraft.getPower());
                res.add(bullet);
            }
        }

        return res;
    }
}
