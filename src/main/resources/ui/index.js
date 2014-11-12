function submit(){
  $.ajax({
    type: 'POST',
    url: 'services',
    data: $('#txtquery').val(),
    success: function(data){
      var result = JSON.parse(data);
      $('#result').text(data);
    }
  });
}

function toRecords(result){
  var data = [];
  $.each(result.result, function(i, v){
    var record = {};
    $.each(v, function(index, val){
      record[result.columns[index]] = val;
    })
    data.push(record);
  });
}
