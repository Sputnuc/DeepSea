package ds.type.dsUnitTypes.parts;

import arc.util.Time;
import deepsea.gen.SubmarineMovec;
import mindustry.gen.Unit;

//Additional class for UnitEntities & UnderWaterUnitEngine
public class EngineInstance {
    public UnderwaterUnitEngine engine;
    private float count;
    public EngineInstance(UnderwaterUnitEngine engine){
        this.engine = engine;
        count = 0;
    }

    public void update(Unit unit, float eff){
        if(unit instanceof SubmarineMovec dive && !dive.diving()) return;
        count += Time.delta * eff;

        if(count >= engine.engineEffectInterval) {
            count %= engine.engineEffectInterval;
            engine.update(unit, eff);
        }
    }
}
