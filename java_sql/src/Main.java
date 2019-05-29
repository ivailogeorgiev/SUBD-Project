import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static DB db = new DB();

    public static void main(String[] args) throws SQLException {


        //Airport airport1 =new Airport("stefanstefan2","suhodol",2);
        //Airport airport2 =new Airport("mitakamadafaka","getomilev",3);
        //Flight flight = new Flight("suhodol","getomilev", Duration.ofHours(1));

        ResultSet rs = null;

        Scanner scanner = new Scanner(System.in);
        List<Flight> flights = new ArrayList<>();
        List<Airport> airports = new ArrayList<>();
        List<Plane> planes = new ArrayList<>();
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

                        System.out.println("Enter Starting Location: ");
                        String startingLocation = passengerScanner.nextLine();
                        passenger = new Passenger(pname, age, gender, startingLocation);
                    }else{
                        System.out.println("You cannot create more than one passenger.");
                    }

                    printMenu();
                    break;

                case "SP":
                    rs = db.getSt().executeQuery("select * from airports");

                    while(rs.next()){
                        System.out.printf("Passenger with name %s of age of %d and %s. His starting location is %s.\n",
                                rs.getString("name"),
                                rs.getInt("age"),
                                rs.getString("gender"),
                                rs.getInt("startingLocation")
                        );
                    }

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
                    airports.add(new Airport(name, location, runways));

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

                case "DA":
                    System.out.print("Enter airport to delete(id): ");
                    Scanner idAScanner = new Scanner(System.in);

                    int idA = idAScanner.nextInt();

                    String queryA = String.format("delete from airports where id = %d", idA);
                    db.getSt().executeUpdate(queryA);

                    System.out.printf("Airport number %d deleted.\n", idA);
                    break;

                case "F":
                    System.out.println("Creating flight.");
                    Scanner flightScanner = new Scanner(System.in);

                    System.out.println("Enter plane model: ");
                    String planeModelF = flightScanner.nextLine();

                    System.out.println("Enter origin: ");
                    String origin = flightScanner.nextLine();

                    System.out.println("Enter destination: ");
                    String destination = flightScanner.nextLine();

                    System.out.println("Enter duration(minutes): ");
                    int durationMin = flightScanner.nextInt();
                    flights.add(new Flight(origin, destination, Duration.ofMinutes(durationMin), planeModelF));

                    printMenu();
                    break;

                case "FF":
                    rs = db.getSt().executeQuery("select f.id, a.Location, an.Location, f.duration from flights f inner join airports a on f.originID = a.id inner join airports an on f.destinationID = an.id inner join passenger p on a.Location = p.startingLocation");

                    for(int i = 1; rs.next(); i++){

                        System.out.printf("%s %s\n.",
                                rs.getString("a.Location"),
                                rs.getString("an.Location")
                        );
                    }

                    break;

                case "AF":
                    rs = db.getSt().executeQuery("select f.id, p.model, a.name, an.name, f.duration from flights f inner join airports a on f.originID = a.id inner join airports an on f.destinationID = an.id inner join planes p on p.id = f.planeID");

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

                case "DF":
                    System.out.print("Enter flight to delete(id): ");
                    Scanner idFScanner = new Scanner(System.in);

                    int idF = idFScanner.nextInt();

                    String queryF = String.format("delete from flights where id = %d", idF);
                    db.getSt().executeUpdate(queryF);

                    System.out.printf("Airport number %d deleted.\n", idF);
                    break;

                case "PL":
                    System.out.println("Creating plane.");
                    Scanner planeScanner = new Scanner(System.in);

                    System.out.println("Enter plane model: ");
                    String planeModelP = planeScanner.nextLine();

                    System.out.println("Enter capacity: ");
                    int capacity = planeScanner.nextInt();

                    planes.add(new Plane(planeModelP, capacity));

                    printMenu();
                    break;

                case "APL":
                    rs = db.getSt().executeQuery("select * from planes");

                    for(int i = 1; rs.next(); i++){
                        System.out.printf("%d. Plane number %d that is %s model and has %d capacity.\n",
                                i,
                                rs.getInt("id"),
                                rs.getString("model"),
                                rs.getInt("capacity")
                        );
                    }

                    break;

                case "DPL":
                    System.out.print("Enter plane to delete(id): ");
                    Scanner idPLScanner = new Scanner(System.in);

                    int idPL = idPLScanner.nextInt();

                    String queryPL = String.format("delete from planes where id = %d", idPL);
                    db.getSt().executeUpdate(queryPL);

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

        System.out.println("Create a flight(F): You need to enter plane model, origin, destination and duration of the flight.");
        System.out.println("Delete flight(DF).");
        System.out.println("See all flights(AF).");
        System.out.println("Filter flights(FF).");

        System.out.println("Create a plane(PL): You need to enter plane model and capacity.");
        System.out.println("Delete plane(DPL).");
        System.out.println("See all planes(APL).");

        System.out.println("Create a passenger(P): You need to enter name, age, gender starting location.");
        System.out.println("Show passenger(SP).");

        System.out.println("Exit(E)");
    }

}