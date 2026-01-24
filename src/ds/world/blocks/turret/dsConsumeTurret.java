package ds.world.blocks.turret;

import arc.Core;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import mindustry.entities.bullet.BulletType;
import mindustry.ui.Bar;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;

public class dsConsumeTurret extends dsTurret{
    public BulletType shootType;
    public dsConsumeTurret(String name) {
        super(name);
        hasItems = true;
        itemCapacity = 10;
    }
    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, StatValues.ammo(ObjectMap.of(this, shootType)));
    }

    public class  FuelTurretBuild extends TurretBuild{
        @Override
        public BulletType useAmmo(){
            consume();
            return shootType;
        }
        @Override
        public boolean hasAmmo(){
            return canConsume();
        }
        @Override
        public BulletType peekAmmo(){
            return shootType;
        }
    }
}
