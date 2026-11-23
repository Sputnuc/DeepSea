package ds;

import arc.graphics.g2d.Font;
import arc.graphics.g2d.GlyphLayout;
import arc.scene.actions.Actions;
import arc.scene.event.Touchable;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Time;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;

import static mindustry.Vars.ui;

public class UI {
    static Table list;
    static final float maxWidth = 300f, fade = 0.4f;

    public static void init(){
        Table root = new Table();
        root.setFillParent(true);
        root.touchable = Touchable.disabled;
        root.visible(() -> ui.hudfrag.shown && Vars.state.isGame());
        root.left().marginLeft(8f);

        list = new Table();
        list.left();
        root.add(list).left();
        ui.hudGroup.addChild(root);
    }

    public static void message(String full, float cps, float hold){
        if(list == null) return;

        String text = wrap(full, maxWidth);
        int total = text.length();
        boolean instant = cps <= 0f;

        Table msg = new Table(Styles.black6);
        msg.margin(8f);
        Label label = new Label("", Styles.outlineLabel);
        label.setWrap(false); // width follows currently visible text
        label.setAlignment(Align.left);
        msg.add(label).left();

        float[] t = {0f};
        int[] last = {-1};
        msg.update(() -> {
            t[0] += Time.delta / 60f;
            int n = instant ? total : Math.min(total, (int)(t[0] * cps));
            if(n == last[0]) return;
            last[0] = n;

            // don't cut inside a [color] tag
            int open = text.lastIndexOf('[', Math.max(0, n - 1));
            if(open >= 0 && n > 0){
                int close = text.indexOf(']', open);
                if(close >= n) n = Math.min(total, close + 1);
            }
            label.setText(text.substring(0, n));
        });

        float typeTime = instant ? 0f : total / cps;
        msg.color.a = 0f;
        msg.actions(
                Actions.fadeIn(0.2f),
                Actions.delay(typeTime + hold),
                Actions.fadeOut(fade),
                Actions.run(() -> list.removeChild(msg))
        );

        list.add(msg).left().padBottom(4f).row();
    }

    static String wrap(String s, float max){
        Font font = Fonts.outline;
        GlyphLayout gl = Pools.obtain(GlyphLayout.class, GlyphLayout::new);
        StringBuilder out = new StringBuilder();
        String[] paragraphs = s.split("\n", -1);

        for(int p = 0; p < paragraphs.length; p++){
            if(p > 0) out.append('\n');
            String[] words = paragraphs[p].split(" ", -1);
            StringBuilder line = new StringBuilder();
            for(int i = 0; i < words.length; i++){
                if(i == 0){
                    line.append(words[i]);
                    continue;
                }
                gl.setText(font, line + " " + words[i]);
                if(gl.width > max){
                    out.append(line).append('\n');
                    line.setLength(0);
                    line.append(words[i]);
                }else{
                    line.append(' ').append(words[i]);
                }
            }
            out.append(line);
        }
        Pools.free(gl);
        return out.toString();
    }
}

