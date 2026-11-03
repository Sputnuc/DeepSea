package ds.type.entities.comp;

import arc.math.Angles;
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
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.control;
import static mindustry.Vars.state;

@EntityComponent
abstract class SubmarineMoveComp implements WaterMovec, Velc{
    @Import UnitType type;
    @Import float elevation;
    @Import boolean dead, disarmed;
    @Import Vec2 vel;
    @SyncLocal float detectTime;
    @SyncLocal float submerging;
    transient boolean wasSubmerged;
    @Import float splashTimer, x, y, rotation;

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

        submerge(Mathf.cosDeg(Time.time / 10) >= 0);

        if((!Vars.net.client() || isLocal()) && (diving() || submerged()) && type instanceof SubmarineUnitType s) {
            vel.scl(Math.max(1f - s.underwaterDrag * Time.delta, 0));
        }
        if(!submerged() && !diving() && type instanceof SubmarineUnitType sub){
            if(sub.doRipple){
                Floor floor = floorOn();
                Tile tile = tileOn();

                if((splashTimer += Mathf.dst(deltaX(), deltaY())) >= (7f + hitSize()/8f)){
                    floor.walkEffect.at(x, y, hitSize() / 8f, tile != null ? tile.getFloorColor() : floor.mapColor);
                    splashTimer = 0f;

                    if(type.emitWalkSound){
                        floor.walkSound.at(x, y, Mathf.random(floor.walkSoundPitchMin, floor.walkSoundPitchMax), floor.walkSoundVolume);
                    }
                }
            }
            float progress = Mathf.clamp(vel.len() / sub.speed);
            float pitch = Mathf.lerp(sub.navalMoveSoundPitchMin, sub.navalMoveSoundPitchMax, progress);
            control.sound.loop(sub.navalMoveSound, this, sub.navalMoveSoundVolume * progress, pitch);
        }
    }

    public void submerge(boolean submerge){
        if(!(type instanceof SubmarineUnitType)) return;
        submerging = Mathf.approachDelta(submerging, Mathf.num(submerge), 0.01f);

        if(!wasSubmerged && submerging <= 0.9f) {
            ((SubmarineUnitType) type).submergeEffect(self());
            wasSubmerged = true;
        }else if(wasSubmerged && submerging > 0.9f){
            ((SubmarineUnitType) type).submergeEffect(self());
            wasSubmerged = false;
        }
    }

    @Replace
    public float speed(){
        float strafePenalty = (isGrounded() && !(type instanceof SubmarineUnitType)) || !isPlayer() ? 1f : Mathf.lerp(1f, type.strafePenalty, Angles.angleDist(vel().angle(), rotation) / 180f);
        float boost = Mathf.lerp(1f, type.canBoost ? type.boostMultiplier : 1f, elevation);
        return type.speed * strafePenalty * boost * floorSpeedMultiplier();
    }

    public boolean submerged(){
        return submerging > 0.45f;
    }

    public boolean detected(){
        return detectTime > 0.01 || !submerged() || diving();
    }

    @MethodPriority(10f)
    @Replace
    public void draw(){
        if(type instanceof SubmarineUnitType sub) {
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

    @Replace
    @Override
    public boolean hittable(){
        return detected() && type.hittable(self());
    }

    @Replace
    @Override
    public boolean targetable(Team targeter) {
        return detected() && type.targetable(self(), targeter);
    }
}