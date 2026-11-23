package ds.type.dsUnitTypes.parts.patterns;

public class KineticDetonateType extends DetonateType{
    public float impactDistance;

    public KineticDetonateType(){
        impactDistance = 12;
    }

    public KineticDetonateType(float impactDistance){
        this.impactDistance = impactDistance;
    }

    @Override
    public boolean detonate(float dist, float time, float alt) {
        return dist < impactDistance && alt < impactDistance;
    }
}
