package ds.newContent.blocks;

import ds.newContent.type.entities.HarpoonBulletType;
import mindustry.entities.bullet.BulletType;

public class dsHarpoonTurret extends dsFuelTurret{
    public dsHarpoonTurret(String name) {
        super(name);
    }
    public class HarpoonTurretBuild extends  FuelTurretBuild{
        public boolean returnedBullet = true;
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
