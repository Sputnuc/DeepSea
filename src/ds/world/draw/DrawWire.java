package ds.world.draw;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

public class DrawWire {
    public static TextureRegion wireText;

    public static void loadTexture(){
        wireText = Core.atlas.find("harpoon-wire");
    }
    public static void draw(float x1, float y1, float x2, float y2, Color color, float stroke) {
        Lines.stroke(stroke);
        Draw.z(Layer.bullet - 5);
        Lines.line(wireText, x1, y1, x2, y2, false);
    }
}
