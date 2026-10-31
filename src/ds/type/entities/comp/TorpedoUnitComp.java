package ds.type.entities.comp;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import deepsea.gen.SubmarineEnginec;
import ds.type.dsUnitTypes.SubmarineUnitType;
import ds.type.dsUnitTypes.parts.EngineInstance;
import ent.anno.Annotations;
import mindustry.gen.Entityc;
import mindustry.gen.TimedKillc;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;

@Annotations.EntityComponent
abstract class TorpedoUnitComp implements TimedKillc, SubmarineEnginec, Unitc {
    public void setType(UnitType type){
        setEngines(type);
    }
}
