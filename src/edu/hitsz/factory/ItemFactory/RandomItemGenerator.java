package edu.hitsz.factory.ItemFactory;

import edu.hitsz.item.AbstractItem;

public class RandomItemGenerator {

    /**
     * 0-1 随机数 用于判断生成道具种类
     * 生成不同道具的随机数临界值[0,x1,x2,1]
     */
    double AddHpNum = 0.40 ;
    double BombNum = 0.55;
    double BulletNum = 0.9;
    double ranNum = 0;

    public  RandomItemGenerator(){
    }

    public  RandomItemGenerator(double AddHpProb, double BombProb, double BulletProb){
        this.AddHpNum = AddHpProb;
        this.BombNum = AddHpProb + BombProb;
        this.BulletNum = AddHpProb + BombProb + BulletProb;
    }
    public  AbstractItem creatItem(int locationX, int locationY, int speedX, int speedY){
        ranNum = Math.random();
        ItemFactory itemFactory;
        if( ranNum < AddHpNum ){
            itemFactory = new AddHpItemFactory();
        }else if(ranNum < BombNum ){
            itemFactory = new BombItemFactory();
        }else if(ranNum < BulletNum){
            itemFactory = new BulletItemFactory();
        }else {
            return null;
        }

        if(itemFactory!= null){
            return itemFactory.creatItem(locationX, locationY, speedX, speedY);
        }
        else return null;
    }
}
