public class Plane {
    int maxSeats;
    String name;
    DB db = new DB();

    public Plane(int maxSeats, String name) {
        this.maxSeats = maxSeats;
        this.name = name;

        String query = String.format("insert into plane(name,maxSeats) values('%s', %d)", name, maxSeats);

        db.exec(query);
    }

    public String getName() {
        return name;
    }
}