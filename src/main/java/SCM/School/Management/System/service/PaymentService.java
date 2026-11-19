package SCM.School.Management.System.service;

import SCM.School.Management.System.model.Payment;
import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();
    void savePayment(Payment payment);
    Payment getPaymentById(Long id);
    void deletePayment(Long id);
}