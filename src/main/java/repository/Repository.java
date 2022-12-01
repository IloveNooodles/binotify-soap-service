package repository;

import db.Mysql;
import lombok.Data;

@Data
public class Repository {
    protected Mysql db;

    public Repository(){
        this.db = new Mysql();
    }
}
