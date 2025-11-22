package ds.newContent.type.entities;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import ds.world.draw.DrawWire;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Posc;

public class HarpoonBulletType extends BasicBulletType {

    public Color wireColor = Color.brown;
    public float wireStroke = 1f;
    public float returnSpeed = 2;
    public float returnDelay = 60f;

    public HarpoonBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
        pierce = true;
        sprite = "deepsea-hb";
        pierceDamageFactor = 0.1f;
        pierceBuilding = false;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
    }
    public HarpoonBulletType(float speed, float damage) {
        super(speed, damage, "ds-harpoon");
        pierce = true;
        sprite = "deepsea-hb";
        pierceDamageFactor = 0.1f;
        pierceBuilding = false;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
    }
    public HarpoonBulletType() {
        super(8, 50, "ds-harpoon");
        pierce = true;
        returnDelay = this.lifetime/1.1f;
        sprite = "deepsea-hb";
        pierceDamageFactor = 0.1f;
        pierceBuilding = false;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
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
        if (b.owner != null && b.owner instanceof Posc) {
            float ownerX = ((Posc)b.owner).getX();
            float ownerY = ((Posc)b.owner).getY();
            DrawWire.draw(b.x, b.y, ownerX, ownerY, wireColor, wireStroke);
        }
        super.draw(b);
    }
    private void  updateReturn(Bullet b, Posc owner){
        b.rotation(b.rotation() + Mathf.random(-3f, 3f));
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
        b.rotation(b.rotation() + Mathf.random(-30f, 30f));
        super.hit(b, x, y);
    }
}
