public class Passenger {
    Flight flight;
    String name;
    int age;
    String gender;
    String location;
    DB db = new DB();

    public Passenger(String name, int age, String gender, String location) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;

        String query = String.format("insert into passenger(name,location, gender, age) values('%s', '%s', '%s', %d)", name, location, gender, age);

        db.exec(query);

        System.out.println("Created passenger.");
    }


}