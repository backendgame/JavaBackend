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

public class Service_Billing_PaymentCheckout extends BasePaymentTransaction {
    public short amount;
    public String description;

    private  HttpServletRequest request;

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    protected MyRespone executeTransaction() {
        try {
            String approvalLink = authorizePayment();
            Map resp = new HashMap();
            resp.put("amount", this.amount);
            resp.put("description", this.description);
            resp.put("payerId", customerUserId);
            resp.put("approvalLink", approvalLink);
            request.getSession().setAttribute("payment", resp);

            return new MyRespone(MyRespone.STATUS_Success, "Success", resp);
        }
        catch (Exception e) {
            return new MyRespone(MyRespone.STATUS_Invalid);
        }
    }

    private String authorizePayment() throws PayPalRESTException {
        // Lấy thông tin của người thanh toán (payer)
        // -> Có thể get user data trên DB để thêm thông tin user vào
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setPayerId(String.valueOf(customerUserId));
        payer.setPayerInfo(payerInfo);

        // Set redirect link khi user cancel hoặc review lại items đã thanh toán
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost/cancel");
        redirectUrls.setReturnUrl("http://localhost/top-up");

        List<Transaction> listTransaction = getTransactionInformation();

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        System.out.println("=== CREATED PAYMENT: ====");
        System.out.println(approvedPayment);

        return getApprovalLink(approvedPayment);
    }

    private List<Transaction> getTransactionInformation() {
        // Add info for Amount
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.valueOf(this.amount));

        // Add info for transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }
}
