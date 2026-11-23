package ds.type.entities.comp;

import arc.math.Mathf;
import arc.math.geom.Vec3;
import deepsea.gen.BallisticMovementc;
import ds.type.dsUnitTypes.IcbtUnitType;
import ds.type.dsUnitTypes.parts.IcbtWarhead;
import ent.anno.Annotations;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;

@Annotations.EntityComponent
public abstract class KineticImpactComp implements Unitc, BallisticMovementc{

    @Annotations.Import float targetX, targetY, flightTime;
    @Annotations.Import float flightT;
    @Annotations.Import Vec3 p0;

    @Annotations.Import
    UnitType type;

    IcbtWarhead warHead;

    //for external spawning ICBT with different warheads
    public void setWarHead(IcbtWarhead h){
        warHead = h;
    }

    @Override
    public void update(){
        if(!(type instanceof IcbtUnitType t) || warHead == null) return;
        if(warHead.detonateType.detonate(Mathf.dst(p0.x, p0.y, targetX, targetY), flightTime, altitude())) warHead.detonate(self());
    }
}
