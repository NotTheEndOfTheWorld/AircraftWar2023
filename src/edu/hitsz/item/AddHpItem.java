package edu.hitsz.item;

import edu.hitsz.application.Game.Game;
import edu.hitsz.application.Main;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ThreadDemo.MusicThread;

public class AddHpItem extends AbstractItem{
    private int AddHp = 100 ;
    public AddHpItem(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void effect(HeroAircraft heroAircraft){
        vanish();
        //播放回血道具音频
        if(Game.getMusicOn()){
//            new MusicThread("src/videos/get_supply.wav").start();
            Game.musicExecutorService.execute(Game.hpMuc);
        }
        heroAircraft.increaseHp(AddHp);
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
