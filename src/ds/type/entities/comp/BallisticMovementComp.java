package ds.type.entities.comp;

import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.math.geom.Vec3;
import arc.util.Time;
import ds.type.dsUnitTypes.IcbtUnitType;
import ent.anno.Annotations.*;
import mindustry.gen.Timerc;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;

@EntityComponent
abstract class BallisticMovementComp implements Unitc, Timerc{

    @Import UnitType type;
    @Import float x, y, rotation, elevation;
    @Import Vec2 vel;
    @Import boolean dead;

    float flightT, startX, startY, targetX, targetY;
    boolean launched;

    transient float alt, pathLength, flightTime;
    transient Vec3 p0 = new Vec3(), p1 = new Vec3(), p2 = new Vec3();

    public void launch(float tx, float ty){
        startX = x;
        startY = y;
        targetX = tx;
        targetY = ty;
        flightT = 0f;
        alt = 0f;
        launched = true;
        pathLength = 0f; //пересчитается в update
    }

    public float altitude(){
        return alt;
    }

    public float altitudeFraction(){
        return type instanceof IcbtUnitType t && t.peakAltitude > 0f ? Mathf.clamp(alt / t.peakAltitude) : 0f;
    }

    public float timeLeft(){
        return launched && type instanceof IcbtUnitType t ? (1f - flightT) * flightTime(t) : 0f;
    }

    public void predict(float ticksAhead, Vec3 out){
        if(!launched || !(type instanceof IcbtUnitType t)){
            out.set(x, y, alt);
            return;
        }
        float tt = Mathf.clamp(flightT + ticksAhead / flightTime(t));
        t.pattern.evaluate(startX, startY, targetX, targetY, t.peakAltitude, tt, out);
    }



    float flightTime(IcbtUnitType t){
        if(pathLength <= 0f) pathLength = Math.max(t.pattern.groundLength(startX, startY, targetX, targetY), 1f);
        return Math.max(pathLength / Math.max(t.speed, 0.01f), 1f);
    }

    @Override
    public void update(){
        elevation = 1f;
        if(!launched || !(type instanceof IcbtUnitType t)) return;

        flightT = Math.min(flightT + Time.delta / flightTime(t), 1f);
        t.pattern.evaluate(startX, startY, targetX, targetY, t.peakAltitude, flightT, p0);
        x = p0.x;
        y = p0.y;
        alt = p0.z;
        t.pattern.evaluate(startX, startY, targetX, targetY, t.peakAltitude, Math.max(flightT - 0.01f, 0f), p1);
        t.pattern.evaluate(startX, startY, targetX, targetY, t.peakAltitude, Math.min(flightT + 0.01f, 1f), p2);
        if(Mathf.dst(p1.x, p1.y, p2.x, p2.y) > 1e-3f) rotation = Angles.angle(p1.x, p1.y, p2.x, p2.y);

        vel.setZero();
    }
}
