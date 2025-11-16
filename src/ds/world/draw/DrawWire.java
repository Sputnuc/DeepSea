package ds.world.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.graphics.Drawf;

public class DrawWire {
    public static void draw(float x1, float y1, float x2, float y2, Color color, float time, float fadeTime, float lifetime, float stroke) {
        Drawf.light(x2, y2, 0, color, 0);
        Lines.stroke(stroke, color);
        Lines.line(x1, y1, x2, y2, true);
        Draw.reset();
    }
}
