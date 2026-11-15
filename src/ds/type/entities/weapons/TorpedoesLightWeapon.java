package ds.type.entities.weapons;

import arc.math.Angles;
import deepsea.gen.SubmarineUnitc;
import ds.draw.DrawDirLight;
import mindustry.Vars;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

public class TorpedoesLightWeapon extends TorpedoesWeapon{
    public float lightLength = 180;
    public float rayWidth = 10;
    public float lightCone = 90;
    public boolean dispelsFog = true;
    public boolean lightTileable = true;

    public TorpedoesLightWeapon(String name){
        super(name);
    }
    public TorpedoesLightWeapon(){
        super();
    }

    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);
        if(unit instanceof SubmarineUnitc s && !s.submerged()) {
            if (Vars.state.rules.lighting) drawLight(unit, mount);
        }
    }

    @Override
    public void draw(Unit unit, WeaponMount mount){
        super.draw(unit, mount);
        if(unit instanceof SubmarineUnitc s && !s.submerged()) {
            if (Vars.state.isPaused() && Vars.state.rules.lighting) drawLight(unit, mount);
        }
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
