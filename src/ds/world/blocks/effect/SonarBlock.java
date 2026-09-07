package ds.world.blocks.effect;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.struct.Seq;
import ds.content.DSSounds;
import mindustry.entities.Effect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.logic.Ranged;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.tilesize;

public class SonarBlock extends Block {

    public float range = 20 * 8;

    public float reload = 600;

    public Sound sonarSound = Sounds.none;

    public Color sonarColor = Color.valueOf("ffffff");

    public Effect detectionEffect = new WaveEffect(){{
        lifetime = 120;
        sizeFrom = 4;
        sizeTo = 60;
        strokeFrom = 8;
        strokeTo = 0;
    }};

    public SonarBlock(String name) {
        super(name);
        solid = true;
        update = true;
        configurable = true;
        hasPower = true;
        canOverdrive = false;
        fogRadius = (int)range / 8;
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, sonarColor);
    }

    public class SonarBlockBuild extends Building implements Ranged{

        public Seq<Unit> detectedUnits = new Seq<>();

        public float currentTimer = 0;

        @Override
        public float range() {
            return range * potentialEfficiency;
        }

        @Override
        public void updateTile(){
            if(efficiency > 0.001f && canConsume()){

            }
        }


    }

}
