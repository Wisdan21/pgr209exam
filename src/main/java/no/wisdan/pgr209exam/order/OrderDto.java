package no.wisdan.pgr209exam.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(long customerId,
                       List<Long> productId,
                       BigDecimal shippingCharge,
                       BigDecimal totalPrice,
                       boolean isShipped,
                       long shippingAddressId) {
}
