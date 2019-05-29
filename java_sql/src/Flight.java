import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

public class Flight {

    String origin;
    String destination;
    Duration duration;
    DB db = new DB();

    public Flight(String origin, String destination, Duration duration, String planeModel) throws SQLException {
        this.origin = origin;
        this.destination=destination;
        this.duration=duration;

        ResultSet rs = null;

        String query;

        Integer originID=null;
        Integer destinationID=null;
        Integer planeID=null;

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

        query=String.format("select id from planes where model like '%s'",planeModel);
        rs = db.getSt().executeQuery(query);
        while(rs.next()){

            planeID=rs.getInt("id");
        }

        if(originID!=null && destinationID!=null && planeID!=null){
            query=String.format("insert into flights(originID,destinationID,planeID,duration) values(%d,%d,%d,%d)",originID,destinationID,planeID,duration.toMinutes());
            db.exec(query);
            System.out.println("Flight created.");
        }
        else{
            System.out.println("Could not create flight");
            if(planeID==null)System.out.printf("No plane with model %s\n",planeModel);
            if(originID==null)System.out.printf("No airport with location %s\n",origin);
            if(destinationID==null)System.out.printf("No airport with location %s\n",destination);
        }


    }





}
