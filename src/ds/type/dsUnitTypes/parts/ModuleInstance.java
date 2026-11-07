package ds.type.dsUnitTypes.parts;

import arc.math.Mathf;
import mindustry.gen.Unit;

public class ModuleInstance{
    public UnitModule module;
    public float health;
    public boolean broken;

    public ModuleInstance(UnitModule module){
        this.module = module;
        health = module.moduleHealth;
        broken = false;
    }

    public UnitModule.ModuleType getModuleType(){
        return module.type;
    }

    public float getModuleHealthFraction(){
        return health / module.moduleHealth;
    }

    public boolean isOperational(){
        return !broken || (health / module.moduleHealth) >= module.restoreThreshold;
    }

    public float getModuleEfficiency(){
        return module.fractionPenalty ? health / module.moduleHealth : broken ? 0 : 1;
    }

    public float getPenalty(){
        return (1 - getModuleEfficiency()) * module.penaltyAffection;
    }

    public float getModuleArmor(Unit unit){
        return module.moduleArmor == -1 ? unit.armor() : module.moduleArmor;
    }

    public float damage(float damage, Unit unit){
        float dam = damage;
        dam = Math.max(1, dam - (module.fractionPenalty ? getModuleArmor(unit) * getModuleEfficiency() : broken ? getModuleArmor(unit) : 0));
        health = Math.max(health - dam , 0);
        broken = (health <= 0) || broken;
        return dam;
    }

    public void damagePierce(float damage){
        health = Math.max(health - damage , 0);
        broken = (health <= 0) || broken;
    }

    public float damagePierce(float damage, Unit unit, float armorMult){
        float dam = damage;
        dam = Math.max(1, dam - (module.fractionPenalty ? getModuleArmor(unit) * getModuleEfficiency() : broken ? getModuleArmor(unit) : 0) * armorMult);
        health = Math.max(health - dam , 0);
        broken = (health <= 0) || broken;
        return dam;
    }

    public void heal(float value, boolean fraction){
        health += fraction ? health * value : value;
    }

    public void updateState(){
        broken = isOperational();
    }
}
