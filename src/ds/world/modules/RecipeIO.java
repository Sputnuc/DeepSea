package ds.world.modules;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.consumers.ConsumeLiquids;
import mindustry.world.draw.DrawBlock;
import mindustry.world.meta.*;

public class RecipeIO {

    public ItemStack[] itemInput;
    public ItemStack[] itemOutput;
    public float powerUse = 0;
    public LiquidStack[] liquidInput;
    public LiquidStack[] liquidOutput;
    public float craftTime = 60;
    public boolean needDoUnlock = true;
    public Seq<Consume> consumers = new Seq<>();

    public DrawBlock uniqueDrawer;

    public boolean[] itemFilter, liquidFilter;

    public Seq<UnlockableContent> recipeReq = new Seq<>();

    public void dumpOutputs(Building build) {
        if(itemOutput != null){
            for (ItemStack i : itemOutput) {
                for (int j = 0; j < i.amount; j++) {
                    build.dump(i.item);
                }
            }
        }
    }

    public void dumpTimedOutputs(Building build) {
        if(itemOutput != null){
            for (ItemStack i : itemOutput) {
                for (int j = 0; j < i.amount; j++) {
                    build.dump(i.item);
                }
            }
        }
    }

    public void craft(Building build){
        if(itemOutput != null){
            for (ItemStack stack : itemOutput) {
                for (int i = 0; i < stack.amount; i++) {
                    build.offload(stack.item);
                }
            }
        }
    }

    public void update(Building build) {
        for (Consume consume : consumers) {
            consume.update(build);
        }

        if(liquidOutput != null){
            float inc = build.getProgressIncrease(1f);
            for (LiquidStack stack : liquidOutput) {
                build.handleLiquid(build, stack.liquid, Math.min(stack.amount * inc, build.block.liquidCapacity - build.liquids.get(stack.liquid)));
            }
        }
    }

    public RecipeIO(){
    }

    public RecipeIO(ItemStack[] itemInput, ItemStack[] itemOutput){
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
    }

    public RecipeIO(ItemStack[] itemInput, ItemStack[] itemOutput, float craftTime){
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.craftTime = craftTime;

    }

    public RecipeIO(ItemStack[] itemInput, ItemStack[] itemOutput, float craftTime, float powerUse){
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.craftTime = craftTime;
        this.powerUse = powerUse;
    }

    public RecipeIO(ItemStack[] itemInput, ItemStack[] itemOutput, LiquidStack[] liquidInput){
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.liquidInput = liquidInput;
    }

    public RecipeIO(ItemStack[] itemInput, ItemStack[] itemOutput, LiquidStack[] liquidInput, float craftTime){
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.liquidInput = liquidInput;
        this.craftTime = craftTime;
    }

    public RecipeIO(ItemStack[] itemInput, ItemStack[] itemOutput, LiquidStack[] liquidInput, float craftTime, float powerUse){
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.liquidInput = liquidInput;
        this.craftTime = craftTime;
        this.powerUse = powerUse;
    }

    public RecipeIO(ItemStack[] itemInput, ItemStack[] itemOutput, LiquidStack[] liquidInput, LiquidStack[] liquidOutput){
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.liquidInput = liquidInput;
        this.liquidOutput = liquidOutput;
    }

    public RecipeIO(ItemStack[] itemInput, ItemStack[] itemOutput, LiquidStack[] liquidInput, LiquidStack[] liquidOutput, float craftTime){
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.liquidInput = liquidInput;
        this.liquidOutput = liquidOutput;
        this.craftTime = craftTime;
    }

    public RecipeIO(ItemStack[] itemInput, ItemStack[] itemOutput, LiquidStack[] liquidInput, LiquidStack[] liquidOutput, float craftTime, float powerUse){
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.liquidInput = liquidInput;
        this.liquidOutput = liquidOutput;
        this.craftTime = craftTime;
        this.powerUse = powerUse;
    }

    public void addRequire(ItemStack[] items){
        for(ItemStack i : items){
            recipeReq.addAll(i.item);
        }
    }

    public void addRequire(LiquidStack[] liquids){
        for(LiquidStack l : liquids){
            recipeReq.addAll(l.liquid);
        }
    }

    public void addRequire(ItemStack[] items, LiquidStack[] liquids){
        for(ItemStack i : items){
            recipeReq.addAll(i.item);
        }

        for(LiquidStack l : liquids){
            recipeReq.addAll(l.liquid);
        }
    }

