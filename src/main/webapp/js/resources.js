$.ajax({
	  dataType: 'json',
	  url: "/resources/get",
	  success: function(json){
		  $.each(json, function(i, value) {
	            $('#resources').append($('<option>').text(value).attr('value', i));
	        });
	  }
	});
