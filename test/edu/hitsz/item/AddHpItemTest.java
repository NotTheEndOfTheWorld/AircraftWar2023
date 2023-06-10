package edu.hitsz.item;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.factory.ItemFactory.AddHpItemFactory;
import edu.hitsz.factory.ItemFactory.ItemFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static edu.hitsz.aircraft.HeroAircraft.getHeroAircraft;
import static org.junit.jupiter.api.Assertions.*;

class AddHpItemTest {
    public static HeroAircraft hero = null;
    public static AbstractItem addhp1 = null;
    public static AbstractItem addhp2 = null;
    @BeforeAll
    static void  beforeAll(){
        System.out.println("**--- Executed once before all test methods in this class ---**");
        hero = getHeroAircraft();
        ItemFactory itFact = new AddHpItemFactory();
        addhp1 = itFact.creatItem(Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                0,5);
        addhp2 = itFact.creatItem(Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT + ImageManager.ADDHP_ITEM_IMAGE.getHeight() + 1,
                0,5);
    }
    @Test
    void crash() {
        System.out.println("**--- Test crash method executed ---**");
        assertTrue(addhp1.crash(hero));
        assertFalse(addhp2.crash(hero));
    }

    @Test
    void effect() throws InterruptedException {
        System.out.println("**--- Test effect method executed ---**");
        hero.decreaseHp(200);
        if(hero.crash(addhp1)){
            int hp1 = hero.getHp();
            addhp1.effect(hero);
            int hp2 = hero.getHp();
            assertEquals(100,hp2-hp1);
        }
    }
}