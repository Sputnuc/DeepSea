package ds.world.blocks.environment;

import arc.math.Mathf;
import ds.content.DSFx;
import ds.world.meta.DSEnv;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.entities.Effect;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

public class EffectFloor extends Floor {
    public EffectFloor(String name) {
        super(name);
        tileEffect = DSFx.geotermalBubbles;
    }
    public Effect tileEffect;
    public float effectChance = 0.2f;
    public boolean needEnv = false;
    public int envRequire = DSEnv.underwaterWarm;

    @Override
    public boolean updateRender(Tile tile) {
        return  Vars.state.rules.env == envRequire || !needEnv;
    }

    @Override
    public void renderUpdate(UpdateRenderState state) {
        if (Mathf.chanceDelta(effectChance) && state.tile.block() == Blocks.air) {
            tileEffect.at(state.tile.worldx(), state.tile.worldy());
        }
    }
}
