package ds.type.entities.comp;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import ds.type.dsUnitTypes.SubmarineUnitType;
import ds.world.meta.DSEnv;
import ent.anno.Annotations.*;
import mindustry.Vars;
import mindustry.async.PhysicsProcess;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.type.UnitType;

import static mindustry.Vars.control;
import static mindustry.Vars.state;

@EntityComponent
abstract class SubmarineMoveComp implements WaterMovec, Velc{
    @Import UnitType type;
    @Import float elevation;
    @Import boolean dead;
    @Import Vec2 vel;
    @SyncLocal float detectTime;
    @SyncLocal float submerging;

    /** @return true, если юнит сейчас должен двигаться как летающий. */
    public boolean diving(){
        int env = type instanceof SubmarineUnitType sub ? sub.diveEnv : DSEnv.underwaterWarm;
        return state.rules.hasEnv(env);
    }

    /** Нужно в add(), чтобы на первом же update() юнит на суше/воде не убился из-за неправильной solidity. */
    @Override
    public void add(){
        elevation = diving() ? 1f : 0f;
        detectTime = 0;
    }

    @Override
    public void update(){
        detectTime = Math.max(detectTime - Time.delta, 0);
        if(!dead) elevation = diving() ? 1f : 0f;
        submerging = !diving() ? submerging : 0f;
        if((!Vars.net.client() || isLocal()) && diving() && type instanceof SubmarineUnitType s) {
            vel.scl(Math.max(1f - s.underwaterDrag * Time.delta, 0));
        }
        if(!diving()){
            if(type instanceof SubmarineUnitType sub){
                float progress = Mathf.clamp(vel.len() / sub.speed);
                float pitch = Mathf.lerp(sub.navalMoveSoundPitchMin,  sub.navalMoveSoundPitchMax, progress);
                control.sound.loop(sub.navalMoveSound, this, sub.navalMoveSoundVolume * progress, pitch);
            }
        }
    }

    public void submerge(boolean submerge){
        if(!(type instanceof SubmarineUnitType)) return;
        submerging = Mathf.approachDelta(submerging, Mathf.num(submerge), 1);
    }

    public boolean submerged(){
        return submerging > 0.45f;
    }

    public boolean detected(){
        return detectTime > 0 || !submerged();
    }

    @MethodPriority(10f)
    @Replace
    public void draw(){
        if(!submerged()){
            type.draw(self());
        } else Drawf.underwater(() -> {
            if(type instanceof SubmarineUnitType sub) {
                sub.drawUnderwater(self());
            } type.draw(self());
        });
    }

    @Replace
    @Override
    public boolean hittable(){
        return detected() && type.hittable(self());
    }

    @Replace
    @Override
    public boolean targetable(Team targeter){
        return detected() && type.targetable(self(), targeter);
    }

}