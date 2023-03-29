var paymentDto = null;

function onBodyLoaded() {
    getPaymentFromSession();
}
function getPaymentFromSession() {
    paymentDto = $.ajax({
        url: 'http://localhost/api/lobby/billing/Review-Payment',
        type: 'GET',
        async: false,
        contentType: 'application/json',
        beforeSend: function(request) {
            request.setRequestHeader("token", "token");
        },
        success: function (payment) {
            return payment;
        },
        error: function (error) {
            alert('Something went wrong!');
            return error;
        }
    }).responseJSON;

    console.log("Payment: ", paymentDto);
}

function executePayment() {
    var url = new URL(window.location.href);
    console.log("url: ", url)
    paymentDto.paymentId = url.searchParams.get("paymentId");
    paymentDto.payerId = url.searchParams.get("PayerID");

    paymentDto = $.ajax({
        url: 'http://localhost/api/lobby/billing/Execute-Payment',
        type: 'POST',
        async: false,
        beforeSend: function(request) {
            request.setRequestHeader("token", "token");
        },
        contentType: 'application/json',
        data: JSON.stringify(paymentDto),
        dataType: 'json',
        success: function(payment) {
            return payment;
        },
        error: function(error) {
            return error;
        }
    }).responseJSON;

    // window.location.href = '/announcement.html?message=' + paymentDto.statusMessage;
}