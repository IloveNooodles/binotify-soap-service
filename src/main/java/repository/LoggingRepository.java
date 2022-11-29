package repository;

import model.LoggingModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoggingRepository extends BaseRepository {
    private Connection conn;

    public LoggingRepository(){
        this.conn = this.getDb().getConnection();
    }

    public void addLogging(LoggingModel lm) throws SQLException {
        String query = "INSERT INTO Logging (description, IP, endpoint) VALUES(?, ?, ?)";
        try {
            PreparedStatement getLoggingById = this.conn.prepareStatement(query);
            getLoggingById.setString(1, lm.getDescription());
            getLoggingById.setString(2, lm.getIP());
            getLoggingById.setString(3, lm.getEndpoint());
            getLoggingById.execute();
            getLoggingById.close();
            this.conn.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<LoggingModel> getAllLogging() throws SQLException{
        String query = "SELECT * FROM Logging";
        try {
            PreparedStatement getLoggingById = this.conn.prepareStatement(query);
            ResultSet rs = getLoggingById.executeQuery();

            if(!rs.isBeforeFirst()){
                return null;
            }

            List<LoggingModel> listLm = new ArrayList<LoggingModel>();

            while(rs.next()){
                LoggingModel lmToAdd = new LoggingModel();
                lmToAdd.setId(rs.getInt(1));
                lmToAdd.setDescription(rs.getString(2));
                lmToAdd.setIP(rs.getString(3));
                lmToAdd.setEndpoint(rs.getString(4));
                lmToAdd.setTimestamp(rs.getTimestamp(5));
                listLm.add(lmToAdd);
            }

            return listLm;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public LoggingModel getLoggingById(int id) throws SQLException{
        String query = "SELECT * FROM Logging WHERE id = ?";
        try {
            PreparedStatement getLoggingById = this.conn.prepareStatement(query);
            getLoggingById.setString(1, Integer.toString(id));
            ResultSet rs = getLoggingById.executeQuery();

            if(!rs.isBeforeFirst()){
                return null;
            }

            LoggingModel newLm = new LoggingModel();

            while(rs.next()){
                newLm.setId(rs.getInt(1));
                newLm.setDescription(rs.getString(2));
                newLm.setIP(rs.getString(3));
                newLm.setEndpoint(rs.getString(4));
                newLm.setTimestamp(rs.getTimestamp(5));
            }

            return newLm;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args){
        LoggingRepository lr = new LoggingRepository();
        try {
            LoggingModel lmt = new LoggingModel();
            lmt.setIP("192.168.0.1");
            lmt.setDescription("mau lagu baru kaka");
            lmt.setEndpoint("/subscription");
            lr.addLogging(lmt);
            List<LoggingModel> lm = lr.getAllLogging();
            System.out.println(lm);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
