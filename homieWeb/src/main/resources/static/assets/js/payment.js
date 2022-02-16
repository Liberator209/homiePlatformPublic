document.getElementById('pay').onclick = function (){

  var type='';
  var method='';
  
	  for( a=0; a< $('input[id^="paymentType"]').length; ++a){
	    if($('input[id^="paymentType"]')[a].checked){
			type = $('input[id^="paymentType"]')[a].id.charAt($('input[id^="paymentType"]')[a].id.length-1);
		}
	}
	
	switch(type){
		case '1':
			method='카드'
			break;
		case '2':
			method='계좌이체'
			break;
		case '3':
			method='가상계좌'
			break;			
			}
  
  var chosen='';
  if(option.length >1)
  	chosen = option[0].option_name + ' 외 ' + parseInt(option.length -1) + '건';
  else
  	chosen = option[0].option_name;

$.ajax({
	type : 'post',
	url:'payRequest',
	contentType: "application/json",
	data: JSON.stringify({
	rec_name : document.getElementById("rec_name").value,
	rec_phone : document.getElementById("rec_phone1").value + document.getElementById("rec_phone2").value + document.getElementById("rec_phone3").value,
	zip : document.getElementById("zip").value,
	addr : document.getElementById("addr").value,
	addr_spec : document.getElementById("addr_spec").value,
	ship_request : document.getElementById("ship_request").value,
	sender_name : document.getElementById("sender_name").value,
	sender_phone : document.getElementById("sender_phone1").value + "-" + document.getElementById("sender_phone2").value + "-" + document.getElementById("sender_phone3").value,
	sender_email : document.getElementById("sender_email1").value + "@" + document.getElementById("sender_email2").value
}),
	dataType:"json",
	success: function(data){
		 if(data.price == -1){
			alert(data.orderer);
			return;
		}
		else{
		  var clientKey = data.clientKey;
 		  var tossPayments = TossPayments(clientKey);
		  tossPayments.requestPayment(method,{
		  amount: data.price,
		  orderId: data.orderId,
		  orderName: data.orderName,
		  customerName: document.getElementsByClassName('name_text_gr')[0].textContent.substring(0,document.getElementsByClassName('name_text_gr')[0].textContent.indexOf("님")),
		  customerEmail: data.orderer,
		  validHours: 1,
		  successUrl: window.location.origin + '/orderComplete',
		  failUrl: window.location.origin + '/fail',
		})
	}
	},
		error: function(data){
		alert(data);
		console.log(data);
	}

});

}
