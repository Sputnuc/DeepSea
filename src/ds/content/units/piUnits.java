package ds.content.units;

import arc.graphics.Color;
import arc.math.Interp;
import arc.math.geom.Rect;
import arc.struct.Seq;
import ds.content.dsFx;
import ds.content.dsSounds;
import ds.world.graphics.dsPal;
import ds.world.type.entities.dsUnits.*;
import ds.world.type.entities.weapons.AdvancedLightWeapon;
import ds.world.type.entities.weapons.AntiTorpedoSystemWeapon;
import ds.world.type.entities.weapons.dsWeapon;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

import static mindustry.Vars.tilesize;
import static mindustry.content.Fx.*;

public class piUnits {
    public static float[] tierMultipliers = {1, 3f, 8f,16, 26};
    public static Seq<UnitType> pi312units = new Seq<>();
    public static UnitType
            //Core units
            moment,
            //Assault units
                //Mech
            condition, oversight,
                //Tank
            note,
                //Submarines
            complicity,
            //fauna

            untitledFish, angler,
            //DONT ASK
            annihilator;
    public static void loadUnits(){
        //Core units
        moment = new SubmarineUnitType("moment"){{
            omniMovement = true;
            strafePenalty = 0.45f;
            constructor = UnitEntity::create;
            targetable = true;
            speed = 3;
            health = 275;
            buildRange = 20 * tilesize;
            buildSpeed = 2.75f;
            mineSpeed = 6.25f;
            mineFloor = true;
            mineWalls = true;
            mineTier = 3;
            weapons.add(new AdvancedLightWeapon(){{
                x = 2;
                y = 1;
                shootCone = 15;
                lightCone = 35;
                lightLength = 22 * tilesize;
                lightTileable = false;
                shootSound = Sounds.shootAlpha;
                rotate = false;
                mirror = true;
                reload = 12;
                bullet = new LaserBoltBulletType(4, 15){{
                    width = 1;
                    height = 8;
                    buildingDamageMultiplier = 0.01f;
                    backColor = dsPal.dsBulletFront;
                    frontColor = Color.white;
                    despawnEffect = hitEffect = dsFx.dsBulletHit;
                    shootEffect = dsFx.dsShoot;
                    smokeEffect = none;
                    lifetime = 44;
                }};
            }});
        }};
        //Assault Submarines
        complicity = new SubmarineUnitType("complicity"){{
            constructor = UnitEntity::create;
            speed = 2.15f;
            health = 115;
            armor = 1;
            hitSize = 8;
            circleTarget = true;
            moveSound = Sounds.none;
            weapons.add(new AdvancedLightWeapon(){{
                x = 18/4f; y = 1;
                mirror = true;
                lightCone = 80;
                lightLength = 16*tilesize;
                shootSound = Sounds.none;
                lightTileable = false;
                reload = 120;
                rotate = false;
                shootCone = 10;
                inaccuracy = 7;
                shoot.shots = 2;
                shoot.shotDelay = 10;
                bullet = new BulletType(){{
                    instantDisappear = true;
                    keepVelocity = false;
                    spawnUnit = new TorpedoUnitType("basic-torpedo"){{
                        speed = 3.5f;
                        homingRange = 10;
                        homingPower = 0.35f;
                        lifetime = 90;
                        maxRange = 5f;
                        homingDelay = 10f;
                        missileAccelTime = 0;
                        rotateSpeed = 0.25f;
                        weapons.add(new dsWeapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            shootSound = Sounds.none;
                            bullet = new ExplosionBulletType(25,3.5f * tilesize){{
                                despawnEffect = Fx.massiveExplosion;
                                damage = 0;
                            }};
                        }});
                    }};
                }};
            }});
        }};
        //Mech
        condition = new dsMechUnitType("condition"){{
            speed = 0.35f;
            health = 195;
            armor = 3;
            hitSize = 8;
            stepSound = dsSounds.dsMechStep;
            weapons.add(new AdvancedLightWeapon("deepsea-condition-weapon"){{
                x = -18 / 4f; y = 5 / 4f;
                reload = 19;
                rotate = false;
                top = false;
                mirror = true;
                lightCone = 15;
                lightLength = 20 * tilesize;
                shootSound = dsSounds.shootSmallWeapon;
                bullet = new BasicBulletType(3.5f, 20){{
                    height = 13;
                    width = 10;
                    lightOpacity = 0.75f;
                    lightRadius = 5f;
                    trailInterval = 3;
                    trailEffect = dsFx.dsBulletTrail;
                    shootEffect = dsFx.dsShoot;
                    lightColor = Color.valueOf("bfe8ff");
                    frontColor = hitColor = dsPal.dsBulletFront;
                    backColor = dsPal.dsBulletBack;
                    lifetime = 45;
                    hitEffect = despawnEffect = dsFx.dsBulletHit;
                }};
            }});
        }};
        oversight = new dsMechUnitType("oversight"){{
            speed = 0.31f;
            health = 195 * tierMultipliers[1];
            armor = 3 * tierMultipliers[1];
            hitSize = 13;
            stepSound = dsSounds.dsMechStep;
            stepSoundPitch = 0.7f;
            stepSoundVolume = 1.25f;
            weapons.add(
                    new AdvancedLightWeapon("deepsea-oversight-weapon"){{
                        x = -7.875f; y = 0;
                        mirror = true;
                        rotate = false;
                        top = false;
                        reload = 45;
                        shake = 3.5f;
                        recoil = 2.1f;
                        shootSound = Sounds.shootDiffuse;
                        shootSoundVolume = 0.6f;
                        lightLength = 15 * tilesize;
                        lightCone = 35f;
                        shootY = 6;
                        inaccuracy = 15;
                        shoot.shots = 9;
                        velocityRnd = 0.25f;
                        bullet = new BasicBulletType(8, 14){{
                            pierce = true;
                            pierceCap = 2;
                            lifetime = 15;
                            trailLength = 6;
                            trailWidth = 0.6f;
                            width = 4;
                            height = 14;
                            frontColor = hitColor = Color.valueOf("7cbcf7");
                            backColor = trailColor = Color.valueOf("6783e5");
                            hitEffect = despawnEffect = Fx.hitBulletColor;
                            trailInterval = 2;
                            trailEffect = dsFx.dsBulletSparkTrail;
                        }};
                    }}
            );
        }};
        //Tanks
        note = new dsTankUnitType("note"){{
            speed = 0.29f;
            health = 350;
            armor = 6;
            hitSize = 8;
            treadRects = new Rect[] {
                    new Rect(-22f, -24f, 11, 50)
            };
            weapons.add(new AdvancedLightWeapon("deepsea-note-weapon"){{
                x = 0;
                y = 0;
                mirror = false;
                rotate = true;
                rotateSpeed = 1.7f;
                reload = 90;
                lightCone = 30;
                lightLength = 25 * tilesize;
                shake = 1;
                shootSound = dsSounds.shootTank;
                bullet = new BasicBulletType(8f, 42){{
                    height = 13;
                    width = 9;
                    sprite = "missile-large";
                    lightOpacity = 0.85f;
                    lightRadius = 9f;
                    trailInterval = 2;
                    trailEffect = dsFx.dsBulletTrail;
                    shootEffect = dsFx.dsShootBig;
                    lightColor = Color.valueOf("bfe8ff");
                    frontColor = hitColor = dsPal.dsBulletFront;
                    backColor = dsPal.dsBulletBack;
                    trailColor = dsPal.dsBulletBack;
                    trailLength = 5;
                    trailWidth = 0.75f;
                    lifetime = 25;
                    hitEffect = despawnEffect = dsFx.dsBulletHit;
                }};
            }});
        }};
        //End load
        loadFauna();
        pi312units.addAll(moment, condition, complicity, note);
    }
    public static void loadFauna(){
        untitledFish = new FaunaUnitType("untitled-fish"){{
            speed = 1;
            health = 30;
            constructor = UnitEntity::create;
            flying = true;
        }};
        angler = new EntityUnitType("angler"){{
            constructor = UnitEntity::create;
            health = 2900;
            loopSound = dsSounds.loopAngler;
            attackSound = dsSounds.loopAnglerAttack;
            loopShakeIntensity = 2;
            hitSize = 24;
        }};

        annihilator = new UnitType("kill-everyone"){{
            constructor = UnitEntity::create;
            flying = true;
            speed = 5;
            health = Float.POSITIVE_INFINITY;
            armor = Float.POSITIVE_INFINITY;
            strafePenalty = 1;
            weapons.add(
                    new Weapon(){{
                        mirror = rotate = false;
                        x = 0;
                        y = 0;
                        reload = 1200;
                        shoot.firstShotDelay = 600;
                        parentizeEffects = true;
                        bullet = new PointBulletType(){{
                            chargeEffect = new WaveEffect(){{
                                followParent = true;
                                rotWithParent = true;
                                sizeFrom = 120;
                                sizeTo = 0;
                                strokeFrom = 0;
                                strokeTo = 5;
                                lifetime = 600;
                                colorFrom = Color.valueOf("fd82ff");
                                colorTo = Color.valueOf("7a1ba6");
                            }};
                            damage = 999999999;
                            trailEffect = none;
                            despawnShake = 20;
                            lifetime = 300;
                            shootEffect = none;
                            fragBullets = 1;
                            fragLifeMax = fragLifeMin = 1;
                            fragOnHit = true;
                            fragRandomSpread = 0;
                            despawnSound = Sounds.explosionReactor2;
                            despawnEffect = new MultiEffect(new WaveEffect(){{
                                sizeFrom = 90;
                                sizeTo = 0;
                                strokeFrom = 0;
                                strokeTo = 5;
                                lifetime = 90;
                                colorFrom = Color.valueOf("ffffff");
                                colorTo = Color.valueOf("ffffff");
                            }},
                            dsFx.smoothColorCircle(Color.valueOf("ffffff"), 70, 25, Interp.pow3Out)
                            );
                            fragBullet = new BulletType(0.000001f, 0){{
                                collides = false;
                                splashDamageRadius = 60 * tilesize;
                                lifetime = 120;
                                despawnShake = 40;
                                despawnSound = Sounds.explosionReactor2;
                                despawnEffect = new MultiEffect(
                                    new WaveEffect(){{
                                        sizeFrom = 20;
                                        sizeTo = 60 * tilesize;
                                        strokeFrom = 40;
                                        strokeTo = 0;
                                        colorFrom = Color.valueOf("fd82ff");
                                        colorTo = Color.valueOf("7a1ba6");
                                        lifetime = 120;
                                    }},
                                    new WaveEffect(){{
                                        sides = 3;
                                        rotate = true;
                                        rotateSpeed = 40;
                                        rotation = 60;
                                        sizeFrom = 20;
                                        sizeTo = 60 * tilesize;
                                        strokeFrom = 40;
                                        strokeTo = 0;
                                        colorFrom = Color.valueOf("fd82ff");
                                        colorTo = Color.valueOf("7a1ba6");
                                        lifetime = 125;
                                    }},
                                    new WaveEffect(){{
                                        sides = 3;
                                        rotate = true;
                                        rotateSpeed = 40;
                                        rotation = 120;
                                        sizeFrom = 20;
                                        sizeTo = 60 * tilesize;
                                        strokeFrom = 40;
                                        strokeTo = 0;
                                        colorFrom = Color.valueOf("fd82ff");
                                        colorTo = Color.valueOf("7a1ba6");
                                        lifetime = 130;
                                    }},
                                        new WaveEffect(){{
                                            sides = 3;
                                            rotate = true;
                                            rotateSpeed = 40;
                                            rotation = 30;
                                            sizeFrom = 20;
                                            sizeTo = 60 * tilesize;
                                            strokeFrom = 40;
                                            strokeTo = 0;
                                            colorFrom = Color.valueOf("fd82ff");
                                            colorTo = Color.valueOf("7a1ba6");
                                            lifetime = 65;
                                        }},
                                        new WaveEffect(){{
                                            sides = 3;
                                            rotate = true;
                                            rotateSpeed = 40;
                                            rotation = 90;
                                            sizeFrom = 20;
                                            sizeTo = 60 * tilesize;
                                            strokeFrom = 40;
                                            strokeTo = 0;
                                            colorFrom = Color.valueOf("fd82ff");
                                            colorTo = Color.valueOf("7a1ba6");
                                            lifetime = 80;
                                        }},

                                    new WaveEffect(){{
                                        sides = 3;
                                        rotate = true;
                                        rotateSpeed = 40;
                                        sizeFrom = 20;
                                        sizeTo = 60 * tilesize;
                                        strokeFrom = 40;
                                        strokeTo = 0;
                                        colorFrom = Color.valueOf("fd82ff");
                                        colorTo = Color.valueOf("7a1ba6");
                                        lifetime = 160;
                                    }},
                                    new ParticleEffect(){{
                                        particles = 40;
                                        length = 60 * tilesize;
                                        sizeFrom = 12;
                                        sizeTo = 0;
                                        colorFrom = Color.valueOf("fd82ff");
                                        colorTo = Color.valueOf("7a1ba6");
                                        lifetime = 175;
                                    }},
                                    new ParticleEffect(){{
                                        particles = 30;
                                        length = 69 * tilesize;
                                        sizeFrom = 8;
                                        sizeTo = 0;
                                        colorFrom = Color.valueOf("fd82ff");
                                        colorTo = Color.valueOf("7a1ba6");
                                        lifetime = 165;
                                    }},
                                    new ParticleEffect(){{
                                        particles = 25;
                                        line = true;
                                        length = 69 * tilesize;
                                        lenFrom = 10;
                                        lenTo = 0;
                                        strokeFrom = 3;
                                        strokeTo = 1;
                                        colorFrom = Color.valueOf("fd82ff");
                                        colorTo = Color.valueOf("7a1ba6");
                                        lifetime = 185;
                                    }},
                                        new ParticleEffect(){{
                                            particles = 30;
                                            length = 69 * tilesize;
                                            sizeFrom = 8;
                                            sizeTo = 0;
                                            colorFrom = Color.valueOf("fd82ff");
                                            colorTo = Color.valueOf("7a1ba6");
                                            lifetime = 165;
                                        }},
                                        new ParticleEffect(){{
                                            particles = 25;
                                            line = true;
                                            length = 69 * tilesize;
                                            lenFrom = 10;
                                            lenTo = 0;
                                            strokeFrom = 3;
                                            strokeTo = 1;
                                            colorFrom = Color.valueOf("fd82ff");
                                            colorTo = Color.valueOf("7a1ba6");
                                            lifetime = 185;
                                        }},
                                        dsFx.smoothColorCircle( Color.valueOf("fd82ff"), 600, 600, Interp.linear)

                                );
                                fragBullets = 360;
                                fragSpread = 1;
                                fragRandomSpread = 0;
                                fragLifeMax = fragLifeMin = fragVelocityMin = fragVelocityMax = 1;
                                fragBullet = new BulletType(1, 999999999){{
                                    pierce = true;
                                    pierceBuilding = true;
                                    absorbable = false;
                                    trailLength = 60;
                                    trailWidth = 3;
                                    trailColor = Color.valueOf("db98fa");
                                    lifetime = 600;
                                    splashDamage = 300000000;
                                    splashDamageRadius = 64;
                                    despawnEffect = new WaveEffect(){{
                                        sides = 3;
                                        rotate = true;
                                        sizeFrom = 20;
                                        sizeTo = 10 * tilesize;
                                        strokeFrom = 40;
                                        strokeTo = 0;
                                        colorFrom = Color.valueOf("fd82ff");
                                        colorTo = Color.valueOf("7a1ba6");
                                        lifetime = 80;
                                    }};
                                    hitEffect = none;
                                }};
                            }};
                        }};
                    }}
            );
        }};
    }
}
