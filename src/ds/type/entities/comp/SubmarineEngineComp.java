package ds.type.entities.comp;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import ds.type.dsUnitTypes.SubmarineUnitType;
import ds.type.dsUnitTypes.TorpedoUnitType;
import ds.type.dsUnitTypes.parts.EngineInstance;
import ent.anno.Annotations;
import mindustry.gen.*;
import mindustry.type.UnitType;

@Annotations.EntityComponent
abstract class SubmarineEngineComp implements Entityc, Physicsc{

    EngineInstance[] engines;

    float enginesEfficiency;

    @Annotations.Import UnitType type;

    @Annotations.Import Vec2 tmp2, tmp1;
    @Annotations.Import boolean dead;
    @Annotations.Import float rotation, speedMultiplier;
    @Annotations.Import Vec2 vel;

    public void update(){
        updateEngines();
    }

    void updateEngines(){
        enginesEfficiency = Mathf.pow(moving() && !dead ? vel.len() / type.speed : 0, 2);
        if(engines != null) for(EngineInstance eng : engines){
            eng.update(self(), enginesEfficiency);
        }
    }

    void setEngines(UnitType type){
        if(type instanceof SubmarineUnitType subm){
            engines = new EngineInstance[subm.undEngines.size];
            for(int i = 0; i < subm.undEngines.size; i++){
                engines[i] = new EngineInstance(subm.undEngines.get(i));
            }
        }

        if(type instanceof TorpedoUnitType torp){
            engines = new EngineInstance[torp.undEngines.size];
            for(int i = 0; i < torp.undEngines.size; i++){
                engines[i] = new EngineInstance(torp.undEngines.get(i));
            }
        }
    }

    public void setType(UnitType type){
        setEngines(type);
    }
}
