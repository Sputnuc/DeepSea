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

    protected float calculateRange(){
        if(spawnUnit != null) return spawnUnit.lifetime * spawnUnit.speed;
        if(despawnUnit != null) return despawnUnit.lifetime * despawnUnit.speed;
        if(rangeOverride > 0) return rangeOverride;
        return Math.max(Mathf.zero(drag) ? speed * lifetime : speed * (1f - Mathf.pow(1f - drag, lifetime)) / drag, maxRange);
    }
}
