package ds.type.entities.comp;

import arc.math.geom.Vec3;
import deepsea.gen.BallisticMovementc;
import ds.type.dsUnitTypes.IcbmUnitType;
import ds.type.dsUnitTypes.parts.IcbmWarhead;
import ent.anno.Annotations;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;

@Annotations.EntityComponent
public abstract class KineticImpactComp implements Unitc, BallisticMovementc{

    @Annotations.Import float targetX, targetY, targetZ, time, x, y;
    @Annotations.Import float flightT;

    @Annotations.Import
    UnitType type;

    boolean raised;
    IcbmWarhead warHead;

    public boolean isRaised(){
        if(!(type instanceof IcbmUnitType t)) return false;
        raised = raised || altitude() >= t.raisingAltitude;
        return raised;
    }

    @Override
    public void setType(UnitType type){
        raised = false;
    }

    //for external spawning ICBT with different warheads
    public void setWarHead(IcbmWarhead h){
        warHead = h;
    }

    @Override
    public void update(){
        if(!(type instanceof IcbmUnitType t) || warHead == null) return;
        if(warHead.detonateType.detonate(Vec3.dst(x, y, altitude(), targetX, targetY, targetZ), time, altitude()) && isRaised()) warHead.detonate(self());
    }
}
