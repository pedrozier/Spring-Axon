package br.com.standard.axon.query;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Order {

    private final String orderID;
    private final String productID;
    private OrderStatus orderStatus;

    public Order(String orderID, String productID) {
        this.orderID = orderID;
        this.productID = productID;
        orderStatus = OrderStatus.CREATED;
    }

    public void setOrderConfirmed() {
        this.orderStatus = orderStatus.CONFIRMED;
    }

    public void setOrderShipped() {
        this.orderStatus = OrderStatus.SHIPPED;
    }

    public enum OrderStatus {
        CREATED, CONFIRMED, SHIPPED
    }

}
