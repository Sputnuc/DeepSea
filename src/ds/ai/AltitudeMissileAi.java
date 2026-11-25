package ds.ai;

import arc.math.Mathf;
import arc.util.Tmp;
import deepsea.gen.BallisticMissilec;
import deepsea.gen.BallisticMovementc;
import deepsea.gen.KineticImpactc;
import ds.type.dsUnitTypes.IcbmUnitType;
import ds.type.dsUnitTypes.parts.patterns.KineticDetonateType;
import mindustry.ai.types.MissileAI;
import mindustry.gen.TimedKillc;

public class AltitudeMissileAi extends MissileAI {
    @Override
    protected void resetTimers(){
        timer.reset(timerTarget, 5f);
    }

    @Override
    public void updateMovement(){
        unloadPayloads();

        float time = unit instanceof TimedKillc t ? t.time() : 1000000f;

        float alightDist = -1;

        float altVel = 1;

        if(unit.type() instanceof IcbmUnitType t){
            alightDist = t.alignmentDistance;
            altVel = t.altitudeSpeed;
        }

        if(time >= unit.type.homingDelay && shooter != null && !shooter.dead()){
            unit.lookAt(shooter.aimX, shooter.aimY);
        }

        //move forward forever
        unit.moveAt(vec.trns(unit.rotation, unit.type.missileAccelTime <= 0f ? unit.speed() : Mathf.pow(Math.min(time / unit.type.missileAccelTime, 1f), 2f) * unit.speed()));
        if(shooter != null){
            if (unit instanceof BallisticMovementc c) {
                float targetAltitude = 0;
                if (target instanceof BallisticMovementc t) {
                    c.setTargetPos(Tmp.v31.set(shooter.aimX, shooter.aimY, t.altitude()));
                    targetAltitude = t.altitude();
                } else c.setTargetPos(Tmp.v3.set(shooter.aimX, shooter.aimY));
                if (Mathf.dst(unit.x, unit.y, shooter.aimX, shooter.aimY) < alightDist) {
                    
                    if(
                            unit.type() instanceof IcbmUnitType t && t.canMiss &&
                            unit instanceof KineticImpactc k && k.warHead().detonateType instanceof KineticDetonateType d &&
                            Math.abs(targetAltitude - c.altitude()) < d.impactDistance
                    ) k.warHead().detonate(unit);

                    c.moveAltitude(targetAltitude, altVel);
                }else if(unit.type() instanceof IcbmUnitType t) c.moveAltitude(t.peakAltitude, altVel);
            }
        }

        var build = unit.buildOn();
    }

    @Override
    public boolean retarget(){
        //more frequent retarget due to high speed. TODO won't this lag?
        return timer.get(timerTarget, 4f);
    }
}
