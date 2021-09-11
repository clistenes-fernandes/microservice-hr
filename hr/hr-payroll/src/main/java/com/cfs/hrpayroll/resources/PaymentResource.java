package com.cfs.hrpayroll.resources;

import com.cfs.hrpayroll.entities.Payment;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cfs.hrpayroll.services.PaymentService;

@RestController
@RequestMapping(value = "payments")
public class PaymentResource {


    @Autowired
    private PaymentService paymentService;

    @HystrixCommand(fallbackMethod = "getPaymentFallback")
    @GetMapping(value = "/{workerId}/days/{days}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days){
        Payment payment = paymentService.getPayment(workerId, days);
        return ResponseEntity.ok(payment);
    }

    public ResponseEntity<Payment> getPaymentFallback(Long workerId, Integer days){
        Payment payment =new Payment("John", 500.00, days);
        return ResponseEntity.ok(payment);
    }
}
