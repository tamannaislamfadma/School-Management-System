package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.Payment;
import SCM.School.Management.System.repository.PaymentRepository;
import SCM.School.Management.System.service.PaymentService;
import SCM.School.Management.System.service.WhatsAppService; // Import this
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final WhatsAppService whatsAppService; // Add Service

    public PaymentServiceImpl(PaymentRepository paymentRepository, WhatsAppService whatsAppService) {
        this.paymentRepository = paymentRepository;
        this.whatsAppService = whatsAppService;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public void savePayment(Payment payment) {
        // 1. Calculate Total
        double total = (payment.getAdmissionFee() != null ? payment.getAdmissionFee() : 0) +
                (payment.getTuitionFee() != null ? payment.getTuitionFee() : 0) +
                (payment.getLabFee() != null ? payment.getLabFee() : 0) +
                (payment.getExtraCurricularFee() != null ? payment.getExtraCurricularFee() : 0) +
                (payment.getDevelopmentFee() != null ? payment.getDevelopmentFee() : 0) +
                (payment.getLibraryFee() != null ? payment.getLibraryFee() : 0) +
                (payment.getExamFee() != null ? payment.getExamFee() : 0) +
                (payment.getInsuranceFee() != null ? payment.getInsuranceFee() : 0) +
                (payment.getOthers() != null ? payment.getOthers() : 0);

        payment.setTotalAmount(total);

        // 2. Save to Database
        paymentRepository.save(payment);

        // 3. TRIGGER WHATSAPP MESSAGE
        whatsAppService.sendPaymentNotification(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}