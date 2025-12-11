package ds.content.units;

import ds.world.type.entities.dsUnits.FaunaUnitType;
import ds.world.type.entities.dsUnits.SubmarineUnitType;
import ds.world.type.entities.dsUnits.TorpedoUnitType;
import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

import static mindustry.Vars.tilesize;

public class zUnits {
    public static UnitType
            //Core units
            moment,
            //fauna
            untitledFish;
    public static void loadUnits(){
        moment = new SubmarineUnitType("moment"){{
            constructor = UnitEntity::create;
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
                            bullet = new ExplosionBulletType(20,3.5f * tilesize){{
                                despawnEffect = Fx.massiveExplosion;
                                buildingDamageMultiplier = 0.1f;
                            }};
                        }});
                    }};
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
