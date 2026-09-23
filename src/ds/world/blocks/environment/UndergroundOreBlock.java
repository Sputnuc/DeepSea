package ds.world.blocks.environment;

import arc.graphics.g2d.Draw;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.OverlayFloor;

public class UndergroundOreBlock extends OverlayFloor {

    public Item itemDrop;

    public float depth = 10;

    /** Used by {@link ds.world.blocks.effect.SonarBlock} **/
    public boolean shouldDrawBase = false;

    public UndergroundOreBlock(String name) {
        super(name);
        this.itemDrop = null;
        this.depth = 1;
    }

    public UndergroundOreBlock(String name, Item itemDrop) {
        super(name);
        this.itemDrop = itemDrop;
        this.depth = 1;
    }

    public UndergroundOreBlock(String name, Item itemDrop, float depth){
        super(name);
        this.itemDrop = itemDrop;
        this.depth = depth;
    }

    @Override
    public void drawBase(Tile tile) {
        if (shouldDrawBase) {
            float l = Draw.z();
            Draw.z(Layer.light);

            super.drawBase(tile);

            Draw.z(l);
        }
    }
}
