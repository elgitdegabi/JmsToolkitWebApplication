$.getScript("/js/ajax-loading.js");

function sendMessage(resource, message){
	$('#status').text("Sending...");

	var loading=$.loading();
	loading.open(); // loading spinner
	
	$.ajax({
		  dataType: 'json',
		  url: "/send/message",
		  data: {resource:resource,message:message},
		  error: function(result){
			  $('#status').text("Error sending the message...");
			  loading.close(); // loading spinner
		  },		  
		  success: function(){
			  $('#status').text("Message sent OK...");
			  loading.close(); // loading spinner			  
		  }
		});    		    		
}
