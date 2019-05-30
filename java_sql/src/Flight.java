import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

public class Flight {

    DB db = new DB();

    public Flight(String origin, String destination, Duration duration) throws SQLException {

        ResultSet rs = null;

        String query;

        Integer originID = null;
        Integer destinationID = null;
        Integer planeID = null;

        query = String.format("select id from airports where location like '%s'", origin);
        rs = db.getSt().executeQuery(query);
        if (rs.next()) {

            originID = rs.getInt("id");
        }


        query = String.format("select id from airports where location like '%s'", destination);
        rs = db.getSt().executeQuery(query);
        if (rs.next()) {

            destinationID = rs.getInt("id");
        }

        if (originID != null && destinationID != null) {

            query = String.format("select id from planes pl inner join airports a on pl.airportID = a.id where a.location = '%s' limit 1", origin);
            rs = db.getSt().executeQuery(query);
            if (rs.next()) {
                planeID = rs.getInt("id");
            }

            if (originID != null && destinationID != null) {
                query = String.format("insert into flights(originID,destinationID,planeID,duration) values(%d,%d,%d,%d)", originID, destinationID, planeID, duration.toMinutes());
                db.exec(query);
                System.out.println("Flight created.");
            } else {
                System.out.println("Could not create flight");
                if (originID == null) System.out.printf("No airport with location %s\n", origin);
                if (destinationID == null) System.out.printf("No airport with location %s\n", destination);
            }


        }


    }
}
