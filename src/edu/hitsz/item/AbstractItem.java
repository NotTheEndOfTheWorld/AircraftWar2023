package edu.hitsz.item;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.aircraft.HeroAircraft;
/**
 * 道具抽象类。
 *
 *
 * @author 郑瑜杰
 */
public abstract class AbstractItem extends AbstractFlyingObject {
    public AbstractItem(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    public abstract void effect(HeroAircraft heroAircraft) throws InterruptedException;
}
