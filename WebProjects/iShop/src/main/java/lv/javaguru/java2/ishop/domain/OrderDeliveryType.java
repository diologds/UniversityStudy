package lv.javaguru.java2.ishop.domain;

/**
 * Created by nikolaylapshin on 08/02/14.
 */

public enum OrderDeliveryType {
    inoffice("inoffice"),
    tocustomeraddress ("tocustomeraddress");

    private String value;

    OrderDeliveryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static OrderDeliveryType getEnum(String value) {
        if(value == null)
            throw new IllegalArgumentException();
        for(OrderDeliveryType v : values())
            if(value.equalsIgnoreCase(v.getValue())) return v;
        throw new IllegalArgumentException();
    }


}