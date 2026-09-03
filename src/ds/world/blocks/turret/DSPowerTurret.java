package ds.world.blocks.turret;

import arc.math.Mathf;
import arc.struct.ObjectMap;
import ds.draw.DrawDirLight;
import ds.world.meta.DSStatValues;
import mindustry.Vars;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class DSPowerTurret extends PowerTurret {
    public DSPowerTurret(String name) {
        super(name);
    }

    public boolean emitDirectLight = true;
    public float lightLength;
    public float lightCone;
    public float lightOffsetX, lightOffsetY;

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, DSStatValues.ammo(ObjectMap.of(this, shootType)));
        stats.remove(Stat.reload);
        stats.add(Stat.reload, (60f / (reload + (!reloadWhileCharging ? shoot.firstShotDelay : 0f))  + " (x"+shoot.shots+")"), StatUnit.perSecond);
    }

    @Override
    public void init(){
        super.init();
        lightLength = Mathf.zero(lightLength) ? range : lightLength;
        lightCone = Mathf.zero(lightCone) ? shootCone * 3: lightCone;
    }

    public class DSPowerTurretBuild extends PowerTurretBuild{
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
    }
}
