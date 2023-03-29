package gameonline.rest.controller_user.Payment;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import gameonline.rest.BasePaymentTransaction;
import gameonline.rest.MyRespone;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service_Billing_PaymentReview extends BasePaymentTransaction {
    private HttpServletRequest request;

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    protected MyRespone executeTransaction() {
        try {
            Object storedPaymentSession = request.getSession().getAttribute("payment");
            return new MyRespone(MyRespone.STATUS_Success, "Success", storedPaymentSession);
        }
        catch (Exception e) {
            return new MyRespone(MyRespone.STATUS_Invalid);
        }
    }
}
