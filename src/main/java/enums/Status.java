package enums;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum(String.class)
public enum Status {
    PENDING,
    ACCEPTED,
    REJECTED,
}
