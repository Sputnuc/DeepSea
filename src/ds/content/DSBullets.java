package ds.content;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Interp;
import arc.math.Mathf;
import ds.type.entities.bullets.AdvArtilleryBulletType;
import ds.type.entities.bullets.AirClusterBulletType;
import ds.world.graphics.DSPal;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.HaloPart;
import mindustry.entities.pattern.ShootPattern;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;

import static mindustry.Vars.tilesize;

public class DSBullets {
    public static BulletType
            smallApBullet, smallExBullet, smallSdBullet,

            smallBackshotBullet, smallBackshosClusterBullet,

            aaApBullet, aaUfBullet, aaSdBullet,

            mediumMortarHe, mediumMortarCluster
    ;

    public static void load(){
        smallApBullet = new BasicBulletType(8, 13){{
            lifetime = 22;
            height = 12;
            width = 7.75f;
            hitEffect = despawnEffect = DSFx.dsBulletHit;
            hitColor = frontColor = DSPal.dsBulletFront;
            backColor = DSPal.dsBulletBack;
            shootEffect = DSFx.dsShoot;
            ammoMultiplier = 1;
            hitShake = 0.75f;
            armorMultiplier = 0.85f;
        }};
        smallExBullet = new BasicBulletType(7.04f, 9){{
            splashDamage = 13;
            splashDamageRadius = 17;
            lifetime = 25;
            height = 14;
            width = 8f;
            hitEffect = new MultiEffect(DSFx.dsBulletHit, new ExplosionEffect(){{
                waveColor = sparkColor = DSPal.dsBulletFront;
                waveRad = 16;
                sparkRad = 19;
                sparkStroke = 0.75f;
                smokeSize = 1.85f;
                smokes = 4;
            }});
            despawnEffect = Fx.none;
            hitColor = frontColor = DSPal.dsBulletFront;
            backColor = DSPal.dsBulletBack;
            shootEffect = DSFx.dsShoot;
            trailEffect = DSFx.dsBulletTrail;
            trailInterval = 1.25f;
            ammoMultiplier = 1;
            reloadMultiplier = 0.85f;
            hitShake = 1.34f;
            armorMultiplier = 1.15f;
        }};
        smallSdBullet = new BasicBulletType(8, 19.5f){{
            lifetime = 24;
            height = 13;
            width = 8.85f;
            hitEffect = despawnEffect = DSFx.dsBulletHit;
            hitColor = frontColor = Color.valueOf("ffcca8");
            backColor = Color.valueOf("e88f74");
            shootEffect = DSFx.dsShoot;
            ammoMultiplier = 1;
            hitShake = 1.75f;
            pierce = true;
            pierceCap = 2;
            pierceBuilding = true;
            armorMultiplier = 0.45f;
            rangeChange = 16;
            reloadMultiplier = 0.75f;
            absorbable = false;
        }};
        smallBackshotBullet = new BasicBulletType(12.4f,15){{
            pierce = true;
            pierceCap = 2;
            lifetime = 11;
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
            shootEffect = smokeEffect = Fx.none;
        }};
        smallBackshosClusterBullet = new BasicBulletType(8,25){{
            shootPattern = new ShootPattern(){{
                shots = 1;
            }};
            reloadMultiplier = 0.75f;
            pierce = true;
            pierceCap = 3;
            lifetime = 8;
            rangeChange = 5 * tilesize;
            trailLength = 6;
            trailWidth = 3;
            width = 15;
            height = 18;
            frontColor = hitColor = Color.valueOf("ffc7bf");
            backColor = trailColor = Color.valueOf("d98e84");
            hitEffect = despawnEffect = Fx.hitBulletColor;
            trailInterval = 2;
            trailEffect = DSFx.dsBulletSparkTrail;
            ammoMultiplier = 1;
            fragVelocityMin = 0.9f;
            fragVelocityMax = 1;
            fragSpread = 2.25f;
            fragRandomSpread = 1.5f;
            fragBullets = 10;
            pierceFragCap = 2;
            fragBullet = new BasicBulletType(11.2f, 19){{
                lifetime = 10;
                rangeChange = 5 * tilesize;
                trailLength = 4;
                trailWidth = 0.6f;
                width = 5;
                height = 12;
                frontColor = hitColor = Color.valueOf("ffc7bf");
                backColor = trailColor = Color.valueOf("d98e84");
                hitEffect = despawnEffect = Fx.hitBulletColor;
                trailInterval = 2;
                trailEffect = DSFx.dsBulletSparkTrail;
            }};
        }};
        aaApBullet = new BasicBulletType(15,28f){{
            pierce = true;
            pierceCap = 3;
            lifetime = 16;
            ammoMultiplier = 2;
            frontColor = hitColor = Color.valueOf("f5d9ff");
            backColor = trailColor = Color.valueOf("cea4de");
            trailEffect = DSFx.dsColorSparkSmall;
            trailInterval = 1;
            trailRotation = true;
            trailLength = 7;
            trailWidth = 1.25f;
            height = 17;
            width = 13;
            shootEffect = Fx.shootBig;
            hitEffect = Fx.hitBulletColor;
            despawnEffect = Fx.hitBulletColor;
            collidesGround = false;
        }};
        aaUfBullet = new BasicBulletType(24,31){{
            armorMultiplier = 1.15f;
            pierce = true;
            pierceCap = 2;
            lifetime = 12;
            ammoMultiplier = 2;
            rangeChange = 48;
            reloadMultiplier = 2f;
            frontColor = hitColor = Color.valueOf("edfdff");
            backColor = trailColor = Color.valueOf("bce3e8");
            trailEffect = DSFx.dsColorSparkSmall;
            trailInterval = 1;
            trailRotation = true;
            trailLength = 7;
            trailWidth = 1.25f;
            height = 19;
            width = 13;
            shootEffect = Fx.shootBig;
            hitEffect = Fx.hitBulletColor;
            despawnEffect = Fx.hitBulletColor;
            collidesGround = false;
        }};
        aaSdBullet = new BasicBulletType(10,38){{
            lifetime = 24;
            ammoMultiplier = 2;
            frontColor = hitColor = Color.valueOf("ab7272");
            backColor = trailColor = Color.valueOf("824d4d");
            trailEffect = DSFx.dsColorSparkSmall;
            trailInterval = 2;
            trailRotation = true;
            reloadMultiplier = 0.25f;
            trailLength = 7;
            trailWidth = 1.25f;
            height = 17;
            width = 13;
            shootEffect = Fx.shootBig;
            hitEffect = Fx.hitBulletColor;
            despawnEffect = Fx.hitBulletColor;
            status = DSStatusEffects.waterLeak;
            statusDuration = 60;
            collidesGround = false;
        }};
        mediumMortarHe = new AdvArtilleryBulletType(3f, 40, "missile-large"){{
            shieldDamageMultiplier = 0.45f;
            buildingDamageMultiplier = 0.15f;
            armorMultiplier = 1.15f;
            lifetime = 120;
            height = 18;
            width = 10;
            shrinkY = 0.75f;
            hitSound = Sounds.explosionDull;
            frontColor = hitColor = DSPal.dsBulletFront;
            backColor = trailColor = DSPal.dsBulletBack;
            hitEffect = new MultiEffect(
                new ExplosionEffect(){{
                    waveColor = Color.white;
                    sparkColor = DSPal.dsBulletFront;
                    waveRad =  50;
                    sparkRad = 59;
                    sparkLen = 8;
                    smokeRad = 37;
                    smokes = 9;
                    sparks = 5;
                    lifetime = 40;
                }},
                DSFx.spikesEffect(DSPal.dsBulletFront, 48, 4, 2.75f, 1f, 35, 4, 360),
                DSFx.spikesEffect(DSPal.dsBulletFront, 13, 2, 1, 0.1f, 25, 13, 360)
            );
            trailEffect = new MultiEffect(DSFx.dsFastSparkTrail, new Effect(10, e->{
                Draw.color(e.color);
                for(int sign : Mathf.signs){
                    Drawf.tri(e.x, e.y, 1.5f + e.fout(), e.fout() * 7, e.rotation + 90 * sign);
                }
            }));

            parts.add(
                    new HaloPart(){{
                        y = -4.5f;
                        rotateSpeed = 0;
                        haloRadius = 1;
                        haloRadiusTo = 1;
                        radius = 3;
                        radiusTo = 4;
                        tri = true;
                        triLength = 3;
                        triLengthTo = 5;
                        progress = PartProgress.life.curve(Interp.slope);
                        shapes = 2;
                        haloRotation = 90;
                        color = colorTo = DSPal.dsBulletFront;
                        moveY = -2;
                    }}
            );
            trailRotation = true;
            trailLength = 9;
            trailWidth = 1.9f;
            hitShake = 18;
            splashDamage = 150;
            splashDamageRadius = 48;
        }};
        mediumMortarCluster = new AirClusterBulletType(4f, 0, "missile-large"){{
            scaleLife = false;
            splashDamage = 0;
            lifetime = 32;
            height = 18;
            width = 10;
            shrinkY = 0.75f;
            shadowIncreaseFactor = 0.2f;
            hitSound = Sounds.explosionMissile;
            frontColor = hitColor = Color.valueOf("ffc7bf");
            backColor = trailColor = Color.valueOf("d98e84");
            hitEffect = new MultiEffect(
                    new ExplosionEffect(){{
                        waveColor = Color.white;
                        sparkColor = Color.valueOf("ffc7bf");
                        waveRad =  80;
                        sparkRad = 74;
                        sparkLen = 5;
                        smokeRad = 39;
                        smokes = 14;
                        sparks = 18;
                        lifetime = 40;
                    }}
            );
            trailEffect = new Effect(10, e->{
                Draw.color(e.color);
                for(int sign : Mathf.signs){
                    Drawf.tri(e.x, e.y, 1.5f + e.fin(Interp.slope), e.fin(Interp.slope) * 9, e.rotation + 90 * sign);
                }
            });

            parts.add(
                    new HaloPart(){{
                        y = -4.5f;
                        rotateSpeed = 0;
                        haloRadius = 1;
                        haloRadiusTo = 1;
                        radius = 3;
                        radiusTo = 4;
                        tri = true;
                        triLength = 3;
                        triLengthTo = 5;
                        progress = PartProgress.life;
                        shapes = 2;
                        haloRotation = 90;
                        color = colorTo = Color.valueOf("ffc7bf");
                        moveY = -2;
                    }}
            );
            trailRotation = true;
            trailLength = 9;
            trailWidth = 1.9f;
            hitShake = 5;
            shrinkY = 0.75f;
            fragBullets = 10;
            fragRandomSpread = 20;
            fragLifeMax = 1.2f;
            fragLifeMin = 0.3f;
            fragVelocityMax = 1.1f;
            fragVelocityMin = 0.95f;
            tragectoryInterp = Interp.pow2Out;
            fragBullet = new AirClusterBulletType(3.45f, 27, "shell"){{
                drag = 0.01f;
                tragectoryInterp = Interp.pow2Out;
                armorMultiplier = 1.75f;
                width = 8;
                height = 12;
                shadowIncreaseFactor = 0.2f;
                lifetime = 80;
                splashDamage = 65;
                splashDamageRadius = 29;
                landing = true;
                trailLength = 4;
                trailWidth = 1.9f;
                hitShake = 8;
                hitSound = Sounds.explosionDull;
                hitSoundVolume = 0.65f;
                hitSoundPitch = 1.05f;
                frontColor = hitColor = Color.valueOf("ffc7bf");
                backColor = trailColor = Color.valueOf("d98e84");
                hitEffect = new MultiEffect(
                        new ExplosionEffect(){{
                            waveColor = Color.white;
                            sparkColor = Color.valueOf("ffc7bf");
                            waveRad =  30;
                            sparkRad = 19;
                            sparkLen = 8;
                            smokeRad = 37;
                            smokes = 3;
                            sparks = 5;
                            lifetime = 40;
                        }},
                        DSFx.spikesEffect(Color.valueOf("ffc7bf"), 18, 3, 1.75f, 0.75f, 25, 3, 360)
                );
            }};
        }};
    }
}
