public class Plane {
    int capacity;
    String model;
    DB db = new DB();


    public Plane( String model,int capacity) {
        this.capacity = capacity;
        this.model = model;

        String query = String.format("insert into planes(model,capacity) values('%s',%d)",model,capacity);

        db.exec(query);
    }
}
