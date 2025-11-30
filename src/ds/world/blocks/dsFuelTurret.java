package ds.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.util.Nullable;
import arc.util.Strings;
import ds.world.meta.dsStats;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.consumers.ConsumeItemFilter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;

public class dsFuelTurret extends Turret {
    public BulletType shootType;
    public @Nullable Item fuelItem;
    public dsFuelTurret(String name) {
        super(name);
        hasItems = true;
        itemCapacity = 10;
    }
    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, StatValues.ammo(ObjectMap.of(this, shootType)));
    }

    @Override
    public void init(){
        consumeItem(fuelItem);
        super.init();
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("Fuel", (FuelTurretBuild entity) ->  new Bar(
                () -> Core.bundle.format("dsbar.Fuel"),
                () -> Color.valueOf("edcdaf"),
                () -> (float) (entity).items.get(fuelItem) / itemCapacity
        ));
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
