package ds.content.blocks;


import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.Interp;
import ds.content.dsFx;
import ds.content.dsSounds;
import ds.content.items.zItems;
import ds.content.units.zUnits;
import ds.world.blocks.dsHarpoonTurret;
import ds.world.blocks.dsPayloadTurret;
import ds.world.meta.DSEnv;
import ds.world.type.entities.HarpoonBulletType;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PayloadAmmoTurret;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.Env;


import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

public class zBlocks {
    public static Block
            //Production
            hydraulicDrill, hydraulicWallDrill,
            //Cores
            coreInfluence, coreEnforcement, coreEminence,
            //Turrets
            testTurret, testTurretAlternate, testTorpedoTurret;
    public static void load(){
        coreInfluence = new CoreBlock("core-influence"){{
            requirements(Category.effect, with(zItems.aluminium, 790, zItems.manganese, 680));
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
        }};
        testTurret = new ItemTurret("test"){{
            requirements(Category.turret, with());
            size = 2;
            reload = 180;
            range = 300;
            shake = 2;
            shootSound = dsSounds.harpoon;
            ammo(
                    zItems.sulfur, new HarpoonBulletType(16, 60){{
                        pierceDrag = 0.25f;
                        shootEffect = dsFx.dsInclinedWave;
                        layer = Layer.bullet - 3;
                        drag = 0.05f;
                        frontColor = Color.valueOf("d4d4d4");
                        backColor = Color.valueOf("929aa8");
                        wireStroke = 3.75f;
                        width = 10;
                        height = 25;
                        returnSpeed = 3;
                    }}
            );
        }};
        testTurretAlternate = new dsHarpoonTurret("alternate"){{
            requirements(Category.turret, with());
            size = 2;
            reload = 20;
            range = 300;
            shake = 2;
            shootSound = dsSounds.harpoon;
            fuelItem = zItems.sulfur;
            shootType = new HarpoonBulletType(16, 60){{
                        pierceDrag = 0.25f;
                        shootEffect = dsFx.dsInclinedWave;
                        layer = Layer.bullet - 3;
                        drag = 0.05f;
                        frontColor = Color.valueOf("d4d4d4");
                        backColor = Color.valueOf("929aa8");
                        wireStroke = 3.75f;
                        width = 10;
                        height = 25;
                        returnSpeed = 3;
                    }};
        }};
        testTorpedoTurret = new dsPayloadTurret("test2"){{
            requirements(Category.turret, with());
            predictTarget = false;
            size = 3;
            shake = 3;
            range = 6 * 175;
            shootSound = Sounds.missileLaunch;
            reload = 600;
            shoot.shots = 3;
            shoot.shotDelay = 60;
            ammoPerShot = 3;
            maxAmmo = 6;
            ammo(
                    Blocks.berylliumWall, new BulletType(){{
                        ammoMultiplier = 1;
                        instantDisappear = true;
                        collides = false;
                        shootEffect = dsFx.dsInclinedWave;
                        spawnUnit = new MissileUnitType("torpedo"){{
                            speed = 6;
                            missileAccelTime = 60f;
                            trailLength = 0;
                            rotateSpeed = 1;
                            lifetime = 180;
                            deathSound = Sounds.none;
                            deathExplosionEffect =  Fx.none;
                            weapons.add(new Weapon(){{
                                shootCone = 360f;
                                mirror = false;
                                reload = 1f;
                                shootOnDeath = true;
                                shootSound = Sounds.none;
                                bullet = new ExplosionBulletType(235,6 * tilesize){{
                                    despawnEffect = new ParticleEffect(){{
                                        particles = 10;
                                        length = 6 * tilesize * 1.75f;
                                        baseLength = 8;
                                        lifetime = 180;
                                        sizeFrom = 3;
                                        sizeTo = 6;
                                        colorFrom = Color.white;
                                        colorTo = Color.valueOf("40404005");
                                    }};
                                }};
                                abilities.add(new MoveEffectAbility(){{
                                    effect = new ParticleEffect(){{
                                        particles = 3;
                                        sizeFrom = 2;
                                        sizeTo = 4;
                                        lifetime = 80;
                                        colorFrom = Color.white;
                                        colorTo = Color.valueOf("40404005");
                                    }};
                                    rotation = 180f;
                                    y = -3f;
                                    interval = 2f;
                                }});
                            }});
                        }};
                    }}
            );
        }};
    }
}
