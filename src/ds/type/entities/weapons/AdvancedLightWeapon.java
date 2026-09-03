package ds.type.entities.weapons;

import arc.math.Angles;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.entities.units.WeaponMount;
import mindustry.game.FogControl;
import mindustry.gen.FogEvent;
import mindustry.gen.Unit;
import ds.draw.*;
import mindustry.world.Block;

public class AdvancedLightWeapon extends DSWeapon {
    public float lightLength = 180;
    public float rayWidth = 10;
    public float lightCone = 90;
    public boolean dispelsFog = true;
    public boolean lightTileable = true;

    public AdvancedLightWeapon(String name){
        super(name);
    }
    public AdvancedLightWeapon(){
        super();
    }

    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);
        if (Vars.state.rules.lighting) drawLight(unit, mount);
    }

    @Override
    public void draw(Unit unit, WeaponMount mount){
        super.draw(unit, mount);
        if(Vars.state.isPaused()) drawLight(unit, mount);
    }

    public void drawLight(Unit unit , WeaponMount mount){
            float mountX = unit.x + Angles.trnsx(unit.rotation - 90, x, y);
            float mountY = unit.y + Angles.trnsy(unit.rotation - 90, x, y);
            float weaponRotation = unit.rotation - 90 + (rotate ? mount.rotation : baseRotation);
            float wX = mountX + Angles.trnsx(weaponRotation, this.shootX, this.shootY);
            float wY = mountY + Angles.trnsy(weaponRotation, this.shootX, this.shootY);
            if (lightTileable) {
                DrawDirLight.DrawLightBeam(wX, wY, mount.rotation + unit.rotation(), lightLength, lightCone, rayWidth);
            } else {
                DrawDirLight.DrawLightBeamNonTileable(wX, wY, mount.rotation + unit.rotation(), lightLength, lightCone, rayWidth);
            }

    }
}
