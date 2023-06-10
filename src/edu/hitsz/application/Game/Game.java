package edu.hitsz.application.Game;

import edu.hitsz.application.HeroController;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.item.BombItem;
import edu.hitsz.ShootStrategy.StraightShootStragety;
import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.EnemyAircraft.AbstractEnemy;
import edu.hitsz.application.SwingDemo.CardLayoutDemo;
import edu.hitsz.application.SwingDemo.ScoreTable;
import edu.hitsz.application.ThreadDemo.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.EnemyFactory.*;
import edu.hitsz.item.AbstractItem;
import edu.hitsz.item.ObserverDemo.BombSubscriber;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;


/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;



    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;

//    protected final AbstractEnemy boss;s
    protected static final List<AbstractEnemy> enemyAircrafts = new LinkedList<>();

    private static final List<BaseBullet> heroBullets = new LinkedList<>();
    private static final List<BaseBullet> enemyBullets= new LinkedList<>();
    private static final List<AbstractItem> ItemList = new LinkedList<>();

    /**
     * 屏幕中出现的敌机最大数量
     */
    public static int enemyMaxNumber = 5;

    /**
     * 普通敌机产生的概率
     */
    protected static double MobProbolility = 0.8; //普通敌机生成概率

    /**
     * 敌机工厂
     */
    protected static BossEnemyFactory bossFactory = new BossEnemyFactory();

    protected static EliteEnemyFactory eliteFactory = new EliteEnemyFactory();

    protected static MobEnemyFactory mobFactory = new MobEnemyFactory();



    /**
     * 当前得分
     */
    protected static int score = 0;
    public int getScore(){
        return score;
    }

    public static void addScore(int addscore){
        score += addscore;
    }
    /**
     * 当前时刻
     */
    private long time = 0;



    /**
     * 周期（ms)
     * 指示子弹的发射频率 （敌机的产生频率另外设置）
     */
    protected int cycleDuration = 600;
    protected int cycleTime = 0;
    /**
     * 周期（ms)
     * 指使产生敌机周期
     */
    protected static int cycleDurationForEnemy = 600;
    protected int cycleTimeForEnemy = 0;

    /**
     * 周期（ms)
     * 指示难度提升频率
     */
    private int cycleDurationDifficultyForGame = 18000;
    private int cycleTimeIncDifficulty = 0;





    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    /**
     * 游戏难度 0:easy; 1:ordinary; 2:hard
     */
    private int gamemode = 0;
    public void setGamemode(int gamemode){
        if(gamemode ==1){
            //难度为 普通
            this.gamemode = 1;
        }
        else if(gamemode ==2 ) {
            //难度为困难
            this.gamemode = 2;
        }
        else if(gamemode == 0){
            this.gamemode = 0;
        }
        // else 其他情况难度默认为 easy 0
    }

    /**
     * 游戏音效 true  开 false 关
     */
    private static boolean musicOn = true;
    public static boolean getMusicOn(){
        return musicOn;
    }
    public static void setMusic(boolean musicOn){
        Game.musicOn = musicOn;
    }
