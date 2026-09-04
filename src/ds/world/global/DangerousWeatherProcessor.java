package ds.world.global;

import arc.audio.Sound;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.type.Weather;

public class DangerousWeatherProcessor extends WorldProcess{

    public Weather weather;

    public BulletType bulletType;

    public Sound strikeSound = Sounds.shootArc;

    public float interval = 60;

    public Team team = Team.derelict;

    public float bulletRotation = 90;

    public float bulletRotationRnd = 5;

    protected float timer;

    @Override
    public void init(){
        super.init();
        timer = 0;
    }

    public DangerousWeatherProcessor(String name) {
        super(name);
    }

    @Override
    public void update(){
        if(weather != null && weather.isActive()){
            timer += Time.delta;
            if (timer >= interval) {
                logProcess("Strike");
                timer = 0;
                Bullet b = bulletType.create(null, team, getRandomPosInUnits()[0], getRandomPosInUnits()[1], bulletRotation + Mathf.randomSeedRange((long) Time.globalTime, bulletRotationRnd));
                strikeSound.at(b.x, b.y, 1, 1);
            }
        }
    }

}
