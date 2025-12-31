package ds.world.blocks.turret;

import arc.Core;
import arc.graphics.Color;
import mindustry.entities.bullet.BulletType;
import mindustry.ui.Bar;

public class dsHarpoonTurret extends dsFuelTurret {
    public dsHarpoonTurret(String name) {
        super(name);
    }
    @Override
    public void setBars(){
        super.setBars();
        addBar("State", (HarpoonTurretBuild entity) ->  new Bar(
                () -> (entity).returnedBullet ? Core.bundle.format("bar.harpoonReady") : Core.bundle.format("bar.harpoonWaiting"),
                () -> (entity).returnedBullet ? Color.valueOf("de6d6d") : Color.valueOf("e03a3a"),
                ()-> 1f
        ));
    }
    public class HarpoonTurretBuild extends FuelTurretBuild{
        protected boolean returnedBullet = true;

        public void bulletReturned(){
            returnedBullet = true;
        }

        @Override
        public boolean hasAmmo(){
            return  canConsume() && returnedBullet;
        }

        @Override
        public BulletType useAmmo(){
            consume();
            returnedBullet = false;
            return shootType;
        }
    }
}
