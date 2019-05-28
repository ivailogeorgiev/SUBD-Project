import java.sql.*;

public class DB {

    static final String db ="jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String jdbc_driver ="com.mysql.cj.jdbc.Driver";

    static final String username = "root";
    static final String password = "root";

    private Connection conn =null;
    private Statement st = null;


    public DB() {
        try {
            Class.forName(jdbc_driver);
            conn = DriverManager.getConnection(db,username,password);
            st = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public Statement getSt() {
        return st;
    }

    public Connection getConn() {
        return conn;
    }

    public ResultSet exec(String query){
        ResultSet rs=null;

        try {
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return rs;
        }


    }

}
