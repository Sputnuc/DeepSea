package ds.world.consumers;

import arc.func.Func;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;

import mindustry.world.*;
import mindustry.world.meta.*;

public class ConsumeRecipe extends Consume {

    private final Func<Building, ItemStack[]> items;

    @SuppressWarnings("unchecked")
    public <T extends Building> ConsumeRecipe(Func<T, ItemStack[]> items){
        this.items = (Func<Building, ItemStack[]>)items;
    }


    public Recipe currentRecipe;

    public Seq<Recipe> recipes = new Seq<>();

    public ConsumeRecipe(Recipe... recipes){
        if(recipes.length > 0) {
            this.recipes.addAll(recipes);
            this.currentRecipe = recipes[0];
        }
        items = null;
    }

    public void setRecipe(int index) {
        if(index >= 0 && index < recipes.size) {
            currentRecipe = recipes.get(index);
        }
    }

    @Override
    public void apply(Block block) {
        // Настраиваем блок
        block.hasItems = true;
        block.hasLiquids = true;
        block.hasPower = true;
    }

    public boolean valid(Building build) {
        if (currentRecipe == null) return false;

        // Проверяем предметы
        if (currentRecipe.inputItem != null) {
            if (build.items.get(currentRecipe.inputItem.item) < currentRecipe.inputItem.amount) {
                return false;
            }
        }

        if (currentRecipe.inputItems != null) {
            for (ItemStack stack : currentRecipe.inputItems) {
                if (build.items.get(stack.item) < stack.amount) {
                    return false;
                }
            }
        }

        // Проверяем жидкости
        if (currentRecipe.inputLiquid != null) {
            if (build.liquids.get(currentRecipe.inputLiquid.liquid) < currentRecipe.inputLiquid.amount) {
                return false;
            }
        }

        if (currentRecipe.inputLiquids != null) {
            for (LiquidStack stack : currentRecipe.inputLiquids) {
                if (build.liquids.get(stack.liquid) < stack.amount) {
                    return false;
                }
            }
        }

        // Проверяем энергию
        if (currentRecipe.powerUse > 0) {
            if (build.power == null || build.power.status <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void trigger(Building build){
        for(ItemStack stack : items.get(build)){
            build.items.remove(stack.item, Math.round(stack.amount * multiplier.get(build)));
        }
        for(ItemStack stack : items.get(build)){
            build.items.remove(stack.item, Math.round(stack.amount * multiplier.get(build)));
        }
    }
}
