package com.mall.common.enume;

public enum  OrderStatusEnum {
    CREATE_NEW(0,"Pending payment"),
    PAYED(1,"paid"),
    SENDED(2,"Shipped"),
    RECIEVED(3,"Completed"),
    CANCLED(4,"Cancelled"),
    SERVICING(5,"After sale"),
    SERVICED(6,"After-sale completion");
    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
