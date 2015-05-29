(function() {
	CKEDITOR.plugins
			.add(
					"d2w",
					{
						requires : "widget,dialog",
						lang : "en,en-gb,eo,es,et,eu,fa,fi,fr,fr-ca,gl,he,hr,hu,id,it,ja,km,ko,ku,lv,nb,nl,no,pl,pt,pt-br,ru,si,sk,sl,sq,sv,th,tr,tt,ug,uk,vi,zh,zh-cn",
						icons : "d2w",
						hidpi : !0,
						onLoad : function() {
							CKEDITOR.addCss(".cke_d2w{background-color:#ff0}")
						},
						ajax : function() {
							if (!CKEDITOR.env.ie
									|| "file:" != location.protocol)
								try {
									return new XMLHttpRequest;
								} catch (a) {
								}
							try {
								return new ActiveXObject("Msxml2.XMLHTTP");
							} catch (b) {
							}
							try {
								return new ActiveXObject("Microsoft.XMLHTTP");
							} catch (e) {
							}
							return null;
						},
						init : function(a) {
							var b = a.lang.d2w;
							console.dir(a);
							console.dir(a.config.d2w_url);
							var xmlhttp = this.ajax();
							xmlhttp.onreadystatechange = function() {
								if (xmlhttp.readyState == 4
										&& xmlhttp.status == 200) {
									a.d2w_paths = JSON
											.parse(xmlhttp.responseText);
									console.dir(a.d2w_paths);
								}
							}
							xmlhttp.open("GET", a.config.d2w_url, true);
							xmlhttp.send();

							/*
							 * var data = CKEDITOR.ajax.load( a.config.d2w_url );
							 * console.dir(data);
							 */
							CKEDITOR.dialog.add("d2w", this.path
									+ "dialogs/d2w.js");
							a.widgets.add("d2w", {
								dialog : "d2w",
								pathName : b.pathName,
								template : '<span class="cke_d2w">[[]]</span>',
								downcast : function() {
									return new CKEDITOR.htmlParser.text("[["
											+ this.data.name + "]]")
								},
								init : function() {
									this.setData("name", this.element.getText()
											.slice(2, -2))
								},
								data : function() {
									this.element.setText("[[" + this.data.name
											+ "]]")
								}
							});
							a.ui.addButton && a.ui.addButton("Created2w", {
								label : b.toolbar,
								command : "d2w",
								toolbar : "insert,5",
								icon : "d2w"
							})
						},
						afterInit : function(a) {
							var b = /\[\[([^\[\]])+\]\]/g;
							a.dataProcessor.dataFilter
									.addRules({
										text : function(f, d) {
											var e = d.parent
													&& CKEDITOR.dtd[d.parent.name];
											if (!e || e.span)
												return f
														.replace(
																b,
																function(b) {
																	var c = null, c = new CKEDITOR.htmlParser.element(
																			"span",
																			{
																				"class" : "cke_d2w"
																			});
																	c
																			.add(new CKEDITOR.htmlParser.text(
																					b));
																	c = a.widgets
																			.wrapElement(
																					c,
																					"d2w");
																	return c
																			.getOuterHtml()
																})
										}
									})
						}
					})
})();