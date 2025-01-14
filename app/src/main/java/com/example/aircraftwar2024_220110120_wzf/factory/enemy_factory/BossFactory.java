package com.example.aircraftwar2024_220110120_wzf.factory.enemy_factory;


import com.example.aircraftwar2024_220110120_wzf.ImageManager;
import com.example.aircraftwar2024_220110120_wzf.activity.GameActivity;
import com.example.aircraftwar2024_220110120_wzf.aircraft.AbstractEnemyAircraft;
import com.example.aircraftwar2024_220110120_wzf.aircraft.BossEnemy;
import com.example.aircraftwar2024_220110120_wzf.shoot.DisperseShoot;

/**
 *
 *
 *  @author hitsz
 */
public class BossFactory implements EnemyFactory {

    private int bossHp = 500;
    @Override
    public AbstractEnemyAircraft createEnemyAircraft(double bosslevel) {
        BossEnemy boss = new BossEnemy(
                (GameActivity.screenWidth - ImageManager.BOSS_ENEMY_IMAGE.getWidth()) / 2,
                (ImageManager.BOSS_ENEMY_IMAGE.getHeight())/2,
                5,
                0,
                (int)(bossHp * bosslevel));
        boss.setShootStrategy(new DisperseShoot());
        return boss;

    }
}
