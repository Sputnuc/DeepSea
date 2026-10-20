package ds.type.entities.bullets;

import arc.graphics.g2d.Draw;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;

import static mindustry.Vars.headless;

public class AdvArtilleryBulletType extends ArtilleryBulletType {
    public float trailMult = 1f, trailSize = 4f;
    //Bullet trajectory height
    public float trajectoryZ = 50;
    //Degree of bullet rotation deviation along the trajectory
    public float angleFactor = 0.25f;

    public float shadowIncreaseFactor = 0.15f;

    private static float cdist = 0f;
    private static Unit result;

    public AdvArtilleryBulletType() {
        super(1, 1, "shell");
    }

    public AdvArtilleryBulletType(float speed, float damage, String sprite){
        super(speed, damage, sprite);
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

    @Override
    public void draw(Bullet b){
        float zLayer = Draw.z();

        float shrink = shrinkInterp.apply(b.fout());
        float height = this.height * ((1f - shrinkY) + shrinkY * shrink);
        float width = this.width * ((1f - shrinkX) + shrinkX * shrink);
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f) + rotationOffset;


        Draw.z(Layer.darkness);

        Draw.color(Pal.shadow, Pal.shadow.a);

        float shadowSize = 1 + (b.fin() * b.fout() * 4) * trajectoryZ / 8 * shadowIncreaseFactor * (b.lifetime / b.type.lifetime);

        Draw.alpha(((1 - (b.fin() * b.fout() * 4)) * Pal.shadow.a * 0.9f) + (Pal.shadow.a * 0.4f));

        Draw.rect(frontRegion, b.x - (b.fin() * b.fout() * 4) * trajectoryZ , b.y - (b.fin() * b.fout() * 4) * trajectoryZ, width * shadowSize, height * shadowSize, b.rotation() + offset);

        Draw.z(zLayer);
        super.draw(b);
    }
}
