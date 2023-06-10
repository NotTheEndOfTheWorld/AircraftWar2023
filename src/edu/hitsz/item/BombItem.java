package edu.hitsz.item;

import edu.hitsz.item.ObserverDemo.BombSubscriber;
import edu.hitsz.application.Game.Game;
import edu.hitsz.application.Main;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ThreadDemo.MusicThread;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BombItem extends AbstractItem{

    private  List<BombSubscriber> subscribers = new LinkedList<>();
    public BombItem(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void effect(HeroAircraft heroAircraft){
        vanish();
        System.out.println("BombSupply active!");
        //播放炸弹道具音频
        if(Game.getMusicOn()){
//            new MusicThread("src/videos/bomb_explosion.wav").start();
            Game.musicExecutorService.execute(Game.bombMuc);
        }
        //添加观察者
        Game.Subscribe(this);
        //炸弹使用，通知所有观察者
        notifySubscribers();
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    public  void subscribe(BombSubscriber subscriber){
        subscribers.add(subscriber);
    }

    public void notifySubscribers(){
        for (BombSubscriber subscriber : subscribers) {
            if(Objects.nonNull(subscriber)){
                subscriber.bombUpdate();
            }
        }
        subscribers.clear();
    }
}
