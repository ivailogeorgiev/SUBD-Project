public class Airport {
    String name;
    String location;
    int capacity;
    int runways;
    DB db =new DB();

    public Airport(String name, String location, int capacity, int runways){
        this.name=name;
        this.location=location;
        this.capacity=capacity;
        this.runways=runways;

        String query = String.format("insert into airports values(%s, %s, %d, %d)", name, location, capacity, runways);

        db.exec(query);

    }
}