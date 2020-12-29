function ArticleDetail__Body__init() {
	if (toastui === undefined){
		return;
	}
	
	var body = document.querySelector('.article-detail__body');
	var initialValue = body.innerHTML.trim();
	
	var viewer = new toastui.Editor.factory({
		el: body,
		initialValue: initialValue,
		viewer: true
	});
}

ArticleDetail__Body__init();