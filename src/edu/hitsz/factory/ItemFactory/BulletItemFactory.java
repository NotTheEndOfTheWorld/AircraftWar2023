package edu.hitsz.factory.ItemFactory;

import edu.hitsz.item.AbstractItem;
import edu.hitsz.item.BulletItem;

public class BulletItemFactory implements ItemFactory{
    @Override
    public AbstractItem creatItem(int locationX, int locationY, int speedX, int speedY){
        return new BulletItem(locationX, locationY, speedX, speedY);
    }
}
