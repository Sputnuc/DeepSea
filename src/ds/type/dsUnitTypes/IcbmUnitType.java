package ds.type.dsUnitTypes;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Tmp;
import deepsea.gen.BallisticMovementc;
import deepsea.gen.KineticImpactc;
import ds.ai.AltitudeMissileAi;
import ds.content.DSFx;
import ds.type.dsUnitTypes.parts.IcbmWarhead;
import mindustry.ai.types.CommandAI;
import mindustry.entities.Effect;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.game.Team;
import mindustry.gen.Sounds;
import mindustry.gen.TimedKillc;
import mindustry.gen.UnderwaterMovec;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.world;

//For ICBT (like ICBM, but torpedoes)
public class IcbmUnitType extends DSUnitType {
    public IcbmWarhead defaultWarhead = new IcbmWarhead(
            "standart-warhead",
            new ExplosionBulletType(){{
                despawnEffect = DSFx.dsMassiveExplosion;
                splashDamage = 120;
                splashDamageRadius = 120;
                hitShake = 19;
                hitSound = Sounds.explosionMissile;
            }},
            ItemStack.empty);
    public Effect engineEffect = DSFx.dsMoveEffect;
    public Color effectColor = Color.grays(0.6f).lerp(Pal.redLight, 0.5f).a(0.4f);
    public float effectInterval = 4, effectChance = 1f;
    public float effectX, effectY = -5;
    public float shadowOffsetScl = 0.5f;
    //a max altitude that icbt can reach (for interceptor bullets/torpedoes)
    public float peakAltitude = 100;
    public float shrinkPower = 0.75f;
    public float alphaPower = 0.75f;
    public float risingSpeedPenalty = 0.85f;
    //min raising altitude
    public float raisingAltitude = 70;
    //determines whether ICBT can be launchable ONLY manually
    public boolean strategic = false;
    //ICBT can be launched from sector A to sector B
    public boolean interSector = false;
    public float raiseSpeed = 1.75f;
    public float descendSpeed = 6;
    public float ballisticAccel = 1f / 3f;
    public float gravity = 0.09f;

    public boolean canMiss = true;
    public float alignmentDistance = 50;

    public IcbmUnitType(String name) {
        super(name);
        speed = 5;
        flying = true;
        softShadowScl = 0.45f;
        drawCell = false;
        engineSize = 0;
        deathSound = wreckSound = Sounds.none;
        omniMovement = false;
        controller = u -> new AltitudeMissileAi();
    }

    @Override
    public Unit create(Team team){
        Unit unit = constructor.get();
        unit.team = team;
        unit.setType(this);
        if(unit.controller() instanceof CommandAI command && defaultCommand != null){
            command.command = defaultCommand;
        }
        for(var ability : unit.abilities){
            ability.created(unit);
        }
        unit.elevation = flying ? 1f : 0;
        unit.heal();
        if(unit instanceof TimedKillc u){
            u.lifetime(lifetime);
        }
        if(unit instanceof KineticImpactc u){
            u.setWarHead(defaultWarhead);
        }
        return unit;
    }

    public Unit launch(Team team, float x, float y, float rotation, float targetX, float targetY, IcbmWarhead warHead){
        Unit u = spawn(team, x, y, rotation);
        if(u instanceof BallisticMovementc b){
            b.targetX(targetX);
            b.targetY(targetY);
        }
        if(u instanceof KineticImpactc c && warHead != null){
            c.setWarHead(warHead);
        }
        return u;
    }

    @Override
    public void update(Unit unit){
        if(!(unit instanceof BallisticMovementc b)) return;
        float x = Angles.trnsx(b.rotation(), effectY, effectX) + b.x();
        float y = Angles.trnsx(b.rotation(), effectY, effectX) + b.y();
        if(effectInterval > 0) {
            if (b.timer(0, effectInterval)) {
                if (Mathf.chance(effectChance)) {
                    engineEffect.at(x, y, b.rotation(), effectColor, 1-b.altitudeFraction());
                }
            }
        } else if (Mathf.chance(effectChance)) {
            engineEffect.at(x, y, b.rotation(), effectColor,  1-b.altitudeFraction());
        }
    }

    @Override
    public void drawBody(Unit unit){
        applyColor(unit);

        float shrinkY = 1;
        float alpha = 1;

        if(unit instanceof UnderwaterMovec){
            Draw.alpha(1f);
            Draw.mixcol(unit.floorOn().mapColor.write(Tmp.c1).mul(0.9f), 1f);
        }

        if(unit instanceof BallisticMovementc b){
            shrinkY = 1 - (1 - b.altitudeFraction()) * shrinkPower;
            alpha =  (1 - b.altitudeFraction() * alphaPower);
        }

        Draw.alpha(alpha);
        Draw.rect(region, unit.x, unit.y, region.width * region.scl(), region.height * region.scl() * shrinkY, unit.rotation - 90);
        Draw.reset();
    }

    @Override
    public void drawShadow(Unit unit){
        float o = 0;
        float shrinkY = 0;
        if(unit instanceof BallisticMovementc c){
            o = c.altitude() * shadowOffsetScl;
            shrinkY = 1 - (1 - c.altitudeFraction()) * shrinkPower;
        }
        float x = unit.x - o, y = unit.y - o;
        Floor floor = world.floorWorld(x, y);

        float dest = floor.canShadow ? 1f : 0f;
        //yes, this updates state in draw()... which isn't a problem, because I don't want it to be obvious anyway
        unit.shadowAlpha = unit.shadowAlpha < 0 ? dest : Mathf.approachDelta(unit.shadowAlpha, dest, 0.11f);
        Draw.color(Pal.shadow, Pal.shadow.a * unit.shadowAlpha);

        Draw.rect(shadowRegion, unit.x - o, unit.y - o, region.width * region.scl(), region.height * region.scl() * shrinkY,unit.rotation - 90);
        Draw.color();
    }

    @Override
    public void drawSoftShadow(Unit unit, float alpha){
        float scl = 1;

        if(unit instanceof BallisticMovementc b){
            scl = 1- (1 - b.altitudeFraction() * 0.5f);
        }

        drawSoftShadow(unit.x, unit.y, unit.rotation, alpha, scl);
    }

    public void drawSoftShadow(float x, float y, float rotation, float alpha, float scl){
        Draw.color(0, 0, 0, 0.4f * alpha);
        float rad = 1.6f;
        float size = Math.max(region.width, region.height) * region.scl() * softShadowScl;
        Draw.rect(softShadowRegion, x, y, size * rad * Draw.xscl, size * rad * Draw.yscl, rotation - 90);
        Draw.color();
    }
}