    //Checks all input items and liquids
    public boolean recipeIsValid(){
        if (!Vars.state.isCampaign() || !needDoUnlock || recipeReq == null) return true;
       for (UnlockableContent ct : recipeReq){
           if(!ct.unlocked()) return false;
       }
       return true;
    }

    public void init(){
        if(itemInput != null) addConsumers(itemInput);
        if(liquidInput != null) addConsumers(liquidInput);
        if(needDoUnlock){
            if(itemInput != null) addRequire(itemInput);
            if(liquidInput != null) addRequire(liquidInput);
        }
    }

    public void addConsumers(ItemStack[] i){
        if(i != null){
            consumers.add(new ConsumeItems(i));
        }
    }

    public void addConsumers(LiquidStack[] l){
        if(l != null){
            consumers.add(new ConsumeLiquids(l));
        }
    }

    public void addConsumers(ItemStack[] i, LiquidStack[] l){
        if(i != null){
            consumers.add(new ConsumeItems(i));
        }
        if(l != null){
            consumers.add(new ConsumeLiquids(l));
        }
    }

    public void apply(Block block){
        if(itemOutput != null || itemInput != null) block.hasItems = true;
        if(liquidInput != null || liquidOutput != null) block.hasLiquids = true;
    }

    public void display(Table table){
        Stats recipeStats = new Stats();
        recipeStats.timePeriod = craftTime;
        for (Consume consume : consumers) {
            consume.display(recipeStats);
        }
        if(itemOutput != null || liquidOutput != null) displayOut(recipeStats);
        table.table(Styles.grayPanel, t -> {
            if(recipeIsValid()){

                t.table(in ->{
                    in.left();
                    OrderedMap<Stat, Seq<StatValue>> map = recipeStats.toMap().get(StatCat.crafting);
                    Seq<StatValue> arr = map.get(Stat.input);
                    if(arr != null) {
                        for (StatValue value : arr) {
                            value.display(in);
                        }
                    }
                    if(this.powerUse != 0) in.table(pwrUse -> {
                        pwrUse.image(Icon.power).color(Pal.accent).size(40);
                        pwrUse.add(this.powerUse * 60 + "/s");
                    });
                }).left().pad(10f);


                t.table(arrow ->{
                    arrow.image(Icon.right).color(Pal.darkishGray).size(40f);
                    arrow.left();
                });

                t.table(time -> {
                    time.image(Icon.crafting).color(Pal.accent).size(40);
                    time.left();
                });

                t.add(Core.bundle.format("ui.craftTime", Strings.autoFixed(this.craftTime / 60, 3))).color(Pal.accent).pad(10f).left();

                t.table(out ->{
                    out.right();
                    OrderedMap<Stat, Seq<StatValue>> map = recipeStats.toMap().get(StatCat.crafting);
                    Seq<StatValue> arr = map.get(Stat.output);
                    if(arr != null) {
                        for (StatValue value : arr) {
                            value.display(out);
                        }
                    }
                }).right().grow().pad(10f);
            }else {
                t.image(Icon.lock).color(Pal.darkerGray).size(40f).grow().pad(10f);
            }
        }).growX();
    }

    public boolean hasLiquids(){
        return liquidInput != null || liquidOutput != null;
    }

    public boolean hasItems(){
        return itemInput != null || itemOutput != null;
    }

    public void displayOut(Stats stats) {
        if(itemInput != null)stats.add(Stat.output, stats.timePeriod < 0 ? StatValues.items(itemOutput) : StatValues.items(stats.timePeriod, itemOutput));
        if(liquidOutput != null)stats.add(Stat.output, StatValues.liquids(1, liquidOutput));
    }

    public boolean shouldConsume(Building build){
        if(itemOutput != null) {
            for (ItemStack i : itemOutput) {
                if (i.amount > build.block.itemCapacity) return false;
            }
        }
        if(liquidOutput != null){
            for (LiquidStack l : liquidOutput) {
                if (l.amount > build.block.liquidCapacity) return false;
            }
        }
        return true;
    }

    public boolean consumesItem(Item item) {
        return itemFilter[item.id];
    }

    public boolean consumesLiquid(Liquid liquid) {
        return liquidFilter[liquid.id];
    }

}
