package ds.type.entities;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import ds.wprocesses.WorldProcess;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;


public class Casing {
    public float width = 2, height = 4;
    public float sizeFrom = 3, sizeTo = 2f;
    public float x, y, rotation, size, lifetime = 400, dissapearTime = lifetime / 4, drag;
    public float angleVelocity;
    public Vec2 velocity;
    public Color colorFrom = Pal.lightOrange, colorTo = Color.lightGray;
    public Effect existEffect = Fx.none, cooldownEffect = Fx.none;
    public float existEffectInterval = -1, existEffectChance = 0, cooldownEffectInterval = -1, cooldownEffectChance = 1;

    public static Seq<Casing> activeCasings = new Seq<>();

    private float heatTime, time = 0;
    private Vec2 vel;
    private float[] effectTimer = new float[2];

    public static void spawnCasing(Casing casing){
        casing.heatTime = casing.lifetime;
        casing.vel = new Vec2(casing.velocity);
        WorldProcess.logStateProcess("Spawn Casing -> " + casing);
        activeCasings.add(casing);
    }

    public static void updateCasings(){
        for (Casing cas : activeCasings) cas.update();
    }

    public static void drawCasings(){
        for (Casing cas : activeCasings) cas.drawCasing();
    }

    public void update(){
        time += Time.delta;
        this.vel = this.vel.scl(1 - drag * Time.delta);
        angleVelocity *= 1 - drag * Time.delta;

        x += Time.delta * vel.x;
        y += Time.delta * vel.y;
        rotation += angleVelocity;

        if(existEffectInterval > 0){
            effectTimer[0] += Time.delta;
        }
        if(cooldownEffectInterval > 0){
            effectTimer[0] += Time.delta * fout();
        }
        if(effectTimer[0] > existEffectInterval || cooldownEffectInterval == -1) {
            effectTimer[0] = 0;
            if(Mathf.chance(existEffectChance)) existEffect.at(x, y, rotation);
        }

        if(effectTimer[0] > cooldownEffectInterval || cooldownEffectInterval == -1) {
            effectTimer[1] = 0;
            if(Mathf.chance(cooldownEffectChance * fout())) cooldownEffect.at(x, y, rotation);
        }

        if(time > lifetime + dissapearTime) remove();
    }

    public void remove(){
        WorldProcess.logStateProcess("Remove Casing -> " + this);
        activeCasings.remove(this);
    }

    public Casing(){}

    public Casing(float x, float y, Vec2 velocity, float rotation, float angleVelocity){
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.rotation = rotation;
        this.angleVelocity = angleVelocity;
    }

    private float fin(){
        return Mathf.clamp(time / lifetime);
    }

    private float fout(){
        return Mathf.clamp(1 - time / lifetime);
    }

    public void drawCasing(){
        Draw.z(Fx.casing1.layer);
        Draw.color(colorFrom, colorTo, fin());
        float siz = Mathf.lerp(sizeFrom, sizeTo, fin());
        Draw.alpha( 1 - Mathf.clamp((time - lifetime) / dissapearTime));
        Fill.rect(x, y, width * siz, height * siz, rotation);
    }
}
