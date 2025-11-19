package ds.world.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.graphics.Drawf;

public class DrawWire {
    public static void draw(float x1, float y1, float x2, float y2, Color color, float stroke) {
        Draw.color(color, 1);
        Blending originalBlend = Draw.getBlend();
        Draw.blend(Blending.normal);
        Lines.stroke(stroke);
        Lines.line(x1, y1, x2, y2, false);
        Draw.reset();
    }
}
