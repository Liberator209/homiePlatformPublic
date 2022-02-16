// 오프라인 대형마트

$.ajax({
  type : 'GET',
  url : '/productList_Marketgraph',
  dataType : "json",
  success : function(data){

    var avg_prices=[];
    var dates=[];
    
    for(var i = 0; i < data.length; i++){

      let item_key = Object.keys(data[i]);
      let item_value = Object.values(data[i]);

      // 날짜, 시간별(x축)

      let time_value = item_value[0];
      dates.push(time_value);
      

      // 날짜, 시간별 100g당 평균가

      let avg_price = parseInt(item_value[6] / item_value[3] / 10);
      avg_prices.push(avg_price);
      $(".today_series_nb_02 strong").html(avg_prices[data.length - 1]);

    } 

    // 전일대비 변화율(오프라인 대형마트)

      let changed_per = parseInt((avg_prices[data.length - 1] - avg_prices[data.length - 3]) / avg_prices[data.length - 3] * 100);
      
      if(changed_per < 0){
        $(".today_series_nb_02").addClass("compare_per_m")
        $(".today_series_nb_02 span").html(Math.abs(changed_per) + "%");
      }else if(changed_per > 0){
        $(".today_series_nb_02").addClass("compare_per_p")
        $(".today_series_nb_02 span").html(Math.abs(changed_per) + "%");
      }else{
        $(".today_series_nb_02").addClass("compare_per_z")
        $(".today_series_nb_02 span").html(Math.abs(changed_per) + "%");
      }

    // 호미

    $.ajax({
      type : 'GET',
      url : '/productList_Homiegraph',
      dataType : "json",
      success : function(data2){

        var avg_prices2=[];
        var dates2=[];

        for(var i = 0; i < data2.length; i++){

          let item_key2 = Object.keys(data2[i]);
          let item_value2 = Object.values(data2[i]);

          // 날짜, 시간별(x축)

          let time_value2 = item_value2[0];
          dates2.push(time_value2);

          // 날짜, 시간별 100g당 평균가

          let avg_price2 = parseInt(item_value2[6] / item_value2[3] / 10);
          avg_prices2.push(avg_price2);
          $(".today_series_nb_01 strong").html(avg_prices2[data2.length - 1]);

        }

        // 전일대비 변화율(호미)

        let changed_per2 = parseInt((avg_prices2[data2.length - 1] - avg_prices2[data2.length - 3]) / avg_prices2[data2.length - 3] * 100);

        if(changed_per2 < 0){
          $(".today_series_nb_01").addClass("compare_per_m")
          $(".today_series_nb_01 span").html(Math.abs(changed_per2) + "%");

        }else if(changed_per2 > 0){
          $(".today_series_nb_01").addClass("compare_per_p")
          $(".today_series_nb_01 span").html(Math.abs(changed_per2) + "%");

        }else{
          $(".today_series_nb_01").addClass("compare_per_z")
          $(".today_series_nb_01 span").html(Math.abs(changed_per2) + "%");
        }
        var today = new Date();
        today.setDate(today.getDate() - 7);

        var replaced_dates_elements = [];
        var replaced_dates2_elements = [];
        
        for(var i = 0; i < dates.length; i++){
            
            var dates_element = dates[i]
            var replaced_dates_element = dates_element.replace(" GMT+9", "");
            replaced_dates_elements.push(replaced_dates_element);
        }

        for(var i = 0; i < dates2.length; i++){
            
            var dates2_element = dates2[i]
            var replaced_dates2_element = dates2_element.replace(" GMT+9", "");
            replaced_dates2_elements.push(replaced_dates2_element);    
        }

        var sum_array = replaced_dates_elements.concat(replaced_dates2_elements);
        var sum_array2 = sum_array.filter((item, pos) => sum_array.indexOf(item) === pos);
        var sum_array3 = sum_array2.sort() 

        // for(var i = 0; i < sum_array3.length; i++){

        //   // avg_prices.splice(32+(14*i),0,null);
        //   // avg_prices.splice(32+(14*i),0,null);
        //   avg_prices.splice(32+(14*i),0,avg_prices[32+(14*i)-1]);
        //   avg_prices.splice(32+(14*i),0,avg_prices[32+(14*i)-1]);

        // }

        for(var i = 0; i < (sum_array3.length - dates2.length); i++){

          avg_prices2.unshift(null);

        }
    
            var options = {
            chart: {
                type: 'line',
                height: 350
            },
            series: [{
                name: '호미상품',
                data: avg_prices2
            },
            {
                name: '오프라인 대형마트',
                data: avg_prices
            }
            ],
            dataLabels: {
                enabled: false
            },
            colors: ["#008ffb", "#FFDA00"],
            stroke: {
                curve: 'smooth'
            },
            xaxis: {
                type: 'datetime',
                datetimeUTC: false,
                categories: sum_array3,
                min: today.getTime(),
                labels: {
                    datetimeFormatter: {
                    year: 'yyyy년',
                    month: 'MM월',
                    day: 'MM/dd',
                    hour: 'HH:mm',
                    }
                },
            },
            tooltip: {
                    x: {
                       format: 'yyyy/MM/dd HH:mm'
                    },
            },
        };

        var chart = new ApexCharts(document.querySelector("#chart_01"), options);
        chart.render();
        
      }

    })    


  }  


})