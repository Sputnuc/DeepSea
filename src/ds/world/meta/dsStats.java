package ds.world.meta;

import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

public class dsStats {
    public static final Stat
    turretFuel = new Stat("turret-fuel", StatCat.function),
    acTurrReloadStart = new Stat("reload-at-start", StatCat.function),
    acTurrReloadEnd = new Stat("reload-at-start", StatCat.function),
    recipes = new Stat("recipes", StatCat.crafting),
    underwater = new Stat("underwater", StatCat.general),
    targetSubmarines = new Stat("target-submarines", StatCat.function);
}
