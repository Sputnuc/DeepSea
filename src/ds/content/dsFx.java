package ds.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.randLenVectors;

public class dsFx {
    public static Effect
    dsInclinedWave = new Effect(30, e -> {
        color(Color.valueOf("ffffff"), Color.valueOf("ffffff00"), e.fin());
        Lines.stroke(1 * e.fout(Interp.circleOut));
        float foffset = 3;
        float eX = Mathf.cosDeg(e.rotation)* foffset;
        float eY = Mathf.sinDeg(e.rotation) * foffset;
        Lines.ellipse(e.x + eX, e.y + eY, 0.45f * e.fin()+ 0.45f, 2.5f, 8, e.rotation);
    }),
    waterBlood = new Effect(600, e ->{
        color(Color.valueOf("e52020fe"), Color.valueOf("9e191900"), e.fin());
        randLenVectors(e.id, 9, 8f * e.fin(Interp.circleOut) * 4.25f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fin(Interp.circleOut) * 5f + 0.75f);
        });
    }),
    torpedoTrail = new Effect(120, e -> {
        color(Color.valueOf("e0f4ff"), Color.valueOf("bfe0f200"), e.fin());
        randLenVectors(e.id, 2, 10 * e.fin(), (x, y)->{
            Fill.circle(e.x + x, e.y + y, e.fin() * 0.75f + 1);
        });
    }),
    drillImpact = new Effect(150, e->{
        color(Color.valueOf("525570"), Color.valueOf("a4adfc00"), e.fin(Interp.circleOut));
        randLenVectors(e.id, 7, 30 * e.fin(), (x, y)->{
            Fill.circle(e.x + x, e.y + y, e.fin() * 1.75f + 1.5f);
        });
    });
}
