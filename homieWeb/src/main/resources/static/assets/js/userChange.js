
//Assigning DOM elements to variables
const form = document.getElementById('modify-form');
const userid = document.getElementById('input-id');
const email = document.getElementById('input-email');
const cur_password = document.getElementById('current-password');
const password = document.getElementById('input-password');
const password2 = document.getElementById('input-password-confirm');
const phone = document.getElementById('input-phone');
const username = document.getElementById('input-name');
const zip = document.getElementById('zip');
const addr = document.getElementById('addr');
const addr_spec = document.getElementById('ship_addr_spec');
const check_terms = document.getElementById('check-terms');
const check_privacy = document.getElementById('check-privacy');
const container = document.querySelector('.container');
const animateCircles = document.querySelector('.animate-circles');


//Listen for for submission
form.addEventListener('submit', (e) => {  
//prevent default loading when form is submitted
    //e.preventDefault();

  // Get values of form fields and assign to new variables
    //const emailValue = email.value;
    const curPasswordValue = cur_password.value;
    const passwordValue = password.value;
    const password2Value = password2.value;
    const phoneValue = phone.value;
    const usernameValue = username.value;
    const zipValue = zip.value;
    const addrValue = addr.value;
    const addr_specValue = addr_spec.value;
  
  //conditional statements to check if form value is valid ..... If form value is not valid an error function is triggered but if it is valid a success function is triggered
	
/*
    if (emailValue === '') {
        errorMessage(email, "이메일을 입력해 주세요");
    } else if (!validateEmail(emailValue)) {
        errorMessage(email, "이메일 형식이 올바르지 않습니다");
    } else {
        successMessage(email);
    }
*/


    if (curPasswordValue === '') {
		e.preventDefault();
        errorMessage(cur_password, "비밀번호를 입력해 주세요");
    } else {
        successMessage(cur_password);
    }

    if (phoneValue === '') {
		e.preventDefault();
        errorMessage(phone, "전화번호를 입력해 주세요");
    } else {
        successMessage(phone);
    }    
    
    if (usernameValue === '') {
		e.preventDefault();
        errorMessage(username, "이름을 입력해 주세요");
    } else {
        successMessage(username);
    }      

	if (password2Value !== passwordValue) {
		e.preventDefault();
        errorMessage(password2, "비밀번호가 일치하지 않습니다");
    } else {
        successMessage(password2);
    }
    
    if (zipValue === '') {
		e.preventDefault();
        errorMessage(zip, "주소를 입력해 주세요");
    } else {
        successMessage(zip);
    }    
    
    if (addrValue === '') {
		e.preventDefault();
        errorMessage(addr, "주소를 입력해 주세요");
    } else {
        successMessage(addr);
    }    
    
    if (addr_specValue === '') {
		e.preventDefault();
        errorMessage(addr_spec, "상세 주소를 입력해 주세요");
    } else {
        successMessage(addr_spec);
    }
               
});


// function to be triggered if form valu is not valid. This function simply adds the error CSS class and removes that of success if it exists


function errorMessage(value, message) {
    const formControl = value.parentElement.parentElement;
	window.alert(message);
    if (formControl.classList.contains('success')) {
        formControl.classList.remove('success');
        formControl.classList.add('error');
    } else {
        formControl.classList.add('error');
    }
    formControl.querySelector('.errorMessage').textContent = message;


}

// function to be triggered if form valu is valid. This function simply adds the success CSS class and removes that of error if it exists

function successMessage(value) {
    const formControl = value.parentElement;

    if (formControl.classList.contains('error')) {
        formControl.classList.remove('error');
        formControl.classList.add('success');
    } else {
        formControl.classList.add('success');
    }
}


function updateInput(value){
	const formControl = value.parentElement.parentElement;
	formControl.classList.remove('error')
	formControl.querySelector('.errorMessage').textContent = "";
}
$(document).ready(function() {
	/*
	email.onkeyup = function() {
		updateInput(email);
	};
	*/
	password.onkeyup = function() {
		updateInput(password);
	};
	password2.onkeyup = function() {
		updateInput(password2);
	};
	phone.onkeyup = function() {
		updateInput(phone);
	};
	username.onkeyup = function() {
		updateInput(username);
	};
	addr_spec.onkeyup = function() {
		updateInput(addr_spec);
	};

})

function findAddr(){
	updateInput(zip);
	updateInput(addr);
	new daum.Postcode({
        oncomplete: function(data) {
        	
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var jibunAddr = data.jibunAddress; // 지번 주소 변수
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zip').value = data.zonecode;
            if(roadAddr !== ''){
                document.getElementById("addr").value = roadAddr;
            } 
            else if(jibunAddr !== ''){
                document.getElementById("addr").value = jibunAddr;
            }
            document.querySelector("input[name=address_detail]").focus();
        }
    }).open();
}

$('#rec-input-phone').keydown(function(event) {
    var key = event.charCode || event.keyCode || 0;
    $text = $(this);
    if (key !== 8 && key !== 9) {
        if ($text.val().length === 3) {
            $text.val($text.val() + '-');
        }
        if ($text.val().length === 8) {
            $text.val($text.val() + '-');
        }
    }
 
    return (key == 8 || key == 9 || key == 46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105));          
});

$('#input-phone').keydown(function(event) {
    var key = event.charCode || event.keyCode || 0;
    $text = $(this);
    if (key !== 8 && key !== 9) {
        if ($text.val().length === 3) {
            $text.val($text.val() + '-');
        }
        if ($text.val().length === 8) {
            $text.val($text.val() + '-');
        }
    }
 
    return (key == 8 || key == 9 || key == 46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105));          
});
