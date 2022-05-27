$('#exampleModal1').on('show.bs.modal', function () {
	$.get("/modals/modal1", function (data) {
		$('#exampleModal1').find('.modal-body').html(data);
	})
});