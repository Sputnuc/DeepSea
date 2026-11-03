package ds.type.entities.comp;

import deepsea.gen.SubmarineEnginec;
import deepsea.gen.SubmarineMovec;
import ent.anno.Annotations;
import mindustry.gen.Weaponsc;
import mindustry.type.UnitType;

@Annotations.EntityComponent
abstract class SubmarineUnitComp implements SubmarineMovec, SubmarineEnginec {

    @Annotations.Import boolean disarmed;
    @Annotations.Import UnitType type;

    @Override
    @Annotations.Replace(100)
    public boolean canShoot(){
        return !disarmed && !(type.canBoost && isFlying()) && !submerged();
    }
}
