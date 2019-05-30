import java.sql.ResultSet;
import java.sql.SQLException;

public class Airport {
    String name;
    String location;
    int runways;
    DB db =new DB();

    public Airport(String name, String location, int runways) throws SQLException {
        this.name=name;
        this.location=location;
        this.runways=runways;

        //crate airport
        String query = String.format("insert into airports(name,location,runways) values('%s', '%s', %d)", name, location, runways);

        db.exec(query);

        //get id of newly created airport
        query=String.format("select * from airports where name = '%s'",name );

        ResultSet rs = db.getSt().executeQuery(query);

        int airportID=-1;
        if(rs.next()){
             airportID = rs.getInt("id");
        }


        //link plane with airport
        query=String.format("update planes set airportID = %d where id = (select id from planes where airportID is null limit 1)",airportID);

        db.exec(query);

        System.out.println("Airport created.");
    }
}