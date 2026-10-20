package ds.world.blocks.power;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.graphics.Drawf;
import mindustry.type.LiquidStack;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.SteamVent;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

public class VentGenerator extends PowerGenerator {
    public VentGenerator(String name) {
        super(name);
    }

    public Effect generateEffect = Fx.none;
    public float effectChance = 0.05f;
    public float minEfficiency = 0f;
    public float displayEfficiencyScale = 1f;
    public boolean displayEfficiency = true;
    public @Nullable LiquidStack outputLiquid;
    public Attribute attribute = Attribute.heat;

    @Override
    public float getDisplayedPowerProduction(){
        return powerProduction / displayEfficiencyScale;
    }

    @Override
    public void init(){
        if(outputLiquid != null){
            outputsLiquid = true;
            hasLiquids = true;
        }
        emitLight = true;
        super.init();
        lightClipSize = Math.max(lightClipSize, 45f * size * 2f * 2f);
    }

    @Override
    public void afterPatch(){
        super.afterPatch();
        if(outputLiquid != null){
            outputsLiquid = true;
            hasLiquids = true;
        }
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.tiles, attribute, floating, size * size * displayEfficiencyScale, !displayEfficiency);
        stats.remove(generationType);
        stats.add(generationType, powerProduction * 60.0f / displayEfficiencyScale, StatUnit.powerSecond);

        if(outputLiquid != null){
            stats.add(Stat.output, StatValues.liquid(outputLiquid.liquid, outputLiquid.amount * size * size * 60f, true));
        }
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        if(displayEfficiency){
            drawPlaceText(Core.bundle.formatFloat("bar.efficiency", sumAttribute(attribute, x, y) * 100 * displayEfficiencyScale, 1), x, y, valid);
        }
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        //make sure there's heat at this location
        return tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attribute)) > minEfficiency && ((tile.floor() instanceof SteamVent v && v.isCenterVent(tile)));
    }

    public class VentGeneratorBuild extends GeneratorBuild{
        public float sum;

        public float totalProgress;

        @Override
        public void updateTile(){
            productionEfficiency = sum + attribute.env();

            if(productionEfficiency > 0.1f && Mathf.chanceDelta(effectChance)){
                generateEffect.at(x + Mathf.range(3f), y + Mathf.range(3f));
            }
            totalProgress += productionEfficiency * displayEfficiencyScale * edelta();
            if(outputLiquid != null && hasLiquids){
                float added = Math.min(productionEfficiency * delta() * outputLiquid.amount, liquidCapacity - liquids.get(outputLiquid.liquid));
                liquids.add(outputLiquid.liquid, added);
                dumpLiquid(outputLiquid.liquid);
            }
        }

        @Override
        public void afterPickedUp(){
            super.afterPickedUp();
            sum = 0f;
        }

        @Override
        public float totalProgress(){
            return enabled && sum > 0 ? totalProgress : 0f;
        }

        @Override
        public float warmup(){
            return enabled && sum > 0 ? productionEfficiency * displayEfficiencyScale : 0;
        }

        @Override
        public void drawLight(){
            //nah, use Drawer
        }

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();

            sum = sumAttribute(attribute, tile.x, tile.y);
        }
    }
}
