package ds.world.draw;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.graphics.Drawf;

public class DrawCurveLightning {

    public static void drawCurveLightning(Vec2 startPos, Vec2 endPos, int segments, float segLen, float rotation, Color color) {
        if (segments <= 0) return;
        float startX = startPos.x;
        float startY = startPos.y;
        float endX = endPos.x;
        float endY = endPos.y;

        Seq<Vec2> points = new Seq<>();

        for(int i = 0; i < segments; i++){
            float progress = (float) i / segments;
            points.add(new Vec2(Mathf.lerp(startX, endX, progress), Mathf.lerp(startY, endY, progress)));
        }

        Seq<Vec2>newPoints = generateRandomDeviations(segLen, points, startPos,endPos);

        Fx.lightning.at(startPos.x, startPos.y, rotation, color, newPoints);
    }

    private static Seq<Vec2> generateRandomDeviations(float segmentLength, Seq<Vec2>Points, Vec2 startPos, Vec2 endPos) {
        for (Vec2 point : Points) {
            point.add(Mathf.range(segmentLength), Mathf.range(segmentLength));
        }
        Points.set(Points.size - 1, endPos);
        Points.set(0, startPos);
        return Points;
    }
}
