package ds.type.dsUnitTypes.parts;

import mindustry.entities.units.WeaponMount;

public class UnitModule {
    //For penalties, future AutoPatcher and checking do this module is armor
    public enum ModuleType {
        mobility, health, weapon, armor, mics
    }
    public String name;
    public String locName;
    public ModuleType type;
    public float moduleHealth = 100;
    public float penaltyAffection = 1;
    public boolean fractionPenalty = false;
    public float restoreThreshold = 0.75f;
    // "-1" - unit's armor
    public float moduleArmor = -1;
    //Higher value means lower priority
    public int modulePriority = 0;

    public UnitModule(String name, ModuleType type){
        this.name = name;
        this.type = type;
    }
}
