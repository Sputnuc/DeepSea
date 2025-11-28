package ds.content;

import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.*;

public class dsFx {
    public static Effect
    dsInclinedWave = new Effect(30, e -> {
        color(Color.valueOf("ffffff"), Color.valueOf("ffffff00"), e.fin());
        Lines.stroke(1 * e.fout(Interp.circleOut));
        float foffset = 3;
        float eX = Mathf.cosDeg(e.rotation)* foffset;
        float eY = Mathf.sinDeg(e.rotation) * foffset;
        Lines.ellipse(e.x + eX, e.y + eY, 0.45f * e.fin()+ 0.45f, 2.5f, 8, e.rotation);
    });
}
