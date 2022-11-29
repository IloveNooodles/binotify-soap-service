package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import enums.Status;
import util.StatusConverter;

import model.SubscriptionModel;

public class SubscriptionRepository extends BaseRepository{
    private Connection conn;

    public SubscriptionRepository(){
        this.conn = this.getDb().getConnection();
    }

    public SubscriptionModel getSubscriptionById(int creator_id, int subscriber_id) throws SQLException {
        String query = "SELECT * FROM Subscription WHERE creator_id = ? AND subscriber_id = ?";
        try {
            PreparedStatement getSubscriptionFromId = this.conn.prepareStatement(query);
            getSubscriptionFromId.setString(1, Integer.toString(creator_id));
            getSubscriptionFromId.setString(2, Integer.toString(subscriber_id));
            ResultSet rs = getSubscriptionFromId.executeQuery();

            if (!rs.isBeforeFirst()){
                return null;
            }

            SubscriptionModel sm = new SubscriptionModel();

            while(rs.next()){
                sm.setCreator_id(rs.getInt(1));
                sm.setSubscriber_id(rs.getInt(2));
                Status s = StatusConverter.StringToStatus(rs.getString(3));
                sm.setStatus(s);
            }

            return sm;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<SubscriptionModel> getSubscriptionBySubscriberId(int subscriber_id) throws SQLException {
        String query = "SELECT * FROM Subscription WHERE subscriber_id = ? AND creator_id = ?";
        try {
            PreparedStatement getSubscriptionFromSubscriptionId = this.conn.prepareStatement(query);
            getSubscriptionFromSubscriptionId.setString(1, Integer.toString(subscriber_id));
            ResultSet rs = getSubscriptionFromSubscriptionId.executeQuery();

            if (!rs.isBeforeFirst()){
                return null;
            }

            List<SubscriptionModel> LSM = new ArrayList<SubscriptionModel>();

            while(rs.next()){
                SubscriptionModel sm = new SubscriptionModel();
                sm.setCreator_id(rs.getInt(1));
                sm.setSubscriber_id(rs.getInt(2));
                Status s = StatusConverter.StringToStatus(rs.getString(3));
                sm.setStatus(s);
                LSM.add(sm);
            }

            return LSM;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void subscribe(int creator_id, int subscriber_id){
        String query = "INSERT INTO subscription (creator_id, subscriber_id) VALUES (?, ?)";
        try {
            PreparedStatement subscribe = this.conn.prepareStatement(query);
            subscribe.setString(1, Integer.toString(subscriber_id));
            subscribe.setString(2, Integer.toString(creator_id));
            subscribe.execute();
            this.conn.commit();
            subscribe.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateSubscription(int creator_id, int subscriber_id, Status s){
        String status = StatusConverter.StatusToString(s);
        String query = "UPDATE subscription SET status = ? WHERE creator_id = ? AND subscriber_id = ?";
        try {
            PreparedStatement updateSubscription = this.conn.prepareStatement(query);
            updateSubscription.setString(1, status);
            updateSubscription.setString(2, Integer.toString(creator_id));
            updateSubscription.setString(3, Integer.toString(subscriber_id));
            updateSubscription.executeUpdate();

            this.conn.commit();
            updateSubscription.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        SubscriptionRepository sr = new SubscriptionRepository();
        try {
            SubscriptionModel sm = new SubscriptionModel();
            sm = sr.getSubscriptionById(0, 2);
            System.out.println(sm);
            sr.updateSubscription(0, 2, Status.REJECTED);
            sm = sr.getSubscriptionById(0, 2);
            System.out.println(sm);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
