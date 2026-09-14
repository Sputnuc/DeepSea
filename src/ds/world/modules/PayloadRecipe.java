package ds.world.modules;

import mindustry.type.UnitType;
import mindustry.world.Block;

public class PayloadRecipe extends BaseRecipe{
    public Block blockInput;
    public Block blockOutput;
    public UnitType unitInput;
    public UnitType unitOutput;

    public boolean buildBlock = true, centerBuild;

    public PayloadRecipe(Block blockInput){
        this.blockInput = blockInput;
    }

    public boolean hasInputBlock(){
        return blockInput != null;
    }
}
