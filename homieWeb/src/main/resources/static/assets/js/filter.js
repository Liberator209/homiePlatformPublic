$('#price_ascending').click(function() {
	price_per_unit_list=[]
	indexing=0;
	$('.product_list_item').each(function() {
		lists = []
		for(i in $(this).data('price')){
			lists.push($(this).data('price')[i].option_price / $(this).data('price')[i].option_unit)
		}
		obj1={index: indexing, price : Math.min.apply(Math, lists)};
		price_per_unit_list.push(obj1);
		indexing+=1;
	});
	
	price_per_unit_list.sort(function(a, b){
	  let priceA = a.price;
	  let priceB = b.price;
	  if (priceA < priceB) 
	  {
	    return -1;
	  }    
	  else if (priceA > priceB)
	  {
	    return 1;
	  }   
	  return 0;
	});
	elements=[]
		for(i in price_per_unit_list){
			elements.push($('.product_list_item')[price_per_unit_list[i].index]);
		}
	$('.product_list_item').remove()
	for(i in elements){
		document.getElementsByClassName("product_lists")[0].appendChild(elements[i]);
	}
})

$('#price_descending').click(function(){
	price_per_unit_list=[]
	indexing=0;
	$('.product_list_item').each(function() {
		lists = []
		for(i in $(this).data('price')){
			lists.push($(this).data('price')[i].option_price / $(this).data('price')[i].option_unit)
		}
		obj1={index: indexing, price : Math.min.apply(Math, lists)};
		price_per_unit_list.push(obj1);
		indexing+=1;
	});
	
	price_per_unit_list.sort(function(a, b){
	  let priceA = a.price;
	  let priceB = b.price;
	  if (priceA > priceB) 
	  {
	    return -1;
	  }    
	  else if (priceA < priceB)
	  {
	    return 1;
	  }   
	  return 0;
	});
	elements=[]
		for(i in price_per_unit_list){
			elements.push($('.product_list_item')[price_per_unit_list[i].index]);
		}
	$('.product_list_item').remove()
	for(i in elements){
		document.getElementsByClassName("product_lists")[0].appendChild(elements[i]);
	}
})

$('#review_count_descending').click(function() {
	review_by_index=[]
	indexing=0;
	$('.reviews').each(function() {
		obj1={index: indexing, reviews: $(this).data('review-count')};
		review_by_index.push(obj1);
		indexing+=1;
	});
	
	review_by_index.sort(function(a, b){
	  let reviewA = a.reviews;
	  let reviewB = b.reviews;
	  if (reviewA > reviewB) 
	  {
	    return -1;
	  }    
	  else if (reviewA < reviewB)
	  {
	    return 1;
	  }   
	  return 0;
	});
	elements=[]
		for(i in review_by_index){
			elements.push($('.product_list_item')[review_by_index[i].index]);
		}
	$('.product_list_item').remove()
	for(i in elements){
		document.getElementsByClassName("product_lists")[0].appendChild(elements[i]);
	}
})

$('#review_score_descending').click(function() {
	review_by_index=[]
	indexing=0;
	$('.reviews').each(function() {
		obj1={index: indexing, reviews: $(this).data('review-scores')};
		review_by_index.push(obj1);
		indexing+=1;
	});
	
	review_by_index.sort(function(a, b){
	  let reviewA = a.reviews;
	  let reviewB = b.reviews;
	  if (reviewA > reviewB) 
	  {
	    return -1;
	  }    
	  else if (reviewA < reviewB)
	  {
	    return 1;
	  }   
	  return 0;
	});
	elements=[]
		for(i in review_by_index){
			elements.push($('.product_list_item')[review_by_index[i].index]);
		}
	$('.product_list_item').remove()
	for(i in elements){
		document.getElementsByClassName("product_lists")[0].appendChild(elements[i]);
	}
})

$('#on_hit').click(function() {
	elements=[]
	id=[]
	$("div.hit_item").each(function(){
		elements.push($(this));
		id.push($(this).data('item_no'));
	})
	$("div.product_list_item").each(function(){
		if(!(id.includes($(this).data('item_no'))))
			elements.push($(this));
	})
	$('.product_list_item').remove()
	for(i in elements){
		document.getElementsByClassName("product_lists")[0].append(elements[i][0]);
	}
		
	$('#search_count').text(elements.length); 
})

$(document).ready(function() {
	all_counts=$('#search_count').text();
	$("#keyword").keyup(function(e) {
		if(e.keyCode === 13){
			$('.input-group-text').click();
		}
		var k = $(this).val();
	$(".product_list_item").hide();
	var temp = $("h5:contains('" + k + "'), span:contains('" + k + "')");
	if(k=="")
		$('#search_count').text(all_counts);
	else 
		$('#search_count').text(temp.length); 
	$(temp).parents(".product_list_item").each(function(){
		$(this).show();
		});
	})
})
