package ds.world.consumers;

import ds.type.DSLiquid;
import ds.world.meta.DSStats;
import mindustry.type.Liquid;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.Stats;

public class ConsumeReactiveLiquid extends ConsumeLiquidFilter {
    public float minReactivity;

    public ConsumeReactiveLiquid(float minReactivity, float amount){
        super(l -> l instanceof DSLiquid a && a.reactivity >= minReactivity, amount);
        this.minReactivity = minReactivity;
    }

    @Override
    public void display(Stats stats){
        stats.add(
                booster ? Stat.booster : Stat.input,
                DSStats.liquidEffMultiplier(
                        l -> l instanceof DSLiquid a ? a.reactivity : 0f,
                        amount,
                        filter
                )
        );
    }

    @Override
    public float liquidEfficiencyMultiplier(Liquid liquid){
        return liquid instanceof DSLiquid a ? a.reactivity : 0f;
    }
}
