package ds.type.entities.bullets;

import arc.math.Mathf;
import mindustry.entities.bullet.BulletType;
import mindustry.type.unit.MissileUnitType;

//For torpedoes
public class EmptyBulletType extends BulletType {
    public EmptyBulletType(){
        speed = 0.0001f;
        instantDisappear = true;
        keepVelocity = false;
    }
}
