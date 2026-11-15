package ds.type.dsUnitTypes;

import arc.audio.Sound;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Tmp;
import deepsea.gen.SubmarineMovec;
import deepsea.gen.TorpedoUnit;
import deepsea.gen.TorpedoUnitc;
import ds.type.dsUnitTypes.parts.UnderwaterUnitEngine;
import ds.world.meta.DSEnv;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

public class TorpedoUnitType extends MissileUnitType {

    public boolean doRipple = true;
    public Sound navalMoveSound = Sounds.shipMove;
    public float navalMoveSoundVolume = 1;
    public float navalMoveSoundPitchMax = 1;
    public float navalMoveSoundPitchMin = 0.85f;
    public float underwaterDrag = 0;
    public Effect submergeEffect;

    public Seq<UnderwaterUnitEngine> undEngines = new Seq<>();
    public TorpedoUnitType(String name) {
        super(name);
        constructor = TorpedoUnit::create;
        health = 40;
        trailLength = 0;
        engineSize = 0;
        lowAltitude = true;
        engineOffset = 0;
        loopSound = Sounds.none;
        outlineColor = Pal.darkOutline;
        setUndEngines(0, -2, 180, false);
    }

    @Override
    public void init(){
        super.init();
        emitWalkEffect = false;
    }

    public void setUndEngines(float x, float y, float rotation, boolean mirror) {
        if (!mirror) {
            undEngines.add(new UnderwaterUnitEngine(x, y, rotation) {{
                engineEffectChance = 0.25f;
            }});
        } else {
            for (int s : Mathf.signs) {
                undEngines.add(new UnderwaterUnitEngine(x * s, y, rotation * s) {{
                    engineEffectChance = 0.25f;
                }});
            }
        }
    }


    public void drawUnderwater(Unit unit){
        draw(unit);
        if(unit instanceof TorpedoUnitc c){
            Draw.alpha(1);
            Draw.mixcol(unit.floorOn().mapColor.write(Tmp.c1).mul(0.9f), 0.65f);
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
