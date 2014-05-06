package lv.javaguru.java2.ishop.domain;

/**
 * Created by nikolaylapshin on 08/02/14.
 */
public enum OrderStatus {
    neworder("neworder"), inprogress("inprogress"), ready("ready"), delivered("delivered");


    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static OrderStatus getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (OrderStatus v : values())
            if (value.equalsIgnoreCase(v.getValue())) return v;
        throw new IllegalArgumentException();
    }



}