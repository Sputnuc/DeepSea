package ds.world.blocks.turret;

import arc.struct.ObjectMap;
import ds.world.meta.DSStatValues;
import mindustry.entities.bullet.BulletType;
import mindustry.world.meta.Stat;

public class DSConsumeTurret extends DSTurret {
    public BulletType shootType;
    public DSConsumeTurret(String name) {
        super(name);
        hasItems = true;
        itemCapacity = 10;
    }
    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, DSStatValues.ammo(ObjectMap.of(this, shootType)));
        stats.remove(Stat.maxEfficiency);
    }

    public class  FuelTurretBuild extends DSTurretBuild{
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
