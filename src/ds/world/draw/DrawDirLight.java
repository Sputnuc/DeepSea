package ds.world.draw;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.Vars;
import mindustry.graphics.Drawf;
import mindustry.world.Tile;

import static ds.utilities.dsMath.*;
import static mindustry.Vars.tilesize;

public class DrawDirLight {

    public static void DrawLightBeamNonTileable(float tx, float ty, float beamRotation, float length, float cone, float rayW){
        float sideCone = cone / 2;
        float minAngleBetweenRays = 2.0f * (float)Math.toDegrees(Math.atan(rayW / (2 * length)));
        int sideRays = Math.max(1, (int)Math.ceil(sideCone / minAngleBetweenRays));
        float angularStep = sideCone / sideRays;
        for(int i = 0; i <= sideRays; i++){
            float angleOffset = i * angularStep;
            if(i == 0){
                DrawBasicRay(tx, ty, rayW, length, beamRotation, 1.0f);
            } else {
                float fadeFactor = 1.0f - (i / (float)sideRays) * 0.5f;
                float rayDir1 = beamRotation - angleOffset;
                float rayDir2 = beamRotation + angleOffset;
                DrawBasicRay(tx, ty, rayW, length, rayDir1, fadeFactor);
                DrawBasicRay(tx, ty, rayW, length, rayDir2, fadeFactor);
            }
        }
    }

    public static void DrawBasicRay(float x1, float y1, float stroke, float length, float rD, float intensity){
        Vec2 endPoint = new Vec2(x1+ Mathf.cosDeg(rD)* length, y1+ Mathf.sinDeg(rD)* length);
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
        Vec2 endPoint = RayCastSolid(x1, y1, rD, (int)length, 2);
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
