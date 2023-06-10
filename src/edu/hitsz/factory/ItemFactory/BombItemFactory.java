package edu.hitsz.factory.ItemFactory;

import edu.hitsz.item.AbstractItem;
import edu.hitsz.item.BombItem;

public class BombItemFactory implements ItemFactory{
    @Override
    public AbstractItem creatItem(int locationX, int locationY, int speedX, int speedY){
        return new BombItem(locationX, locationY, speedX, speedY);
    };
}
