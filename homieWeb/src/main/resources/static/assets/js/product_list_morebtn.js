// 프리미엄 항목은 default로 최상단 배치

$(".product_list_item").each(function(i){
  if($($(".product_list_item")[i]).hasClass("premium_item")){
   $(this).prependTo(".product_lists");
  };
 });

// 처음 화면 항목 리스트가 5개 미만일 때 상품 더보기 버튼 d-none

$(".product_list_item").each(function(){
  if( $(".product_list_item").length < 5 ){
    $("#product_list_morebtn").addClass("d-none");
  }
 });

// 처음 5개 항목 리스트만 보이게 하기

$(".product_list_item").each(function(i){
  if(i > 4){
    $($(".product_list_item")[i]).addClass("d-none");
  }
});


// 상품 더보기 버튼을 눌렀을 때 실행되는 함수

var cnt = 0;

$("#product_list_morebtn").on("click", function(){

  cnt++;
  $("#product_list_morebtn").removeClass("d-none");

  for(var i = 5; i < $(".product_list_item").length; i++){ 
    if(i == 5*cnt){
      $($(".product_list_item")[i]).removeClass("d-none");
      $($(".product_list_item")[i+1]).removeClass("d-none");
      $($(".product_list_item")[i+2]).removeClass("d-none");
      $($(".product_list_item")[i+3]).removeClass("d-none");
      $($(".product_list_item")[i+4]).removeClass("d-none");
    }else if( i > 5*cnt+4){
      $($(".product_list_item")[i]).addClass("d-none");
    }else if( i == $(".product_list_item").length -1){
      $("#product_list_morebtn").addClass("d-none");
    }
  };  
});

// 필터 적용된 항목들이 5개 미만일 때 상품 더보기 버튼 d-none

$("#on_hit").on("click", function(){
  $(".product_list_item").each(function(){
      if($(".product_list_item").length < 4){
       $("#product_list_morebtn").addClass("d-none");
      }
   });
 });
 
 $("#price_ascending").on("click", function(){
  $(".product_list_item").each(function(){
      if($(".product_list_item").length < 4){
       $("#product_list_morebtn").addClass("d-none");
      }
   });
 });

 $("#price_descending").on("click", function(){
  $(".product_list_item").each(function(){
      if($(".product_list_item").length < 4){
       $("#product_list_morebtn").addClass("d-none");
      }
   });
 });
 
 $("#review_count_descending").on("click", function(){
  $(".product_list_item").each(function(){
      if($(".product_list_item").length < 4){
       $("#product_list_morebtn").addClass("d-none");
      }
   });
 });

 $("#review_score_descending").on("click", function(){
  $(".product_list_item").each(function(){
      if($(".product_list_item").length < 4){
       $("#product_list_morebtn").addClass("d-none");
      }
   });
 });

 // 품종 선택 click 기능 구현

$(".cultivar_menu").on("click", function(){
  $(this).addClass("on_menu");
  $(".cultivar_menu").not(this).removeClass("on_menu");
});

// 구매하기 click -> 감귤 or 딸기
// $($(".list-group-item")[0]).on("click", function(){
//   $($(".cultivar_menu")[0]).addClass("on_menu");
//   $($(".cultivar_menu")[1]).removeClass("on_menu");
// });

// $($(".list-group-item")[1]).on("click", function(){
//   $($(".cultivar_menu")[1]).addClass("on_menu");
//   $($(".cultivar_menu")[0]).removeClass("on_menu");
// });
