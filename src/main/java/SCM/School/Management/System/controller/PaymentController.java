package SCM.School.Management.System.controller;

import SCM.School.Management.System.model.Payment;
import SCM.School.Management.System.model.Student;
import SCM.School.Management.System.service.PaymentService;
import SCM.School.Management.System.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final StudentService studentService;

    public PaymentController(PaymentService paymentService, StudentService studentService) {
        this.paymentService = paymentService;
        this.studentService = studentService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("students", studentService.getAllStudents());
        return "payment/addPayment";
    }

    @PostMapping("/save")
    public String savePayment(@ModelAttribute Payment payment, @RequestParam("studentId") Long studentId) {
        Student student = studentService.getStudentById(studentId);
        payment.setStudent(student);
        paymentService.savePayment(payment);
        return "redirect:/payment/list";
    }

    @GetMapping("/list")
    public String getAllPayments(Model model) {
        model.addAttribute("paymentList", paymentService.getAllPayments());
        return "payment/paymentList";
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "redirect:/payment/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Payment payment = paymentService.getPaymentById(id);
        model.addAttribute("payment", payment);
        model.addAttribute("students", studentService.getAllStudents());
        return "payment/addPayment"; // Reusing the add form for editing
    }
}