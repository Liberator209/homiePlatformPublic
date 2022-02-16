$.ajax({
	type : 'post',
	url:'getOrderView',
	contentType: "application/json",
	data: JSON.stringify({
	orderId : document.getElementById("orderId").value
}),
	dataType:"json",
	success: function(data){
		console.log(data)
		if(data.method == '카드'){
			document.getElementById("payMethod").innerHTML = data.card.intallmentPlanMonths == 0 ? "("+ String(data.card.intallmentPlanMonths) + ")" : data.card.company + "(일시불)" ;
			document.getElementById("cardnum").innerHTML = data.card.number;
			apdt = new Date(data.approvedAt);
			minutes = apdt.getMinutes() < 10 ? "0" + apdt.getMinutes() : apdt.getMinutes();
			document.getElementById("payApprovedAt").innerHTML = "입금완료<br>" +  apdt.getFullYear() + "-" + 
			(apdt.getMonth()+1) + "-" + apdt.getDate() + " " + apdt.getHours() + ":"
			+ minutes + ":" + apdt.getSeconds();
			}
		else if(data.method == '계좌이체'){
			document.getElementById("payMethod").innerHTML = data.transfer.bank + '(이체완료)'
			apdt = new Date(data.approvedAt);
			minutes = apdt.getMinutes() < 10 ? "0" + apdt.getMinutes() : apdt.getMinutes();
			document.getElementById("payApprovedAt").innerHTML = "입금완료<br>" +  apdt.getFullYear() + "-" + 
			(apdt.getMonth()+1) + "-" + apdt.getDate() + " " + apdt.getHours() + ":"
			+ minutes + ":" + apdt.getSeconds();
			}
		else if(data.method  == "가상계좌"){
			document.getElementById("payMethod").innerHTML = data.virtualAccount.bank;
			document.getElementById("cardnum").innerHTML = data.virtualAccount.accountNumber + "\n" + "(무통장입금)";
			if(data.status == "DONE"){
				apdt = new Date(data.approvedAt);
				minutes = apdt.getMinutes() < 10 ? "0" + apdt.getMinutes() : apdt.getMinutes();
				document.getElementById("payApprovedAt").innerHTML = "입금완료<br>" +  apdt.getFullYear() + "-" + 
				(apdt.getMonth()+1) + "-" + apdt.getDate() + " " + apdt.getHours() + ":"
				+ minutes + ":" + apdt.getSeconds();
				}
			else
				document.getElementById("payApprovedAt").innerHTML = "입금대기중";
			
		}
			
		
		document.getElementById("price").innerHTML = data.balanceAmount.toLocaleString();
	},
		error: function(data){
		alert(data);
	}

});