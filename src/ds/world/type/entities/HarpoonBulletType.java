package ds.world.type.entities;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.Angles;
import arc.util.Time;
import ds.world.blocks.dsHarpoonTurret;
import ds.world.draw.DrawWire;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.turrets.Turret;

public class HarpoonBulletType extends BasicBulletType {

    public Color wireColor = Color.brown;
    public float wireStroke = 1f;
    public float returnSpeed = 2;
    public float returnDelay = 60f;
    public static TextureRegion wireRegion;
    public float pierceDrag = 0.5f;

    public HarpoonBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
        pierce = true;
        sprite = "deepsea-spear";
        pierceBuilding = false;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
        layer = Layer.bullet - 2;
        despawnEffect = Fx.none;
        drawSize = 999;

    }

    public HarpoonBulletType(float speed, float damage) {
        super(speed, damage, "deepsea-spear");
        pierce = true;
        sprite = "deepsea-spear";
        pierceBuilding = false;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
        layer = Layer.bullet - 2;
        despawnEffect = Fx.none;
        drawSize = 999;
        hittable = false;
        absorbable = false;
    }

    public HarpoonBulletType() {
        super(8, 50, "deepsea-spear");
        pierce = true;
        returnDelay = this.lifetime/1.1f;
        sprite = "deepsea-spear";
        pierceBuilding = false;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
        layer = Layer.bullet - 2;
        despawnEffect = Fx.none;
        drawSize = 999;
        hittable = false;
        absorbable = false;
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
    public void load(){
        super.load();
        wireRegion = Core.atlas.find("deepsea-harpoon-wire");
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
        }else if(b.owner instanceof Posc){
            updateReturn(b, (Posc)b.owner);
        }
    }

    private boolean ownerValid(Bullet b){
        if(b.owner == null) return false;

        if(b.owner instanceof Unit unit){
            return unit.isValid();
        }

        if(b.owner instanceof Building building){
            return building.isValid();
        }

        return false;
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        if (b.owner != null && b.owner instanceof Posc && ownerValid(b)){
            float ownerX = ((Posc)b.owner).getX();
            float ownerY = ((Posc)b.owner).getY();

            if(b.owner instanceof Turret.TurretBuild turret){
                float rotation = turret.rotation;
                float muzzleX = turret.x + Angles.trnsx(rotation, turret.block.size * 4f);
                float muzzleY = turret.y + Angles.trnsy(rotation, turret.block.size * 4f);

                ownerX = muzzleX;
                ownerY = muzzleY;
            }

            DrawWire.draw(b.x, b.y, ownerX, ownerY, wireRegion, wireStroke);
        }
    }

    private void updateReturn(Bullet b, Posc owner){
        if (ownerValid(b)){
            float targetX = owner.getX();
            float targetY = owner.getY();

            // Если владелец - турель, получаем позицию ствола
            if(owner instanceof Turret.TurretBuild turret){
                // Используем последний угол выстрела для определения позиции ствола
                float rotation = turret.rotation;
                float muzzleX = turret.x + Angles.trnsx(rotation, turret.block.size * 4f);
                float muzzleY = turret.y + Angles.trnsy(rotation, turret.block.size * 4f);

                targetX = muzzleX;
                targetY = muzzleY;
            }

            float targetAngle = b.angleTo(targetX, targetY);
            b.x(b.x + Mathf.cosDeg(targetAngle) * Time.delta * returnSpeed);
            b.y(b.y + Mathf.sinDeg(targetAngle) * Time.delta * returnSpeed);
            b.time = -1;

            if (b.dst(targetX, targetY) < 20f) {
                Fx.bubble.at(b.x, b.y);
                b.remove();
            }
        }
    }
    @Override
    public void removed(Bullet b){
        super.removed(b);
        if(b.owner instanceof dsHarpoonTurret.HarpoonTurretBuild){
            ((dsHarpoonTurret.HarpoonTurretBuild) b.owner).bulletReturned();
        }
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        if(b.data == null) {
            super.hitEntity(b, entity, health);
        }
    }

    @Override
    public void hit(Bullet b, float x, float y) {
        if(b.data == null) {
            b.vel().scl(pierceDrag);
            super.hit(b, x, y);
        }
    }
}