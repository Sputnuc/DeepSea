package ds.content.blocks;


import arc.graphics.Color;
import ds.content.dsFx;
import ds.content.dsSounds;
import ds.content.items.zItems;
import ds.content.units.zUnits;
import ds.world.blocks.distribution.ClosedConveyor;
import ds.world.blocks.dsHarpoonTurret;
import ds.world.graphics.DSPal;
import ds.world.meta.DSEnv;
import ds.world.type.entities.bullets.HarpoonBulletType;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.production.BurstDrill;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.Env;


import static ds.content.items.zItems.*;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

public class zBlocks {
    public static Block
            //Production
            hydraulicDrill, hydraulicWallDrill,
            //Logistic
            isolatedConveyor, isolatedRouter,
            //Cores
            coreInfluence, coreEnforcement, coreEminence,
            //Turrets
            cutoff, irritation,
            //Defends
            aluminiumWall, aluminiumWallLarge;
    public static void load(){
        loadLogisticBlocks();
        loadProductionBlocks();
        loadCraftBlocks();
        loadEffectBlocks();
        loadTurrets();
        loadDefence();
    }
    public static void loadTurrets(){
        cutoff = new dsHarpoonTurret("cutoff"){{
            requirements(Category.turret, with(aluminium, 75, silver, 45));
            outlineColor = DSPal.dsTurretOutline;
            size = 2;
            reload = 300;
            range = 200;
            shake = 2;
            shootSound = dsSounds.shootHarpoon;
            fuelItem = sulfur;
            fuelAmount = 3;
            shootCone = 1;
            drawer = new DrawTurret("ds-turret-");
            shootY = 2;
            shootType = new HarpoonBulletType(16, 45){{
                lifetime = 45;
                pierceDrag = 0.25f;
                shootEffect = dsFx.dsInclinedWave;
                layer = Layer.bullet - 3;
                drag = 0.08f;
                frontColor = Color.valueOf("d4d4d4");
                backColor = Color.valueOf("929aa8");
                wireStroke = 3.75f;
                width = 10;
                height = 25;
                returnSpeed = 3;
            }};
        }};
        irritation = new ItemTurret("irritation"){{
            requirements(Category.turret, with(aluminium, 75, silver, 45));
            outlineColor = DSPal.dsTurretOutline;
            size = 2;
            shoot = new ShootSpread(){{
                spread = 1.75f;
                shots = 9;
            }};
            inaccuracy = 1;
            shootCone = 10;
            velocityRnd = 0.1f;
            shootSound = Sounds.shootDiffuse;
            range = 15 * tilesize;
            reload = 40;
            targetAir = false;
            drawer = new DrawTurret("ds-turret-");
            ammoUseEffect = Fx.casing2Double;
            ammo(
                    silver, new BasicBulletType(8,19){{
                        pierce = true;
                        pierceCap = 2;
                        lifetime = 15;
                        trailLength = 4;
                        trailWidth = 0.6f;
                        width = 5;
                        height = 6;
                        frontColor = hitColor = Color.valueOf("f0fdff");
                        backColor = trailColor = Color.valueOf("ace1e8");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                    }},
                    ferrum, new BasicBulletType(8,26){{
                        reloadMultiplier = 0.75f;
                        pierce = true;
                        pierceCap = 3;
                        lifetime = 20;
                        rangeChange = 5 * tilesize;
                        trailLength = 4;
                        trailWidth = 0.6f;
                        width = 5;
                        height = 6;
                        frontColor = hitColor = Color.valueOf("ffc7bf");
                        backColor = trailColor = Color.valueOf("d98e84");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                    }}
            );
        }};
    }
    public static void loadEffectBlocks(){
        coreInfluence = new CoreBlock("core-influence"){{
            requirements(Category.effect, with(aluminium, 790, zItems.manganese, 680));
            size = 3;
            isFirstTier = true;
            alwaysUnlocked = true;
            health = 2100;
            itemCapacity = 5300;
            buildCostMultiplier = 3;
            unitCapModifier = 12;
            unitType = zUnits.moment;
            envEnabled |= Env.terrestrial | DSEnv.underwaterWarm;
            envDisabled = Env.none;
            squareSprite = false;
        }};
    }
    public static void loadLogisticBlocks(){
        isolatedConveyor = new ClosedConveyor("isolated-conveyor"){{
            requirements(Category.distribution, with(aluminium, 1));
            speed = 0.085f;
            displayedSpeed = 11.25f;
        }};
        isolatedRouter = new Router("isolated-router"){{
            requirements(Category.distribution, with(aluminium, 5));
            speed =  1 / 0.085f;
        }};
    }
    public static void loadProductionBlocks(){
        hydraulicDrill = new BurstDrill("hydraulic-drill"){{
            requirements(Category.production, with(aluminium, 12));
            drillTime = 60 * 6;
            size = 2;
            tier = 4;
            shake = 1.5f;
            drillEffect = dsFx.drillImpact;
            arrows = 0;
        }};
    }
    public static void loadCraftBlocks(){

    }
    public static void loadDefence(){
        float dswallHealthMultiplier = 45;
        aluminiumWall = new Wall("aluminium-wall"){{
            size = 1;
            health = (int) (dswallHealthMultiplier * this.size * this.size * 10);
            requirements(Category.defense, with(aluminium, 9 * this.size * this.size));
        }};
        aluminiumWallLarge = new Wall("aluminium-wall-large"){{
            size = 2;
            health = (int) (dswallHealthMultiplier * this.size * this.size * 10);
            requirements(Category.defense, with(aluminium, 9 * this.size * this.size));
        }};
    }
}
