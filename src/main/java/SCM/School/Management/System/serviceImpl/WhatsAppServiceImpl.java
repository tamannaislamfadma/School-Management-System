package SCM.School.Management.System.serviceImpl;

import SCM.School.Management.System.model.Payment;
import SCM.School.Management.System.service.WhatsAppService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.phone_number}")
    private String fromNumber;

    @Override
    public void sendPaymentNotification(Payment payment) {
        try {
            // Initialize Twilio
            Twilio.init(accountSid, authToken);

            // 1. Get Student Phone Number
            String rawNumber = payment.getStudent().getGuardianContact();
            if (rawNumber == null || rawNumber.isEmpty()) {
                System.out.println("No contact number found for student: " + payment.getStudent().getName());
                return;
            }

            // 2. Format Number to E.164 (Assuming BD Number if starts with 0)
            String formattedNumber = rawNumber;
            if (rawNumber.startsWith("0")) {
                formattedNumber = "+88" + rawNumber;
            }
            // Ensure it has "whatsapp:" prefix
            String toWhatsAppNumber = "whatsapp:" + formattedNumber;

            // 3. Construct the Message Body
            String messageBody = String.format(
                    "Please Make sure to payment!\n\n" +
                            "Student: %s\n" +
                            "Month: %s\n" +
                            "Total Amount: %.2f Tk\n\n" +
                            "Thank you for your payment.",
                    payment.getStudent().getName(),
                    payment.getMonth(),
                    payment.getTotalAmount()
            );

            // 4. Send Message
            Message message = Message.creator(
                    new PhoneNumber(toWhatsAppNumber),
                    new PhoneNumber(fromNumber),
                    messageBody
            ).create();

            System.out.println("WhatsApp Message Sent! SID: " + message.getSid());

        } catch (Exception e) {
            // Log error but don't stop the application
            System.err.println("Failed to send WhatsApp message: " + e.getMessage());
        }
    }
}