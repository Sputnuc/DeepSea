package ds.type.entities.comp;

import deepsea.gen.SubmarineEnginec;
import ent.anno.Annotations;
import mindustry.gen.Entityc;
import mindustry.gen.TimedKillc;
import mindustry.gen.Unitc;

@Annotations.EntityComponent
abstract class TorpedoUnitComp implements TimedKillc, SubmarineEnginec, Unitc {
}
