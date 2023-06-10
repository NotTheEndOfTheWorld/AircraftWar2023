package edu.hitsz.aircraft.EnemyAircraft;

import edu.hitsz.factory.EnemyFactory.BossEnemyFactory;
import edu.hitsz.factory.EnemyFactory.EnemyFactory;
import edu.hitsz.item.AbstractItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossEnemyTest {

    public static AbstractEnemy boss = null;
    @BeforeAll
    static void  beforeAll(){
        System.out.println("**--- Executed once before all test methods in this class ---**");
        EnemyFactory bossfact = new BossEnemyFactory();
        boss = bossfact.creatEnemy();
    }

    @Test
    void getScore() {
        System.out.println("**---Test getScore method--**");
        assertEquals(100,boss.getScore());
    }

    @Test
    void getItem() {
        System.out.println("**---Test getItem method--**");
        assertEquals(3,boss.getItem().size());
        boolean allisvalid = true;
        for (AbstractItem item: boss.getItem() ) {
            if(item==null){
                allisvalid = false;
            }
        }
        assertTrue(allisvalid);
    }
}