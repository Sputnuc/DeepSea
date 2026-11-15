package ds.utilities;

import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class Utilities {
    public static int[] signsZero = {-1, 0, 1};
    //for FX
    public static Vec2 RayCastSolid(float stx, float sty, float dir, int len, int stepS, Seq<Block> exceptions){
        Vec2 vec = new Vec2(Mathf.cosDeg(dir) * len + stx, Mathf.sinDeg(dir) * len + sty);
        Vec2 dirV = new Vec2(Mathf.cosDeg(dir), Mathf.sinDeg(dir)).nor();
        float x = stx;
        float y = sty;
        float deltaDistX = dirV.x;
        float deltaDistY = dirV.y;
        for(int i = 0; i < (len / stepS); i++){
            x += deltaDistX * stepS;
            y += deltaDistY * stepS;
            int tileX = (int)(x / tilesize);
            int tileY = (int)(y / tilesize);
            Tile tile = world.tile(tileX, tileY);
            if(tile != null && (tile.solid() || exceptions.contains(tile.block()))){
                vec = new Vec2(x, y);
                return vec;
            }
        }
        return vec;
    }

    public static Vec2 rayCast(float x1, float y1, float x2, float y2){
        Vec2 out = new Vec2();
        rayCastVec(x1, y1, x2, y2, out);
        return out;
    }

    public static boolean rayCastVec(float x1, float y1, float x2, float y2, Vec2 out){
        float ax = x1 / tilesize + 0.5f, ay = y1 / tilesize + 0.5f;
        float bx = x2 / tilesize + 0.5f, by = y2 / tilesize + 0.5f;
        float dx = bx - ax, dy = by - ay;

        int cx = Mathf.floor(ax), cy = Mathf.floor(ay);
        int sx = dx > 0 ? 1 : -1, sy = dy > 0 ? 1 : -1;
        float tdx = dx != 0 ? Math.abs(1f / dx) : Float.POSITIVE_INFINITY;
        float tdy = dy != 0 ? Math.abs(1f / dy) : Float.POSITIVE_INFINITY;
        float tmx = dx > 0 ? (cx + 1 - ax) / dx : dx < 0 ? (ax - cx) / -dx : Float.POSITIVE_INFINITY;
        float tmy = dy > 0 ? (cy + 1 - ay) / dy : dy < 0 ? (ay - cy) / -dy : Float.POSITIVE_INFINITY;

        int ww = world.width(), wh = world.height();

        while(true){
            float t;
            if(tmx < tmy){
                t = tmx;
                cx += sx;
                tmx += tdx;
            }else{
                t = tmy;
                cy += sy;
                tmy += tdy;
            }

            if(t >= 1f) break;

            out.set(x1 + (x2 - x1) * t, y1 + (y2 - y1) * t);
            if(cx < 0 || cy < 0 || cx >= ww || cy >= wh) return false;
            if(blocksLight(world.tile(cx, cy))) return true;
        }

        out.set(x2, y2);
        return false;
    }
    public static boolean blocksLight(Tile tile){
        return tile != null && (tile.build != null || tile.solid());
    }

}
