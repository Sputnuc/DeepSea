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
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.bullet.PointBulletType;
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
    public static Seq<UnitType> pi312units = new Seq<>();
    public static UnitType
            //Core units
            moment,
            //Assault units
                //Mech
            condition,
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
            targetable = false;
            speed = 3;
            health = 75;
            buildRange = 20 * tilesize;
            buildSpeed = 2.75f;
            mineSpeed = 7.5f;
            mineFloor = true;
            mineWalls = true;
            mineTier = 3;
            weapons.add(new AdvancedLightWeapon(){{
                x = 0;
                y = 1;
                shootCone = 0;
                lightCone = 35;
                lightLength = 22 * tilesize;
                lightTileable = false;
                shootSound = Sounds.none;
                rotate = false;
                mirror = false;
                reload = 1;
                bullet = new BulletType(){{
                    shootEffect = despawnEffect = hitEffect  = smokeEffect = none;
                    instantDisappear = true;
                }};
                display = false;
                noAttack = true;
                predictTarget = false;
            }});
        }};
        //Assault Submarines
        complicity = new SubmarineUnitType("complicity"){{
            constructor = UnitEntity::create;
            speed = 2.85f;
            health = 175;
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
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            shootSound = Sounds.none;
                            bullet = new ExplosionBulletType(25,3.5f * tilesize){{
                                despawnEffect = Fx.massiveExplosion;
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
        //Tanks
        note = new dsTankUnitType("note"){{
            speed = 0.29f;
            health = 450;
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
            health = 4900;
            loopSound = dsSounds.loopAngler;
            attackSound = dsSounds.loopAnglerAttack;
            loopShakeIntensity = 2;
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
