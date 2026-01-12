package ds.world.blocks.environment;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import mindustry.graphics.Drawf;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Seaweed;

public class GlowingSeaweed extends Seaweed {
    public GlowingSeaweed(String name) {
        super(name);
    }
    public float lightRadius = 4;
    public float lightOpasity = 0.15f;
    public Color lightColor = Color.valueOf("aafad3");

    @Override
    public  void drawBase(Tile tile){
        super.drawBase(tile);
        Drawf.light(tile.worldx(), tile.worldy(), lightRadius, lightColor, lightOpasity);
    }
}
