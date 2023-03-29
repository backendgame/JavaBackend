function onTestPayment() {
  let payment = {
      'amount': 5,
      'description': "Hello",
  }
  console.log("hello");

  let paymentDto = $.ajax({
      url: 'http://localhost/api/lobby/billing/Checkout-Payment',
      type: 'POST',
      async: false,
      contentType: 'application/json',
      data: JSON.stringify(payment),
      dataType: 'json',
      beforeSend: function(request) {
          request.setRequestHeader("token", "token");
      },
      success: function(link) {
          return link;
      },
      error: function(error) {
          alert('Something went wrong!');
          return error;
      }
  }).responseJSON

  if(paymentDto.data && paymentDto.data.approvalLink) {
      window.location.href = paymentDto.data.approvalLink;
  }
}
