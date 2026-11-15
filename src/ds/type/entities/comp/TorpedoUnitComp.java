package ds.type.entities.comp;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import deepsea.gen.SubmarineEnginec;
import deepsea.gen.SubmarineMovec;
import ds.type.dsUnitTypes.SubmarineUnitType;
import ds.type.dsUnitTypes.TorpedoUnitType;
import ds.type.dsUnitTypes.parts.EngineInstance;
import ent.anno.Annotations;
import mindustry.gen.Entityc;
import mindustry.gen.TimedKillc;
import mindustry.gen.Unitc;
import mindustry.graphics.Drawf;
import mindustry.type.UnitType;

@Annotations.EntityComponent
abstract class TorpedoUnitComp implements TimedKillc, SubmarineEnginec, Unitc, SubmarineMovec {

    @Annotations.Import float submerging;
    @Annotations.Import UnitType type;

    public void setType(UnitType type){
        setEngines(type);
    }

    @Override
    @Annotations.Replace(100)
    public boolean submerged(){
        return !diving();
    }

    @Annotations.MethodPriority(10f)
    @Annotations.Replace
    public void draw(){
        if(type instanceof TorpedoUnitType sub) {
            if(!submerged()){
                sub.drawUnderwater(self());
            } else Drawf.underwater(() -> {
                sub.drawUnderwater(self());
            });
        } else {
            if(submerged()){
                Drawf.underwater(()->{
                    type.draw(self());
                });
            }else type.draw(self());
        }
    }
}
