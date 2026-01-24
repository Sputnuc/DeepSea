package ds.world.blocks.turret;

import ds.world.meta.dsStats;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;

public class dsItemTurret extends ItemTurret {
    public dsItemTurret(String name) {
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
