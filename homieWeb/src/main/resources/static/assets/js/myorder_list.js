// 처음 5개 항목 리스트만 보이게 하기

$(".myorder_list_item").each(function(i){
  if(i > 4){
    $($(".myorder_list_item")[i]).addClass("d-none");
  }
 });
 

// 처음 화면 항목 리스트가 5개 미만일 때 상품 더보기 버튼 d-none

 $(".myorder_list_item").each(function(){
  if( $(".myorder_list_item").length < 5 ){
    $("#product_list_morebtn").addClass("d-none");
  }
 });

 
// 주문한 상품이 없을 때 상품 더보기 버튼 d-none

 $(".order_item_gr").each(function(){
  if( $(".order_item_gr").length < 2 ){
    $("#product_list_morebtn").addClass("d-none");
  }
 });



// 상품 더보기 버튼(주문리스트 남은 갯수 포함) 

 $(".more_btn").text($(".myorder_list_item").length -5 + "개 더보기");



// 상품 더보기 버튼을 눌렀을 때 실행되는 함수

 $("#product_list_morebtn").on("click", function(){

   $("#product_list_morebtn").addClass("d-none");
   $(".myorder_list_item").removeClass("d-none");

 });   

// var cnt = 0;

// $("#product_list_morebtn").on("click", function(){

//   cnt++;
//   $("#product_list_morebtn").removeClass("d-none");

//   for(var i = 5; i < $(".myorder_list_item").length; i++){ 
//     if(i == 5*cnt){
//       $($(".myorder_list_item")[i]).removeClass("d-none");
//       $($(".myorder_list_item")[i+1]).removeClass("d-none");
//       $($(".myorder_list_item")[i+2]).removeClass("d-none");
//       $($(".myorder_list_item")[i+3]).removeClass("d-none");
//       $($(".myorder_list_item")[i+4]).removeClass("d-none");
//     }else if( i > 5*cnt+4){
//       $($(".myorder_list_item")[i]).addClass("d-none");
//     }else if( i == $(".myorder_list_item").length -1){
//       $("#product_list_morebtn").addClass("d-none");
//     }
//   };  
// });

// 내가 주문한 상품, 작성한 리뷰 active 기능 

$(".list-group-item").on("click", function(){
   $(this).addClass("active");
   $(".list-group-item").not(this).removeClass("active");
 });

// 내가 작성한 리뷰 불러오기
function findReview(reviews){

}