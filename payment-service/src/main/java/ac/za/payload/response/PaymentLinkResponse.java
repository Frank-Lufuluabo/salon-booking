package ac.za.payload.response;

import ac.za.domain.PaymentMethod;
import ac.za.model.PaymentOrder;
import ac.za.payload.dto.BookingDTO;
import ac.za.payload.dto.UserDTO;
import com.razorpay.PaymentLink;

public interface PaymentLinkResponse {

    PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id);

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId);


}
