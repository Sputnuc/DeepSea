package ds.world.type.entities.weapons;

import arc.math.Angles;
import arc.util.Time;
import mindustry.entities.Effect;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;

public class EffectSpawnWeapon extends Weapon {
    public Effect effect = null;

    public float effectInterval = 60;
    public float effectX = 0;
    public float effectY = 0;

    private float effectTimer = 0f;

    public EffectSpawnWeapon(String name){
        super(name);
    }

    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);
        effectTimer += Time.delta;
        float mountX = unit.x + Angles.trnsx(unit.rotation - 90, x, y);
        float mountY = unit.y + Angles.trnsy(unit.rotation - 90, x, y);
        float weaponRotation = unit.rotation - 90 + (rotate ? mount.rotation : baseRotation);
        float wX = mountX + Angles.trnsx(weaponRotation, this.effectX, this.effectY);
        float wY = mountY + Angles.trnsy(weaponRotation, this.effectX, this.effectY);
        if(effectTimer >= effectInterval){
            effectTimer = 0f;
            effect.at(wX, wY, unit.rotation + mount.rotation);
        }
    }

    @Override
    public void draw(Unit unit, WeaponMount mount){
        super.draw(unit, mount);
    }
}
