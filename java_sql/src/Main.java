import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class Main {

    static DB db = new DB();

    public static void main(String[] args) throws SQLException {

        //Airport airport1 =new Airport("stefanstefan2","suhodol",2);
        //Airport airport2 =new Airport("mitakamadafaka","getomilev",3);

        //Flight flight = new Flight("suhodol","getomilev", Duration.ofHours(1));

        ResultSet rs = db.getSt().executeQuery("select * from flights");

        while(rs.next()) {
            System.out.printf("%d, %d, %d\n",
                    rs.getInt("originID"),
                    rs.getInt("destinationID"),
                    rs.getInt("duration")
            );
        }

    }
}