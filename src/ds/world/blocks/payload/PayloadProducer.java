package ds.world.blocks.payload;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import ds.world.consumers.ConsumeLiquidDynamic;
import ds.world.graphics.DSDrawf;
import ds.world.meta.DSStatValues;
import ds.world.meta.DSStats;
import ds.world.modules.BaseRecipe;
import ds.world.modules.PayloadRecipe;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class PayloadProducer extends PayloadBlock{
    private float scrollPos;

    public Seq<PayloadRecipe> recipes = new Seq<>();
    public boolean hasTop = true;

    public PayloadProducer(String name){
        super(name);

        update = true;
        rotate = true;
        configurable = logicConfigurable = true;

        config(Block.class, (PayloadCrafterBuild tile, Block block) -> {
            if(tile.recipe != block) tile.progress = 0f;
            if(canProduce(block)){
                tile.recipe = block;
            }
        });

        configClear((PayloadCrafterBuild tile) -> {
            tile.recipe = null;
            tile.progress = 0f;
        });
    }

    public void recipes(Block... blocks){
        for(Block b : blocks){
            recipes.add(new PayloadRecipe(b));
        }
    }

    public void recipes(PayloadRecipe... newRecipes){
        for(PayloadRecipe r : newRecipes){
            recipes.add(r);
        }
    }

    public void setRecipeProductionStats(){
        for(PayloadRecipe r : recipes){
            if(r.blockOutput != null){
                r.blockOutput.stats.add(DSStats.recipes, s -> {
                    s.row();
                    s.table(Styles.grayPanel, t -> {
                        t.left().defaults().top().left();
                        if(state.rules.bannedBlocks.contains(this)){
                            t.image(Icon.cancel).color(Pal.remove).size(40);
                            return;
                        }

                        t.image(fullIcon).size(96f);
                        t.table(n -> {
                            n.defaults().left();
                            n.add(localizedName);
                            n.row();
                            DSStatValues.infoButton(n, this, 4f * 8f).padTop(4f);
                        }).padLeft(8f);
                    }).left().top().growX().margin(10f).padTop(5).padBottom(5);
                });

                if(r.blockInput != null){
                    r.blockInput.stats.add(DSStats.recipes, s -> {
                        s.row();
                        s.table(Styles.grayPanel, t -> {
                            t.left().defaults().top().left();
                            if(state.rules.bannedBlocks.contains(r.blockOutput)){
                                t.image(Icon.cancel).color(Pal.remove).size(40);
                                return;
                            }
                            if(!r.blockOutput.unlockedNow()){
                                t.image(Icon.lock).color(Pal.darkerGray).size(40);
                                t.add("@pm-missing-research").center().left();
                                return;
                            }

                            t.image(r.blockOutput.fullIcon).size(96f);
                            t.table(n -> {
                                n.defaults().left();
                                n.add(r.blockOutput.localizedName);
                                n.row();
                                DSStatValues.infoButton(n, this, 4f * 8f).padTop(4f);
                            }).padLeft(8f);
                        }).left().top().growX().margin(10f).padTop(5).padBottom(5);
                    });
                }
            }
        }
    }

    @Override
    public void init(){
        if(recipes.contains(r -> r.powerUse > 0)){
            consumePowerDynamic(b -> ((PayloadCrafterBuild)b).powerUse());
        }
        if(recipes.contains(r -> r.itemInput != null)){
            consume(new ConsumeItemDynamic((PayloadCrafterBuild e) -> e.hasRecipe() && e.recipe().itemInput != null ? e.recipe().itemInput : ItemStack.empty));
        }
        if(recipes.contains(r -> r.liquidInput != null)){
            consume(new ConsumeLiquidDynamic((PayloadCrafterBuild e) -> e.hasRecipe() ? e.recipe().liquidInput : null));
        }
        if(recipes.contains(r -> r.blockInput != null)) acceptsPayload = true;
        if(recipes.contains(r -> r.blockOutput != null)) outputsPayload = true;

        super.init();
    }

    @Override
    public void load(){
        super.load();

        inRegion = Core.atlas.find(name + "-in", Core.atlas.find("factory-in-" + size + regionSuffix, "prog-mats-factory-in-" + size + regionSuffix));
        outRegion = Core.atlas.find(name + "-out", Core.atlas.find("factory-out-" + size + regionSuffix, "prog-mats-factory-out-" + size + regionSuffix));
        if(!hasTop) topRegion = Core.atlas.find("clear");
    }

    @Override
    public TextureRegion[] icons(){
        if(recipes.contains(PayloadRecipe::hasInputBlock)){
            return new TextureRegion[]{region, inRegion, outRegion, topRegion};
        }
        return new TextureRegion[]{region, outRegion, topRegion};
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.powerUse);
        stats.remove(Stat.itemCapacity);
        stats.remove(Stat.liquidCapacity);

        stats.add(DSStats.recipes, DSStatValues.payloadProducts(recipes));
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("progress", (PayloadCrafterBuild entity) -> new Bar(
                "bar.progress",
                Pal.ammo,
                () -> entity.recipe() == null ? 0f : (entity.progress / entity.recipe().craftTime)
        ));
    }

    @Override
    public void drawPlanRegion(BuildPlan req, Eachable<BuildPlan> list){
        Draw.rect(region, req.drawx(), req.drawy());
        if(recipes.contains(r -> r.blockInput != null)) Draw.rect(inRegion, req.drawx(), req.drawy(), req.rotation * 90);
        Draw.rect(outRegion, req.drawx(), req.drawy(), req.rotation * 90);
        Draw.rect(topRegion, req.drawx(), req.drawy());
    }

    public boolean canProduce(Block b){
        if(recipes.contains(r -> r.blockOutput == b)){
            return recipes.find(r -> r.blockOutput == b).recipeIsValid();
        }
        return false;
    }

    public class PayloadCrafterBuild extends PayloadBlockBuild<BuildPayload>{
        public float progress, time, heat;
        public @Nullable Block recipe;
        public boolean produce;

        public @Nullable PayloadRecipe recipe(){
            return recipes.find(r -> r.blockOutput == recipe);
        }

        public boolean hasRecipe(){
            return recipe() != null;
        }

        @Override
        public Object senseObject(LAccess sensor){
            if(sensor == LAccess.config) return recipe;
            if(sensor == LAccess.progress) return progress;
            return super.senseObject(sensor);
        }

        @Override
        public void updateTile(){
            super.updateTile();
            PayloadRecipe recipe = recipe();
            produce = recipe != null && canConsume() &&
                    (recipe.blockInput != null ? (payload != null && hasArrived() && payload.block() == recipe.blockOutput) : payload == null);

            if(payload != null){
                if(recipe != null){
                    if(payload.block() != recipe.blockInput){
                        moveOutPayload();
                    }
                }else if(!recipes.contains(r -> r.blockInput == payload.block())){
                    moveOutPayload();
                }
            }

            if(recipe != null && payload != null && payload.block() == recipe.blockInput){
                moveInPayload(false);
            }

            if(produce && recipe != null){
                progress += edelta();

                if(progress >= recipe.craftTime){
                    craft(recipe);
                }
            }else if(recipe == null || !canConsume()){
                progress = 0f;
            }

            heat = Mathf.lerpDelta(heat, Mathf.num(produce), 0.15f);
            time += heat * delta();
        }

        public void craft(PayloadRecipe recipe){
            consume();

            payload = new BuildPayload(recipe.blockOutput, team);
            payVector.setZero();
            progress %= 1f;
        }

        public float powerUse(){
            return hasRecipe() ? recipe().powerUse : 0f;
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);

            //draw input
            if(curInput()){
                for(int i = 0; i < 4; i++){
                    if(blends(i) && i != rotation){
                        Draw.rect(inRegion, x, y, (i * 90f) - 180f);
                    }
                }
            }

            Draw.rect(outRegion, x, y, rotdeg());

            if(recipe != null){
                PayloadRecipe r = recipe();
                Draw.draw(Layer.blockBuilding, () -> {
                    if(r.buildBlock){
                        for(TextureRegion region : recipe.getGeneratedIcons()){
                            if(r.centerBuild){
                                DSDrawf.blockBuildCenter(x, y, region, recipe.rotate ? rotdeg() : 0, progress / r.craftTime);
                            }else{
                                DSDrawf.blockBuild(x, y, region, recipe.rotate ? rotdeg() : 0, progress / r.craftTime);
                            }
                        }
                    }else{
                        Drawf.construct(this, recipe.fullIcon, 0, progress / r.craftTime, heat, time);
                    }
                });

                if(r.buildBlock){
                    Draw.z(Layer.blockBuilding + 0.01f);
                    Draw.color(Pal.accent, heat);

                    Lines.lineAngleCenter(x + Mathf.sin(time, 10f, Vars.tilesize / 2f * recipe.size + 1f), y, 90, recipe.size * Vars.tilesize + 1f);

                    Draw.reset();
                }
            }
            Draw.z(Layer.blockBuilding + 1f);
            Draw.rect(topRegion, x, y);

            drawPayload();
        }

        public boolean curInput(){
            return hasRecipe() && recipe().blockInput != null;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return items != null && items.get(item) < getMaximumAccepted(item);
        }

        @Override
        public int getMaximumAccepted(Item item){
            if(recipe() == null) return 0;
            for(ItemStack stack : recipe().itemInput){
                if(stack.item == item) return stack.amount * 2;
            }
            return 0;
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            if (!hasLiquids) return false;

            PayloadRecipe currentRecipe = recipe();
            boolean recipeConsumes = false;

            if (configurable) {
                if (currentRecipe != null) {
                    recipeConsumes = currentRecipe.consumesLiquid(liquid);
                }
            } else {
                for (BaseRecipe recipe : recipes) {
                    if (recipe.consumesLiquid(liquid)) {
                        recipeConsumes = true;
                        break;
                    }
                }
            }

            return (consumesLiquid(liquid) || recipeConsumes);
        }

        @Override
        public void buildConfiguration(Table table){
            ButtonGroup<ImageButton> group = new ButtonGroup<>();
            group.setMinCheckCount(0);
            Table cont = new Table();
            cont.defaults().size(40);

            int i = 0;

            for(Block b : content.blocks()){
                if(recipes.contains(r -> r.blockOutput == b)){
                    Cell<ImageButton> cell = cont.button(Tex.clear, Styles.clearTogglei, 24, () -> {}).group(group);
                    ImageButton button = cell.get();
                    PayloadRecipe r = recipes.find(rec -> rec.blockOutput == b);
                    button.update(() -> button.setChecked(recipe == b));

                    if(r.recipeIsValid()){
                        button.getStyle().imageUp = new TextureRegionDrawable(b.uiIcon);
                        cell.tooltip(b.localizedName);
                    }else{
                        button.getStyle().imageUp = Icon.lock;
                        cell.tooltip("@pm-missing-research");
                    }
                    button.changed(() -> configure(button.isChecked() ? b : null));

                    if(i++ % 4 == 3){
                        cont.row();
                    }
                }
            }

            //add extra blank spaces so it looks nice
            if(i % 4 != 0){
                int remaining = 4 - (i % 4);
                for(int j = 0; j < remaining; j++){
                    cont.image(Styles.black6);
                }
            }

            ScrollPane pane = new ScrollPane(cont, Styles.smallPane);
            pane.setScrollingDisabled(true, false);
            pane.setScrollYForce(scrollPos);
            pane.update(() -> scrollPos = pane.getScrollY());

            pane.setOverscroll(false, false);
            table.add(pane).maxHeight(Scl.scl(40 * 5));
        }

        @Override
        public Object config(){
            return recipe;
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload){
            PayloadRecipe r= recipe();
            return this.payload == null && r != null && payload instanceof BuildPayload p && p.block() == r.blockInput;
        }

        @Override
        public void display(Table table){
            super.display(table);

            Image prev = new Image();
            TextureRegionDrawable prevReg = new TextureRegionDrawable();

            table.row();
            table.table(p -> {
                p.update(() -> {
                    p.clear();
                    if(hasRecipe() && recipe().hasInputBlock()){
                        p.label(() -> Core.bundle.get("pm-requires")).color(Color.lightGray).padRight(2f);
                        prev.setDrawable(prevReg.set(recipe().blockInput.uiIcon));
                        ReqImage r = new ReqImage(prev, () -> payload != null && hasArrived() && payload.block() == recipe().blockInput);
                        r.setSize(32);
                        p.add(r).size(32).padBottom(-4).padRight(2);
                        p.label(() -> recipe().blockInput.localizedName).color(Color.lightGray);
                    }
                });
            }).left();

            TextureRegionDrawable reg = new TextureRegionDrawable();

            table.row();
            table.table(t -> {
                t.left();
                t.image().update(i -> {
                    i.setDrawable(recipe == null ? Icon.cancel : reg.set(recipe.uiIcon));
                    i.setScaling(Scaling.fit);
                    i.setColor(recipe == null ? Color.lightGray : Color.white);
                }).size(32).padBottom(-4).padRight(2);
            }).left().get().label(() -> recipe == null ? "@none" : recipe.localizedName).color(Color.lightGray);
        }

        @Override
        public boolean shouldAmbientSound(){
            return super.shouldAmbientSound() && produce;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.s(recipe == null ? -1 : recipe.id);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            if(revision >= 1){
                recipe = Vars.content.block(read.s());
            }
        }

        @Override
        public byte version(){
            return 1;
        }
    }
}