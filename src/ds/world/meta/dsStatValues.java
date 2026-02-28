package ds.world.meta;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.Scaling;
import arc.util.Strings;
import ds.world.type.entities.bullets.HarpoonBulletType;
import ds.world.type.entities.bullets.PointLightningBulletType;
import mindustry.content.StatusEffects;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Icon;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.StatValues;

import static mindustry.Vars.tilesize;


public class dsStatValues extends StatValues {


    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map) {
        return ammo(map, 0, false);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, boolean showUnit) {
        return ammo(map, 0, showUnit);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, int indent, boolean showUnit) {
        return table -> {

            table.row();

            var orderedKeys = map.keys().toSeq();
            orderedKeys.sort();

            for (T t : orderedKeys) {
                boolean compact = t instanceof UnitType && !showUnit || indent > 0;

                BulletType type = map.get(t);

                if (type.spawnUnit != null && type.spawnUnit.weapons.size > 0) {
                    ammo(ObjectMap.of(t, type.spawnUnit.weapons.first().bullet), indent, false).display(table);
                    continue;
                }

                table.table(Styles.grayPanel, bt -> {
                    bt.left().top().defaults().padRight(3).left();
                    //no point in displaying unit icon twice
                    if (!compact && !(t instanceof Turret)) {
                        bt.table(title -> {
                            title.image(icon(t)).size(3 * 8).padRight(4).right().scaling(Scaling.fit).top();
                            title.add(t.localizedName).padRight(10).left().top();
                        });
                        bt.row();
                    }
                    if(type.damage > 0){
                        sep(bt, Core.bundle.format("bullet.damage", type.damage));
                    }

                    if (type.buildingDamageMultiplier != 1) {
                        int val = (int) (type.buildingDamageMultiplier * 100 - 100);
                        sep(bt, Core.bundle.format("bullet.buildingdamage", ammoStat(val)));
                    }

                    if (type.rangeChange != 0 && !compact) {
                        sep(bt, Core.bundle.format("bullet.range", ammoStat(type.rangeChange / tilesize)));
                    }

                    if (type.splashDamage > 0) {
                        sep(bt, Core.bundle.format("bullet.splashdamage", type.splashDamage, Strings.fixed(type.splashDamageRadius / tilesize, 1)));
                    }

                    if (!compact && !Mathf.equal(type.ammoMultiplier, 1f) && type.displayAmmoMultiplier && (!(t instanceof Turret turret) || turret.displayAmmoMultiplier)) {
                        sep(bt, Core.bundle.format("bullet.multiplier", (int) type.ammoMultiplier));
                    }

                    if (!compact && !Mathf.equal(type.reloadMultiplier, 1f)) {
                        int val = (int) (type.reloadMultiplier * 100 - 100);
                        sep(bt, Core.bundle.format("bullet.reload", ammoStat(val)));
                    }

                    if (type.knockback > 0) {
                        sep(bt, Core.bundle.format("bullet.knockback", Strings.autoFixed(type.knockback, 2)));
                    }

                    if (type.healPercent > 0f) {
                        sep(bt, Core.bundle.format("bullet.healpercent", Strings.autoFixed(type.healPercent, 2)));
                    }

                    if (type.healAmount > 0f) {
                        sep(bt, Core.bundle.format("bullet.healamount", Strings.autoFixed(type.healAmount, 2)));
                    }

                    if (type.pierce || type.pierceCap != -1) {
                        sep(bt, type.pierceCap == -1 ? "@bullet.infinitepierce" : Core.bundle.format("bullet.pierce", type.pierceCap));
                    }

                    if (type.incendAmount > 0) {
                        sep(bt, "@bullet.incendiary");
                    }

                    if (type.homingPower > 0.01f) {
                        sep(bt, "@bullet.homing");
                    }

                    if (type.lightning > 0) {
                        sep(bt, Core.bundle.format("bullet.lightning", type.lightning, type.lightningDamage < 0 ? type.damage : type.lightningDamage));
                    }

                    if (type.pierceArmor) {
                        sep(bt, "@bullet.armorpierce");
                    }

                    if (type.suppressionRange > 0) {
                        sep(bt, Core.bundle.format("bullet.suppression", Strings.autoFixed(type.suppressionDuration / 60f, 2), Strings.fixed(type.suppressionRange / tilesize, 1)));
                    }

                    if (type.status != StatusEffects.none) {
                        sep(bt, (type.status.minfo.mod == null ? type.status.emoji() : "") + "[stat]" + type.status.localizedName + (type.status.reactive ? "" : "[lightgray] ~ [stat]" + ((int) (type.statusDuration / 60f)) + "[lightgray] " + Core.bundle.get("unit.seconds")));
                    }

                    if (type.intervalBullet != null) {
                        bt.row();

                        Table ic = new Table();
                        ammo(ObjectMap.of(t, type.intervalBullet), indent + 1, false).display(ic);
                        Collapser coll = new Collapser(ic, true);
                        coll.setDuration(0.1f);

                        bt.table(it -> {
                            it.left().defaults().left();

                            it.add(Core.bundle.format("bullet.interval", Strings.autoFixed(type.intervalBullets / type.bulletInterval * 60, 2)));
                            it.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                        });
                        bt.row();
                        bt.add(coll);
                    }

                    if(type instanceof PointLightningBulletType){
                        sep(bt, Core.bundle.format("bullet.lightningNum", Strings.fixed(((PointLightningBulletType) type).lightningNum, 0)));
                        if(((PointLightningBulletType) type).createSubBolts) {
                            sep(bt, Core.bundle.format("bullet.subLightNum", Strings.fixed(((PointLightningBulletType) type).subBoltAmount, 0)));
                            if(((PointLightningBulletType) type).subBoltDamage != 0) {
                                sep(bt, Core.bundle.format("bullet.subLightDamage", Strings.fixed(((PointLightningBulletType) type).subBoltDamage, 0)));
                            } else sep(bt, Core.bundle.format("bullet.subLightDamage", Strings.fixed(type.damage, 0)));
                        }
                    }
                    if(type instanceof HarpoonBulletType){
                        sep(bt, Core.bundle.format("bullet.pierceDrag", Strings.fixed(((HarpoonBulletType) type).pierceDrag, 1)));
                    }

                }).padLeft(indent * 5).padTop(5).padBottom(compact ? 0 : 5).growX().margin(compact ? 0 : 10);
                table.row();
            }
        };
    }

    private static void sep(Table table, String text) {
        table.row();
        table.add(text);
    }

    //for AmmoListValue
    private static String ammoStat(float val) {
        return (val > 0 ? "[stat]+" : "[negstat]") + Strings.autoFixed(val, 1);
    }

    private static TextureRegion icon(UnlockableContent t) {
        return t.uiIcon;
    }
}
