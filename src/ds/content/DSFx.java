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
import arc.struct.Seq;
import arc.util.Tmp;
import ds.world.graphics.DSPal;
import mindustry.entities.Effect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

public class DSFx {
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
    dsColorSparkSmall = new Effect(15f, e -> {
        color(e.color);
        stroke(e.fout() * 1.1f + 0.5f);

        randLenVectors(e.id, 2, 14f * e.fin(), e.rotation, 0f, (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 5f + 0.5f);
        });
    }),
            dslightning = new Effect(10f, 500f, e -> {
                if(!(e.data instanceof Seq)) return;
                Seq<Vec2> lines = e.data();

                stroke(3f * e.fout());
                color(e.color, Color.white, e.fin());

                for(int i = 0; i < lines.size - 1; i++){
                    Vec2 cur = lines.get(i);
                    Vec2 next = lines.get(i + 1);
                    Drawf.light(cur.x, cur.y, next.x, next.y, 5f * e.fout(Interp.circleOut), e.color, e.fout(Interp.circleOut));
                    Lines.line(cur.x, cur.y, next.x, next.y, false);
                }

                for(Vec2 p : lines){
                    Fill.circle(p.x, p.y, Lines.getStroke() / 2f);
                }
            }),
    dsMoveEffect = new Effect(45, e -> {
        color(Color.valueOf("e0f4ff"), Color.valueOf("bfe0f200"), e.fin());
        randLenVectors(e.id, 2, 30 * e.fin(),  e.rotation - 180,  85,(x, y)->{
            Fill.circle(e.x + x, e.y + y, e.fout() * 1.25f);
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
    dsShootTank = new Effect(16, e->{
        color(Color.valueOf("a1e9ff"), Color.valueOf("ffffff"), Color.valueOf("2e4b94"), e.fin());
        for(int sign : Mathf.signs){
            Drawf.tri(e.x, e.y, 4 * e.fout(Interp.circleOut), 8 * e.fin(), e.rotation + 45 * sign + (65 * sign * e.fout(Interp.pow5Out)));
            Drawf.tri(e.x, e.y, 6 * e.fout(Interp.pow3Out), 13 * e.fin(), e.rotation);
            color(Color.valueOf("a1e9ff"));
            stroke(1 * e.fout(Interp.circleOut));
            float foffset = 2 + 14 * e.fin(Interp.circleOut);
            float eX = Mathf.cosDeg(e.rotation)* foffset;
            float eY = Mathf.sinDeg(e.rotation) * foffset;
            Lines.ellipse(e.x + eX, e.y + eY, 0.45f * e.fin()+ 0.25f, 2.5f, 8, e.rotation);
        };
    }),
    dsBulletTrail = new Effect(12, e ->{
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
    dsMassiveExplosion = new Effect(40, e ->{
        color(e.color);
        stroke(e.fout() * 2.5f);
        float circleRad = 6f + e.finpow() * 90f;
        Lines.circle(e.x, e.y, circleRad);
        stroke(e.fout() * 0.25f + 0.9f);
        rand.setSeed(e.id);
        for(int i = 0; i < 19; i++){
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1f);
            Lines.lineAngle(e.x, e.y, angle, e.foutpow() * 40f * rand.random(1f, 0.8f) + 2f, e.finpow() * 89f * lenRand + 6f);
        }
        Drawf.light(e.x, e.y, circleRad * 2.5f, e.color, e.fout());
    }),
    dsMassiveDeepSmoke = new Effect(100, e ->{
        color(e.color ,Color.valueOf("b5b3b3").a(0.8f), Color.valueOf("212121").a(0), e.fin());
        randLenVectors(e.id, 14, 70f * e.fin(Interp.pow5Out), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout(Interp.circleOut) * 4 + 1.45f);
        });
        randLenVectors(e.id+1, 8, 60f * e.fin(Interp.pow5Out), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2 + 0.95f);
        });
    }),
    dsMassiveSparkSpikes = new Effect(29, e->{
        color(e.color);
        rand.setSeed(e.id);
        for(int i = 0; i < 5; i++){
            float len = rand.random(50, 90);
            float wid = rand.random(1, 2);
            float rot = rand.random(360);
            Drawf.tri(e.x, e.y, wid * e.fout(Interp.pow3Out), len * 0.8f + (len * 0.2f * e.fin()), rot);
        }
        rand.setSeed(e.id+1);
        for(int i = 0; i < 3; i++){
            float len = rand.random(90, 140);
            float wid = rand.random(8, 6);
            float rot = rand.random(360);
            Drawf.tri(e.x, e.y, wid * e.fout(Interp.pow3Out), len * e.fin(), rot);
        }
    }),
    dsColorSparkTrail = new Effect(12, e->{
        color(e.color);
        stroke(0.6f + e.fout() * 1.1f);
        rand.setSeed(e.id);

        for(int i = 0; i < 3; i++){
            float rot = e.rotation + rand.range(15f) + 180f;
            v.trns(rot, rand.random(e.fin() * 25f));
            lineAngle(e.x + v.x, e.y + v.y, rot, e.fout() * rand.random(4f, 9f) + 1.2f);
        }
        rand.setSeed(e.id+1);
        for(int s : Mathf.signs){
            lineAngle(e.x, e.y, e.rotation + 90 * s + 20 * s * e.fin(Interp.circleOut), e.fout(Interp.circleOut) * 5 + 1.2f);
        }
        Drawf.light(e.x, e.y,  e.fout(Interp.circleOut) * 10, e.color, e.fout() * 0.5f);
    }),
    dsFastSparkTrail = new ParticleEffect(){{
        cone = 12.5f;
        length = -16;
        lifetime = 15;
        particles = 3;
        line = true;
        lenFrom = 3;
        lenTo = 5.85f;
        interp = Interp.linear;
        sizeInterp = Interp.circleOut;
        strokeFrom = 1.75f;
        strokeTo = 0;
        colorFrom = DSPal.dsBulletFront;
        colorTo = DSPal.dsBulletBack;
    }},
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
    }},

    drillDetonateEffectTri = new Effect(70, e ->{
        color(Color.valueOf("fcffeb"), Color.valueOf("d1c497"), e.fin());
        float rotation = Mathf.randomSeedRange(e.id, 35);
        for(int sign : Mathf.signs){
            float cOut = e.fout(Interp.circleOut);
            Drawf.tri(e.x, e.y, 12 * cOut, 30 * e.fin(Interp.bounceOut), 90 + rotation + 90 * sign);
            Drawf.light(e.x, e.y, 30 * cOut, Color.valueOf("fcffeb"), 0.4f * cOut + 0.15f);
            stroke(e.fout(Interp.circleOut) * 1.15f);
            randLenVectors(e.id + 1, 9, 3 + 50 * e.fin(Interp.pow5Out), 90 + rotation + 90 * sign, 35, (x, y)->{
                lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout(Interp.pow5Out) * 6f);
            });

        }
    }),
    drillDetonateEffectWave = new Effect(40, e ->{
        color(Color.valueOf("fcffeb"), Color.valueOf("d1c497"), e.fin());

        stroke(e.fout(Interp.circleOut) * 1.85f);
        randLenVectors(e.id + 1, 12, 1f + 33f * e.fin(Interp.pow2Out), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 7f);
        });
        stroke(e.fout(Interp.circleOut));
        Lines.circle(e.x, e.y, e.fin(Interp.pow2Out) * 29f + 1);
    });

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
