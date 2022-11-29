package repository;

import java.sql.Connection;

public class SubscriptionRepository extends BaseRepository{
    private Connection conn;

    public SubscriptionRepository(){
        this.conn = this.getDb().getConnection();
    }
}
