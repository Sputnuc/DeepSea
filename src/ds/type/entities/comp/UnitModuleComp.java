package ds.type.entities.comp;

import ds.type.dsUnitTypes.parts.ModuleInstance;
import ent.anno.Annotations.*;
import mindustry.gen.*;

@EntityComponent
abstract class UnitModuleComp implements Unitc{

    @SyncField(false) @SyncLocal ModuleInstance[] modules = {};

    public void setType(){

    }

}
