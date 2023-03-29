import {getQueryParams, setEventForGeneralComponent, useAuthentication, vModal} from "./global_functions.js";
import {getSessionPayment, makePayment, validatePayment} from "./apis/payment.js";
import {errorNotify, hideGlobalLoading, showGlobalLoading, successNotify} from "./utils.js";

$(document).ready(() => {
    $('.v-dropdown').dropdown();
    useAuthentication(true)
        .then(async (auth) => {
            if(!auth) return;
            setEventForGeneralComponent();
            $('.v-dropdown').dropdown();
        })
        .then(async () => {
            $(".paypal-buy-now-button").unbind("click").click(async e => {
                await authorizePaymentTransaction();
            })
            let url = window.location.href;
            if(url.includes("paymentId")) {
                let ssPayment = await getSessionPayment();
                $("#amount").val(ssPayment.data.amount);
                vModal("Confirm",
                modalRowStructure(ssPayment.data.amount),
                modalRowStructureFooter,
                "vModalClose",
                "modalStructureConfirm",
                async (close) => {
                    close();
                }, () => {
                    console.log("Closed");
                });
                $("#modalConfirmMakePayment").unbind("click").click(async e => {
                    let paymentId = getQueryParams("paymentId");
                    let payerId = getQueryParams("PayerID");
                    showGlobalLoading();
                    let respp = await makePayment({paymentId, payerId});
                    hideGlobalLoading();
                    if(respp.requestStatus) {
                        successNotify(null, "Make payment successfully!");
                        setTimeout(() => {
                            let url = window.location.href;
                            let pureUrl = url.split("?");
                            window.location.href = pureUrl[0];
                        }, 1400);
                    }
                    else {
                        errorNotify(null, "Error when make payment!");
                    }
                })
            }
        })
});

const authorizePaymentTransaction = async () => {
    showGlobalLoading();
    const resp = await validatePayment({
        amount: Number($("#amount").val()),
        description: "Top-up binary backend" + $("#amount").val()
    });
    hideGlobalLoading();
    if(resp.data) {
        showGlobalLoading();
        window.location.href = resp.data.approvalLink;
    }
}

const modalRowStructureFooter =`
    <div class="d-flex justify-content-end">
        <button type="button" class="mr-2 btn btn-outline-secondary vModalClose">Close</button>
        <button type="button" id="modalConfirmMakePayment" class="btn btn-primary ml-2">Confirm</button>
    </div>
`
const modalRowStructure = (amount) => {
    return ` <div>
        Are you sure to want to make payment ${amount}$?
    </div>`
}