public class Supervisor implements User {
    private final int id = 1001;
    private final String name = "Supervisor";

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return "Supervisor";
    }
}