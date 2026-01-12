package ds.utilities;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.Vars;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;

import static mindustry.Vars.tilesize;

public class dsMath {
    //for FX
    public static Vec2 RayCastSolid(float stx, float sty, float dir, int len, int stepS){
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
}
