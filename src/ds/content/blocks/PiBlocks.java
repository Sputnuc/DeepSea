package ds.content.blocks;


import arc.graphics.Color;
import arc.math.Interp;
import arc.struct.Seq;
import ds.content.DSFx;
import ds.content.DSSounds;
import ds.content.DSStatusEffects;
import ds.content.units.PiUnits;
import ds.world.blocks.crafting.DynamicCrafter;
import ds.world.blocks.distribution.ClosedConveyor;
import ds.world.blocks.power.AdvancedThermalGenerator;
import ds.world.blocks.production.WallDrill;
import ds.world.blocks.turret.AccelItemTurret;
import ds.world.blocks.turret.AccelPowerTurret;
import ds.world.blocks.turret.DSHarpoonTurret;
import ds.world.blocks.turret.DSItemTurret;
import ds.world.consumers.RecipeIO;
import ds.draw.drawers.DrawBetterRegion;
import ds.world.graphics.DSPal;
import ds.world.meta.DSEnv;
import ds.type.entities.bullets.HarpoonBulletType;
import ds.type.entities.bullets.PointLightningBulletType;
import ds.type.entities.dsUnits.TorpedoUnitType;
import ds.type.entities.effect.RandRadialEffect;
import ds.type.entities.weapons.DSWeapon;
import mindustry.content.Fx;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.BeamDrill;
import mindustry.world.blocks.production.BurstDrill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Env;


import static ds.content.DSAttributes.*;
import static ds.content.items.PiItems.*;
import static ds.content.liquids.PiLiquids.*;
import static mindustry.Vars.tilesize;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;
import static mindustry.type.ItemStack.*;

public class PiBlocks {
    public static Block
            //Production
            hydraulicDrill, hydraulicWallDrill, gasBore, detonateDrill,
            //Power
            powerTransmitter, powerDistributor, condensator, hydroTurbineGenerator, geothermalGenerator, fuelGenerator,
            //Effect
            lightProjector, repairModule,
            //Crafting
            hydrogenSulfideCollector, hydrogenSulfideDiffuser, manganeseSynthesizer, decompositionChamber, steelKiln, chemicalPlant,
            //Logistic
            isolatedConveyor, isolatedRouter, isolatedJunction, isolatedSorter, isolatedInvertedSorter, isolatedOverflowGate, isolatedUnderflowGate, isolatedBridge, pipe, liquidDistributor, pipeBridge, pipeJunction,
            //Storage
            pressuredLiquidContainer, pressuredContainer, pressuredUnloader,
            //Cores
            coreInfluence, coreEnforcement, coreEminence,
            //Turrets
            cutoff, irritation, discharge, hydroid, execution,
            //Defends
            aluminiumWall, aluminiumWallLarge,
            //UnitBlocks
            shadeUnitFactory, shadeUnitReconstructor;

