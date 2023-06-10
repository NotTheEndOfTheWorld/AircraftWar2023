package edu.hitsz.factory.ItemFactory;

import edu.hitsz.item.AbstractItem;
import edu.hitsz.item.AddHpItem;

public class AddHpItemFactory implements ItemFactory{
    @Override
    public AbstractItem creatItem(int locationX, int locationY, int speedX, int speedY){
        return new AddHpItem(locationX, locationY, speedX, speedY);
    };

}
