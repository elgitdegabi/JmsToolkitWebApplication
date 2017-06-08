$.getScript("/js/ajax-loading.js");

function browseForResults(selectedValue){
	var loading=$.loading();
	loading.open(); // loading spinner

	$('#browseResult').empty(); // clean the table
	$('#browseResult').append('<tr><td>Loading...</td><td></td><tr>');
      
	$.ajax({
		  dataType: 'json',
		  url: "/browse/list",
		  data: {resource:selectedValue},
		  error: function(){
			  $('#browseResult').empty(); // clean the table to show the error
			  $('#browseResult').append('<tr><td>Error!</td><td></td><tr>');
			  loading.close(); // loading spinner
		  },
		  success: function(json){
			  $('#browseResult').empty(); // clean the table to show the messages
			  $.each(json, function(i, value) {
		            $('#browseResult').append('<tr><td>' + i + '</td><td>' + value + '</td><tr>');
		        });
			  loading.close(); // loading spinner
		  }
		});    		    		
}
