package edu.hitsz.application.Game;

import edu.hitsz.ShootStrategy.StrDivShootStragety;
import edu.hitsz.aircraft.EnemyAircraft.AbstractEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.factory.EnemyFactory.BossEnemyFactory;
import edu.hitsz.factory.EnemyFactory.EnemyFactory;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class HardGame extends Game{

    //boss直射子弹数和散射子弹数  以及最大数量
    private int bossStraightShootNum = 2;
    private int bossDivergentShootNum = 3;

    private int bossMaxStraightShootNum = 6;
    private int bossMaxDivergentShootNum = 7;

    private int bossHpInc = 100;

    //切换背景
    private boolean hasChangedBackground;

    int CreatBossThreshold = 400;
    int CreatBossTopScore = CreatBossThreshold + 100;

    /**
     * 周期（ms)
     * 指示子弹的发射频率 （敌机的产生频率另外设置）
     *      protect final int cycleDuration = 600;
     */
    /**
     * 周期（ms)
     * protect int cycleDurationForEnemy = 600;
     * 指使产生敌机周期
     */

    public HardGame(){
        //困难模式初始周期缩短
        cycleDurationForEnemy = 500;

        //困难模式初始精英机概率增加（简单模式为0.8）
        MobProbolility = 0.7;
    }




//    Game.enemyMaxNumber;
    @Override
    protected void increaseDifficulty(){
        System.out.println("困难模式难度提升信息：");
        System.out.println("Boss采用多子弹直射和不同方向散射相结合的射击策略");
        enemyMaxNumber+=2;
        if(enemyMaxNumber>=12){
            enemyMaxNumber= 12;
        }
        System.out.println("敌机数量最大数量为"+enemyMaxNumber);
//        System.out.println("Boss基础血量为400，每次被击败后下次产生最大血量增加100");
        if(MobProbolility>0.5){
            MobProbolility -= 0.05;
        }
        System.out.println("精英敌机产生概率增加至" + (1-MobProbolility)+"（不超过0.5）");

        //普通机和精英机属性增加
        increaseEnemyProperty(eliteFactory);
        increaseEnemyProperty(mobFactory);


        cycleDurationForEnemy -= 60;
        cycleTimeForEnemy -= 60;
        if (cycleDurationForEnemy <= 200) {
            cycleDurationForEnemy = 200;
        }
        System.out.println("敌机产生的频率变为简单模式的" + ((double) cycleDuration / (double) cycleDurationForEnemy) + "倍+" +
                "\n当前敌机产生周期为"+cycleDurationForEnemy);

    }

    /**
     * 困难模式敌机的血量提升15，Y速度提升2
     *
     */
    @Override
    protected void increaseEnemyProperty(EnemyFactory factory) {
        factory.increaseProperty(20,2);
    }

    /**
     * 检查得分是否到达阈值，适时产生或刷新boss机
     */
    protected void scoreCheck() {
        AbstractEnemy boss = BossEnemyFactory.getBoss();

        //创造boss机 每间隔everyScoreCreatBoss分数生成 boss 其中boss
        if(score > CreatBossThreshold && score< CreatBossTopScore){
            //若boss不存在，则产生boss
            if(Objects.isNull(boss) || boss.notValid()){
                if(enemyAircrafts.size()<enemyMaxNumber){
                    boss = bossFactory.creatEnemy();
                    //采用散射+直射的射击策略
                    boss.setStragety(new StrDivShootStragety(bossStraightShootNum,bossDivergentShootNum));
                    enemyAircrafts.add(boss);
                    System.out.println("boss产生，困难模式每次产生提升血量"+bossHpInc+
                            "当前血量为"+ boss.getHp()  +
                            "\n且子弹直射数增加至"+ bossStraightShootNum +
                            " 子弹散射数增加至"+bossDivergentShootNum);

                    //下次产生提升boss属性
                    //血量属性提升
                    bossFactory.increaseProperty(bossHpInc,0);
                    //子弹数量提升
                    bossDivergentShootNum = bossDivergentShootNum < bossMaxDivergentShootNum ?
                            bossDivergentShootNum+1 :
                            bossMaxDivergentShootNum  ;
                    bossStraightShootNum = bossStraightShootNum <bossMaxStraightShootNum ?
                            bossStraightShootNum +1  :
                            bossMaxStraightShootNum  ;
                    //下次生成boss阈值更新
                    CreatBossThreshold += CreatBossThreshold /4 + 6* boss.getScore();
                    CreatBossTopScore = CreatBossThreshold + boss.getScore();
                }

            }
        }else if(CreatBossTopScore<score){
            //更新产生boss的阈值顶部，防止不产生boss
            //CreatBossTopScore用于防止刚击败一架boss达到阈值长时间未击败且
            // 又达到下一次的阈值后立刻刷新下一架
            CreatBossThreshold = score + score /4 + 10* boss.getScore();
            CreatBossTopScore = CreatBossThreshold +boss.getScore();
        }


        //分数到达一定值后，切换背景
        if (score >= 2000 && !hasChangedBackground) {
            hasChangedBackground = true;
            System.out.println("切换背景");
            try {
                ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
