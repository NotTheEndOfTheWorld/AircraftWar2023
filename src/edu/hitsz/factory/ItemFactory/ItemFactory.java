package edu.hitsz.factory.ItemFactory;

import edu.hitsz.item.AbstractItem;

public interface ItemFactory {
     AbstractItem creatItem(int locationX, int locationY, int speedX, int speedY);
}
