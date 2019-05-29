public class Airport {
    String name;
    String location;
    int runways;
    DB db =new DB();

    public Airport(String name, String location, int runways){
        this.name=name;
        this.location=location;
        this.runways=runways;

        String query = String.format("insert into airports(name,location,runways) values('%s', '%s', %d)", name, location, runways);

        db.exec(query);

    }
}