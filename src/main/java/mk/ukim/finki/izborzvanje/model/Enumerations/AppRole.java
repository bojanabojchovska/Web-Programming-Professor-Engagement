package mk.ukim.finki.izborzvanje.model.Enumerations;
public enum AppRole {
    PROFESSOR, ADMIN, GUEST;


    public String roleName() {
        return "ROLE_" + this.name();
    }
}
