package ds.world.type.entities.bullets;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import arc.util.Tmp;
import ds.world.draw.DrawCurveLightning;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.core.World;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.defense.turrets.Turret;

public class PointLightningBulletType extends BulletType {
    private static float cdist = 0f;
    private static Unit result;
    private static Building furthest;
    private static final Rect rect = new Rect();
    private static final Vec2 tmp1 = new Vec2();
    private static final Vec2 tmp2 = new Vec2();
    private static final Vec2 tmp3 = new Vec2();
    private static final FloatSeq floatSeq = new FloatSeq();

    public float segLen = 6;
    public Color lightningColor;
    public float trailSpacing = 10f;

    public int lightningNum = 3; // количество ветвей молнии
    public float lightningWidth = 2.5f;
    public boolean createSubBolts = true; // создавать дополнительные молнии
    public float subBoltDamage = 0f; // урон дополнительных молний
    public int subBoltLength = 10; // длина дополнительных молний
    public float hitChance = 1.0f; // шанс попадания
    public float boltRandomRange = 1.0f; // разброс ветвей молнии
    public float rotDst = 2f; // расстояние между изгибами

    public PointLightningBulletType() {
        this(1f);
    }

    public PointLightningBulletType(float damage) {
        this.damage = damage;
        trailEffect = Fx.none;
        scaleLife = true;
        lifetime = 100f;
        collides = false;
        reflectable = false;
        keepVelocity = false;
        lightningColor = Color.valueOf("e3ffff");
    }

    @Override
    public void init(Bullet b) {
        super.init(b);

        // Проверка шанса попадания
        if (!Mathf.chance(hitChance)) {
            b.remove();
            return;
        }

        // Вычисляем конечную позицию
        float px = b.x + b.lifetime * b.vel.x;
        float py = b.y + b.lifetime * b.vel.y;
        float rot = b.rotation();

        // Ищем перехваченную цель, используя tmp2 для хранения целевой позиции
        tmp2.set(px, py);
        Position intercepted = findInterceptedPoint(b, tmp2, b.team);

        // Если нашли перехваченную цель (здание), используем её координаты
        if (intercepted != tmp2) {
            px = intercepted.getX();
            py = intercepted.getY();
        }

        // Создаем эффект молнии с ветвлением
        createLightningEffect(b, new Vec2(b.x, b.y), new Vec2(px, py), rot, lightningColor);

        // Создаем дополнительные молнии (если нужно)
        if (createSubBolts) {
            createSubLightnings(b, px, py, rot);
        }

        // Наносим урон цели
        hitTarget(b, px, py);

        b.time = b.lifetime;
        b.set(px, py);
        b.remove();
        b.vel.setZero();
    }

    private void createLightningEffect(Bullet b, Vec2 from, Vec2 to, float rotation, Color color) {
        if (Vars.headless) return;

        if (lightningNum < 1) {
            // Простая молния
            Fx.chainLightning.at(from.x, from.y, 0, color, new Vec2().set(to));
        } else {
            float dst = from.dst(to);

            for (int i = 0; i < lightningNum; i++) {
                // Создаем случайные изгибы как в PositionLightning
                float len = getBoltRandomRange();
                float randRange = len * boltRandomRange;

                floatSeq.clear();
                for (int num = 0; num < dst / (rotDst * len) + 1; num++) {
                    floatSeq.add(Mathf.range(randRange) / (num * 0.025f + 1));
                }

                // Отрисовываем изогнутую молнию
                Seq<Vec2> points = computeVectors(floatSeq, from, to);
                DrawCurveLightning.drawCurveLightning(from, to, points.size, segLen, rotation, color);

                points.each(pos -> {
                    // Свет в каждой точке с пульсацией
                    float lightRadius = lightningWidth * 4f + Mathf.random(2f);
                    Drawf.light(pos.x, pos.y, lightRadius, color, 0.7f);
                });

                // Дополнительный свет в начале и конце
                Drawf.light(from.x, from.y, lightningWidth * 6f, color, 0.8f);
                Drawf.light(to.x, to.y, lightningWidth * 6f, color, 0.8f);

                // Добавляем искры (как в createBoltEffect)
                points.each(pos -> {
                    if (Mathf.chance(0.0855f)) {
                        // Здесь можно добавить эффект искры
                        Fx.lightning.at(pos.x, pos.y, Mathf.random(2f + lightningWidth, 4f + lightningWidth), color);
                    }
                });
            }
        }
    }

    private void createSubLightnings(Bullet b, float x, float y, float rotation) {
        // Создаем дополнительные молнии от точки попадания
        for (int i = 0; i < 3; i++) {
            mindustry.entities.Lightning.create(b.team, lightningColor,
                    subBoltDamage <= 0f ? damage : subBoltDamage,
                    x, y, Mathf.random(360f), subBoltLength);
        }
    }

    private void hitTarget(Bullet b, float px, float py) {
        // Поиск юнита для попадания
        cdist = 0f;
        result = null;
        float range = 10f; // Радиус поиска

        Units.nearbyEnemies(b.team, px - range, py - range, range * 2f, range * 2f, e -> {
            if (e.dead() || !e.checkTarget(collidesAir, collidesGround) || !e.hittable()) return;

            e.hitbox(Tmp.r1);
            if (!Tmp.r1.contains(px, py)) return;

            float dst = e.dst(px, py);
            if (result == null || dst < cdist) {
                result = e;
                cdist = dst;
            }
        });

        if (result != null) {
            b.collision(result, px, py);
            result.damage(b.damage());
            result.apply(status, statusDuration);
        } else if (collidesTiles) {
            Building build = Vars.world.buildWorld(px, py);
            if (build != null && build.team != b.team) {
                build.collision(b);
                build.damage(b.damage());
                hit(b, px, py);
                b.hit = true;
            }
        }
    }

    private Position findInterceptedPoint(Bullet owner, Position target, Team fromTeam) {
        furthest = null;

        // Raycast для поиска перекрывающих блоков
        boolean hit = Geometry.raycast(
                World.toTile(owner.x),
                World.toTile(owner.y),
                World.toTile(target.getX()),
                World.toTile(target.getY()),
                (x, y) -> {
                    Building build = Vars.world.build(x, y);
                    return (furthest = build) != null &&
                            build.team() != fromTeam &&
                            build.block.insulated;
                }
        );

        // Если нашли изолированное здание на пути, возвращаем его
        if (hit && furthest != null) {
            return furthest;
        }

        // Иначе возвращаем исходную цель (которая указывает на tmp2)
        return target;
    }

    private float getBoltRandomRange() {
        return Mathf.random(1.0f, 7.0f);
    }

    private Seq<Vec2> computeVectors(FloatSeq randomVec, Position from, Position to) {
        int param = randomVec.size;
        float angle = from.angleTo(to);
        float dst = from.dst(to);

        Seq<Vec2> lines = new Seq<>();
        tmp1.trns(angle, dst / (param - 1));

        lines.add(new Vec2(from.getX(), from.getY()));

        for (int i = 1; i < param - 2; i++) {
            // Вычисляем позицию с отклонением
            tmp3.trns(angle - 90, randomVec.get(i));
            Vec2 point = new Vec2(tmp1).scl(i).add(from.getX(), from.getY()).add(tmp3);
            lines.add(point);
        }

        lines.add(new Vec2(to.getX(), to.getY()));
        return lines;
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        // Дополнительная логика при попадании в сущность
        if (entity instanceof Unit unit) {
            unit.apply(status, statusDuration);
        }
        super.hitEntity(b, entity, health);
    }
}