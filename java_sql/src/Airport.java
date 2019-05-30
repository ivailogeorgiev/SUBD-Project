import java.sql.ResultSet;
import java.sql.SQLException;

public class Airport {

    DB db =new DB();

    public Airport(String name, String location, int runways){

        try{
            db.getConn().setAutoCommit(false);

            //crate airport
            String query = String.format("insert into airports(name,location,runways) values('%s', '%s', %d)", name, location, runways);

            db.exec(query);

            //get id of newly created airport
            query=String.format("select * from airports where name = '%s'",name );

            ResultSet rs = db.getSt().executeQuery(query);

            Integer airportID=null;
            if(rs.next()){
                 airportID = rs.getInt("id");
            }

            rs.close();

            //link plane with airport
            query=String.format("update planes set airportID = %d where airportID is null limit 1",airportID);

            db.exec(query);

            db.getConn().commit();

            System.out.println("Airport created.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}