//    MusicThread bgm =  new MusicThread("src/videos/bgm.wav");
    MusicThread bgm =  null;
    MusicThread bossBgm = null;

    /**
     * Cache 线程池，用于音乐线程任务调度
     */
    public static final ExecutorService musicExecutorService = Executors.newCachedThreadPool()  ;
    public static MusicThread bulletHitMuc = new MusicThread("src/videos/bullet_hit.wav");
    public static MusicThread hpMuc = new MusicThread("src/videos/get_supply.wav");
    public static MusicThread bombMuc = new MusicThread("src/videos/bomb_explosion.wav");
    public static MusicThread bulletMuc = new MusicThread("src/videos/bullet.wav");

    /**
     * 观察者模式
     */
    public static void Subscribe(BombItem bombItem){
        for (AbstractEnemy enemy : enemyAircrafts) {
            //判断非空 并且 有效
            if(Objects.nonNull(enemy)&&!enemy.notValid()){
                bombItem.subscribe((BombSubscriber) enemy);
            }

        }
        for (BaseBullet bullet: enemyBullets) {
            //判断非空 并且 有效
            if( Objects.nonNull(bullet) && (!bullet.notValid() ) ){
                bombItem.subscribe((BombSubscriber) bullet);
            }
        }
    }



    public Game()  {
        heroAircraft = HeroAircraft.getHeroAircraft();
//        enemyAircrafts = new LinkedList<>();
//        heroBullets = new LinkedList<>();
//        enemyBullets = new LinkedList<>();
//        Items        = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());




        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            //周期性产生敌机（会随难度提升增加频率）
            if(timeCountAndNewCycleJudgeForEnemyCreat()){
                // 新敌机产生
                creatEnemy();
            }

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
//                System.out.println(time);
                // 飞机射出子弹
                shootAction();
            }

            //周期性提升游戏难度
            if(timeCountAndNewCycleJudgeForGameDifficulty()){
                increaseDifficulty();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            itemsMoveAction();

            // 撞击检测
            try {
                crashCheckAction();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            //播放背景音乐
            if(musicOn){
                updateMusic();
            }

            //检查分数，每达到一定分数产生boss机；
            //达到一定分值切换背景
            scoreCheck();


            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();

                gameOverFlag = true;
                System.out.println("Game Over!");

                //播放游戏结束音乐
                if(musicOn){
                    MusicThread gameOverMusic = new MusicThread("src/videos/game_over.wav");
                    gameOverMusic.start();
                    try {
                        gameOverMusic.join();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    updateMusic();
                }



                ScoreTable table = null;
                try {
                    table = new ScoreTable(score,gamemode);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(table!=null){
                    CardLayoutDemo.cardPanel.add(table.getMainPanel());
                    CardLayoutDemo.cardLayout.last(CardLayoutDemo.cardPanel);
                }
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************



    /**
     * 提升游戏难度
     */
    protected  void increaseDifficulty(){

    }

    /**
     * 添加道具方法
     */
    public  static void  addItems(List<AbstractItem> items){
        if(items!=null){
            for (AbstractItem item : items) {
                if(item!=null){
                    ItemList.add(item);
                }
            }
        }
    }


    /**
     * 用于改变敌机属性的钩子
     * @param
     */
    protected void increaseEnemyProperty(EnemyFactory factory) {

    }

    /**
     * 检查得分是否到达阈值，适时产生或刷新boss机
     */
    protected abstract void scoreCheck();

    /**
     * 游戏音效播放
     * 背景音乐循环播放，切换boss音乐
     */
    private void updateMusic(){
        AbstractEnemy boss = BossEnemyFactory.getBoss();
        //游戏未结束
        if(!gameOverFlag){
            //背景音乐不存在 且 boss不存在时  循环播放背景音乐
            if((Objects.isNull(bgm)|| !bgm.isAlive())
                    && (Objects.isNull(boss)||boss.notValid())){
                bgm = new MusicThread("src/videos/bgm.wav");
                if(Objects.nonNull(bgm)){
                    bgm.start();
                    System.out.println("bgm play!");
                }
//                musicExecutorService.execute(bgm);
            }
            //boss音乐停止与播放
            //boss不存在 停止boss音乐
            if(Objects.isNull(boss) || boss.notValid()){
                if(Objects.nonNull(bossBgm)){
                    bossBgm.StopMusic();
                }
            }
            else {
                //boss存在，暂停背景音乐并播放boss音乐
                bgm.StopMusic();
//                System.out.println("暂停背景音乐");
                if(Objects.isNull(bossBgm) || !bossBgm.isAlive()){
                    bossBgm = new MusicThread("src/videos/bgm_boss.wav");
                    bossBgm.start();
//                    System.out.println("播放boss音乐");
                }
            }
        }else {
            //游戏结束
            if(Objects.nonNull(bgm)){
                bgm.StopMusic();
            }
            if(Objects.nonNull(bossBgm)){
                bossBgm.StopMusic();
            }
            System.out.println("GameOver,StopMusic");

        }

    }


    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 为了实现每隔一定时间产生敌机
     * @ private int cycleDurationForEnemy = 600; which decrease by difficultyInc
     *     private int cycleTimeForEnemy = 0;
     */
    private boolean timeCountAndNewCycleJudgeForEnemyCreat() {
        cycleTimeForEnemy += timeInterval;
        if (cycleTimeForEnemy >= cycleDurationForEnemy && cycleTimeForEnemy - timeInterval < cycleDurationForEnemy) {
            // 跨越到新的周期
            cycleTimeForEnemy %= cycleDurationForEnemy;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 为了实现每隔一定时间提升游戏难度，进行时间周期的记录
     * @return
     */
    private boolean timeCountAndNewCycleJudgeForGameDifficulty() {
        cycleTimeIncDifficulty += timeInterval;
        if (cycleTimeIncDifficulty >= cycleDurationDifficultyForGame && cycleTimeIncDifficulty - timeInterval < cycleDurationDifficultyForGame) {
            // 跨越到新的周期
            cycleTimeIncDifficulty %= cycleDurationDifficultyForGame;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击 使用策略模式
        for (AbstractAircraft enemy: enemyAircrafts)
            {
                enemyBullets.addAll(enemy.shoot());
            }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void itemsMoveAction() {
        for (AbstractItem item : ItemList) {
            item.forward();
        }
    }

    /**
     * 生成敌机
     * 1. 使用随机敌机工厂随机选择一个工厂
     * 2. 调用工厂的生成敌机方法

     */
    public void creatEnemy(){
        //方案二：仅使用一个敌机链表，可在创建时修改敌机射击策略。
        AbstractEnemy enemy = null;
        if (enemyAircrafts.size() < enemyMaxNumber) {
                //生成普通或者精英机
                if(Math.random() < MobProbolility ){
                        enemy = mobFactory.creatEnemy();
                        //设置策略为空
                        enemy.setStragety(null);
                    }
                    else { // 生成精英机
                        enemy = eliteFactory.creatEnemy();
                        //精英机设置直射策略
                        enemy.setStragety(new StraightShootStragety(1));

                    }
                    enemyAircrafts.add(enemy);
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() throws InterruptedException {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if(bullet.notValid()) {
                continue;
            }
            if(heroAircraft.crash(bullet)){
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
                //播放子弹命中音频
                if(Game.getMusicOn()){
                    //为了提高性能，减少噪音，敌机命中子弹不开启音效
////                  new MusicThread("src/videos/bullet_hit.wav").start();
//                    musicExecutorService.execute(bulletMuc);
                }
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    //播放子弹命中音频
                    if(Game.getMusicOn()){
//                        new MusicThread("src/videos/bullet_hit.wav").start();
                        musicExecutorService.execute(bulletHitMuc);
                    }
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        addScore(enemyAircraft.getScore());
                        addItems(enemyAircraft.getItem());
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractItem item: ItemList) {
            if(item.notValid()){
                continue;
            }
            if(item.crash(heroAircraft)|| heroAircraft.crash(item)){
                item.effect(heroAircraft);
            }
        }


    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        ItemList.removeIf(AbstractFlyingObject::notValid);
    }




    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g Graphics
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);

        //绘制道具
        paintImageWithPositionRevised(g, ItemList);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }
}
