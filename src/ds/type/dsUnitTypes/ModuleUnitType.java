package ds.type.dsUnitTypes;

import arc.struct.Seq;
import ds.type.dsUnitTypes.parts.UnitModule;
import ds.type.entities.DSUnitType;

import static ds.type.dsUnitTypes.parts.UnitModule.*;


public class ModuleUnitType extends DSUnitType {
    public Seq<UnitModule> modules;
    public boolean addStandartModules = true;

    public ModuleUnitType(String name) {
        super(name);
        if(modules.isEmpty() && addStandartModules) addStandartModules();
    }

    public void addStandartModules(){
        module("armor-plates", ModuleType.armor, 90);
        module("engine", ModuleType.mobility, 45, 20).fractionPenalty = true;
    }

    public UnitModule module(String name, UnitModule.ModuleType type){
        UnitModule mod = new UnitModule(name, type);
        modules.add(mod);
        return mod;
    }

    public UnitModule module(String name, UnitModule.ModuleType type, float moduleHealth){
        UnitModule mod = new UnitModule(name, type);
        mod.moduleHealth = moduleHealth;
        modules.add(mod);
        return mod;
    }

    public UnitModule module(String name, UnitModule.ModuleType type, float moduleHealth, float moduleArmor){
        UnitModule mod = new UnitModule(name, type);
        mod.moduleHealth = moduleHealth;
        mod.moduleArmor = moduleArmor;
        modules.add(mod);
        return mod;
    }
}
