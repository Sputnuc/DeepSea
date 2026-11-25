package ds.type.dsUnitTypes.parts.patterns;

public class TimedDetonateType extends DetonateType{
    public float detonateTime;

    public TimedDetonateType(){
        this.detonateTime = 120;
    }

    public TimedDetonateType(float detonateTime){
        this.detonateTime = detonateTime;
    }

    @Override
    public boolean detonate(float dist, float time, float alt) {
        return time > this.detonateTime;
    }
}
