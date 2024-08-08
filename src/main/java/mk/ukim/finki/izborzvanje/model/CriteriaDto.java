package mk.ukim.finki.izborzvanje.model;

public class CriteriaDto
{
    public String name;
    public Double points;
    public boolean activitiesOfWiderInterest;

    public CriteriaDto(String name, Double points, boolean ActivitiesOfWiderInterest) {
        this.name = name;
        this.points = points;
        this.activitiesOfWiderInterest = ActivitiesOfWiderInterest;
    }
}
