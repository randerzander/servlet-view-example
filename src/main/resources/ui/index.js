$('#submit').on('click', function(){
  var $btn = $(this).button('loading');
  $.ajax({
    type: 'POST',
    url: 'services',
    data: $('#txtquery').val(),
    success: function(data){
      var result = JSON.parse(data);
      var csv = "data:text/csv;charset=utf-8,";

      //Append headers
      csv += result.columns.join(',') + '\n';
      $.each(result.columns, function(i, v){
        $('#rows>thead>tr').append('<th>' + v + '</th>');
      });
      //Append rows
      $.each(result.result, function(i, v){
        //Append cells
        csv += v.join(',') + '\n';
        var row = '<tr>';
        $.each(v, function(index, val){
          row += '<td>' + val + '</td>';
        });
        $('#rows>tbody').append(row + '</tr>');
      });
      $btn.button('reset');

      //Make csv results downloadable
      var link = document.createElement('a');
      link.setAttribute('href', encodeURI(csv));
      link.setAttribute('download', 'result.csv');
      link.innerHTML='Save CSV';
      $('#csvlink').removeClass('hidden').append(link);

      //Update panes collapse state
      $('#collapseOne').removeClass('in').addClass('collapsing');
      $('#collapseTwo').addClass('in');
    }
  });
});

/* For future integration with datatables or dynatable */
function toRecords(result){
  var data = [];
  $.each(result.result, function(i, v){
    var record = {};
    $.each(v, function(index, val){
      record[result.columns[index]] = val;
    })
    data.push(record);
  });
  return data;
}
