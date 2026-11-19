package SCM.School.Management.System.service;

import SCM.School.Management.System.model.Payment;

public interface WhatsAppService {
    void sendPaymentNotification(Payment payment);
}