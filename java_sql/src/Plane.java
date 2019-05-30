import java.sql.ResultSet;
import java.sql.SQLException;

public class Plane {
    DB db = new DB();


    public Plane( String model, int capacity){

        String query = String.format("insert into planes(model,capacity) values('%s', %d)",model,capacity);

        db.exec(query);

        System.out.println("Created plane.");


    }
}
