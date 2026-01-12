package ds.content.blocks;


import arc.graphics.Color;
import ds.content.dsFx;
import ds.content.dsSounds;
import ds.content.items.zItems;
import ds.content.units.zUnits;
import ds.world.blocks.distribution.ClosedConveyor;
import ds.world.blocks.turret.AccelItemTurret;
import ds.world.blocks.turret.AccelPowerTurret;
import ds.world.blocks.turret.dsHarpoonTurret;
import ds.world.graphics.DSPal;
import ds.world.meta.DSEnv;
import ds.world.type.entities.bullets.HarpoonBulletType;
import ds.world.type.entities.bullets.PosLightningBulletType;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.BurstDrill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.Env;


import static ds.content.dsAttributes.*;
import static ds.content.items.zItems.*;
import static ds.content.liquids.zLiquids.*;
import static mindustry.Vars.tilesize;
import static mindustry.content.Liquids.*;
import static mindustry.type.ItemStack.with;

public class zBlocks {
    public static Block
            //Production
            hydraulicDrill, hydraulicWallDrill,
            //Power
            powerTransmitter, hydroTurbineGenerator,
            //Crafting
            hydrogenSulfideCollector, hydrogenSulfideDiffuser,
            //Logistic
            isolatedConveyor, isolatedRouter, isolatedJunction, isolatedBridge, pipe, pipeRouter,
            //Cores
            coreInfluence, coreEnforcement, coreEminence,
            //Turrets
            cutoff, irritation, discharge,
            //Defends
            aluminiumWall, aluminiumWallLarge;
    public static void load(){
        loadLogisticBlocks();
        loadProductionBlocks();
        loadPowerBlocks();
        loadCraftBlocks();
        loadEffectBlocks();
        loadTurrets();
        loadDefence();
    }
    public static void loadTurrets(){
        cutoff = new dsHarpoonTurret("cutoff"){{
            health = 785;
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
            health = 650;
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
            consumePower(15/60f);
            range = 15 * tilesize;
            reload = 30;
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
                        height = 12;
                        frontColor = hitColor = Color.valueOf("f0fdff");
                        backColor = trailColor = Color.valueOf("ace1e8");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailInterval = 2;
                        trailEffect = dsFx.dsBulletSparkTrail;
                    }},
                    ferrum, new BasicBulletType(8,26){{
                        reloadMultiplier = 0.75f;
                        pierce = true;
                        pierceCap = 3;
                        lifetime = 20;
                        rangeChange = 5 * tilesize;
                        trailLength = 4;
                        trailWidth = 0.6f;
                        width = 5.75f;
                        height = 13;
                        frontColor = hitColor = Color.valueOf("ffc7bf");
                        backColor = trailColor = Color.valueOf("d98e84");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailInterval = 2;
                        trailEffect = dsFx.dsBulletSparkTrail;
                    }}
            );
        }};
        discharge = new AccelPowerTurret("discharge"){{
            shootY = 28/4f;
            health = 975;
            requirements(Category.turret, with(aluminium, 95, silver, 75, manganese, 55));
            outlineColor = DSPal.dsTurretOutline;
            size = 3;
            range = 25 * tilesize;
            shootCone = 15;
            shootSound = Sounds.shootArc;
            consumePower(360/60f);
            reload = 300;
            maxAccel = 40;
            shake = 1.65f;
            speedUpPerShoot = 3f;
            drawer = new DrawTurret("ds-turret-"){{
                parts.add(
                    new RegionPart("-decal"){{
                        progress = PartProgress.recoil;
                        heatProgress = PartProgress.warmup;
                        heatColor = Color.valueOf("a1fff9");
                        drawRegion = false;
                        mirror = false;
                        under = false;
                }});
            }};
            shoot.shots = 4;
            shoot.shotDelay = 0.6f;

            shootType = new LightningBulletType(){{
                shootEffect = Fx.none;
                damage = 12;
                lifetime = 25;
                lightningDamage = 8;
                lightningLength = 25;
                lightningLengthRand = 5;
                lightningColor = Color.valueOf("a1fff9");
                lightningCone = 3;
            }};
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
        float spd = 0.085f;
        isolatedConveyor = new ClosedConveyor("isolated-conveyor"){{
            requirements(Category.distribution, with(aluminium, 1));
            speed = spd;
            displayedSpeed = 12.5f;
            junctionReplacement = isolatedJunction;
            bridgeReplacement = isolatedBridge;
        }};
        isolatedJunction = new Junction("isolated-junction"){{
            requirements(Category.distribution, with(aluminium, 2));
            speed = 1 / spd;
        }};
        ((ClosedConveyor) isolatedConveyor).junctionReplacement = isolatedJunction;
        isolatedBridge = new ItemBridge("isolated-bridge"){{
            requirements(Category.distribution, with(aluminium, 7, silver, 3));
            range = 5;
            hasPower = false;
            transportTime = 60/13f;
        }};
        ((ClosedConveyor) isolatedConveyor).bridgeReplacement = isolatedBridge;
        isolatedRouter = new Router("isolated-router"){{
            requirements(Category.distribution, with(aluminium, 5));
            speed =  1 / spd;
        }};
        pipe = new ArmoredConduit("liquid-pipe"){{
            requirements(Category.liquid, with(silver, 2));
            liquidPressure = 1.025f;
            liquidCapacity = 40;
        }};
    }
    public static void loadProductionBlocks(){
        hydraulicDrill = new BurstDrill("hydraulic-drill"){{
            requirements(Category.production, with(aluminium, 12));
            drillTime = 600;
            liquidBoostIntensity = 1;
            size = 2;
            tier = 4;
            shake = 1.5f;
            drillEffect = dsFx.drillImpact;
            arrows = 0;
        }};
    }
    public static void loadPowerBlocks(){
        powerTransmitter = new PowerNode("power-transmitter"){{
            requirements(Category.power, with(aluminium, 15, silver, 3));
            size = 2;
            laserColor1 = Color.valueOf("ffdede");
            laserColor2 = Color.valueOf("e56e6e");
            laserScale = 0.75f;
            maxNodes = 3;
            laserRange = 15;
            underBullets = true;
        }};
        hydroTurbineGenerator = new ThermalGenerator("hydro-turbine-generator"){{
            requirements(Category.power, with(aluminium, 95, silver, 85));
            displayEfficiencyScale = 1f / 9f;
            minEfficiency = 9f - 0.0001f;
            displayEfficiency = false;
            attribute = geyser;
            size = 3;
            powerProduction = 2f / 9f;
            outputLiquid = new LiquidStack(hydrogenSulfide, 3f/60f/9f);
            liquidCapacity = 30;
            hasLiquids = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(hydrogenSulfide), new DrawBlurSpin("-rotator", 6), new DrawDefault());
        }};
    }
    public static void loadCraftBlocks(){
        hydrogenSulfideCollector = new AttributeCrafter("hydrogen-sulfide-collector"){{
            requirements(Category.production, with(aluminium, 65, silver, 25));
            liquidCapacity = 60;
            attribute = sulfuric;
            boostScale = 1f/9f;
            minEfficiency = 9f - 0.0001f;
            baseEfficiency = 0;
            outputLiquid = new LiquidStack(hydrogenSulfide, 0.1f);
            size = 3;
            consumePower(0.5f);
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(hydrogenSulfide), new DrawBlurSpin("-rotator", 12), new DrawDefault());
        }};
        hydrogenSulfideDiffuser = new GenericCrafter("hydrogen-sulfide-diffuser"){{
            requirements(Category.crafting, with(aluminium, 85, silver, 55));
            liquidCapacity = 60;
            size = 3;
            consumeLiquid(hydrogenSulfide, 12/60f);
            consumePower(1);
            craftTime = 60;
            outputItem = new ItemStack(sulfur, 1);
            outputLiquid = new LiquidStack(hydrogen, 9/60f);
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(hydrogenSulfide), new DrawLiquidTile(hydrogen),new DrawDefault());
            ignoreLiquidFullness = true;
        }};
    }
    public static void loadDefence(){
        float dswallHealthMultiplier = 45;
        aluminiumWall = new Wall("aluminium-wall"){{
            size = 1;
            health = (int) (dswallHealthMultiplier * this.size * this.size * 10);
            requirements(Category.defense, with(aluminium, 9 * this.size * this.size));
        }};
        aluminiumWallLarge = new Wall("aluminium-wall-large"){{
            researchCostMultiplier = 0.5f;
            size = 2;
            health = (int) (dswallHealthMultiplier * this.size * this.size * 10);
            requirements(Category.defense, with(aluminium, 9 * this.size * this.size));
        }};
    }
}
