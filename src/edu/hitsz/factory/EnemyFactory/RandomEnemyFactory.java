package edu.hitsz.factory.EnemyFactory;


public class RandomEnemyFactory{

    // 0-1 random number ;decide creat Enemy probability
    private double MobEnemy = 0.7 ;
    private double EliteEnemy = 1.0;
//    double BossEnemy = 1.0;
    private double ranNum = 0;

    public EnemyFactory ChooseFactory(boolean creatBoss ){
        ranNum = Math.random();
        EnemyFactory enemyFactory = null;
        if(creatBoss == true){
            enemyFactory = new BossEnemyFactory();
        }
        else if( ranNum < MobEnemy ){
            enemyFactory = new MobEnemyFactory();
        }else if(ranNum < EliteEnemy ){
            enemyFactory = new EliteEnemyFactory();
        }
        return enemyFactory;
    }
}
