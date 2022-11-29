package repository;

import db.Mysql;
import lombok.Data;

@Data
public class BaseRepository {
    protected Mysql db;

    public BaseRepository(){
        this.db = new Mysql();
    }
}
