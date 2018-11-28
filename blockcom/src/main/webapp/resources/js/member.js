$(function(){
		$("#member tr").click(function(){
			var tr = $(this);
			var td = tr.children();
			var mem_idx = td.eq(0).text();
			var data = {};
			data["mem_idx"] = mem_idx;
			$.ajax({
				type : "POST",
				url  : "/blockcom/member",
				data : data,
				success : function() {
					location.href="/blockcom/boardlist?bf_cate_idx=1&page=1&perPageNumber=10&pageNumber=5";
				},
				error : function(){
					console.log('error');
				}	
			});
		});
	});