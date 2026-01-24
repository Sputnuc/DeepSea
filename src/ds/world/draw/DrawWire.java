package ds.world.draw;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

//Draws line with coordinates
//TODO segmented wire with physics
public class DrawWire {
    public static void draw(float x1, float y1, float x2, float y2, TextureRegion region, float stroke) {
        Lines.stroke(stroke);
        Draw.z(Layer.turret - 1);
        Lines.line(region, x1, y1, x2, y2, false);
    }
}
