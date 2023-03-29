package gameonline.rest.controller_user.Payment;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import gameonline.rest.BasePaymentTransaction;
import gameonline.rest.MyRespone;

import java.util.HashMap;
import java.util.Map;

public class Service_Billing_PaymentExecute extends BasePaymentTransaction {
    public String paymentId;
    public String payerId;

    @Override
    protected MyRespone executeTransaction() {
        try {
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(String.valueOf(payerId));

            Payment paymentRequest = new Payment().setId(paymentId);
            APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

            Payment payment = paymentRequest.execute(apiContext, paymentExecution);

            //Check payment in PayPal
            Payment paymentReview = getPaymentDetails(payment.getId());

            return resSuccess;
        }
        catch (Exception e) {
            return new MyRespone(MyRespone.STATUS_Invalid, e.getMessage(), null);
        }
    }
    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }
}
