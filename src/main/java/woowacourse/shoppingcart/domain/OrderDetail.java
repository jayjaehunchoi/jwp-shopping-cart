package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class OrderDetail {

    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;

    public OrderDetail(final Long orderId, final Long productId, final int quantity) {
        this(null, orderId, productId, quantity);
    }

    public OrderDetail(final Long id, final Long orderId, final Long productId, final int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderDetail that = (OrderDetail) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
