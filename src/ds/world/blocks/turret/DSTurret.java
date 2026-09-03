package ds.world.blocks.turret;

import arc.math.Mathf;
import ds.draw.DrawDirLight;
import mindustry.Vars;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class DSTurret extends Turret {
    public DSTurret(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.reload);
        stats.add(Stat.reload, (60f / (reload + (!reloadWhileCharging ? shoot.firstShotDelay : 0f))  + " (x"+shoot.shots+")"), StatUnit.perSecond);
    }

    public boolean emitDirectLight = true;
    public float lightLength;
    public float lightCone;
    public float lightOffsetX, lightOffsetY;

    @Override
    public void init(){
        super.init();
        lightLength = Mathf.zero(lightLength) ? range : lightLength;
        lightCone = Mathf.zero(lightCone) ? shootCone * 3 : lightCone;
    }

    public class DSTurretBuild extends TurretBuild{

        @Override
        public void updateTile(){
            super.updateTile();
        }

        @Override
        public void draw(){
            super.draw();
        }
    }
}
