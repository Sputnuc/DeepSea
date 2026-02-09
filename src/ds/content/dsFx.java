package ds.content;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

public class dsFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static Effect
    dsInclinedWave = new Effect(30, e -> {
        color(Color.valueOf("ffffff"), Color.valueOf("ffffff00"), e.fin());
        stroke(1 * e.fout(Interp.circleOut));
        float foffset = 3;
        float eX = Mathf.cosDeg(e.rotation)* foffset;
        float eY = Mathf.sinDeg(e.rotation) * foffset;
        Lines.ellipse(e.x + eX, e.y + eY, 0.45f * e.fin()+ 0.45f, 2.5f, 8, e.rotation);
    }),
    dsMoveEffect = new Effect(45, e -> {
        color(Color.valueOf("e0f4ff"), Color.valueOf("bfe0f200"), e.fin());
        randLenVectors(e.id, 3, 10 * e.fin(), (x, y)->{
            Fill.circle(e.x + x, e.y + y, e.fin() * 0.75f + 1);
        });
    }),
    waterBlood = new Effect(600, e ->{
        color(Color.valueOf("e52020fe"), Color.valueOf("9e191900"), e.fin());
        randLenVectors(e.id, 9, 8f * e.fin(Interp.circleOut) * 4.25f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fin(Interp.circleOut) * 5f + 0.75f);
        });
    }),
    staticBlackSmoke = new Effect(60, e ->{
        color(Color.valueOf("141414"), Color.valueOf("05040400"), e.fin());
        Draw.z(90);
        randLenVectors(e.id, 4, 40f * e.fin(Interp.circleOut) + 25f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fin(Interp.circleOut) * 6f + 4.75f);
        });
    }),
    torpedoTrail = new Effect(120, e -> {
        color(Color.valueOf("e0f4ff80"), Color.valueOf("bfe0f200"), e.fin());
        randLenVectors(e.id, 2, 10 * e.fin(), (x, y)->{
            Fill.circle(e.x + x, e.y + y, e.fin() * 0.75f + 1);
        });
    }),
    drillImpact = new Effect(150, e->{
        color(Color.valueOf("525570"), Color.valueOf("a4adfc00"), e.fin(Interp.circleOut));
        randLenVectors(e.id, 7, 30 * e.fin(), (x, y)->{
            Fill.circle(e.x + x, e.y + y, e.fin() * 1.75f + 1.5f);
        });
    }),
    dsShoot = new Effect(15, e->{
        color(Color.valueOf("a1e9ff"), Color.valueOf("ffffff"), Color.valueOf("2e4b94"), e.fin());
        Drawf.tri(e.x, e.y, 1.75f * e.fout(Interp.circleOut), 5.75f * e.fout(), e.rotation - 90);
        Drawf.tri(e.x, e.y, 1.75f * e.fout(Interp.circleOut), 5.75f * e.fout(), e.rotation + 90);
        Drawf.tri(e.x, e.y, 2.5f * e.fout(Interp.circleOut), 2 + 7 * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, 2.5f * e.fout(Interp.circleOut), 1 + 1 * e.fout(), e.rotation + 180);
    }),
    dsShootBig = new Effect(15, e->{
        color(Color.valueOf("a1e9ff"), Color.valueOf("ffffff"), Color.valueOf("2e4b94"), e.fin());
        Drawf.tri(e.x, e.y, 2.75f * e.fout(Interp.circleOut), 8.75f * e.fout(), e.rotation - 90);
        Drawf.tri(e.x, e.y, 2.75f * e.fout(Interp.circleOut), 8.75f * e.fout(), e.rotation + 90);
        Drawf.tri(e.x, e.y, 3.75f * e.fout(Interp.circleOut), 2 + 10 * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, 3.75f * e.fout(Interp.circleOut), 1 + 2.5f * e.fout(), e.rotation + 180);
        stroke(1 * e.fout(Interp.circleOut));
        float foffset = 2 + 2 * e.fin();
        float eX = Mathf.cosDeg(e.rotation)* foffset;
        float eY = Mathf.sinDeg(e.rotation) * foffset;
        Lines.ellipse(e.x + eX, e.y + eY, 0.45f * e.fin()+ 0.25f, 1.5f, 3, e.rotation);
    }),
    dsBulletTrail = new Effect(15, e ->{
        color(Color.valueOf("e0f4ff"), Color.valueOf("bfe0f200"), e.fin());
        randLenVectors(e.id, 3, 5 * e.fin(), (x, y)->{
            Fill.circle(e.x + x, e.y + y, e.fin() * 0.85f + 0.45f);
        });
    }),
    dsBulletSparkTrail = new Effect(4, e ->{
        color(Color.valueOf("ffffff"), e.color, e.fin());
        stroke(0.75f);
        randLenVectors(e.id, 3, 6 * e.fin(), (x, y)->{
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 3);
        });
        Drawf.light(e.x, e.y, 6 * e.fin(), e.color, 0.2f * e.fout());
        stroke(1.5f * e.fout());
    }),
    dsBulletHit = new Effect(15, e->{
        Color eColor = e.color;
        color(Color.valueOf("ffffff"), eColor, e.fin());
        stroke(0.75f);
        randLenVectors(e.id, 6, 3 + 13 * e.fin(), (x, y)->{
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 6);
        });
        stroke(1.5f * e.fout());
        Lines.circle(e.x, e.y, 10 * e.fin());
        Drawf.light(e.x, e.y, 4 + 7 * e.fout(), Color.valueOf("ccdef0"), 0.7f * e.fout());
    }),
    sulfurVentSteam = new Effect(250f, e -> {
        color(e.color, Color.valueOf("22261000"), e.fin());

        alpha(e.fslope() * 0.78f);

        float length = 3f + e.finpow() * 15f;
        rand.setSeed(e.id);
        for(int i = 0; i < rand.random(3, 5); i++){
            v.trns(rand.random(360f), rand.random(length));
            Fill.circle(e.x + v.x, e.y + v.y, rand.random(1.2f, 2.5f) + e.fin() * 1.95f);
        }
    }).layer(Layer.darkness - 1),

    geyserSteam = new Effect(60f, e -> {
        color(e.color, Color.valueOf("22261000"), e.fin());

        alpha(e.fslope() * 0.78f);

        float length = 3f + e.finpow() * 5f;
        rand.setSeed(e.id);
        for(int i = 0; i < rand.random(3, 5); i++){
            v.trns(rand.random(360f), rand.random(length));
            Fill.circle(e.x + v.x, e.y + v.y, rand.random(1.2f, 2.5f) + e.fin() * 1.95f);
        }
    }).layer(Layer.darkness - 1),

    geotermalBubbles = new ParticleEffect(){{
        particles = 1;
        lifetime = 240;
        region = "deepsea-water-bubble";
        cone = 10;
        lightScl = 0;
        lightOpacity = 0;
        rotWithParent = false;
        useRotation = false;
        baseRotation = 90;
        length = 45;
        interp = Interp.circleOut;
        sizeInterp = Interp.pow5Out;
        sizeFrom = 1; sizeTo = 0.35f;
        colorFrom = Color.white;
        colorTo = Color.valueOf("ffffff").a(0);
    }};

    //FX Functions

    public static Effect smoothColorCircle(Color out, float rad, float lifetime, Interp interpolation) {
        return new Effect(lifetime, rad * 2, e -> {
            Draw.blend(Blending.additive);
            float radius = e.fin(interpolation) * rad;
            Fill.light(e.x, e.y, circleVertices(radius), radius, Color.clear, Tmp.c1.set(out).a(e.fout(Interp.pow5Out)));
            Drawf.light(e.x, e.y, radius * 1.3f, out, 0.7f * e.fout(0.23f));
            Draw.blend();
        }).layer(Layer.effect + 0.15f);
    }
}
