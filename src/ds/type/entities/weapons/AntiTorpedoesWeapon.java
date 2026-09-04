package ds.type.entities.weapons;

import arc.Core;
import arc.scene.ui.layout.Table;
import ds.type.entities.dsUnits.TorpedoUnitType;
import mindustry.entities.Units;
import mindustry.gen.Groups;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class AntiTorpedoesWeapon extends DSWeapon {

    public AntiTorpedoesWeapon(String name){
        super(name);
    }

    public AntiTorpedoesWeapon(){
    }

    {
        autoTarget = true;
        controllable = false;
        rotate = true;
        useAttackRange = false;
        targetInterval = targetSwitchInterval = 5f;
    }

    @Override
    public void addStats(UnitType u, Table t){
        super.addStats(u, t);
        t.add(Core.bundle.get("weapon.anti-torpedo-defence"));
    }

    @Override
    protected Teamc findTarget(Unit unit, float x, float y, float range, boolean air, boolean ground){
        return Units.closestTarget(unit.team, x, y, range + Math.abs(shootY), u -> u.checkTarget(air, ground) && u.type instanceof TorpedoUnitType);
    }

    protected boolean checkTarget(Unit unit, Teamc target, float x, float y, float range){
        return !(target instanceof Unit u && u.type instanceof TorpedoUnitType);
    }


}
