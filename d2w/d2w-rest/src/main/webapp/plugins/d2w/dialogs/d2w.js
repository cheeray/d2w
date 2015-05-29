CKEDITOR.dialog.add("d2w", function(a) {
	var paths = a.d2w_paths;
	console.dir(paths);
	var b = a.lang.d2w, a = a.lang.common.generalTab;
	
	var items = [];
	for (var i = 0; i < paths.length; i++) {
		var obj = paths[i];
		console.dir(obj);
		for (var key in obj) {
			console.dir(key);
			var d = [];
			d.push(key);
			   items.push(d);
			}
	}
	console.dir(items);
	console.info(items);
	return {
		title : b.title,
		minWidth : 300,
		minHeight : 80,
		contents : [ {
			id : "info",
			label : a,
			title : a,
			elements : [ {
				id : "name",
				type : "select",
				style : "width: 100%;",
				label : b.name,
				items: items,
				"default" : "",
				required : !0,
			
				setup : function(a) {
					this.setValue(a.data.name)
				},
				commit : function(a) {
					a.setData("name", this.getValue())
				}
			} ]
		} ]
	}
});