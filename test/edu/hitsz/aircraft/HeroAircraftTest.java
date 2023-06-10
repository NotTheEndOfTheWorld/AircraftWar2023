package edu.hitsz.aircraft;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static edu.hitsz.aircraft.HeroAircraft.getHeroAircraft;
import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    public static HeroAircraft hero = null;
    @BeforeAll
    static void  beforeAll(){
        System.out.println("**--- Executed once before all test methods in this class ---**");
         hero = getHeroAircraft();
    }

    @Test
    void decreaseHp() {
        System.out.println("**---Test decreaseHp method--**");
        int hp1 = hero.getHp();
        hero.decreaseHp(30);
        int hp2 =hero.getHp();
        assertEquals(30,hp1-hp2);
    }

    @Test
    void notValid() {
        System.out.println("**---Test notValid method--**");
        hero.decreaseHp(hero.maxHp);
        assertTrue(hero.notValid());
    }
}