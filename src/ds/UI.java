package ds;

import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.scene.Element;
import arc.scene.Group;
import arc.util.Reflect;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.graphics.g3d.PlanetParams;

public class UI {
    public static void loadCustomUi(){
        Element menu = ((Element) Reflect.get(Vars.ui.menufrag, "container")).parent.parent;
        Group menuCont = menu.parent;
        menuCont.addChildBefore(menu, new Element() {
            public final PlanetParams params = new PlanetParams() {{
                planet = Planets.sun;
                camPos = new Vec3(Mathf.cosDeg(Time.time), 0f, Mathf.sinDeg(Time.time)).nor();
                zoom = 4f;
            }};
        });
    }
}

