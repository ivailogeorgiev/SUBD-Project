public class Passenger {
    Flight flight;
    String name;
    int age;
    String gender;
    String startingLocation;
    DB db = new DB();

    public Passenger(String name, int age, String gender, String startingLocation) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.startingLocation = startingLocation;

        String query = String.format("insert into passenger(name,startingLocation, gender, age) values('%s', '%s', '%s', %d)", name, startingLocation, gender, age);

        db.exec(query);

        System.out.println("Created passenger.");
    }


}