import { httpRequest } from "./base.js";

const AUTHORIZE_PAYMENT = "/api/lobby/billing/Checkout-Payment";

async function validatePayment({amount, description}) {
    const resp = await httpRequest.post(AUTHORIZE_PAYMENT, {
        amount,
        description
    });
    return resp;
}

async function getSessionPayment() {
    const resp = await httpRequest.get("/api/lobby/billing/Review-Payment");
    return resp;
}

async function makePayment({paymentId, payerId}) {
    const resp = await httpRequest.post("/api/lobby/billing/Execute-Payment", {
        paymentId,
        payerId
    });
    return resp;
}
export {
    validatePayment,
    getSessionPayment,
    makePayment
}