   // 옵션 선택 구현

		
var shoppingCart = (function() {
  // =============================
  // Private methods and propeties
  // =============================
  cart = [];
  
  // Constructor
  function Item(name, price, count, unit) {
    this.name = name;
    this.price = price;
    this.count = count;
    this.unit = unit;
  }
  
  // Save cart
  function saveCart() {
    sessionStorage.setItem('shoppingCart', JSON.stringify(cart));
  }
  
    // Load cart
  function loadCart() {
    cart = JSON.parse(sessionStorage.getItem('shoppingCart'));
  }
  if (sessionStorage.getItem("shoppingCart") != null) {
    loadCart();
  }
  

  // =============================
  // Public methods and propeties
  // =============================
  var obj = {};
  
  // Add to cart
  obj.addItemToCart = function(name, price, count, unit) {
    for(var item in cart) {
      if(cart[item].name === name) {
        cart[item].count ++;
        saveCart();
        return;
      }
    }
    var item = new Item(name, price, count, unit);
    cart.push(item);
    saveCart();
  }
  // Set count from item
  obj.setCountForItem = function(name, count) {
    for(var i in cart) {
      if (cart[i].name === name) {
        cart[i].count = count;
        break;
      }
    }
  };
  // Remove item from cart
  obj.removeItemFromCart = function(name) {
      for(var item in cart) {
        if(cart[item].name === name) {
          cart[item].count --;
          if(cart[item].count === 0) {
            cart.splice(item, 1);
          }
          break;
        }
    }
    saveCart();
  }

  // Remove all items from cart
  obj.removeItemFromCartAll = function(name) {
    for(var item in cart) {
      if(cart[item].name === name) {
        cart.splice(item, 1);
        break;
      }
    }
    saveCart();
  }

  // Clear cart
  obj.clearCart = function() {
    cart = [];
    saveCart();
  }

  // Count cart 
  obj.totalCount = function() {
    var totalCount = 0;
    for(var item in cart) {
      totalCount += cart[item].count;
    }
    return totalCount;
  }

  // Total cart
  obj.totalCart = function() {
    var totalCart = 0;
    for(var item in cart) {
      totalCart += cart[item].price * cart[item].count;
    }
    return Number(totalCart.toFixed(2));
  }

  // List cart
  obj.listCart = function() {
    var cartCopy = [];
    for(i in cart) {
      item = cart[i];
      itemCopy = {};
      for(p in item) {
        itemCopy[p] = item[p];

      }
      itemCopy.total = Number(item.price * item.count).toFixed(2);
      cartCopy.push(itemCopy)
    }
    return cartCopy;
  }

  return obj;
})();

