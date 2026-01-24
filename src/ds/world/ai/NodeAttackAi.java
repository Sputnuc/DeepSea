package ds.world.ai;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.Units;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.world.blocks.power.LightBlock;

import static mindustry.Vars.world;

public class NodeAttackAi extends AIController {

    public float destroyRadius = 32f;
    public float targetReachedDistance = 8f;

    private Seq<Building> allLightBlocks = new Seq<>();
    private Seq<Building> currentPath = new Seq<>();
    private int currentTargetIndex = -1;
    private boolean needsPathUpdate = true;
    private float updateTimer = 0f;
    private static final float UPDATE_INTERVAL = 30f;
    private Vec2 targetPos = new Vec2();

    @Override
    public void init() {
        super.init();
        // Первоначальный поиск всех световых блоков
        findAllLightBlocks();
        updateTimer = UPDATE_INTERVAL;
    }

    @Override
    public void updateUnit() {
        super.updateUnit();

        if (unit == null || unit.dead()) return;

        // Периодическое обновление списка целей
        updateTimer -= Time.delta;
        if (updateTimer <= 0) {
            findAllLightBlocks();
            needsPathUpdate = true;
            updateTimer = UPDATE_INTERVAL;
        }

        // Если нет целей - ничего не делаем
        if (allLightBlocks.isEmpty()) {
            unit.moveAt(Vec2.ZERO);
            return;
        }

        // Если нужен новый путь или текущий путь пуст
        if (needsPathUpdate || currentPath.isEmpty()) {
            buildOptimalPath();
            needsPathUpdate = false;
        }

        // Если есть текущая цель
        if (currentTargetIndex >= 0 && currentTargetIndex < currentPath.size) {
            followCurrentTarget();
        } else {
            // Начинаем сначала
            currentTargetIndex = 0;
        }
    }

    private void findAllLightBlocks() {
        allLightBlocks.clear();

        // Получаем все здания из мира
        for (Building build : Groups.build) {
            if (isValidLightBlock(build)) {
                allLightBlocks.add(build);
            }
        }
        if(allLightBlocks.isEmpty()) unit.kill();
    }

    private boolean isValidLightBlock(Building build) {
        return build != null &&
                !build.dead() &&
                build.block instanceof LightBlock &&
                // Опционально: игнорируем свои блоки
                build.team != unit.team;
    }

    private void buildOptimalPath() {
        if (allLightBlocks.isEmpty()) {
            currentPath.clear();
            currentTargetIndex = -1;
            return;
        }

        currentPath.clear();

        // Создаем копию для сортировки
        Seq<Building> unsorted = new Seq<>(allLightBlocks);
        Seq<Building> sorted = new Seq<>();

        // Начинаем с ближайшего к юниту блока
        Building current = getNearestBuilding(unsorted, unit.x, unit.y);

        while (current != null) {
            sorted.add(current);
            unsorted.remove(current);

            if (unsorted.isEmpty()) break;

            // Ищем следующий ближайший блок
            current = getNearestBuilding(unsorted, current.x, current.y);
        }

        currentPath = sorted;
        currentTargetIndex = 0;

    }

    private Building getNearestBuilding(Seq<Building> buildings, float x, float y) {
        Building nearest = null;
        float minDist = Float.MAX_VALUE;

        for (Building build : buildings) {
            if (build == null || build.dead()) continue;

            float dist = Mathf.dst2(x, y, build.x, build.y);
            if (dist < minDist) {
                minDist = dist;
                nearest = build;
            }
        }

        return nearest;
    }

    private void followCurrentTarget() {
        Building target = currentPath.get(currentTargetIndex);

        // Если цель уже уничтожена, пропускаем её
        if (target == null || target.dead()) {
            removeDestroyedTargets();
            needsPathUpdate = true;
            return;
        }

        // Устанавливаем позицию цели
        targetPos.set(target.x, target.y);

        // Поворачиваемся к цели
        unit.lookAt(targetPos.x - unit.x, targetPos.y - unit.y);

        // Двигаемся к цели
        float angle = unit.angleTo(targetPos);
        unit.moveAt(Tmp.v1.trns(angle, unit.speed()));

        // Проверяем, достигли ли цели
        float distance = Mathf.dst(unit.x, unit.y, target.x, target.y);

        if (distance <= targetReachedDistance) {
            // Уничтожаем блоки в радиусе
            destroyNearbyLights(target.x, target.y);

            removeDestroyedTargets();

            // Переходим к следующей цели
            currentTargetIndex++;

            // Если дошли до конца пути, перестраиваем
            if (currentTargetIndex >= currentPath.size) {
                needsPathUpdate = true;
                currentTargetIndex = 0;
            }
        }
    }

    private void destroyNearbyLights(float x, float y) {
        int destroyed = 0;
        float radiusSq = destroyRadius * destroyRadius;

        for (Building build : Groups.build) {
            if (build == null || build.dead() || !(build.block instanceof LightBlock)) {
                continue;
            }

            float distSq = Mathf.dst2(x, y, build.x, build.y);
            if (distSq <= radiusSq) {
                build.kill();
                destroyed++;
            }
        }
    }

    private void removeDestroyedTargets() {
        // Удаляем уничтоженные блоки из всех списков
        allLightBlocks.removeAll(b -> b == null || b.dead());
        currentPath.removeAll(b -> b == null || b.dead());
    }

    @Override
    public void removed(Unit unit) {
        super.removed(unit);
        allLightBlocks.clear();
        currentPath.clear();
    }
}
