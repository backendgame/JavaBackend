package gameonline.rest.controller_user;

import gameonline.config.API;
import gameonline.rest.MyRespone;
import gameonline.rest.controller_user.Payment.Service_Billing_PaymentCheckout;
import gameonline.rest.controller_user.Payment.Service_Billing_PaymentExecute;
import gameonline.rest.controller_user.Payment.Service_Billing_PaymentReview;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/lobby/billing")
public class Controller010_BillingScreen {
    public static String PATH = null;

    @PostMapping(value = API.PAYPAL_CHECKOUT_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE)
    public MyRespone authorizeTransaction(@RequestHeader("token") final String token,
                                          @RequestBody Service_Billing_PaymentCheckout service,
                                          HttpServletRequest request) {
        service.setRequest(request);
        return service.process(token);
    }

    @GetMapping(value = API.PAYPAL_REVIEW_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE)
    public MyRespone reviewTransaction(@RequestHeader("token") final String token,
                                          HttpServletRequest request) {
        Service_Billing_PaymentReview service = new Service_Billing_PaymentReview();
        service.setRequest(request);
        return service.process(token);
    }


    @PostMapping(value = API.PAYPAL_EXECUTE_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE)
    public MyRespone executePayment(@RequestHeader("token") final String token,
                                    @RequestBody Service_Billing_PaymentExecute service) {
        return service.process(token);
    }
}
