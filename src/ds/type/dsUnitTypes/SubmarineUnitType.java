package ds.type.dsUnitTypes;

import arc.audio.Sound;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import deepsea.gen.SubmarineMovec;
import ds.content.DSFx;
import ds.world.graphics.DSPal;
import ds.type.entities.DSUnitType;
import ds.type.dsUnitTypes.parts.UnderwaterUnitEngine;
import ds.world.meta.DSEnv;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

public class SubmarineUnitType extends DSUnitType {
    public Seq<UnderwaterUnitEngine> undEngines = new Seq<>();

    public int diveEnv = DSEnv.underwaterWarm;

    public boolean doRipple = true;
    public Sound navalMoveSound = Sounds.shipMove;
    public float navalMoveSoundVolume = 1;
    public float navalMoveSoundPitchMax = 1;
    public float navalMoveSoundPitchMin = 0.85f;
    public float underwaterDrag = 0;
    public Effect submergeEffect;

    public SubmarineUnitType(String name) {
        super(name);
        fallEngineEffect = Fx.none;
        fallEffect = Fx.none;
        fallSpeed *= 0.35f;
        outlineColor = DSPal.dsUnitOutline;
        flying = true;
        engineOffset = 3;
        engineSize = 0;
        strafePenalty = 0.15f;
        accel = 1 / (hitSize * 10);
        drag = accel * 0.8f;
        underwaterDrag = accel * 0.5f;
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
        omniMovement = omni;
        emitWalkEffect = false;
    }

    public void drawUnderwater(Unit unit){
        draw(unit);
        if(unit instanceof SubmarineMovec c){
            Draw.alpha(1);
            Draw.mixcol(unit.floorOn().mapColor.write(Tmp.c1).mul(0.9f), c.submerging() * 0.65f);
            Draw.rect(region, unit.x, unit.y, unit.rotation - 90);
            Draw.reset();
        }
    }

    public void submergeEffect(Unit unit){
        if(submergeEffect == null){
            Tile tile = unit.tileOn();
            Floor floor = unit.floorOn();
            floor.walkEffect.at(unit.x, unit.y, unit.hitSize() / 8f, tile != null ? tile.getFloorColor() : floor.mapColor);
            return;
        }
        submergeEffect.at(unit.x, unit.y, unit.rotation);
    }


}
