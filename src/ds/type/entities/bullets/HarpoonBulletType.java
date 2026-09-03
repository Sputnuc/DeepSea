package ds.type.entities.bullets;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.Angles;
import arc.util.Log;
import arc.util.Time;
import ds.world.blocks.turret.DSHarpoonTurret;
import ds.draw.DrawWire;
import ds.type.entities.comp.HarpoonBulletComp;
import ds.world.blocks.turret.DSTurret;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.turrets.Turret;

public class HarpoonBulletType extends BasicBulletType {
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    public Color wireColor = Color.brown;
    public float wireStroke = 0.1f;
    public float wireLen = 8f;
    public float returnSpeed = 2;
    public float returnDelay = 60f;
    public TextureRegion wireRegion;
    public TextureRegion startRegion;
    public float pierceDrag = 0.5f;
    public float pierceAccelFactor = 0.15f;


    public HarpoonBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
        pierce = true;
        sprite = "deepsea-spear";
        pierceBuilding = true;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
        layer = Layer.bullet - 2;
        despawnEffect = Fx.none;
        drawSize = 999;
        lightRadius = 0;
    }

    public HarpoonBulletType(float speed, float damage) {
        super(speed, damage, "deepsea-spear");
        pierce = true;
        sprite = "deepsea-spear";
        pierceBuilding = true;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
        layer = Layer.bullet - 2;
        despawnEffect = Fx.none;
        drawSize = 999;
        hittable = false;
        absorbable = false;
        lightRadius = 0;
    }

    public HarpoonBulletType() {
        super(8, 50, "deepsea-spear");
        pierce = true;
        returnDelay = this.lifetime/1.1f;
        sprite = "deepsea-spear";
        pierceBuilding = true;
        pierceCap = -1;
        drag = 0.05f;
        shrinkX = shrinkY = 0;
        layer = Layer.bullet - 2;
        despawnEffect = Fx.none;
        drawSize = 999;
        hittable = false;
        absorbable = false;
        lightRadius = 0;
    }

    @Override
    public void init(Bullet b){
        super.init(b);
        b.data = new HarpoonBulletComp(){{
            returning = false;
        }};
        b.lifetime += speed * lifetime / returnSpeed;
    }

    @Override
    public void load(){
        super.load();
        wireRegion = Core.atlas.find("deepsea-harpoon-wire");
        Log.debug(wireRegion.found() ? "Deep sea log: Wire region is found" : "Deep sea log: Wire region isn't found" );
        startRegion = Core.atlas.find("deepsea-harpoon-wire-end");
        Log.debug(startRegion.found() ? "Deep sea log: Wire start region is found" : "Deep sea log: Wire start region isn't found" );
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        HarpoonBulletComp hdata = (HarpoonBulletComp)b.data;
        if(b.data != null){
            if (!hdata.returning) {
                if (b.time >= lifetime && b.owner instanceof Posc) {
                    hdata.returning = true;
                }
            } else {
                if (b.owner instanceof Posc) {
                    updateReturn(b, (Posc) b.owner);
                }
            }
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

        if (b.owner instanceof Posc && ownerValid(b)){
            float ownerX = ((Posc)b.owner).getX();
            float ownerY = ((Posc)b.owner).getY();

            if(b.owner instanceof Turret.TurretBuild turret){
                float rotation = turret.rotation;
                float muzzleX = turret.x + Angles.trnsx(rotation, turret.block.size * 2f);
                float muzzleY = turret.y + Angles.trnsy(rotation, turret.block.size * 2f);
                if(turret.block instanceof Turret bl){
                    muzzleX = turret.x + Angles.trnsx(rotation - 90, bl.shootX, bl.shootY);
                    muzzleY = turret.y + Angles.trnsy(rotation - 90, bl.shootX, bl.shootY);
                }
                ownerX = muzzleX;
                ownerY = muzzleY;
            }

            DrawWire.draw(ownerX, ownerY, b.x, b.y, wireRegion, startRegion, wireStroke, wireLen, b.rotation());
        }
    }

    private void updateReturn(Bullet b, Posc owner){
        if (ownerValid(b)){
            float targetX = owner.getX();
            float targetY = owner.getY();

            if(owner instanceof DSHarpoonTurret.HarpoonTurretBuild turret){
                float rotation = turret.rotation;
                float muzzleX = turret.x;
                float muzzleY = turret.y;

                if(turret.block instanceof  Turret t){
                    muzzleX = turret.x + Angles.trnsx(rotation - 90, t.shootX, t.shootY);
                    muzzleY = turret.y + Angles.trnsy(rotation - 90, t.shootX, t.shootY);
                }

                targetX = muzzleX;
                targetY = muzzleY;
            }
            float oldRot = b.rotation();
            float targetAngle = b.angleTo(targetX, targetY);
            b.x(b.x + Mathf.cosDeg(targetAngle) * Time.delta * returnSpeed);
            b.y(b.y + Mathf.sinDeg(targetAngle) * Time.delta * returnSpeed);
            b.rotation(oldRot);
            b.time = -1;

            if (b.dst(targetX, targetY) < 10f) {
                Fx.bubble.at(b.x, b.y);
                b.remove();
            }
        }
    }
    @Override
    public void removed(Bullet b){
        super.removed(b);
        if(b.owner instanceof DSHarpoonTurret.HarpoonTurretBuild){
            ((DSHarpoonTurret.HarpoonTurretBuild) b.owner).bulletReturned();
        }
    }

    @Override
    public void hit(Bullet b, float x, float y){
        HarpoonBulletComp hdata = (HarpoonBulletComp)b.data;
        if(!hdata.returning) {
            b.vel().scl(1 - pierceDrag);
            super.hit(b, x, y);
            if(b.vel().len2() < speed * pierceAccelFactor) manualBackup(b);
        }
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        HarpoonBulletComp hdata = (HarpoonBulletComp) b.data;
        if (!hdata.returning) {
            super.hitEntity(b, entity, health);
        }
    }

    protected void manualBackup(Bullet b){
        if(b.data instanceof HarpoonBulletComp){
            ((HarpoonBulletComp) b.data).returning = true;
            b.vel.scl(0.05f);
        }
    }

    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
        HarpoonBulletComp hdata = (HarpoonBulletComp) b.data;
        if (!hdata.returning) {
            super.hitTile(b, build, x, y, initialHealth, direct);
            manualBackup(b);
        }
    }
}