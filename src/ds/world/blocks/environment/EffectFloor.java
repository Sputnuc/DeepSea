package ds.world.blocks.environment;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import ds.content.dsFx;
import mindustry.content.Blocks;
import mindustry.entities.Effect;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

public class EffectFloor extends Floor {
    public EffectFloor(String name) {
        super(name);
        drawEdgeOut = false;
        drawEdgeIn = false;
        tileEffect = dsFx.geotermalBubbles;
    }
    public Effect tileEffect;
    public float effectChance = 0.2f;

    @Override
    public boolean updateRender(Tile tile) {
        return true;
    }

    @Override
    public void renderUpdate(UpdateRenderState state) {
        if(Mathf.chanceDelta(effectChance) && state.tile.block() == Blocks.air) {
            tileEffect.at(state.tile.worldx(), state.tile.worldy());
        }
    }
}
