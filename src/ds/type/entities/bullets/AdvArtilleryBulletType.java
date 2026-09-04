package ds.type.entities.bullets;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import ds.type.entities.Cable;
import ds.world.global.CableProcess;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;

import static mindustry.Vars.headless;

public class AdvArtilleryBulletType extends BasicBulletType {
    public float trailMult = 1f, trailSize = 4f;
    //Bullet trajectory height
    public float trajectoryZ = 50;
    //Degree of bullet rotation deviation along the trajectory
    public float angleFactor = 0.25f;

    public float shadowIncreaseFactor = 1f;

    private static float cdist = 0f;
    private static Unit result;

    public AdvArtilleryBulletType() {
        super(1, 1, "shell");
        collidesTiles = false;
        collides = false;
        collidesAir = false;
        scaleLife = true;
        hitShake = 1f;
        hitSound = Sounds.explosionArtillery;
        hitEffect = Fx.flakExplosion;
        shootEffect = Fx.shootBig;
        trailEffect = Fx.artilleryTrail;
        shrinkInterp = Interp.slope;
    }

    public AdvArtilleryBulletType(float speed, float damage, String sprite){
        super(speed, damage, sprite);
        collidesTiles = false;
        collides = false;
        collidesAir = false;
        scaleLife = true;
        hitShake = 1f;
        hitSound = Sounds.explosionArtillery;
        hitEffect = Fx.flakExplosion;
        shootEffect = Fx.shootBig;
        trailEffect = Fx.artilleryTrail;
        shrinkInterp = Interp.slope;
    }

    @Override
    public void init(Bullet b){
        super.init(b);
        drawSize += trajectoryZ * 1.2f;
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        if(b.time >= (b.lifetime - 5) && !b.hit){
            cdist = 0f;
            result = null;
            float range = b.hitSize;

            Units.nearbyEnemies(b.team, b.x - range, b.y - range, range * 2f, range * 2f, e -> {
                if (e.dead() || !e.checkTarget(collidesAir, collidesGround) || !e.hittable()) return;

                e.hitbox(Tmp.r1);
                if (!Tmp.r1.contains(b.x, b.y)) return;

                float dst = e.dst(b.x, b.y) - e.hitSize;
                if ((result == null || dst < cdist)) {
                    result = e;
                    cdist = dst;
                }
            });

            if (result != null) {
                b.collision(result, b.x, b.y);
                b.hit = true;
            } else if (collidesTiles) {
                Building build = Vars.world.buildWorld(b.x, b.y);
                if (build != null && build.team != b.team) {
                    build.collision(b);
                    hit(b, b.x, b.y);
                    b.hit = true;
                }
            }
        }
    }

    public void drawLight(Bullet b){
        if(lightOpacity <= 0f || lightRadius <= 0f) return;
        float pr = Mathf.sinDeg(b.time() / b.lifetime * 180);
        float bulletZ =  Mathf.sinDeg(b.rotation()) * pr * trajectoryZ * (b.lifetime / b.type.lifetime);
        Drawf.light(b.x, b.y + bulletZ, lightRadius, lightColor, lightOpacity);
    }

    @Override
    public void updateTrail(Bullet b){
        float pr = Mathf.sinDeg(b.time() / b.lifetime * 180);
        float bulletZ =  Mathf.sinDeg(b.rotation()) * pr * trajectoryZ * (b.lifetime / b.type.lifetime);

        if(!headless && trailLength > 0){
            if(b.trail == null){
                b.trail = new Trail(trailLength);
            }
            b.trail.length = trailLength;
            b.trail.update(b.x, b.y + bulletZ, trailInterp.apply(b.fin()) * (1f + (trailSinMag > 0 ? Mathf.absin(Time.time, trailSinScl, trailSinMag) : 0f)));
        }
    }


    @Override
    public void updateTrailEffects(Bullet b){

        float pr = Mathf.sinDeg(b.time() / b.lifetime * 180);
        float bulletZ =  Mathf.sinDeg(b.rotation()) * pr * trajectoryZ * (b.lifetime / b.type.lifetime);
        float secProg = Mathf.sinDeg(b.time() / b.lifetime * 180 + 90);

        float str = Mathf.cosDeg(b.rotation()) * Mathf.sinDeg(b.rotation()) * 2;

        float degree = angleFactor * -secProg * str * Math.min(b.fin() * 2, 1);

        float drawRotation = b.rotation() - degree;

        boolean canSpawn = trailMinVelocity <= 0f || b.vel.len2() >= trailMinVelocity * trailMinVelocity;

        if(trailChance > 0 && canSpawn){
            if(Mathf.chanceDelta(trailChance)){
                if(trailSpread > 0){
                    Tmp.v1.rnd(Mathf.random(trailSpread));
                }else{
                    Tmp.v1.setZero();
                }
                trailEffect.at(b.x + Tmp.v1.x, b.y + Tmp.v1.y + bulletZ, trailRotation ? drawRotation : trailParam, trailColor);
            }
        }

        if(trailInterval > 0f && canSpawn){
            if(b.timer(0, trailInterval)){
                if(trailSpread > 0){
                    Tmp.v1.rnd(Mathf.random(trailSpread));
                }else{
                    Tmp.v1.setZero();
                }
                trailEffect.at(b.x + Tmp.v1.x, b.y + Tmp.v1.y + bulletZ, trailRotation ? drawRotation: trailParam, trailColor);
            }
        }
    }

    @Override
    public void draw(Bullet b){
        float zLayer = Draw.z();

        if(trailLength > 0 && b.trail != null){
            //draw below bullets
            Draw.z(zLayer - 0.0001f);
            b.trail.draw(trailColor, trailWidth);
            Draw.z(zLayer);
        }

        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f) + rotationOffset;
        float pr = Mathf.sinDeg(b.time() / b.lifetime * 180);
        float secProg = Mathf.sinDeg(b.time() / b.lifetime * 180 + 90);
        float shrink = shrinkInterp.apply(b.fout());
        float height = this.height + ((1f - shrinkX) + shrinkX * shrink * Math.min(0.35f + Mathf.sinDeg(b.rotation()), 1) );
        float width = this.width * ((1f - shrinkX) + shrinkX * shrink);


        float bulletZ =  Mathf.sinDeg(b.rotation()) * pr * trajectoryZ * (b.lifetime / b.type.lifetime);


        Draw.z(Layer.darkness);

        Draw.color(Pal.shadow, Pal.shadow.a);

        Draw.rect(frontRegion, b.x , b.y, Mathf.lerp(width, width + trajectoryZ / 8, pr * (b.lifetime / b.type.lifetime) * shadowIncreaseFactor), Mathf.lerp(height, height + trajectoryZ / 8, pr * (b.lifetime / b.type.lifetime) * shadowIncreaseFactor), b.rotation() + offset);

        Draw.z(zLayer);

        Color mix = Tmp.c1.set(mixColorFrom).lerp(mixColorTo, b.fin());
        Draw.mixcol(mix, mix.a);

        float str = Mathf.cosDeg(b.rotation()) * Mathf.sinDeg(b.rotation()) * 2;

        float degree = angleFactor * -secProg * str * Math.min(b.fin() * 2, 1);

        float drawRotation = b.rotation() - degree;

        if(backRegion.found()){
            Draw.color(backColor);
            Draw.rect(backRegion, b.x, b.y + bulletZ, width, height, drawRotation + offset);
        }

        Draw.color(frontColor);
        Draw.rect(frontRegion, b.x, b.y + bulletZ, width, height, drawRotation + offset);
        Draw.reset();
    }
}
