package model;

import com.google.protobuf.Internal;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class ResponseModel {
    private HashMap<String, Object> response;

    public ResponseModel(){
        this.response = new HashMap<>();
    }

    public Object getValue(String key){
        return this.response.get(key);
    }

    public void put(String key, Object value){
        this.response.put(key, value);
    }
}
