package ds.world.ai;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import ds.world.blocks.effect.ArmoredLightBlock;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.world.blocks.power.LightBlock;
import mindustry.world.blocks.power.PowerGenerator;

//Code is bad, ik. I'll remade this shit someday
public class NodeAttackAi extends AIController {

    public float destroyRadius = 16f;
    public float targetReachedDistance = 4f;
    private final Seq<Building> allTargetBlocks = new Seq<>();
    private Seq<Building> currentPath = new Seq<>();
    private int currentTargetIndex = -1;
    private boolean needsPathUpdate = true;
    private float updateTimer = 0f;
    private static final float interval = 30f;
    private final Vec2 targetPos = new Vec2();

    @Override
    public void init() {
        super.init();
        findTargetsPos();
        updateTimer = interval;
    }

    @Override
    public void updateUnit() {
        super.updateUnit();

        if (unit == null || unit.dead()) return;

        updateTimer -= Time.delta;
        if (updateTimer <= 0) {
            findTargetsPos();
            needsPathUpdate = true;
            updateTimer = interval;
        }

        if (allTargetBlocks.isEmpty()) {
            unit.moveAt(Vec2.ZERO);
            return;
        }

        if (needsPathUpdate || currentPath.isEmpty()) {
            buildOptimalPath();
            needsPathUpdate = false;
        }

        if (currentTargetIndex >= 0 && currentTargetIndex < currentPath.size) {
            followCurrentTarget();
        } else {
            currentTargetIndex = 0;
        }
    }

    private void findTargetsPos() {
        allTargetBlocks.clear();

        for (Building build : Groups.build) {
            if (isTarget(build)) {
                allTargetBlocks.add(build);
            }
        }
        if(allTargetBlocks.isEmpty()) unit.kill();
    }

    private boolean isTarget(Building build) {
        return build != null &&
                !build.dead() &&
                ((build.block instanceof LightBlock && !(build.block instanceof ArmoredLightBlock)) || build.block instanceof PowerGenerator) &&
                build.team != unit.team;
    }
    private void buildOptimalPath() {
        if (allTargetBlocks.isEmpty()) {
            currentPath.clear();
            currentTargetIndex = -1;
            return;
        }
        currentPath.clear();
        Seq<Building> unsorted = new Seq<>(allTargetBlocks);
        Seq<Building> sorted = new Seq<>();
        Building current = getNearestBuilding(unsorted, unit.x, unit.y);

        while (current != null) {
            sorted.add(current);
            unsorted.remove(current);
            if (unsorted.isEmpty()) break;
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
        if (target == null || target.dead()) {
            removeDestroyedTargets();
            needsPathUpdate = true;
            return;
        }
        targetPos.set(target.x, target.y);
        unit.lookAt(targetPos.x - unit.x, targetPos.y - unit.y);
        float angle = unit.angleTo(targetPos);
        unit.moveAt(Tmp.v1.trns(angle, unit.speed()));
        float distance = Mathf.dst(unit.x, unit.y, target.x, target.y);

        if (distance <= targetReachedDistance) {
            destroyNearbyLights(target.x, target.y);
            removeDestroyedTargets();
            currentTargetIndex++;
            if (currentTargetIndex >= currentPath.size) {
                needsPathUpdate = true;
                currentTargetIndex = 0;
            }
        }
    }

    private void destroyNearbyLights(float x, float y) {
        float radiusSq = destroyRadius * destroyRadius;

        for (Building build : Groups.build) {
            if (build == null || build.dead() || !isTarget(build)) {
                continue;
            }

            float distSq = Mathf.dst2(x, y, build.x, build.y);
            if (distSq <= radiusSq) {
                build.damage(120);
            }
        }
    }

    private void removeDestroyedTargets() {
        allTargetBlocks.removeAll(b -> b == null || b.dead());
        currentPath.removeAll(b -> b == null || b.dead());
    }

    @Override
    public void removed(Unit unit) {
        super.removed(unit);
        allTargetBlocks.clear();
        currentPath.clear();
    }
}