function displayCart() {
  var cartArray = shoppingCart.listCart();
  var output = "";
  var total_price=0
  var total_count=0;
  for(var i in cartArray) {
	total_price += cartArray[i].price * cartArray[i].count;
	total_count += cartArray[i].count;
    output +=
      	"<div class='row'>"
      + 	"<input type='hidden' class='js_1 col-9 txt-tit-01 input_option' name='selected[" + i +"].option_name' value ='" + cartArray[i].name + "' readonly>"
      +		"<h6 class='js_1 col-9 txt-tit-01' name='selected[" + i +"].option_name'>"+cartArray[i].name+"</h6>"  
      + 	"<div class='col-3 text-right'>"
      + 		"<button type='button' class='clear-item button-close-01' aria-label='상품삭제' data-name=" + cartArray[i].name + ">"
      + 			"<i class='fas fa-times-circle btn-close-01' data-name="+ cartArray[i].name +">" + "</i>"
      + 		"</button>"
      + 	"</div>"
      + "</div>" 
      + 	"<div class='row'>"
      +			"<div class='col-6'>"
      +				"<input type='button' value = ' - ' class='minus-item pd_btn_style_02' data-name=" + cartArray[i].name + ">" 
      +				"<input type='hidden' name='selected[" + i +"].option_price' value=" + cartArray[i].price + ">"
      +				"<input type='hidden' name='selected[" + i +"].option_unit' value=" + cartArray[i].unit + ">"
      +				"<input class='pd_btn_style_01' readonly type='text' name='selected[" + i +"].option_amount' value=" + cartArray[i].count + " onchange='change();'" + "data-name = "+ cartArray[i].name + ">"
      +				"<input type='button' value=' + ' class='plus-item pd_btn_style_02' data-name=" + cartArray[i].name + ">" 
      +			"</div>"
      +			"<div class='col-6 text-right'>"
      +				"<div class='text-right pd_txt_style_01'>"
      +					"<input style='width:100%' type='text' name=" + cartArray[i].name +" readonly" + " value = '" + (cartArray[i].price * cartArray[i].count).format() + "원'>"
      +				"</div>"
      +			"</div>"
      +		"</div>"
      +	"</form>";
  }
  
  if(total_count>0)
  	document.getElementById('total_price').setAttribute("value", (total_price).format()); //추후 배송비에 따라 다르게할꺼임
  else
  	document.getElementById('total_price').setAttribute("value", 0); //추후 배송비에 따라 다르게할꺼임
  document.getElementById('total_count').innerText = total_count.toString() + "개"; 
  $('.pd_item_gr').html(output);
 // $('.total-cart').html(shoppingCart.totalCart());
  //$('.total-count').html(shoppingCart.totalCount());
}
shoppingCart.clearCart();

    $(".buy_btn").on("click", function(){
      $(".product_info").removeClass("d-none");
      $($(".btn-danger")[0]).html("결제하기");
      $(".btn-danger")[0].setAttribute("type", "submit");
      event.preventDefault();
      $(this).unbind('click').click()
      $(".btn-danger")[0].setAttribute("form", "order-form");

      });

	$('form').submit(function () {

    // Get the Login Name value and trim it
    var tp = $('#total_price')[0].value;
	
    // Check if empty of not
    if (tp  === '0') {
        alert('구매하실 품목을 선택해주세요.');
        return false;
    }
	});
	
    $(".b2c_aaa_gr").on("click", function(){
      $(".b2c_aaa_gr").toggleClass("op_on")
    });

    $(".bd_29NDZ").on("click", function(){
      $(".product_info").toggleClass("d-none")
    });

    $(".b2c_aaa_list").on("click", function(event){
      $(".pd_item_gr").removeClass("d-none");
      $(".pd_item_mg").removeClass("d-none");
	  
	  event.preventDefault();
	  var name = $(this).data('opt-name');
	  name=name.replaceAll(" ","");

	  var price = $(this).data('opt-price');
	  
	  var unit =$(this).data('opt-unit');

	  shoppingCart.addItemToCart(name,price,1,unit);
	  displayCart();
	});

  $(".button-close-01").on("click", function(){
    $(".pd_item_gr").toggleClass("d-none")
    $(".pd_item_mg").toggleClass("d-none")
  });
  
  $('.pd_item_gr').on("click", ".plus-item", function(){
  var name = $(this).data('name')
  shoppingCart.addItemToCart(name);
  displayCart();
})

  $('.pd_item_gr').on("click", ".minus-item", function(){
  var name = $(this).data('name')
  shoppingCart.removeItemFromCart(name);
  displayCart();
})

  $('.pd_item_gr').on("click", ".clear-item", function(){
  var name = $(this).data('name')
  shoppingCart.removeItemFromCartAll(name);
  displayCart();
})

// 수량, 총가격 계산

  // var sell_price;
  // var amount;

  // function init () {
  //   sell_price = document.form.sell_price.value;
  //   amount = document.form.amount.value;
  //   document.form.sum.value = sell_price;
  //   change();
  // }

  // function add () {
  //   hm = document.form.amount;
  //   sum = document.form.sum;
  //   hm.value ++ ;

  //   sum.value = parseInt(hm.value) * sell_price;
  // }

  // function del () {
  //   hm = document.form.amount;
  //   sum = document.form.sum;
  //     if (hm.value > 1) {
  //       hm.value -- ;
  //       sum.value = parseInt(hm.value) * sell_price;
  //     }
  // }

  // function change () {
  //   hm = document.form.amount;
  //   sum = document.form.sum;

  //     if (hm.value < 0) {
  //       hm.value = 0;
  //     }
  //   sum.value = parseInt(hm.value) * sell_price;
  // } 

  // URL 구현

    $(function(){
    $("#btn_url").on("click", function(){
      var urlText = document.getElementById("urlText");
      urlText.value = window.document.location.href;  // 현재 URL 을 세팅해 줍니다.
    })
    })
    function clip(){
    var url = '';
    var textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    url = window.document.location.href;
    textarea.value = url;
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);
    alert("URL이 복사되었습니다.")
    }
    
    // review display 구현

    Number.prototype.format = function(){
    if(this==0) return 0;
 
    var reg = /(^[+-]?\d+)(\d{3})/;
    var n = (this + '');
 
    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
 
    return n;
};

    // 상품 상세 펼처보기, 접기 버튼 구현

    $(".btn_pd_board_in_01").on("click", function(){
      $(".pd_board_gr").removeClass("h-min");  
    });

    $(".btn_pd_board_in_02").on("click", function(){
      $(".pd_board_gr").addClass("h-min");  
    });
   
    // review 더보기, 숨기기 버튼 구현

    // $(".btn_review_board_in_01").on("click", function(){
    //   $(".review_board_gr").each(function(i){
    //     $($(this)[i]).removeClass("h-min_v2");
    //   });
    // });

    // $(".btn_review_board_in_02").on("click", function(){
    //   $(".review_board_gr").each(function(){
    //     $($(this)[i]).addClass("h-min_v2");
    //   });
    // });
     
