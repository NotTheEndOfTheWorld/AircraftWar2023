package edu.hitsz.application.Game;

import edu.hitsz.ShootStrategy.DivergentShootStragety;
import edu.hitsz.ShootStrategy.StrDivShootStragety;
import edu.hitsz.aircraft.EnemyAircraft.AbstractEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.factory.EnemyFactory.BossEnemyFactory;
import edu.hitsz.factory.EnemyFactory.EnemyFactory;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class OrdinaryGame extends Game {


    //boss散射子弹数
    private int bossDivergentShootNum = 3;
    //boss最大子弹数
    private int bossMaxDivergentShootNum = 7;


    //切换背景
    private boolean hasChangedBackground;

    @Override
    protected void increaseDifficulty() {
        System.out.println("普通模式难度变化如下");
        //@enemyMaxNumber 最大敌机数量
        enemyMaxNumber += 1;
        if (enemyMaxNumber >= 8) {
            enemyMaxNumber = 8;
        }
        System.out.println("敌机数量最大数量为" + enemyMaxNumber);
//        System.out.println("Boss基础血量为400，每次被击败后下次产生最大血量增加100");
        //@MobProbolility 普通敌机产生概率
        if (MobProbolility > 0.5) {
            MobProbolility -= 0.05;
        }
        System.out.println("精英敌机产生概率增加至" + (1 - MobProbolility) + "（不超过0.5）");
        increaseEnemyProperty(eliteFactory);
        increaseEnemyProperty(mobFactory);
        /**
         * 周期（ms)
         * 指示子弹的发射频率 （敌机的产生频率另外设置）
         *      protect int cycleDuration = 600;
         */
        /**
         * 周期（ms)
         * protect int cycleDurationForEnemy = 600;
         * 指使产生敌机周期
         */
        cycleDurationForEnemy -= 60;
        cycleTimeForEnemy -= 60;
        if (cycleDurationForEnemy <= 300) {
            cycleDurationForEnemy = 300;
        }
        System.out.println("敌机产生周期缩短为"+ cycleDurationForEnemy+"\n敌机产生的频率变为初始值的" + ((double) cycleDuration / (double) cycleDurationForEnemy) + "倍");
    }
    /**
     * 普通模式敌机的血量提升10，Y速度提升1
     *
     */
    @Override
    protected void increaseEnemyProperty (EnemyFactory factory){
        factory.increaseProperty(10, 1);
    }
    /*
     * 检查得分是否到达阈值，适时产生或刷新boss机
     */
    protected void scoreCheck() {
        AbstractEnemy boss = BossEnemyFactory.getBoss();
        int everyScoreCreatBoss = 512;
        int CreatBossOverModScores = everyScoreCreatBoss-100;
        //创造boss机 每间隔everyScoreCreatBoss分数生成 boss 其中boss
        if(score%everyScoreCreatBoss > CreatBossOverModScores){
            //若boss不存在，则产生boss
            if(enemyAircrafts.size()<enemyMaxNumber){
                if(Objects.isNull(boss) || boss.notValid()){
                    boss = bossFactory.creatEnemy();
                    //采用散射的射击策略
                    boss.setStragety(new DivergentShootStragety(bossDivergentShootNum));
                    enemyAircrafts.add(boss);
                    System.out.println("boss产生，普通模式血量为"+
                            boss.getHp()  +
                            " 子弹散射数增加至"+bossDivergentShootNum);

                    //子弹数量提升
                    bossDivergentShootNum = bossDivergentShootNum < bossMaxDivergentShootNum ?
                            bossDivergentShootNum+1 :
                            bossMaxDivergentShootNum  ;
                }
            }
        }


        //分数到达1024后，切换背景
        if (score >= 1024 && !hasChangedBackground) {
            hasChangedBackground = true;
            System.out.println("切换背景");
            try {
                ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
