package ds.newContent.type.entities;

import arc.graphics.Color;
import arc.math.Mathf;
import ds.world.draw.DrawWire;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Posc;

public class HarpoonBulletType extends BasicBulletType {

    public Color wireColor = Color.brown;
    public float wireStroke = 1f;
    public float wireStrokeTo = 0.1f;
    public float fadeTime = 60;

    public HarpoonBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
        pierce = true;
        sprite = "deepsea-hb";
        pierceCap = 5;
        pierceBuilding = false;
        drag = 0.05f;
        backColor = Color.gray;
        frontColor = Color.lightGray;
        shrinkX = shrinkY = 0;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        if (b.owner != null && b.owner instanceof Posc) {
            float ownerX = ((Posc)b.owner).getX();
            float ownerY = ((Posc)b.owner).getY();
            DrawWire.draw(b.x, b.y, ownerX, ownerY, wireColor, b.time(), fadeTime, this.lifetime, Mathf.lerp(wireStroke, wireStrokeTo, b.time / lifetime));
        }
    }


    @Override
    public void hit(Bullet b, float x, float y) {
        b.vel().scl(0.8f);
        super.hit(b, x, y);
    }
}
