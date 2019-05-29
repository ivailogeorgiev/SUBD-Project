import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

public class Flight {

    String origin;
    String destination;
    Duration duration;
    DB db = new DB();

    public Flight(String origin, String destination, Duration duration) throws SQLException {
        this.origin = origin;
        this.destination=destination;
        this.duration=duration;

        ResultSet rs = null;

        String query;

        Integer originID=null;
        Integer destinationID=null;

        query=String.format("select id from airports where location like '%s'",origin);
        rs = db.getSt().executeQuery(query);
        while(rs.next()){

            originID=rs.getInt("id");
        }


        query=String.format("select id from airports where location like '%s'",destination);
        rs = db.getSt().executeQuery(query);
        while(rs.next()){

            destinationID=rs.getInt("id");
        }

        if(originID!=null&&destinationID!=null){
            query=String.format("insert into flights(originID,destinationID,duration) values(%d,%d,%d)",originID,destinationID,duration.toMinutes());
            db.exec(query);
            System.out.println("flight created");
        }


    }





}
