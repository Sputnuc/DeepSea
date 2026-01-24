package ds.world.consumers;

import arc.util.Nullable;
import ds.content.dsFx;
import mindustry.entities.Effect;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.draw.DrawMulti;

    //A Class for MultiRecipeCrafter
public class Recipe {
    //Consume
    @Nullable
    public ItemStack[] inputItems;
    @Nullable
    public LiquidStack[] inputLiquids;

    @Nullable
    public ItemStack inputItem;
    @Nullable
    public LiquidStack inputLiquid;

    public float powerUse = 0f;
    public float craftTime = 60f;
    public float heatUse = 0f;

    //Output
    @Nullable
    public ItemStack[] outputItems;
    @Nullable
    public LiquidStack[] outputLiquids;

    @Nullable
    public ItemStack outputItem;
    @Nullable
    public LiquidStack outputLiquid;

    public Effect craftEffect = dsFx.drillImpact;
    @Nullable
    public DrawMulti customDrawer = null;

    public Recipe(){}
}