    public static void load(){
        loadLogisticBlocks();
        loadProductionBlocks();
        loadPowerBlocks();
        loadCraftBlocks();
        loadEffectBlocks();
        loadTurrets();
        loadDefence();
        loadUnitBlocks();
    }
    public static void loadTurrets(){
        cutoff = new DSHarpoonTurret("cutoff"){{
            scaledHealth = 60;
            requirements(Category.turret, with(aluminium, 75, silver, 45));
            outlineColor = DSPal.dsTurretOutline;
            size = 2;
            reload = 300;
            range = 200;
            shake = 2;
            envRequired = DSEnv.underwaterWarm;
            shootSound = DSSounds.shootHarpoon;
            consumeItem(sulfur, 3);
            shootCone = 1;
            drawer = new DrawTurret("ds-turret-");
            shootY = 2;
            shootType = new HarpoonBulletType(16, 45){{
                buildingDamageMultiplier = 0.3f;
                lifetime = 45;
                pierceDrag = 0.25f;
                shootEffect = DSFx.dsInclinedWave;
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
        irritation = new DSItemTurret("irritation"){{
            scaledHealth = 80;
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
            consumePower(30/60f);
            range = 17 * tilesize;
            reload = 30;
            targetAir = false;
            drawer = new DrawTurret("ds-turret-");
            ammoUseEffect = Fx.casing2Double;
            ammo(
                    silver, new BasicBulletType(8,15){{
                        pierce = true;
                        pierceCap = 2;
                        lifetime = 17;
                        trailLength = 4;
                        trailWidth = 0.6f;
                        width = 5;
                        height = 12;
                        frontColor = hitColor = Color.valueOf("f0fdff");
                        backColor = trailColor = Color.valueOf("ace1e8");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailInterval = 2;
                        trailEffect = DSFx.dsBulletSparkTrail;
                        ammoMultiplier = 1;
                    }},
                    steel, new BasicBulletType(8,23){{
                        reloadMultiplier = 0.75f;
                        pierce = true;
                        pierceCap = 3;
                        lifetime = 22;
                        rangeChange = 5 * tilesize;
                        trailLength = 4;
                        trailWidth = 0.6f;
                        width = 5.75f;
                        height = 13;
                        frontColor = hitColor = Color.valueOf("ffc7bf");
                        backColor = trailColor = Color.valueOf("d98e84");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailInterval = 2;
                        trailEffect = DSFx.dsBulletSparkTrail;
                        ammoMultiplier = 2;
                    }}
            );
        }};
        discharge = new AccelPowerTurret("discharge"){{
            shootY = 28/4f;
            scaledHealth = 90;
            predictTarget = false;
            requirements(Category.turret, with(aluminium, 95, silver, 75, manganese, 55));
            outlineColor = DSPal.dsTurretOutline;
            size = 3;
            range = 25 * tilesize;
            cooldownInterval = 120;
            shootCone = 15;
            shootSound = Sounds.shootArc;
            shootEffect = Fx.none;
            consumePower(360/60f);
            reload = 300;
            maxAccel = 40;
            shake = 1.65f;
            speedUpPerShoot = 6f;
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

            shootType = new PointLightningBulletType(6.5f){{
                pierceArmor = true;
                despawnEffect = Fx.none;
                hitColor = lightningColor;
                hitEffect = Fx.colorSpark;
                buildingDamageMultiplier = 0.1f;
            }};
        }};

        execution = new AccelItemTurret("execution"){{
            requirements(Category.turret, with(aluminium, 125, silver, 35, graphite, 50, steel, 35));
            size = 3;
            outlineColor = DSPal.dsTurretOutline;
            scaledHealth = 100;
            rotateSpeed = 4.35f;
            targetInterval = 0.5f;
            newTargetInterval = 1;
            shootCone = 13;
            inaccuracy = 5;
            reload = 150;
            maxAccel = 50;
            shake = 1.25f;
            speedUpPerShoot = 10;
            recoil = 3;
            recoilTime = 10;
            cooldownSpeed = 1f;
            targetGround = false;
            consumeLiquid(hydrogen, 0.25f);
            consumePower(1f);
            shootSound = Sounds.shootDisperse;
            range = 30 * tilesize;
            drawer = new DrawTurret("ds-turret-");
            shoot = new ShootBarrel(){{
                barrels = new float[]{
                        2f, 0, 0,
                        -2f, 0, 0,
                        6.5f, -1.5f, 0,
                        -6.5f, -1.5f, 0
                };
            }};
            ammo(
                    aluminium, new BasicBulletType(8,28f){{
                        pierce = true;
                        pierceCap = 3;
                        lifetime = 30;
                        ammoMultiplier = 3;
                        frontColor = hitColor = Color.valueOf("f5d9ff");
                        backColor = trailColor = Color.valueOf("cea4de");
                        trailEffect = DSFx.dsColorSparkSmall;
                        trailInterval = 1;
                        trailRotation = true;
                        trailLength = 8;
                        trailWidth = 0.75f;
                        height = 17;
                        width = 5;
                        shootEffect = Fx.shootSmallColor;
                        hitEffect = Fx.hitBulletColor;
                        despawnEffect = Fx.hitBulletColor;
                        collidesGround = false;
                    }},
                    silver, new BasicBulletType(12,21){{
                        pierce = true;
                        pierceCap = 2;
                        lifetime = 24;
                        ammoMultiplier = 2;
                        rangeChange = 48;
                        reloadMultiplier = 2f;
                        frontColor = hitColor = Color.valueOf("edfdff");
                        backColor = trailColor = Color.valueOf("bce3e8");
                        trailEffect = DSFx.dsColorSparkSmall;
                        trailInterval = 1;
                        trailRotation = true;
                        trailLength = 8;
                        trailWidth = 0.75f;
                        height = 17;
                        width = 5;
                        shootEffect = Fx.shootSmallColor;
                        hitEffect = Fx.hitBulletColor;
                        despawnEffect = Fx.hitBulletColor;
                        collidesGround = false;
                    }},
                    steel, new BasicBulletType(4,38){{
                        lifetime = 60;
                        ammoMultiplier = 4;
                        frontColor = hitColor = Color.valueOf("ab7272");
                        backColor = trailColor = Color.valueOf("824d4d");
                        trailEffect = DSFx.dsColorSparkSmall;
                        trailInterval = 2;
                        trailRotation = true;
                        reloadMultiplier = 0.25f;
                        trailLength = 8;
                        trailWidth = 0.75f;
                        height = 17;
                        width = 5;
                        shootEffect = Fx.shootSmallColor;
                        hitEffect = Fx.hitBulletColor;
                        despawnEffect = Fx.hitBulletColor;
                        status = DSStatusEffects.waterLeak;
                        statusDuration = 60;
                    }}
            );
        }};

        hydroid = new DSItemTurret("hydroid"){{
            requirements(Category.turret, with(aluminium, 95, silver, 65, manganese, 95, steel, 20));
            size = 3;
            outlineColor = DSPal.dsTurretOutline;
            scaledHealth = 110;
            rotateSpeed = 1.1f;
            shootCone = 1;
            reload = 300;
            targetUnderBlocks = false;
            shootY = -1;
            consumeLiquid(oxygen, 15/60f);
            consumePower(1);
            shootSound = Sounds.shootRetusa;
            minWarmup = 0.94f;
            predictTarget = false;
            unitSort = UnitSorts.strongest;
            shootSoundVolume = 1.45f;
            range = 400;
            shootWarmupSpeed = 0.06f;
            ammoPerShot = 10;
            maxAmmo = 30;
            drawer = new DrawTurret("ds-turret-"){{
                parts.add(
                        new RegionPart("-draw-torpedo"){{
                            progress = PartProgress.reload.curve(Interp.pow2In);
                            colorTo = new Color(1f, 1f, 1f, 0f);
                            color = Color.white;
                            mixColorTo = Pal.accent;
                            mixColor = new Color(1f, 1f, 1f, 0f);
                            outline = false;
                            under = true;
                            layerOffset = -0.01f;
                            moves.add(new PartMove(PartProgress.warmup.inv(), 0f, -2, 0f));
                        }}
                );
            }};
            ammo(
                    steel, new BulletType(){{
                        keepVelocity = false;
                        instantDisappear = true;
                        ammoMultiplier = 1;
                        speed = 0.01f;
                        spawnUnit = new TorpedoUnitType("hydroid-torpedo"){{
                            speed = 5;
                            rotateSpeed = 1;
                            deathSound = Sounds.explosionPlasmaSmall;
                            lifetime = 110;
                            health = 60;
                            missileAccelTime = 30f;
                            maxRange = 9;
                            deathSoundVolume = 1.35f;
                            weapons.add(new DSWeapon(){{
                                shootCone = 360f;
                                mirror = false;
                                reload = 1f;
                                shootOnDeath = true;
                                shootSound = Sounds.none;
                                bullet = new ExplosionBulletType(120,5.5f * tilesize){{
                                    buildingDamageMultiplier = 0.15f;
                                    status = DSStatusEffects.waterLeak;
                                    statusDuration = 600;
                                    despawnEffect = new MultiEffect(
                                            Fx.massiveExplosion,
                                            DSFx.smoothColorCircle(Color.white, 5.5f* tilesize, 45, Interp.circleOut)
                                    );
                                    damage = 0;
                                }};
                            }});
                        }};
                    }}
            );
        }};
    }
    public static void loadEffectBlocks(){
        coreInfluence = new CoreBlock("core-influence"){{
            requirements(Category.effect, with(aluminium, 790, silver, 680));
            size = 3;
            isFirstTier = true;
            alwaysUnlocked = true;
            health = 2100;
            itemCapacity = 5300;
            buildCostMultiplier = 3;
            unitCapModifier = 18;
            unitType = PiUnits.moment;
            envEnabled |= Env.terrestrial | DSEnv.underwaterWarm;
            envDisabled = Env.none;
            squareSprite = false;
        }};
        pressuredContainer = new StorageBlock("pressured-container"){{
            requirements(Category.effect, with(aluminium, 120, silver, 65, steel, 65));
            size = 2;
            itemCapacity = 800;
        }};
        pressuredUnloader = new Unloader("pressured-unloader"){{
            requirements(Category.distribution, with(aluminium, 20, manganese, 15));
            size = 1;
            speed = 4.8f;
        }};
        repairModule = new MendProjector("repair-module"){{
            requirements(Category.effect, with(aluminium, 55, silver, 45, manganese, 30));
            consumePower(1);
            consumeLiquid(hydrogen, 3/60f);
            researchCostMultiplier = 0.6f;
            size = 2;
            reload = 300;
            range = 120;
            healPercent = 15;
            scaledHealth = 55;
            phaseRangeBoost = 0;
            phaseBoost = 0;
            baseColor = phaseColor = Color.valueOf("d1ffff");
        }};
        lightProjector = new LightBlock("light-projector"){{
            requirements(Category.effect, BuildVisibility.lightingOnly, with(aluminium, 25, silver, 15));
            size = 2;
            brightness = 0.875f;
            radius = 25 * tilesize;
            consumePower(0.5f);
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
        isolatedSorter = new Sorter("isolated-sorter"){{
            requirements(Category.distribution, with(aluminium, 7, silver, 2));
        }};
        isolatedInvertedSorter = new Sorter("isolated-inverted-sorter"){{
            requirements(Category.distribution, with(aluminium, 7, silver, 2));
            invert = true;
        }};
        isolatedOverflowGate = new OverflowGate("isolated-overflow-gate"){{
            requirements(Category.distribution, with(aluminium, 6, silver, 3));
        }};
        isolatedUnderflowGate = new OverflowGate("isolated-underflow-gate"){{
            requirements(Category.distribution, with(aluminium, 6, silver, 3));
            invert = true;
        }};
        pipe = new ArmoredConduit("liquid-pipe"){{
            requirements(Category.liquid, with(silver, 2));
            liquidPressure = 1.025f;
            liquidCapacity = 20;
        }};
        pipeJunction = new LiquidJunction("pipe-junction"){{
            requirements(Category.liquid, with(silver, 5));
            liquidPressure = 1.025f;
        }};
        ((ArmoredConduit) pipe).junctionReplacement = pipeJunction;
        liquidDistributor = new LiquidRouter("liquid-distributor"){{
            requirements(Category.liquid, with(silver, 4));
            liquidPressure = 1.025f;
            liquidCapacity = 40f;
        }};
        pipeBridge = new LiquidBridge("pipe-bridge"){{
            requirements(Category.liquid, with(silver, 9));
            range = 5;
            liquidPressure = 1.025f;
            liquidCapacity = 60;
            hasPower = false;
        }};
        ((ArmoredConduit) pipe).bridgeReplacement = pipeBridge;
        pressuredLiquidContainer = new LiquidRouter("pressured-liquid-container"){{
            requirements(Category.liquid, with(aluminium, 40, silver, 50));
            liquidPressure = 1.025f;
            liquidCapacity = 1900f;
            size = 2;
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
            drillEffect = DSFx.drillImpact;
            arrows = 0;
        }};
        hydraulicWallDrill = new WallDrill("hydraulic-wall-drill"){{
            requirements(Category.production, with(aluminium, 50, silver, 24));
            drillTime = 240;
            liquidBoostIntensity = 1;
            size = 3;
            tier = 4;
            drillEffectChance = 0.01f;
            drillEffect = Fx.mineWallSmall;
        }};
        gasBore  = new BeamDrill("gas-bore"){{
            requirements(Category.production, with(aluminium, 75, silver, 65, graphite, 70, manganese, 25));
            size = 3;
            range = 3;
            drillTime = 120;
            liquidCapacity = 30;
            optionalBoostIntensity = 1;
            consumePower(2);
            consumeLiquid(oxygen, 3/60f);
            consumeLiquid(hydrogen, 6/60f);
            tier = 5;
        }};
        detonateDrill = new BurstDrill("detonate-drill"){{
            requirements(Category.production, with(aluminium, 85, silver, 55, graphite, 45, steel, 70));
            size = 4;
            arrows = 1;
            itemCapacity = 70;
            glowColor = Color.valueOf("c9f3ff").a(0.75f);
            arrowColor = Color.valueOf("c9f3ff");
            drillTime = 120;
            liquidBoostIntensity = 1;
            arrowOffset = 2f;
            arrowSpacing = 5f;
            tier = 6;
            consumePower(3);
            consumeLiquid(oxygen, 0.1f);
            consumeLiquid(hydrogen, 0.2f);
            shake = 4;
            drillSoundVolume = 1.4f;
            drillEffect = new MultiEffect(
                    DSFx.drillDetonateEffectTri,
                    DSFx.drillDetonateEffectWave,
                    DSFx.smoothColorCircle(Color.valueOf("fcffeb"), 30, 40, Interp.pow2Out)
            );
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
        powerDistributor = new PowerNode("power-distributor"){{
            requirements(Category.power, with(aluminium, 6, silver, 4, manganese, 2));
            size = 1;
            laserColor1 = Color.valueOf("ffdede");
            laserColor2 = Color.valueOf("e56e6e");
            laserScale = 0.35f;
            maxNodes = 10;
            laserRange = 6;
            underBullets = true;
        }};
        condensator = new Battery("condensator"){{
            requirements(Category.power, with(aluminium, 45, silver, 25, manganese, 35));
            size = 2;
            fullLightColor = Color.valueOf("bed5f7");
            consumePowerBuffered(7800f);
            baseExplosiveness = 3.5f;
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
        geothermalGenerator = new AdvancedThermalGenerator("geothermal-generator"){{
            requirements(Category.power, with(aluminium, 100, silver, 95, manganese, 65));
            size = 4;
            attribute = Attribute.heat;
            powerProduction = 5/16f;
            displayEfficiencyScale = 1/16f;
            effectChance = 0.1f;
            lightRadius = 1;
            generateEffect = new RandRadialEffect(){{
                amount = 3;
                rotationSpacing = 360f / amount;
                effectSpacingRndMultiplier = 1.35f;
                lengthOffset = 42/4f;
                effect = DSFx.geotermalBubbles;
            }};
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawBetterRegion("-coil", -1){{
                        spinSprite = true;
                        rotation = 0;
                    }},
                    new DrawBetterRegion("-coil", -1){{
                        spinSprite = true;
                        rotation = 22.5f;
                    }},
                    new DrawBetterRegion("-coil", -1){{
                        spinSprite = true;
                        rotation = 45f;
                    }},
                    new DrawBetterRegion("-coil", -1){{
                        spinSprite = true;
                        rotation = 67.5f;
                    }},
                    new DrawGlowRegion("-glow"){{
                        color = Color.valueOf("ffabb5");
                        alpha = 0.35f;
                        glowScale = 0.1f;
                    }},
                    new DrawDefault()
            );
        }};
        fuelGenerator = new ConsumeGenerator("fuel-generator"){{
            requirements(Category.power, with(aluminium, 65, silver, 95, graphite, 50));
            size = 4;
            powerProduction = 3;
            consume(new ConsumeItemFlammable(0.45f));
            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion(){{
                color = Color.valueOf("ebfaff");
            }});
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
        manganeseSynthesizer = new GenericCrafter("manganese-synthesizer"){{
            requirements(Category.crafting, with(aluminium, 125, silver, 95));
            size = 4;
            consumePower(2);
            consumeLiquid(hydrogen, 9/60f);
            consumeItems(with(manganeseHydroxide, 5, aluminium, 2));
            craftTime = 120;
            outputItem = new ItemStack(manganese, 4);
            drawer = new DrawMulti(
                    new DrawRegion("-bottom2"),
                    new DrawLiquidTile(hydrogen),
                    new DrawRegion("-bottom1"),
                    new DrawArcSmelt(){{
                        flameRad = 5;
                        flameColor= Color.valueOf("f5a4de");
                        midColor = Color.valueOf("db6999");
                        circleStroke = 0;
                        particles = 20;
                        particleRad = 8;
                    }},
                    new DrawDefault()
            );
        }};
        decompositionChamber = new AttributeCrafter("decomposition-chamber"){{
            requirements(Category.crafting, with(aluminium, 95, silver, 75, graphite, 85));
            size = 4;
            craftTime = 60;
            attribute = Attribute.heat;
            baseEfficiency = 0.25f;
            boostScale = 1f/16f;
            minEfficiency = 4f;
            rotate = true;
            invertFlip = true;
            group = BlockGroup.liquids;
            liquidCapacity = 90f;
            consumePower(2f);
            outputLiquids = LiquidStack.with(hydrogen, 18/60f, oxygen, 9/60f);
            drawer = new DrawMulti(
                    new DrawRegion("-bottom-1"),
                    new DrawLiquidTile(hydrogen),
                    new DrawRegion("-bottom-2"),
                    new DrawLiquidTile(oxygen, 10),
                    new DrawBubbles(Color.valueOf("ffffff")){{
                        sides = 10;
                        recurrence = 3f;
                        spread = 6;
                        radius = 1.5f;
                        amount = 20;
                    }},
                    new DrawRegion(),
                    new DrawGlowRegion("-glow"){{
                        alpha = 0.6f;
                        color = Color.valueOf("cce4ff");
                        glowIntensity = 0.2f;
                        glowScale = 4f;
                    }},
                    new DrawLiquidOutputs()
            );
            ambientSound = Sounds.loopElectricHum;
            ambientSoundVolume = 0.12f;
            regionRotated1 = 3;
            liquidOutputDirections = new int[]{1, 3};
            envRequired = DSEnv.underwaterWarm;
        }};
        steelKiln = new GenericCrafter("steel-kiln"){{
            requirements(Category.crafting, with(aluminium, 125, silver, 75, graphite, 85, manganese, 35));
            size = 4;
            liquidCapacity = 50;
            consumePower(2);
            consumeItems(ItemStack.with(ironstone, 2, graphite, 1));
            consumeLiquid(oxygen, 0.1f);
            outputItem = new ItemStack(steel, 1);
            craftTime = 60;
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.19f;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(oxygen),
                    new DrawArcSmelt(){{
                        flameRad = 2;
                        particleRad = 5;
                        midColor = Color.valueOf("fffce5");
                        flameColor = Color.valueOf("f2daaa");
                        flameRadiusMag = 2f;
                        flameRadiusScl = 2.6f;
                    }},
                    new DrawDefault()
            );
        }};
        chemicalPlant = new DynamicCrafter("chemical-plant"){{
            requirements(Category.crafting, with(aluminium, 175, silver, 125, manganese, 85, steel, 120));
            alwaysUnlocked = true;
            size = 4;
            buildVisibility = BuildVisibility.sandboxOnly;
            itemCapacity = 30;
            liquidCapacity = 120;
            addRecipes(
                    new RecipeIO(){{
                        liquidOut = LiquidStack.with(sulfuricAcid, 0.1f);
                        liquidIn = LiquidStack.with(hydrogenSulfide, 0.1f, oxygen, 0.4f);
                        input = ItemStack.with(sulfur, 2);
                    }},
                    new RecipeIO(){{
                        input = ItemStack.with(manganeseHydroxide, 8, aluminium, 3, sulfur, 1);
                        liquidIn = LiquidStack.with(hydrogen, 0.25f);
                        output = ItemStack.with(manganese, 7);
                        recipeTime = 120f;
                    }}
            );
            consumePower(4);
            drawer = new DrawMulti(new DrawDefault(), new DrawGlowRegion("-glow"){{
                color = Color.valueOf("bae6ff");
            }});
        }};
    }
    public static void loadUnitBlocks(){
        shadeUnitFactory = new UnitFactory("shade-unit-factory"){{
            requirements(Category.units, with(aluminium, 95, silver, 75, manganese, 55));
            size = 3;
            consumePower(2.5f);
            consumeLiquid(oxygen, 0.2f);
            plans = Seq.with(
                    new UnitPlan(PiUnits.condition, 60f * 25, with(silver, 30, graphite, 15)),
                    new UnitPlan(PiUnits.note, 60f * 40, with(silver, 55, graphite, 30, manganese, 45)),
                    new UnitPlan(PiUnits.complicity, 60f * 25, with(aluminium, 25, manganese, 20))
            );
        }};
        shadeUnitReconstructor = new Reconstructor("shade-unit-reconstructor"){{
            requirements(Category.units, with(aluminium, 125, silver, 95, manganese, 75, steel, 55));
            size = 3;
            consumePower(3);
            consumeLiquid(hydrogen, 0.25f);
            consumeItems(with(aluminium, 45, graphite, 55, steel, 30));
            upgrades.addAll(
                    new UnitType[]{PiUnits.condition, PiUnits.oversight},
                    new UnitType[]{PiUnits.complicity, PiUnits.consequences},
                    new UnitType[]{PiUnits.note, PiUnits.sound}
            );
            constructTime = 20 * 60f;
        }};
    }
    public static void loadDefence(){
        float dswallHealthMultiplier = 45;
        aluminiumWall = new Wall("aluminium-wall"){{
            size = 1;
            health = (int) (dswallHealthMultiplier * this.size * this.size * 10);
            requirements(Category.defense, with(aluminium, 6 * this.size * this.size));
        }};
        aluminiumWallLarge = new Wall("aluminium-wall-large"){{
            researchCostMultiplier = 0.5f;
            size = 2;
            health = (int) (dswallHealthMultiplier * this.size * this.size * 10);
            requirements(Category.defense, with(aluminium, 6 * this.size * this.size));
        }};
    }
}
