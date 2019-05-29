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

        Plane plane1 = new Plane("boing747",300);
        Plane plane2 = new Plane("boing69",69);

        ResultSet rs = db.getSt().executeQuery("select * from flights f inner join airports a on f.originID = a.id or f.destinationID = a.id");

        while(rs.next()) {
            System.out.printf("%d, %d, %d %s %s\n",
                    rs.getInt("originID"),
                    rs.getInt("destinationID"),
                    rs.getInt("duration"),
                    rs.getString("location"),
                    rs.getString("name")
            );
        }

        Scanner scanner = new Scanner(System.in);
        List<Flight> flights = new ArrayList<>();
        List<Airport> airports = new ArrayList<>();

        printMenu();
        while(scanner.hasNextLine()){
            String input = scanner.nextLine();

            switch (input){
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
                        System.out.printf("%d. Airport %s in %s with %d runways.\n",
                                i,
                                rs.getString("Name"),
                                rs.getString("Location"),
                                rs.getInt("Runways"));

                    }

                    printMenu();
                    break;

                case "F":
                    System.out.println("Creating flight.");
                    Scanner flightScanner = new Scanner(System.in);
                    System.out.println("Enter origin: ");
                    String origin = flightScanner.nextLine();

                    System.out.println("Enter location: ");
                    String destination = flightScanner.nextLine();

                    System.out.println("Enter duration(minutes): ");
                    int durationMin = flightScanner.nextInt();
                    flights.add(new Flight(origin, destination, Duration.ofMinutes(durationMin)));

                    printMenu();
                    break;

                case "AF":

                    rs = db.getSt().executeQuery("select f.id, a.name, an.name, f.duration from flights f inner join airports a on f.originID = a.id inner join airports an on f.destinationID = an.id");

                    for(int i = 1; rs.next(); i++){
                        System.out.printf("%d. Flight number %d from %s to %s has duration of %d minutes.\n",
                                i,
                                rs.getInt("f.id"),
                                rs.getString("a.name"),
                                rs.getString("an.name"),
                                rs.getInt("f.duration"));

                    }

                    printMenu();
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
        System.out.println("See all airports(AA).");
        System.out.println("Create a flight(F): You need to enter origin, destination and duration of the flight.");
        System.out.println("See all flights(AF).");
        System.out.println("Exit(E)");
    }

}