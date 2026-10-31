package ds.type.dsUnitTypes;

import arc.audio.Sound;
import arc.math.Mathf;
import arc.struct.Seq;
import ds.world.graphics.DSPal;
import ds.type.entities.DSUnitType;
import ds.type.dsUnitTypes.parts.UnderwaterUnitEngine;
import ds.world.meta.DSEnv;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;

public class SubmarineUnitType extends DSUnitType {
    public Seq<UnderwaterUnitEngine> undEngines = new Seq<>();

    public int diveEnv = DSEnv.underwaterWarm;

    public Sound navalMoveSound = Sounds.shipMove;
    public float navalMoveSoundVolume = 1;
    public float navalMoveSoundPitchMax = 1;
    public float navalMoveSoundPitchMin = 0.85f;
    public float underwaterDrag = 0;

    public SubmarineUnitType(String name) {
        super(name);
        fallEngineEffect = Fx.none;
        fallEffect = Fx.none;
        fallSpeed *= 0.35f;
        outlineColor = DSPal.dsUnitOutline;
        flying = true;
        engineOffset = 3;
        engineSize = 0;
        accel = 1 / (hitSize * 10);
        drag = accel * 0.8f;
        underwaterDrag = accel * 0.195f;
    }

    public void setUndEngines(float x, float y, float rotation, boolean mirror){
        if(!mirror){
            undEngines.add(new UnderwaterUnitEngine(x, y, rotation));
        }else {
            for (int s : Mathf.signs){
                undEngines.add(new UnderwaterUnitEngine(x * s, y, rotation * s));
            }
        }
    }

    @Override
    public void init(){
        boolean omni = omniMovement; // то, что выставлено в конструкторе/DSUnits
        super.init();
        omniMovement = omni;         // ванильный water preset его сбрасывает
    }

    public void drawUnderwater(Unit unit){
        draw(unit);
    }
}
