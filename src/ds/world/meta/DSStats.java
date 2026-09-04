package ds.world.meta;

import arc.Core;
import arc.func.Boolf;
import arc.func.Floatf;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.type.Liquid;
import mindustry.ui.Styles;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatValue;

import static mindustry.world.meta.StatValues.displayLiquid;
import static mindustry.world.meta.StatValues.fixValue;

public class DSStats {
    public static final Stat
    turretFuel = new Stat("turret-fuel", StatCat.function),
    acTurrReloadStart = new Stat("reload-at-start", StatCat.function),
    acTurrReloadEnd = new Stat("reload-at-end", StatCat.function),
    recipes = new Stat("recipes", StatCat.crafting),

    //Liquid Stats
    reactivity = new Stat("reactivity"),
    exothermic = new Stat("exothermic");

    public static StatValue liquidEffMultiplier(Floatf<Liquid> efficiency, float amount, Boolf<Liquid> filter){
        return (Table table) -> {
            if(table.getCells().size > 0){
                ((Cell<?>)table.getCells().peek()).growX();
            }

            table.row();
            table.table(c -> {
                for(Liquid liquid : Vars.content.liquids().select(l -> filter.get(l) && l.unlockedNow() && !l.isHidden())){
                    c.table(Styles.grayPanel, b -> {
                        b.add(displayLiquid(liquid, amount * 60, true)).pad(10f).left().grow();
                        b.add(Core.bundle.format("stat.efficiency", fixValue(efficiency.get(liquid) * 100f)))
                                .right().pad(10f).padRight(15f);
                    }).growX().pad(5f).row();
                }
            }).growX().colspan(table.getColumns()).row();
        };
    }
}
