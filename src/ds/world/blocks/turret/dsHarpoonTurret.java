package ds.world.blocks.turret;

import arc.Core;
import arc.graphics.Color;
import arc.math.Angles;
import arc.util.Time;
import ds.content.dsFx;
import mindustry.entities.bullet.BulletType;
import mindustry.ui.Bar;

public class dsHarpoonTurret extends dsConsumeTurret {
    public float brokenTime = 1200;
    public dsHarpoonTurret(String name) {
        super(name);
        targetBlocks = false;
    }
    @Override
    public void setBars(){
        super.setBars();
        addBar("State", (HarpoonTurretBuild entity) ->  new Bar(
                () -> (entity).returnedBullet ? !(entity).broken ? Core.bundle.format("bar.harpoonReady") : Core.bundle.format("bar.harpoonIsBroken") : Core.bundle.format("bar.harpoonWaiting"),
                () -> (entity).returnedBullet ? !(entity).broken ? Color.valueOf("de6d6d") : Color.valueOf("6e2626") : Color.valueOf("e03a3a"),
                ()-> !(entity).broken ? 1f : (entity).timer / brokenTime
        ));
    }

    public class HarpoonTurretBuild extends FuelTurretBuild{
        protected boolean returnedBullet = true;
        protected boolean broken = false;
        protected float timer = 0;
        public void bulletReturned(){
            returnedBullet = true;
        }

        public void bulletBroken(){
            returnedBullet = true;
            broken = true;
            float rotation = this.rotation;
            float muzzleX = this.x + Angles.trnsx(rotation, shootX);
            float muzzleY = this.y + Angles.trnsy(rotation, shootY);
            dsFx.dsBulletHit.at(muzzleX, muzzleY);
        }

        @Override
        public boolean hasAmmo(){
            return canConsume() && returnedBullet && !broken;
        }

        @Override
        public void updateTile(){
            super.updateTile();
            if(broken){
                timer += Time.delta;
                if(timer >= brokenTime){
                    timer = 0;
                    broken = false;
                }
            }
        }

        @Override
        public BulletType useAmmo(){
            returnedBullet = false;
            consume();
            return shootType;
        }
    }
}
