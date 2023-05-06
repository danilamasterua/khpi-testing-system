package ds.testingsystem.database;

import java.sql.ResultSet;

public interface EntityMapper <T>{
    T mapRow(ResultSet rs);
}
