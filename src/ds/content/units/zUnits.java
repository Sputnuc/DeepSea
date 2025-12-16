package ds.content.units;

import arc.graphics.Color;
import ds.content.dsFx;
import ds.world.graphics.DSPal;
import ds.world.type.entities.dsUnits.FaunaUnitType;
import ds.world.type.entities.dsUnits.SubmarineUnitType;
import ds.world.type.entities.dsUnits.TorpedoUnitType;
import ds.world.type.entities.dsUnits.dsMechUnitType;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.gen.MechUnit;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

import static mindustry.Vars.tilesize;

public class zUnits {
    public static UnitType
            //Core units
            moment,
            //Assault units
            condition,
            //fauna
            untitledFish;
    public static void loadUnits(){
        moment = new SubmarineUnitType("moment"){{
            constructor = UnitEntity::create;
            outlineColor = Pal.darkOutline;
            speed = 3;
            health = 75;
            buildRange = 20 * tilesize;
            buildSpeed = 1;
            mineSpeed = 5.5f;
            mineFloor = true;
            mineWalls = true;
            mineTier = 3;
            weapons.add(new Weapon(){{
                x = 14/4f;
                y = 1;
                reload = 120;
                shootCone = 75;
                shootSound = Sounds.none;
                predictTarget = false;
                rotate = false;
                mirror = true;
                bullet = new BulletType(){{
                    instantDisappear = true;
                    spawnUnit = new TorpedoUnitType("moment-torpedo"){{
                        speed = 4;
                        lifetime = 60;
                        predictTarget = false;
                        missileAccelTime = 0;
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            shootSound = Sounds.none;
                            bullet = new ExplosionBulletType(47,3.5f * tilesize){{
                                despawnEffect = Fx.massiveExplosion;
                                buildingDamageMultiplier = 0.1f;
                            }};
                        }});
                    }};
                }};
            }});
        }};

        condition = new dsMechUnitType("condition"){{
            speed = 0.65f;
            health = 195;
            armor = 3;
            hitSize = 8;
            weapons.add(new Weapon("deepsea-condition-weapon"){{
                x = -17 / 4f; y = 2;
                reload = 15;
                rotate = false;
                mirror = true;
                top = false;
                layerOffset = -0.01f;
                bullet = new BasicBulletType(3.5f, 20){{
                    height = 14;
                    width = 10;
                    lightOpacity = 0.75f;
                    lightRadius = 5f;
                    trailInterval = 3;
                    trailEffect = dsFx.dsBulletTrail;
                    shootEffect = dsFx.dsShoot;
                    lightColor = Color.valueOf("bfe8ff");
                    frontColor = hitColor = DSPal.dsBulletFront;
                    backColor = DSPal.dsBulletBack;
                    lifetime = 45;
                    hitEffect = despawnEffect = dsFx.dsBulletHit;
                }};
            }});
        }};

        //End load
        loadFauna();
    }
    public static void loadFauna(){
        untitledFish = new FaunaUnitType("untitled-fish"){{
            speed = 1;
            health = 30;
            constructor = UnitEntity::create;
            flying = true;
        }};
    }
}
