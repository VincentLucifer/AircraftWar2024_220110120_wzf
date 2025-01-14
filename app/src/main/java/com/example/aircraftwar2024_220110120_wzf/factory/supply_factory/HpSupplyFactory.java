package com.example.aircraftwar2024_220110120_wzf.factory.supply_factory;

import com.example.aircraftwar2024_220110120_wzf.supply.AbstractFlyingSupply;
import com.example.aircraftwar2024_220110120_wzf.supply.HpSupply;

/**
 * 加血道具工厂
 *
 * @author hitsz
 */
public class HpSupplyFactory implements SupplyFactory {

    @Override
    public AbstractFlyingSupply createFlyingSupply(int x, int y) {
        return new HpSupply(x,y,0,2);
    }
}
