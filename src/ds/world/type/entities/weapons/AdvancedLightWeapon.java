package ds.world.type.entities.weapons;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.Weapon;
import mindustry.world.Tile;
import ds.world.draw.*;

import static ds.world.draw.DrawDirLight.*;
import static mindustry.Vars.tilesize;

public class AdvancedLightWeapon extends Weapon {
    public float lightLength = 180;
    public float rayWidth = 10;
    public float lightCone = 90;
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
        drawLight(unit, mount);
    }

    public void drawLight(Unit unit , WeaponMount mount){
        if (Vars.state.rules.lighting){
            float mountX = unit.x + Angles.trnsx(unit.rotation - 90, x, y);
            float mountY = unit.y + Angles.trnsy(unit.rotation - 90, x, y);
            float weaponRotation = unit.rotation - 90 + (rotate ? mount.rotation : baseRotation);
            float wX = mountX + Angles.trnsx(weaponRotation, this.shootX, this.shootY);
            float wY = mountY + Angles.trnsy(weaponRotation, this.shootX, this.shootY);
            DrawDirLight.DrawLightBeam(wX, wY, mount.rotation + unit.rotation(), lightLength, lightCone, rayWidth);
        }
    }
}
