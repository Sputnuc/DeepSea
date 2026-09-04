package ds.draw;

import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.graphics.Layer;

//Draws line with coordinates
//TODO segmented wire with physics
public class DrawWire {

    public static void draw(float x1, float y1, float x2, float y2, TextureRegion region, TextureRegion startRegion, float regionStroke, float regionLen, float rotation){
        float hypot = (float) Math.hypot(x2-x1, y2-y1);
        int steps = Math.round(hypot/regionLen);

        //Calculate all points to draw
        Seq<Vec2> points = calculatePoints(x1, y1, x2, y2, steps);
        Vec2 tmpVec2 = new Vec2(x1, y1);

        //Starts drawing
        Draw.z(Layer.turret - 1);
        Lines.stroke(regionStroke);
        for(Vec2 curPoint : points){
            Lines.line(region, tmpVec2.x, tmpVec2.y, curPoint.x, curPoint.y, true);
            tmpVec2 = curPoint;
        }
        Draw.rect(startRegion, x1, y1, startRegion.width * regionStroke / 10, startRegion.width * regionStroke / 10, rotation);
    }

    public static void draw(float x1, float y1, float x2, float y2, TextureRegion region, TextureRegion startRegion, float regionStroke, float regionLen) {
        float rot = Angles.angle(x1, y1, x2, y2);
        draw(x1, y1, x2, y2, region, startRegion, regionStroke, regionLen, rot);
    }

    public static void draw(Vec2 startPos, Vec2 endPos, TextureRegion region, TextureRegion startRegion, float regionStroke, float regionLen){
        draw(startPos.x, startPos.y, endPos.x, endPos.y, region, startRegion, regionStroke, regionLen);
    }



    public static Seq<Vec2> calculatePoints(float x1, float y1, float x2, float y2, int steps){
        Seq<Vec2> points = new Seq<>();
        for(int step = 0; step < steps; step++){
            float progress = (float) step / steps;
            points.add(new Vec2(Mathf.lerp(x1, x2, progress), Mathf.lerp(y1, y2, progress)));
        }
        return points;
    }
}
