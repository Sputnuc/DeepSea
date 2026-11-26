package ds.type.entities.comp;

import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.math.geom.Vec3;
import arc.util.Time;
import arc.util.Tmp;
import deepsea.gen.BallisticMovementc;
import ds.type.dsUnitTypes.IcbmUnitType;
import ent.anno.Annotations;
import ent.anno.Annotations.*;
import mindustry.game.Team;
import mindustry.gen.Posc;
import mindustry.gen.TimedKillc;
import mindustry.gen.Timerc;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;

@EntityComponent
abstract class BallisticMovementComp implements Unitc, Timerc, TimedKillc {

    @Import UnitType type;
    @Import float x, y, rotation, elevation;

    Posc target;

    float targetX, targetY, targetZ;

    @Import float time, lifetime;

    @SyncField(true) @SyncLocal float alt, altVel;

    @Override
    @Replace(100)
    public float speed(){
        float strafePenalty = isGrounded() || !isPlayer() ? 1f : Mathf.lerp(1f, type.strafePenalty, Angles.angleDist(vel().angle(), rotation) / 180f);
        float boost = Mathf.lerp(1f, type.canBoost ? type.boostMultiplier : 1f, elevation);
        return type.speed * strafePenalty * boost * floorSpeedMultiplier();
    }

    public void setVel3D(float vx, float vy, float vz){
        vel().set(vx, vy);
        rotation = Angles.angle(vx, vy);
        altVel = vz;
    }

    public void setVel3D(Vec3 v){
        setVel3D(v.x, v.y, v.z);
    }

    public void setTargetPos(Vec2 pos){
        targetX = pos.x; targetY = pos.y; targetZ = 0;
    }

    public void setTargetPos(Vec3 pos){
        targetX = pos.x; targetY = pos.y; targetZ = pos.z;
    }

    public void updateTarget(){
        targetX = target.getX(); targetY = target.getY();
        if(target instanceof BallisticMovementc c) targetZ = c.altitude();
    }

    public void updateTarget(Posc t){
        target = t;
        updateTarget();
    }

    public float altitude(){
        return alt;
    }

    public float altitudeIntercept(){
        return alt + altVel * Time.delta;
    }

    public void moveAltitude(float to, float vel){
        altVel = Mathf.clamp((to - alt) / Time.delta, -vel, vel);
    }

    @Override
    @Annotations.Replace(10)
    public void lookAt(float angle){
        rotation = Angles.moveToward(rotation, angle, type.rotateSpeed * Time.delta * speedMultiplier());
    }

    public Vec3 getPos(){
        return Tmp.v31.set(x, y, alt);
    }

    @Override
    @Replace(100)
    public boolean targetable(Team targeter){
        return type.targetable(self(), targeter) && altitudeIntercept() < 10;
    }

    @Override
    @Replace(100)
    public boolean hittable(){
        return type.hittable(self()) && altitudeIntercept() < 10;
    }

    public boolean targetable(Team targeter, float peakAltitude){
        return type.targetable(self(), targeter) && peakAltitude >= altitudeIntercept();
    }

    public boolean hittable(float interceptAltitude){
        return  type.hittable(self()) && interceptAltitude >= altitudeIntercept();
    }

    public float altitudeFraction(){
        return type instanceof IcbmUnitType t && t.peakAltitude > 0f ? Mathf.clamp(alt / t.peakAltitude) : 0f;
    }

    public float altitudeFraction(float min){
        return type instanceof IcbmUnitType t && t.peakAltitude > 0f ? Math.max(Mathf.clamp(alt / t.peakAltitude), min) : min;
    }

    @Override
    public void kill(){
        elevation = 0;
    }

    @Override
    public void update(){
        elevation = dead() ? 0 : 1;
        alt += altVel * Time.delta;

        if(target != null) updateTarget();
    }
}
