package edu.hitsz.application.Game;

public class EasyGame extends Game{
    @Override
    protected void increaseDifficulty(){
        System.out.println("简单模式难度不随时间增加");
        System.out.println("简单模式难度如下");
        System.out.println("敌机数量最大值为5");
        System.out.println("简单模式不产生boss");
        System.out.println("精英敌机产生概率为" + (1-MobProbolility));
        System.out.println("普通敌机和精英敌机血量为30");
    }

    @Override
    protected void scoreCheck() {
        //简单模式不产生boss，不切合背景
//        System.out.println("简单模式不产生boss");
    }
}
