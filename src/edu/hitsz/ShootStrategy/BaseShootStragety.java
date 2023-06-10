package edu.hitsz.ShootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface BaseShootStragety {
     public List<BaseBullet> shoot(AbstractAircraft aircraft, boolean isHero) ;
}
