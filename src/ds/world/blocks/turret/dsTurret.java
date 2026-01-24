package ds.world.blocks.turret;

import ds.world.meta.dsStats;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.Stat;

public class dsTurret extends Turret {
    public dsTurret(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.targetsAir);
        stats.remove(Stat.targetsGround);
        stats.add(dsStats.targetSubmarines, targetAir);
        stats.add(Stat.targetsGround, targetGround);
    }
}
