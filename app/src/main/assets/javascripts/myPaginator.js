function tableRowsPagination(rows,          paginator,  lengthPerPage,	pageAnchorsPerBatch) {
	                       //rows: $(<tr>)  paginator: $(<div>)
	// reset/清空 頁碼
	$("ul.paginator", paginator).remove();

	//內部轉換paginator從<div>為<ul>
	$(paginator).append("<ul class='paginator'>");
	paginator = $("ul.paginator", paginator);

	// 計算總頁數
	var pages = Math.ceil(rows.length / lengthPerPage);

	//在paginator插入頁碼超連結 如: <li><a href='0'>1</a></li>
	for (var i = 0; i < pages; i++) {
		var pageItem = "<li><a href='#' class='pageAnchor' rel='" + i + "' >"
				+ (i + 1) + "</a></li>";
		$(paginator).append(pageItem);
	}
	//增加"上批"頁碼超連結
	var prevBatchButton = $("<li><a href='#' class='btn prevBatch' title='"
			+ ("上" + pageAnchorsPerBatch + "頁") + "'>&lt;</a></li>");
	$(paginator).prepend(prevBatchButton);
	//增加"下批"頁碼超連結            
	var nextBatchButton = $("<li><a href='#' class='btn nextBatch' title='"
			+ ("下" + pageAnchorsPerBatch + "頁") + "'>&gt;</a></li>");
	$(paginator).append(nextBatchButton);

	//計算pageAnchors之批次數
	var pageAnchors = $("li a.pageAnchor", paginator);
	var numOfBatch = Math.ceil(pageAnchors.length / pageAnchorsPerBatch);

	// 註冊"頁碼超連結"之click事件處理器
	$("li a.pageAnchor", paginator).click(
			function() {
				var p = parseInt($(this).attr("rel"));
				rows.hide().slice(p * lengthPerPage,
						p * lengthPerPage + lengthPerPage).show(); // 顯示第p頁資料

				// 設定被點選的li.page-item <a> 的 class='current'
				$("li a.pageAnchor", paginator).removeClass("current");
				$(this).addClass("current");

				return false;
			});

	//註冊"上/下批次按鈕"之 click事件處理器
	$("li a.btn", paginator).click(
			function() {

				if ($(this).is(".nextBatch")) {
					currentBatchOfPageAnchors += 1;
				} else if ($(this).is(".prevBatch")) {
					currentBatchOfPageAnchors -= 1;
				}
				pageAnchors.hide().slice(
						currentBatchOfPageAnchors * pageAnchorsPerBatch,
						currentBatchOfPageAnchors * pageAnchorsPerBatch
								+ pageAnchorsPerBatch).show();

				//處理「上/下批次按鈕」是否禁用					
				processBatchButton();

				//預設當新批次顯示後即顯示該批次的第一頁的資料列
				var activePageAnchor = $("li a.pageAnchor:visible:first",
						paginator);
				//activePageAnchor.addClass("current");
				activePageAnchor.click();

				return false;
			});

	//預設只顯示第一批次之頁碼
	pageAnchors.hide().slice(0, pageAnchorsPerBatch).show();

	//處理「上/下批次按鈕」是否禁用
	var currentBatchOfPageAnchors = 0;
	processBatchButton();

	//預設顯示第一頁
	$("li a.pageAnchor:visible:first", paginator).click();

	function processBatchButton() {
		//處理「上/下批次按鈕」是否禁用
		if (currentBatchOfPageAnchors < numOfBatch - 1)
			nextBatchButton.removeClass("disabled");
		else
			nextBatchButton.addClass("disabled");

		if (currentBatchOfPageAnchors > 0)
			prevBatchButton.removeClass("disabled");
		else
			prevBatchButton.addClass("disabled");
	}

}
