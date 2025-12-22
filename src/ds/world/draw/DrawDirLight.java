package ds.world.draw;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.Vars;
import mindustry.graphics.Drawf;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;

public class DrawDirLight {

    //RC syst
    public static Vec2 RayCast(float stx, float sty, float dir, int len, int stepS){
        Vec2 vec = new Vec2(Mathf.cosDeg(dir) * len + stx, Mathf.sinDeg(dir) * len + sty);
        Vec2 dirV = new Vec2(Mathf.cosDeg(dir), Mathf.sinDeg(dir)).nor();
        float x = stx;
        float y = sty;
        float deltaDistX = dirV.x;
        float deltaDistY = dirV.y;
        for(int i = 0; i < (int)(len / stepS); i++){
            x += deltaDistX * stepS;
            y += deltaDistY * stepS;
            int tileX = (int)(x / tilesize);
            int tileY = (int)(y / tilesize);
            Tile tile = Vars.world.tile(tileX, tileY);
            if(tile != null && tile.solid()){
                vec = new Vec2(x, y);
                return vec;
            }
        }
        return vec;
    }

    public static void DrawLightBeam(float tx, float ty, float beamRotation, float length, float cone, float rayW){
        float sideCone = cone / 2;
        float minAngleBetweenRays = 2.0f * (float)Math.toDegrees(Math.atan(rayW / (2 * length)));
        int sideRays = Math.max(1, (int)Math.ceil(sideCone / minAngleBetweenRays));
        float angularStep = sideCone / sideRays;
        for(int i = 0; i <= sideRays; i++){
            float angleOffset = i * angularStep;
            if(i == 0){
                DrawGradientRayAdv(tx, ty, rayW, length, beamRotation, 1.0f);
            } else {
                float fadeFactor = 1.0f - (i / (float)sideRays) * 0.5f;
                float rayDir1 = beamRotation - angleOffset;
                float rayDir2 = beamRotation + angleOffset;
                DrawGradientRayAdv(tx, ty, rayW, length, rayDir1, fadeFactor);
                DrawGradientRayAdv(tx, ty, rayW, length, rayDir2, fadeFactor);
            }
        }
    }

    public static void DrawGradientRayAdv(float x1, float y1, float stroke, float length, float rD, float intensity){
        Vec2 endPoint = RayCast(x1, y1, rD, (int)length, 2);
        int segments = 10;
        Vec2 dir = new Vec2(Mathf.cosDeg(rD), Mathf.sinDeg(rD)).nor();
        float segmentLength = Mathf.dst(x1, y1, endPoint.x, endPoint.y) / segments;
        for(int j = 0; j < segments; j++){
            float segmentStartX = x1 + dir.x * segmentLength * j;
            float segmentStartY = y1 + dir.y * segmentLength * j;
            float segmentEndX = x1 + dir.x * segmentLength * (j + 1);
            float segmentEndY = y1 + dir.y * segmentLength * (j + 1);
            float segmentAlpha = intensity * (1.0f - (j / (float)segments) * 0.7f) * 0.75f;
            Drawf.light(segmentStartX, segmentStartY, segmentEndX, segmentEndY, stroke, Color.white, segmentAlpha);
        }
    }
}
