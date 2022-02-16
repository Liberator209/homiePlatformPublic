var sum1 = 0;
var sum2 = 0;
var sum3 = 0;

var sum_set1 = [];
var sum_set2 = [];
var sum_set3 = [];


$(".my_op_box .text-md strong").each(function(i){

  var item_cnt = $($(".my_op_box .text-md strong")[i]).text();

  if(item_cnt.indexOf("수확중") != -1){
    
    item_cnt = parseInt(item_cnt);
    sum1 += item_cnt;
    sum_set1.push(sum1);   
  }

  else if(item_cnt.indexOf("배송중") != -1){
    
    item_cnt = parseInt(item_cnt);
    sum2 += item_cnt;
    sum_set2.push(sum2);   
  }

  else if(item_cnt.indexOf("배송완료") != -1){
    
    item_cnt = parseInt(item_cnt);
    sum3 += item_cnt;
    sum_set3.push(sum3);   
  }
 
});

if(sum_set1.length != 0){
 $($(".status_cnt")[0]).text(sum_set1[sum_set1.length - 1]+"개");
}else{
 $($(".status_cnt")[0]).text(sum_set1.length+"개");
}

if(sum_set2.length != 0){
 $($(".status_cnt")[1]).text(sum_set2[sum_set2.length - 1]+"개");
}else{
 $($(".status_cnt")[1]).text(sum_set2.length+"개");
}

if(sum_set3.length != 0){
 $($(".status_cnt")[2]).text(sum_set3[sum_set3.length - 1]+"개");
}else{
 $($(".status_cnt")[2]).text(sum_set3.length+"개");
}

$(".my_op_box .text-md strong").each(function(i){

  var item_cnt = $($(".my_op_box .text-md strong")[i]).text();

  if(parseInt(item_cnt) == 0){
    
    $(this).addClass("d-none");
  
  }else{
    $(this).removeClass("d-none");
  }
});
  
// var cnt = 0;

// $(".text_style_04 strong").each(function(i){
//  if($($(".text_style_04 strong")[i]).text() == "수확중"){
//    cnt++;
//  }
// });
// $($(".status_cnt")[0]).text(cnt+"건");

// var cnt = 0;

// $(".text_style_04 strong").each(function(i){
//  if($($(".text_style_04 strong")[i]).text() == "배송중"){
//    cnt++;
//  }
// });
// $($(".status_cnt")[1]).text(cnt+"건");

// var cnt = 0;

// $(".text_style_04 strong").each(function(i){
//  if($($(".text_style_04 strong")[i]).text() == "배송완료"){
//    cnt++;
//  }
// });
// $($(".status_cnt")[2]).text(cnt+"건");