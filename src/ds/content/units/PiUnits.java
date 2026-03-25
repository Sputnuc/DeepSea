package ds.content.units;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import ds.content.DSFx;
import ds.content.DSSounds;
import ds.content.DSStatusEffects;
import ds.world.graphics.DSPal;
import ds.type.entities.dsUnits.*;
import ds.type.entities.weapons.AdvancedLightWeapon;
import ds.type.entities.weapons.DSWeapon;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Drawf;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.*;
import static mindustry.Vars.tilesize;
import static mindustry.content.Fx.*;

public class PiUnits {
    public static float[] tierMultipliers = {1, 2f, 4f, 8f, 20f};
    public static Seq<UnitType> pi312units = new Seq<>();
    public static UnitType
            //Core units
            moment,
            //Assault units
                //Mech
            condition, oversight,
                //Tank
            note, sound,
                //Submarines
            complicity, consequences,
            //fauna

            untitledFish, angler,
            //DONT ASK
            annihilator;
    public static void loadUnits(){
        //Core units
        moment = new SubmarineUnitType("moment"){{
            setEngine(0, -5, 180, false);
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
            drawCell = false;
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
                    backColor = DSPal.dsBulletFront;
                    frontColor = Color.white;
                    despawnEffect = hitEffect = DSFx.dsBulletHit;
                    shootEffect = DSFx.dsShoot;
                    smokeEffect = none;
                    lifetime = 44;
                }};
            }});
        }};
        //Assault Submarines
        complicity = new SubmarineUnitType("complicity"){{
            setEngine(0, -5, 180, false);
            constructor = UnitEntity::create;
            speed = 1.8f;
            health = 115;
            armor = 1;
            omniMovement = true;
            hitSize = 8;
            moveSound = Sounds.none;
            weapons.add(new AdvancedLightWeapon(){{
                x = 18/4f; y = 1;
                mirror = true;
                lightCone = 80;
                lightLength = 16*tilesize;
                shootSound = Sounds.shootRetusa;
                lightTileable = false;
                reload = 120;
                rotate = false;
                shootCone = 40;
                inaccuracy = 7;
                shoot.shots = 2;
                shoot.shotDelay = 10;
                bullet = new BulletType(){{
                    instantDisappear = true;
                    keepVelocity = false;
                    speed = 0.01f;
                    spawnUnit = new TorpedoUnitType("basic-torpedo"){{
                        speed = 3.5f;
                        lifetime = 40;
                        rotateSpeed = 1.75f;
                        maxRange = 4;
                        deathSound = Sounds.explosionPlasmaSmall;
                        deathSoundVolume = 0.65f;
                        weapons.add(new DSWeapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            shootSound = Sounds.none;
                            bullet = new ExplosionBulletType(31,3.5f * tilesize){{
                                buildingDamageMultiplier = 0.75f;
                                despawnEffect = Fx.massiveExplosion;
                                damage = 0;
                            }};
                        }});
                    }};
                }};
            }});
        }};
        consequences = new SubmarineUnitType("consequences"){{
            setEngine(18/4f, -26/4f, 180, true);
            speed = 1.85f;
            health = 495;
            armor = 1 * tierMultipliers[1];
            hitSize = 14;
            constructor = UnitEntity::create;
            omniMovement = true;
            strafePenalty = 0.25f;
            weapons.add(new AdvancedLightWeapon(){{
                x = 24/4f; y = 1;
                mirror = true;
                lightCone = 32;
                lightLength = 30*tilesize;
                shootSound = Sounds.shootRetusa;
                lightTileable = false;
                reload = 60;
                rotate = false;
                shootCone = 2;
                bullet = new BulletType(){{
                    instantDisappear = true;
                    keepVelocity = false;
                    speed = 0.01f;
                    spawnUnit = new TorpedoUnitType("consequences-torpedo"){{
                        speed = 5.5f;
                        rotateSpeed = 1.5f;
                        lifetime = 50;
                        maxRange = 6f;
                        deathSound = Sounds.explosionPlasmaSmall;
                        weapons.add(new DSWeapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            shootSound = Sounds.none;
                            bullet = new ExplosionBulletType(49,4.5f * tilesize){{
                                buildingDamageMultiplier = 0.75f;
                                despawnEffect = Fx.massiveExplosion;
                                status = DSStatusEffects.waterLeak;
                                statusDuration = 300;
                                damage = 0;
                            }};
                        }});
                    }};
                }};
            }});
        }};
        //Mech
        condition = new DSMechUnitType("condition"){{
            speed = 0.38f;
            health = 195;
            armor = 3;
            hitSize = 8;
            stepSound = DSSounds.dsMechStep;
            stepSoundVolume *= 0.23f;
            weapons.add(new AdvancedLightWeapon("deepsea-condition-weapon"){{
                x = -18 / 4f; y = 5 / 4f;
                reload = 19;
                rotate = false;
                top = false;
                mirror = true;
                lightCone = 15;
                lightLength = 20 * tilesize;
                shootSound = DSSounds.shootSmallWeapon;
                shootSoundVolume = 0.45f;
                bullet = new BasicBulletType(3.5f, 22){{
                    height = 13;
                    width = 10;
                    lightOpacity = 0.75f;
                    lightRadius = 5f;
                    trailInterval = 3;
                    trailEffect = DSFx.dsBulletTrail;
                    shootEffect = DSFx.dsShoot;
                    lightColor = Color.valueOf("bfe8ff");
                    frontColor = hitColor = DSPal.dsBulletFront;
                    backColor = DSPal.dsBulletBack;
                    lifetime = 45;
                    hitEffect = despawnEffect = DSFx.dsBulletHit;
                }};
            }});
        }};
        oversight = new DSMechUnitType("oversight"){{
            speed = 0.34f;
            health = 685;
            armor = 3 * tierMultipliers[1];
            hitSize = 13;
            stepSound = DSSounds.dsMechStep;
            stepSoundPitch = 0.7f;
            stepSoundVolume *= 0.31f;
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
                        inaccuracy = 10;
                        shoot.shots = 9;
                        velocityRnd = 0.25f;
                        bullet = new BasicBulletType(8, 21){{
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
                            trailEffect = DSFx.dsBulletSparkTrail;
                        }};
                    }}
            );
        }};
        //Tanks
        note = new DSTankUnitType("note"){{
            speed = 0.29f;
            health = 380;
            armor = 6;
            hitSize = 8;
            treadRects = new Rect[] {
                    new Rect(-22f, -24f, 11, 50)
            };
            tankMoveVolume *= 0.22f;
            tankMoveSound = Sounds.tankMoveSmall;
            weapons.add(new AdvancedLightWeapon("deepsea-note-weapon"){{
                x = 0;
                y = -1;
                mirror = false;
                rotate = true;
                rotateSpeed = 1.9f;
                reload = 90;
                lightCone = 30;
                lightLength = 25 * tilesize;
                shake = 1;
                shootSound = Sounds.shootStell;
                bullet = new BasicBulletType(8f, 40){{
                    height = 13;
                    width = 9;
                    sprite = "missile-large";
                    lightOpacity = 0.85f;
                    lightRadius = 9f;
                    trailInterval = 2;
                    trailEffect = DSFx.dsBulletTrail;
                    shootEffect = DSFx.dsShootBig;
                    lightColor = Color.valueOf("bfe8ff");
                    frontColor = hitColor = DSPal.dsBulletFront;
                    backColor = DSPal.dsBulletBack;
                    trailColor = DSPal.dsBulletBack;
                    trailLength = 5;
                    trailWidth = 0.75f;
                    lifetime = 25;
                    hitEffect = despawnEffect = DSFx.dsBulletHit;
                }};
            }});
        }};
        sound = new DSTankUnitType("sound"){{
            speed = 0.26f;
            health = 895;
            armor = 8 * tierMultipliers[1];
            hitSize = 14;
            treadRects = new Rect[] {
                    new Rect(-26f, -31f, 11, 66)
            };
            treadPullOffset = 5;
            tankMoveVolume *= 0.35f;
            tankMoveSound = Sounds.tankMove;
            weapons.add(
                    new DSWeapon("deepsea-sound-light-weapon"){{
                        x = -30 / 4f; y = -20 / 4f;
                        rotate = true;
                        mirror = true;
                        reload = 25;
                        shootSound = Sounds.shootLocus;
                        bullet = new RailBulletType(){{
                            length = 17 * tilesize;
                            damage = 17;
                            hitColor = DSPal.dsBulletFront;
                            hitEffect = Fx.hitBulletColor;
                            despawnEffect = none;
                            pierceDamageFactor = 0.2f;
                            endEffect = new Effect(14f, e -> {
                                color(e.color);
                                for(int sign : Mathf.signs) {
                                    Drawf.tri(e.x, e.y, e.fout() * 1.12f, 5f + 9 * e.fin(), e.rotation + 15 * sign);
                                }
                            });
                            shootEffect = new Effect(10, e ->{
                                color(e.color);
                                Drawf.tri(e.x, e.y, 1.25f+  1.25f * e.fout(Interp.circleOut), 20f * e.fout(), e.rotation);
                                color(e.color);
                                for(int sign : Mathf.signs){
                                    Drawf.tri(e.x, e.y, 0.85f +  1.25f * e.fout(), 18f * e.fout(), e.rotation + sign * 90f);
                                }
                            });
                            lineEffect = new Effect(20, e->{
                                if(!(e.data instanceof Vec2 v)) return;

                                color(e.color);
                                stroke(e.fout() * 0.8f + 0.4f);
                                Fx.rand.setSeed(e.id);
                                for(int i = 0; i < 7; i++){
                                    Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                                    Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);

                                    e.scaled(14f, b -> {
                                        stroke(b.fout() * 1.1f);
                                        color(e.color);
                                        Lines.line(e.x, e.y, v.x, v.y);
                                    });
                                }
                            });
                        }};
                    }},
                    new AdvancedLightWeapon("deepsea-sound-weapon"){{
                        x = 0; y = 0;
                        mirror = false;
                        rotate = true;
                        rotateSpeed = 1.6f;
                        reload = 180;
                        lightCone = 35;
                        lightLength = 28 * tilesize;
                        recoil = 3;
                        shootSound = DSSounds.shootMediumTank;
                        shootY = 30 / 4f;
                        shake = 4;
                        bullet = new BasicBulletType(9, 96){{
                            lifetime = 28 * tilesize / 9f;
                            width = 8;
                            height = 16;
                            sprite = "missile-large";
                            shootEffect = DSFx.dsShootTank;
                            lightColor = Color.valueOf("bfe8ff");
                            frontColor = hitColor = DSPal.dsBulletFront;
                            backColor = DSPal.dsBulletBack;
                            trailColor = DSPal.dsBulletBack;
                            pierce = true;
                            pierceBuilding = true;
                            pierceDamageFactor = 0.4f;
                            despawnEffect = new MultiEffect(
                                    DSFx.dsBulletHit,
                                    hitSquaresColor,
                                    circleColorSpark
                            );
                            hitEffect = new Effect(15, e->{
                                color(e.color);
                                for(int sign : Mathf.signs){
                                    Drawf.tri(e.x, e.y, 3.45f * e.fout(Interp.circleOut), 3 + 12 * e.fin(), e.rotation - 75 * sign);
                                }
                                Drawf.tri(e.x,e.y, 5 * e.fout(Interp.circleOut), 17 * e.fin(), e.rotation);
                                color(e.color, e.fin());
                                stroke(e.fout() * 0.65f);
                                Lines.circle(e.x, e.y, 9 * e.fin(Interp.circleOut));
                                randLenVectors(e.id, 8, 19 * e.fin(), e.rotation, 55, (x, y)->{
                                    Fill.circle(e.x + x, e.y + y, e.fin() * 0.75f);
                                });
                                Drawf.light(e.x, e.y, 20, e.color, e.fout(Interp.circleOut));
                            });
                            hitShake = 3;
                            despawnShake = 3;
                            trailEffect = new ParticleEffect(){{
                                trailRotation = true;
                                cone = 12.5f;
                                length = -16;
                                lifetime = 15;
                                particles = 3;
                                line = true;
                                lenFrom = 3;
                                lenTo = 5.85f;
                                interp = Interp.linear;
                                sizeInterp = Interp.circleOut;
                                strokeFrom = 1.75f;
                                strokeTo = 0;
                                colorFrom = DSPal.dsBulletFront;
                                colorTo = DSPal.dsBulletBack;
                            }};
                            trailLength = 8;
                            trailWidth = 1.55f;
                            lightOpacity = 1.95f;
                            lightRadius = 15f;
                            trailInterval = 1f;
                        }};
                    }}
            );
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
            loopSound = DSSounds.loopAngler;
            attackSound = DSSounds.loopAnglerAttack;
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
                            DSFx.smoothColorCircle(Color.valueOf("ffffff"), 70, 25, Interp.pow3Out)
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
                                        DSFx.smoothColorCircle( Color.valueOf("fd82ff"), 600, 600, Interp.linear)

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
