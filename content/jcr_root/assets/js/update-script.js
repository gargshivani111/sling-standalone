/**
 *Update the content when it changes
 */

$(document).ready(function(){
	
	$('.changeable_title').blur(function() {
		var paths = window.location.pathname;
		var contents = $('.changeable_title').html();

		$.ajax({
			type : "GET",
			url : "/services/newsupdate?title=" + contents + "&path=" + paths,
			success : function(result) {

			}
		});

	});
	
	$('.changeable_desc').blur(function() {
		var paths = window.location.pathname;
		var contents = $('.changeable_desc').html();

		$.ajax({
			type : "GET",
			url : "/services/newsupdate?desc=" + contents + "&path=" + paths,
			success : function(result) {

			}
		});

	});

	$('.delete_news').click(function() {				
		var paths = window.location.pathname;
		$.ajax({
			type : "GET",
			url : "/services/newsupdate?path=" + paths,
			success : function(result) {
				if(result == "success")
				window.location= "/content/newsfeed.html";
			}	
		});

	});	
	
});
