package ds.world.blocks.turret;

import arc.Core;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Strings;
import ds.draw.DrawDirLight;
import ds.world.meta.DSStatValues;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class DSItemTurret extends ItemTurret {
    public DSItemTurret(String name) {
        super(name);
    }

    public boolean emitDirectLight = true;
    public float lightLength;
    public float lightCone;
    public float lightOffsetX, lightOffsetY;
    public Effect ejectEffect = Fx.none;

    public Effect shootEffect = Fx.none;
    public Effect smokeEffect = Fx.none;
    public float ejectX = 0, ejectY = 0, ejectRotation = 0;

    //X, Y, Rotation
    @Nullable
    public float[] ejectPositions = {};

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, DSStatValues.ammo(ammoTypes));
        stats.remove(Stat.reload);
        stats.add(Stat.reload, (60f / (reload + (!reloadWhileCharging ? shoot.firstShotDelay : 0f))  + " (x"+shoot.shots+")"), StatUnit.perSecond);
    }

    @Override
    public void init(){
        super.init();
        lightLength = Mathf.zero(lightLength) ? range : lightLength;
        lightCone = Mathf.zero(lightCone) ? shootCone * 3 : lightCone;
    }

    public class DSItemTurretBuild extends  ItemTurretBuild{
        public int curShot = 0;

        @Override
        public void updateTile(){
            super.updateTile();
            if(!Vars.state.isPaused() && hasAmmo() && emitDirectLight){
                DrawDirLight.DrawLightBeamNonTileable(x, y, rotation, lightLength, lightCone, 10);
            }
        }

        @Override
        public void draw(){
            super.draw();
            if(Vars.state.isPaused() && hasAmmo() && emitDirectLight){
                DrawDirLight.DrawLightBeamNonTileable(x, y, rotation, lightLength, lightCone, 10);
            }
        }

        @Override
        protected void shoot(BulletType type){
            super.shoot(type);
            if(ejectPositions != null && ejectPositions.length >= 3){
                int i = curShot * 3;
                float
                        ejX = x + Angles.trnsx(rotation - 90, ejectPositions[i], ejectPositions[i+1]),
                        ejY = y + Angles.trnsy(rotation - 90, ejectPositions[i], ejectPositions[i+1]);
                ejectEffect.at(ejX, ejY, rotation + ejectPositions[i+2]);
                curShot = (curShot + 1) % Math.abs(ejectPositions.length / 3);
            } else{
                float
                        ejX = x + Angles.trnsx(rotation - 90, ejectX, ejectY),
                        ejY = y + Angles.trnsy(rotation - 90, ejectX, ejectY);
                ejectEffect.at(ejX, ejY, rotation + ejectRotation);
            }

            float
                    bulletX = x + Angles.trnsx(rotation - 90, shootX, shootY),
                    bulletY = y + Angles.trnsy(rotation - 90, shootX, shootY);
            if(type.shootEffect == Fx.none){
                shootEffect.at(bulletX, bulletY, rotation);
            }
            if(type.smokeEffect == Fx.none){
                smokeEffect.at(bulletX, bulletY, rotation);
            }
        }
    }
}
