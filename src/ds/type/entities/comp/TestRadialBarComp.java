package ds.type.entities.comp;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import ds.wprocesses.UnitBarProcess;
import ent.anno.Annotations.*;
import mindustry.gen.Drawc;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;

@EntityComponent
public abstract class TestRadialBarComp implements Drawc, Unitc {

    @Import float x, y;

    @Override
    public void setType(UnitType type){
        UnitBarProcess.addBar(self());
    }

    public void drawRadial(float pr){
        Draw.z(Layer.overlayUI);
        Draw.color(Color.black);
        Draw.alpha(0.55f);
        Fill.circle(x, y - hitSize() - 9, 6);
        Lines.stroke(1f);
        Draw.color(Pal.health);
        Lines.arc(x, y -hitSize() - 9, 5, pr);
        Draw.color(Color.white);
        Lines.arc(x, y - hitSize() - 9, 5, healthf());
    }
}
