package ds.type.entities.dsUnits.parts;

import arc.util.Log;
import arc.util.Time;
import mindustry.gen.Unit;

//Additional class for UnitEntities & UnderWaterUnitEngine
public class EngineInstance {
    public UnderwaterUnitEngine engine;
    private float count;
    public EngineInstance(UnderwaterUnitEngine engine){
        this.engine = engine;
    }

    public void update(Unit unit, float eff){

        count += Time.delta * eff;
        if(count >= engine.engineEffectInterval) {
            engine.update(unit);
            count = 0;
        }
    }
}
