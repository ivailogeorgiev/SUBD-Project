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
        Passenger passenger = null;

        printMenu();
        while(scanner.hasNextLine()){
            String input = scanner.nextLine();

            switch (input){
                case "P":
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
                    passenger = new Passenger(pname, age, gender,startingLocation);

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
                    System.out.println("Enter origin: ");
                    String origin = flightScanner.nextLine();

                    System.out.println("Enter destination: ");
                    String destination = flightScanner.nextLine();

                    System.out.println("Enter duration(minutes): ");
                    int durationMin = flightScanner.nextInt();
                    flights.add(new Flight(origin, destination, Duration.ofMinutes(durationMin)));

                    printMenu();
                    break;

                case "AF":

                    rs = db.getSt().executeQuery("select f.id, a.Location, an.Location, f.duration from flights f\n" +
                            "inner join airports a on f.originID = a.id\n" +
                            "inner join airports an on f.destinationID = an.id\n" +
                            "inner join passenger p on a.Location = p.startingLocation");

                    for(int i = 1; rs.next(); i++){
                        System.out.printf("%d. Flight number %d from %s to %s has duration of %d minutes.\n",
                                i,
                                rs.getInt("f.id"),
                                rs.getString("a.Location"),
                                rs.getString("an.Location"),
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
        System.out.println("Create a flight(F): You need to enter origin, destination and duration of the flight.");
        System.out.println("Delete flight(DF).");
        System.out.println("See all flights(AF).");
        System.out.println("Exit(E)");
    }

}