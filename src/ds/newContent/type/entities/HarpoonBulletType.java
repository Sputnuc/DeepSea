package ds.newContent.type.entities;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import ds.world.draw.DrawWire;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Posc;
import mindustry.graphics.Layer;

public class HarpoonBulletType extends BasicBulletType {

    public Color wireColor = Color.brown;
    public float wireStroke = 1f;
    public float returnSpeed = 2;
    public float returnDelay = 60f;

    public HarpoonBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
        pierce = true;
        sprite = "deepsea-spear";
        pierceDamageFactor = 0.1f;
        pierceBuilding = false;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
        layer = Layer.bullet - 2;
    }
    public HarpoonBulletType(float speed, float damage) {
        super(speed, damage, "deepsea-spear");
        pierce = true;
        sprite = "deepsea-spear";
        pierceDamageFactor = 0.1f;
        pierceBuilding = false;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
        layer = Layer.bullet - 2;
    }
    public HarpoonBulletType() {
        super(8, 50, "deepsea-spear");
        pierce = true;
        returnDelay = this.lifetime/1.1f;
        sprite = "deepsea-spear";
        pierceDamageFactor = 0.1f;
        pierceBuilding = false;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
        layer = Layer.bullet - 2;
    }
    @Override
    public void init(Bullet b){
        super.init(b);
        b.fdata = returnDelay;
        b.time = (lifetime - returnDelay) >= 0 ? lifetime - returnDelay : lifetime;
        float length = speed * lifetime;
        float returnTime = length / returnSpeed;
        b.time -= returnTime;
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        if(b.data == null){
            b.fdata -= Time.delta;
            if(b.fdata <= 0 && b.owner instanceof Posc){
                b.data = Boolean.TRUE;
                Fx.bubble.at(b.x, b.y);
            }

        }else if(b.owner instanceof  Posc){
            updateReturn(b, (Posc)b.owner);
        }
    }
    @Override
    public void draw(Bullet b) {
        drawTrail(b);
        drawParts(b);
        float shrink = shrinkInterp.apply(b.fout());
        float height = this.height * ((1f - shrinkY) + shrinkY * shrink);
        float width = this.width * ((1f - shrinkX) + shrinkX * shrink);
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f) + rotationOffset;

        Color mix = Tmp.c1.set(mixColorFrom).lerp(mixColorTo, b.fin());

        Draw.mixcol(mix, mix.a);

        if(backRegion.found()){
            Draw.color(backColor);
            Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() + offset);
        }

        Draw.color(frontColor);
        Draw.rect(frontRegion, b.x, b.y, width, height, b.rotation() + offset);

        Draw.reset();

        if (b.owner != null && b.owner instanceof Posc) {
            float ownerX = ((Posc)b.owner).getX();
            float ownerY = ((Posc)b.owner).getY();
            DrawWire.draw(b.x, b.y, ownerX, ownerY, wireColor, wireStroke);
        }
    }
    private void  updateReturn(Bullet b, Posc owner){
        float targetAngle = b.angleTo(owner);
        float newAngle = Mathf.slerpDelta(b.rotation(), targetAngle, 0.3f);
        b.vel().setAngle(newAngle).setLength(returnSpeed);
        if(b.dst(owner) < 20f){
            Fx.bubble.at(b.x, b.y);
            b.remove();
        }
    }
    @Override
    public void hit(Bullet b, float x, float y) {
        b.vel().scl(0.5f);
        super.hit(b, x, y);
    }
}