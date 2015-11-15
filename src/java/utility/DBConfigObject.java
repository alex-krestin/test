package utility;

import entity.TransferObject;

import java.io.Serializable;


public interface DBConfigObject extends Serializable {
    String getHost();
    Integer getPort();
    String getDatabase();
    String getUser();
    String getPassword();
    String getPath();
    String getTablePrefix();
}
