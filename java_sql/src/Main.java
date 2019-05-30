import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;

public class Main {

    static DB db = new DB();

    public static void main(String[] args) throws SQLException {

        String [] planeModels =
                {
                        "Airbus A333-300","Airbus A340-300","Airbus A340-500","Airbus A350-900","Boeing 777-200",
                        "Airbus A340-600","Boeing 777-300","Boeing 747-400","Boeing 747-8","Airbus A380-800"
                };

        ResultSet rs;

        String query = "select count(*) from planes where airportID is null";

        rs = db.getSt().executeQuery(query);

        if(rs.next()){
            int count = rs.getInt("count(*)");
            if(count == 0){
                Arrays.stream(planeModels).forEach(p->new Plane(p,300));
            }
        }


        Scanner scanner = new Scanner(System.in);

        db.getSt().executeUpdate("delete from passenger");
        Passenger passenger = null;

        printMenu();

        while(scanner.hasNextLine()){

            String input = scanner.nextLine();

            switch (input){
                case "P":
                    if(passenger == null) {
                        System.out.println("Creating passenger.");
                        Scanner passengerScanner = new Scanner(System.in);

                        System.out.println("Enter name: ");
                        String pname = passengerScanner.nextLine();

                        System.out.println("Enter age: ");
                        int age = passengerScanner.nextInt();

                        passengerScanner.nextLine();

                        System.out.println("Enter gender: ");
                        String gender = passengerScanner.nextLine();

                        System.out.println("Enter location: ");
                        String startingLocation = passengerScanner.nextLine();
                        passenger = new Passenger(pname, age, gender, startingLocation);
                    }else{
                        System.out.println("You cannot create more than one passenger.");
                    }

                    printMenu();
                    break;

                case "SP":
                    rs = db.getSt().executeQuery("select * from passenger");

                    while(rs.next()){
                        System.out.printf("Passenger with name %s of age of %d and %s. His location is %s.\n",
                                rs.getString("name"),
                                rs.getInt("age"),
                                rs.getString("gender"),
                                rs.getString("location")
                        );
                    }

                    break;

                case "EP":
                    System.out.println("Editing passenger.");
                    Scanner passengerEScanner = new Scanner(System.in);

                    System.out.println("Enter new name: ");
                    String newname = passengerEScanner.nextLine();

                    System.out.println("Enter new age: ");
                    int newage = passengerEScanner.nextInt();

                    passengerEScanner.nextLine();

                    System.out.println("Enter new gender: ");
                    String newgender = passengerEScanner.nextLine();

                    System.out.println("Enter new location: ");
                    String newlocation = passengerEScanner.nextLine();

                    String queryEP = String.format("update passenger set name = '%s', age = %d, gender = '%s', location = '%s'", newname, newage, newgender, newlocation);
                    db.exec(queryEP);

                    System.out.println("Passenger has been edited.");
                    printMenu();
                    break;

                case "A":
                    System.out.println("Creating airport.");
                    Scanner airportScanner = new Scanner(System.in);

                    System.out.println("Enter name: ");
                    String name = airportScanner.nextLine();

                    System.out.println("Enter location: ");
                    String location = airportScanner.nextLine();

                    System.out.println("Enter runways: ");
                    int runways = airportScanner.nextInt();

                    new Airport(name, location, runways);

                    printMenu();
                    break;

                case "AA":
                    rs = db.getSt().executeQuery("select * from airports");

                    for(int i = 1; rs.next(); i++){
                        System.out.printf("%d. Airport %d - %s in %s with %d runways.\n",
                                i,
                                rs.getInt("id"),
                                rs.getString("Name"),
                                rs.getString("Location"),
                                rs.getInt("Runways"));
                    }

                    break;

                case "EA":
                    Scanner airportEScanner = new Scanner(System.in);
                    System.out.println("Editing airport.");
                    System.out.println("Which airport do you want to edit?");
                    int idEA = airportEScanner.nextInt();

                    airportEScanner.nextLine();
                    System.out.println("Enter new name: ");
                    String newName = airportEScanner.nextLine();

                    System.out.println("Enter new location: ");
                    String newLocation = airportEScanner.nextLine();

                    System.out.println("Enter new number of runways: ");
                    int newRunways = airportEScanner.nextInt();
                    airportEScanner.nextLine();

                    String queryEA = String.format("update airports set Name = '%s', Location = '%s', Runways = %d where id = %d", newName, newLocation, newRunways, idEA);
                    db.exec(queryEA);

                    System.out.printf("Airport %d has been edited.\n", idEA);
                    printMenu();
                    break;

                case "DA":
                    System.out.print("Enter airport to delete(id): ");
                    Scanner idAScanner = new Scanner(System.in);

                    int idA = idAScanner.nextInt();
                    db.getConn().setAutoCommit(false);
                    try {

                        String queryF = String.format("delete from flights where originID = %d or destinationID = %d", idA, idA);
                        db.exec(queryF);

                        String queryP = String.format("delete from planes where airportID = %d", idA);
                        db.exec(queryP);

                        String queryA = String.format("delete from airports where id = %d", idA);
                        db.exec(queryA);

                        db.getConn().commit();
                    }catch (SQLException e){
                        db.getConn().rollback();
                        throw e;
                    }finally {
                        db.getConn().setAutoCommit(true);
                    }

                    System.out.printf("Airport number %d deleted.\n", idA);
                    break;

                case "F":
                    System.out.println("Creating flight.");
                    Scanner flightScanner = new Scanner(System.in);

                    System.out.println("Enter origin: ");
                    String origin = flightScanner.nextLine();

                    System.out.println("Enter destination: ");
                    String destination = flightScanner.nextLine();

                    System.out.println("Enter duration(minutes): ");
                    int durationMin = flightScanner.nextInt();

                    new Flight(origin, destination, Duration.ofMinutes(durationMin));

                    printMenu();
                    break;

                case "FF":
                    if(passenger!=null){

                        rs = db.getSt().executeQuery("select f.id, a.Location, an.Location, pl.model, f.duration from flights f inner join airports a on f.originID = a.id inner join airports an on f.destinationID = an.id inner join passenger p on a.Location = p.location inner join planes pl on pl.airportID = a.id");

                        for(int i = 1; rs.next(); i++){

                            System.out.printf("%d. Flight number %d with %s plane from %s to %s has duration of %d minutes.\n",
                                    i,
                                    rs.getInt("f.id"),
                                    rs.getString("pl.model"),
                                    rs.getString("a.Location"),
                                    rs.getString("an.Location"),
                                    rs.getInt("f.duration"));
                        }
                    }
                    else {
                        System.out.println("You must first create a passenger.");
                    }


                    break;

                case "AF":
                    rs = db.getSt().executeQuery("select f.id, p.model, a.name, an.name, f.duration from flights f inner join airports a on f.originID = a.id inner join airports an on f.destinationID = an.id left join planes p on p.airportID = a.id");

                    for(int i = 1; rs.next(); i++){
                        System.out.printf("%d. Flight number %d with %s plane from %s to %s has duration of %d minutes.\n",
                                i,
                                rs.getInt("f.id"),
                                rs.getString("p.model"),
                                rs.getString("a.name"),
                                rs.getString("an.name"),
                                rs.getInt("f.duration"));

                    }

                    break;

                case "EF":
                    System.out.println("Editing flight.");
                    Scanner flightEScanner = new Scanner(System.in);

                    System.out.println("Which flight do you want to edit? ");
                    int flightID = flightEScanner.nextInt();

                    flightEScanner.nextLine();
                    System.out.println("Enter new origin: ");
                    String neworigin = flightEScanner.nextLine();

                    System.out.println("Enter new destination: ");
                    String newdestination = flightEScanner.nextLine();

                    System.out.println("Enter new duration(minutes): ");
                    int newdurationMin = flightEScanner.nextInt();

                    String queryY = String.format("select a.id, an.id from flights f inner join airports a on a.Location = '%s' inner join airports an on an.Location = '%s'", neworigin, newdestination);
                    rs = db.getSt().executeQuery(queryY);

                    String queryEF = null;
                    while(rs.next()) {
                        queryEF = String.format("update flights f set originID = %d, destinationID = %d, duration = %d where f.id = %d ", rs.getInt("a.id"), rs.getInt("an.id"), newdurationMin, flightID);
                    }
                    db.exec(queryEF);

                    System.out.println("Flight has been edited.");
                    printMenu();
                    break;


                case "DF":
                    System.out.print("Enter flight to delete(id): ");
                    Scanner idFScanner = new Scanner(System.in);

                    int idF = idFScanner.nextInt();

                    db.getConn().setAutoCommit(false);
                    try {

                        String queryDF = String.format("delete from flights where id = %d", idF);
                        db.getSt().executeUpdate(queryDF);
                        db.getConn().commit();

                    }catch (SQLException e) {
                        db.getConn().rollback();
                        throw e;
                    }finally {
                        db.getConn().setAutoCommit(true);
                    }

                    System.out.printf("Airport number %d deleted.\n", idF);
                    break;

                case "PL":
                    System.out.println("Creating plane.");
                    Scanner planeScanner = new Scanner(System.in);

                    System.out.println("Enter plane model: ");
                    String planeModelP = planeScanner.nextLine();

                    System.out.println("Enter capacity: ");
                    int capacity = planeScanner.nextInt();

                    new Plane(planeModelP, capacity);

                    printMenu();
                    break;

                case "APL":
                    rs = db.getSt().executeQuery("select * from planes p left join airports a on a.id = p.airportID");

                    for(int i = 1; rs.next(); i++){
                        String s = rs.getString("a.Location");
                        s=s==null?"doesn't belong to airport":String.format("belongs to %s",s);

                        System.out.printf("%d. Plane number %d that is %s model and has capacity of %s, %s.\n",
                                i,
                                rs.getInt("id"),
                                rs.getString("model"),
                                rs.getInt("capacity"),
                                s
                        );
                    }

                    break;

                case "DPL":
                    System.out.print("Enter plane to delete(id): ");
                    Scanner idPLScanner = new Scanner(System.in);

                    int idPL = idPLScanner.nextInt();

                    db.getConn().setAutoCommit(false);

                    try {

                        String queryPL = String.format("delete from planes where id = %d", idPL);
                        db.getSt().executeUpdate(queryPL);

                        db.getConn().commit();
                    }catch (SQLException e) {
                        db.getConn().rollback();
                        throw e;
                    }finally {
                        db.getConn().setAutoCommit(true);
                    }

                    System.out.printf("Plane number %d deleted.\n", idPL);
                    break;

                case "E":
                    System.out.println("Goodbye");
                    return;

                default:
                    System.out.println("Unknown command.");
                    break;
            }

        }

    }

    public static void printMenu(){
        System.out.println("Menu");
        System.out.println("Create an airport(A): You need to enter name, location and runways(number).");
        System.out.println("Delete airport(DA).");
        System.out.println("See all airports(AA).");
        System.out.println("Edit an airport(EA).");

        System.out.println("Create a flight(F): You need to enter plane model, origin, destination and duration of the flight.");
        System.out.println("Delete flight(DF).");
        System.out.println("See all flights(AF).");
        System.out.println("Filter flights(FF).");
        System.out.println("Edit flight(EF).");

        System.out.println("Create a plane(PL): You need to enter plane model and capacity.");
        System.out.println("Delete plane(DPL).");
        System.out.println("See all planes(APL).");

        System.out.println("Create a passenger(P): You need to enter name, age, gender starting location.");
        System.out.println("Show passenger(SP).");
        System.out.println("Edit passenger(EP).");

        System.out.println("Exit(E)");
    }

}