package edu.hitsz.item;

import edu.hitsz.application.Game.Game;
import edu.hitsz.application.Main;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ThreadDemo.MusicThread;

public class BulletItem extends AbstractItem{
    public BulletItem(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public  void effect(HeroAircraft heroAircraft) throws InterruptedException {
        vanish();
        heroAircraft.BulletItem();
        System.out.println("FireSupply active!");
        //播放子弹道具音频
        if(Game.getMusicOn()){
//            new MusicThread("src/videos/bullet.wav").start();
            Game.musicExecutorService.execute(Game.bulletMuc);
        }

    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}