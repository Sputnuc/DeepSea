package ds.type.dsUnitTypes.parts;

import ds.type.dsUnitTypes.parts.patterns.DetonateType;
import ds.type.dsUnitTypes.parts.patterns.KineticDetonateType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Unit;
import mindustry.type.ItemStack;

public class IcbmWarhead {

    public DetonateType detonateType = new KineticDetonateType();

    public String name;
    public BulletType bullet;
    public ItemStack[] requirements;

    public void detonate(Unit unit){
        bullet.create(unit, unit.team(), unit.x(), unit.y(), unit.rotation());
        unit.destroy();
    }

    public IcbmWarhead(String name, BulletType bullet, ItemStack[] requirements){
        this.bullet = bullet;
        this.requirements = requirements;
    }
}
