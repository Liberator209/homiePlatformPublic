// 처음 5개 항목 리스트만 보이게 하기

$(".myreview_list_item").each(function(i){
  if(i > 4){
    $($(".myreview_list_item")[i]).addClass("d-none");
  }
 });

// 상품 더보기 버튼을 눌렀을 때 실행되는 함수

var cnt = 0;

$("#product_list_morebtn").on("click", function(){

  cnt++;
  $("#product_list_morebtn").removeClass("d-none");

  for(var i = 5; i < $(".myreview_list_item").length; i++){ 
    if(i == 5*cnt){
      $($(".myreview_list_item")[i]).removeClass("d-none");
      $($(".myreview_list_item")[i+1]).removeClass("d-none");
      $($(".myreview_list_item")[i+2]).removeClass("d-none");
      $($(".myreview_list_item")[i+3]).removeClass("d-none");
      $($(".myreview_list_item")[i+4]).removeClass("d-none");
    }else if( i > 5*cnt+4){
      $($(".myreview_list_item")[i]).addClass("d-none");
    }else if( i == $(".myreview_list_item").length -1){
      $("#product_list_morebtn").addClass("d-none");
    }
  };  
});

// 내가 주문한 상품, 작성한 리뷰 active 기능 

$(".list-group-item").on("click", function(){
   $(this).addClass("active");
   $(".list-group-item").not(this).removeClass("active");
 });
