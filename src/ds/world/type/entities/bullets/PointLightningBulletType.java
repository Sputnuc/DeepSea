package ds.world.type.entities.bullets;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import ds.world.draw.DrawCurveLightning;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;

public class PointLightningBulletType extends BulletType {
    private static float cdist = 0f;
    private static Unit result;
    public float segLen = 4;
    public Color lightningColor;

    public float trailSpacing = 10f;

    public PointLightningBulletType(){
        trailEffect = Fx.none;
        scaleLife = true;
        lifetime = 100f;
        collides = false;
        reflectable = false;
        keepVelocity = false;
        lightningColor = Color.valueOf("e3ffff");
    }
    public PointLightningBulletType(float damage){
        this.damage = damage;
        trailEffect = Fx.none;
        scaleLife = true;
        lifetime = 100f;
        collides = false;
        reflectable = false;
        keepVelocity = false;
        lightningColor = Color.valueOf("e3ffff");
    }

    @Override
    public void init(Bullet b){
        super.init(b);
        float px = b.x + b.lifetime * b.vel.x,
                py = b.y + b.lifetime * b.vel.y,
                rot = b.rotation();
        float vecLen = (float) Math.hypot(px-b.x, py-b.y);
        int segments = (int) (vecLen / segLen);
        Vec2 pos = new Vec2(b.x, b.y);
        Vec2 toPos = new Vec2(px, py);

        DrawCurveLightning.drawCurveLightning(pos, toPos, segments, segLen, rot, lightningColor);


        b.time = b.lifetime;
        b.set(px, py);

        //calculate hit entity

        cdist = 0f;
        result = null;
        float range = 1f;

        Units.nearbyEnemies(b.team, px - range, py - range, range*2f, range*2f, e -> {
            if(e.dead() || !e.checkTarget(collidesAir, collidesGround) || !e.hittable()) return;

            e.hitbox(Tmp.r1);
            if(!Tmp.r1.contains(px, py)) return;

            float dst = e.dst(px, py) - e.hitSize;
            if((result == null || dst < cdist)){
                result = e;
                cdist = dst;
            }
        });

        if(result != null){
            b.collision(result, px, py);
        }else if(collidesTiles){
            Building build = Vars.world.buildWorld(px, py);
            if(build != null && build.team != b.team){
                build.collision(b);
                hit(b, px, py);
                b.hit = true;
            }
        }

        b.remove();

        b.vel.setZero();
    }


}
