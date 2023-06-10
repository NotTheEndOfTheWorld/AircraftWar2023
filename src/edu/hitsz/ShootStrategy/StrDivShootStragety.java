package edu.hitsz.ShootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class StrDivShootStragety implements BaseShootStragety {
    /**
     * 子弹一次发射数量
     */


    private int strShootNum = 2;

    private int divShootNum = 3;

    private final int MaxStraightShootNum = 6;
    private final int MaxDivergentShootNum = 7;

    public StrDivShootStragety(int strShootNum, int divShootNum){
        if(strShootNum>0 && strShootNum< MaxStraightShootNum){
            this.strShootNum = strShootNum ;
        }
        else if( strShootNum>=MaxStraightShootNum){
            this.strShootNum = MaxStraightShootNum;
        }

        if(divShootNum>0 && divShootNum <MaxDivergentShootNum){
            this.divShootNum = divShootNum;
        }else  if(divShootNum>=MaxDivergentShootNum){
            this.divShootNum = MaxDivergentShootNum;
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
        int speedY = aircraft.getSpeedY() + direction * 12;


        BaseBullet bullet;
        //散射
        for (int i = 0; i < divShootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移 散射
            if (isHero) {
                bullet = new HeroBullet(x + (i * 2 - divShootNum + 1) * 10, y- 4*Math.abs(2*i-(divShootNum-1)),
                        speedX+(i*2 - divShootNum + 1),
                        speedY, aircraft.getPower());
                res.add(bullet);
            } else {
                bullet = new EnemyBullet(x + (i * 2 - divShootNum + 1) * 10, y,
                        speedX+(i*2 - divShootNum + 1),
                        speedY, aircraft.getPower());
                res.add(bullet);
            }
        }

        //直射
        for(int i = 0; i< strShootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移 直射
            if(isHero){
                bullet = new HeroBullet(x + (i*2 - divShootNum + 1)*10, y, speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }else {
                bullet = new EnemyBullet(x + (i*2 - divShootNum + 1)*70, y, speedX, speedY, aircraft.getPower());
                res.add(bullet);
            }
        }

        return res;
    }

}
