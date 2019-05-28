import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    static DB db = new DB();

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(DB.jdbc_driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Airport airport1 =new Airport("stefanstefan2","suhodol",69,2);
        Airport airport2 =new Airport("mitakamadafaka","getomilev",39,3);

        ResultSet rs = db.getSt().executeQuery("select * from airports");

        while (rs.next()){
            System.out.printf("%s, %s, %d, %d",
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getInt("capacity"),
                    rs.getInt("runways")
            );
        }

    }
}