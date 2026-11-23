package ds.type;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.graphics.g3d.PlanetGrid;
import mindustry.type.Planet;
import mindustry.type.Sector;

public class DSSector extends Sector {

    public static String[] threats = {"clear", "low", "moderate", "high", "extreme", "insane", "final"};

    public DSSector(Planet planet, PlanetGrid.Ptile tile) {
        super(planet, tile);
    }

    @Override
    public String displayThreat(){
        float step = 1f / threats.length;
        String color = Tmp.c1.set(Color.white).lerp(Color.red, Mathf.round(this.threat, step)).toString();
        int index = Math.min((int)(this.threat / step), threats.length - 1);
        return "[#" + color + "]" + Core.bundle.get("threat." + threats[index]);
    }
}
