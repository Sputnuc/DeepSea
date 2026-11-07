package ds.type.entities.comp;

import arc.struct.Seq;
import ds.type.dsUnitTypes.ModuleUnitType;
import ds.type.dsUnitTypes.parts.ModuleInstance;
import ds.type.dsUnitTypes.parts.UnitModule;
import ent.anno.Annotations.*;
import mindustry.gen.*;
import mindustry.type.UnitType;

@EntityComponent
abstract class UnitModuleComp implements Unitc{

    @SyncField(false) @SyncLocal Seq<ModuleInstance> modules, armorModules;
    @Import UnitType type;

    private void createModules(){
        if(!(type instanceof ModuleUnitType)) return;
        for(UnitModule mod : ((ModuleUnitType) type).modules){
            ModuleInstance ins = new ModuleInstance(mod);
            if(mod.type.equals(UnitModule.ModuleType.armor)){
                armorModules.add(ins);
                continue;
            }
            modules.add(ins);
        }
        modules.sort(m -> m.module.modulePriority);
    }

    public void setType(UnitType type){
        if(modules.isEmpty()) createModules();
    }

    public void damage(float amount){
        float damage = amount;
        //Armor in the firs priority of all
        for(ModuleInstance ins : armorModules){
            damage = ins.damage(damage, self());
        }
        damageModules(damage);
    }

    public void damageModules(float amount){
        float damage = amount;
        while(damage > 0){
            damage = modules.random().damage(damage, self());
        }
    }
}
