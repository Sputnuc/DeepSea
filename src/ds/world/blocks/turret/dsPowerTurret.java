package ds.world.blocks.turret;

import arc.struct.ObjectMap;
import ds.world.meta.dsStatValues;
import ds.world.meta.dsStats;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.Stat;

public class dsPowerTurret extends PowerTurret {
    public dsPowerTurret(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, dsStatValues.ammo(ObjectMap.of(this, shootType)));
    }
}
