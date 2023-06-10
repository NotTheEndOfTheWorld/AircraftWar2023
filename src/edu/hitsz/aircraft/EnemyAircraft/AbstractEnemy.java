package edu.hitsz.aircraft.EnemyAircraft;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.item.AbstractItem;
import edu.hitsz.item.ObserverDemo.BombSubscriber;

import java.util.List;

public abstract class AbstractEnemy extends AbstractAircraft implements BombSubscriber {
    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    protected static int score = 10 ;
    public abstract int getScore();

    public abstract List<AbstractItem> getItem();

    public abstract void bombUpdate();

}
