$.getScript("/js/ajax-loading.js");

function purgeMessages(selectedValue){
	var loading=$.loading();
	loading.open(); // loading spinner
	
	$.ajax({
		  dataType: 'json',
		  url: "/purge/messages",
		  data: {resource:selectedValue},
		  error: function(result){
			  loading.close(); // loading spinner
		  },
		  success: function(result){
			  loading.close(); // loading spinner
		  }
		});    		    		
}
