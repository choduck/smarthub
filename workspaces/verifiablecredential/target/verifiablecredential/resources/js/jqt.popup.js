/*

	jQuery Popup Class Beta. 2
    
    Auth : 정병태 (bentene@pulipinc.com)
    Create : 2011/01/12
    Update : 2011/11/28
	
 */

(function($) {
	
	var gOverlay,
		gOverlayCnt = 0,
		ie6 = ($.browser.msie && jQuery.browser.version == '6.0');
	
	$Popup = function (url, opt) {
		
		var element, overlay, parent, id, innerOverlay,
			innerOverlayCnt = 0,
			childEntry = [],
			options = {
				parent : 'body',
				top : 0, left : 0, width : 0, height : 0, 
				id : null,
				draggable : true,
				overlay : true,
				data : '',
				type : 'POST',
				load : function() {}, 
				close : function() {},
				error : function() { /*alert('on error!');*/ },
				_parentPopup : null
			};
		   
		init();

		/*
			팝업 초기화 함수
			@return void
		*/
		function init() {
			try {
				options = $.extend({}, options, opt);
				
				var overlayCss = {
					position : 'absolute', 
					display: 'none',
					top : '0px', 
					left : '0px', 
					backgroundColor : '#000000', 
					zIndex : 2000
				};
				
				if(options.overlay) {
					if(!!options._parentPopup) { // child popup인 경우 
						parent = options._parentPopup;
						if(parent._innerOverlayCnt == 0) {
							parent._innerOverlay = $("<div/>").appendTo(parent.element);
							parent._innerOverlay.css(overlayCss);
							parent._innerOverlay.css({
								width : parent.element.outerWidth({margin:true})+"px", 
								height : parent.element.outerHeight({margin:true})+"px"
							});
							if(ie6) $Util.hideOverlapElement(parent._innerOverlay, "select");
							parent._innerOverlay.fadeTo("fast",0.3);
						}
						parent._innerOverlayCnt++;
					} else { // child popup이 아닌 경우
						if(gOverlayCnt == 0) {
							if(!gOverlay) {
								var pageSizeMap = $Util.getPageSize();
								gOverlay = $("<div/>").appendTo("body");
								gOverlay.css(overlayCss);
								gOverlay.css({
									width : "100%", 
									height : pageSizeMap.pageHeight+"px"
								});
							}
							if(ie6) $Util.hideOverlapElement(gOverlay, "select");
							gOverlay.fadeTo("fast",0.7);
						}
						gOverlayCnt++;
					}
				}
				if(!!options.id && $("#"+options.id).size() > 0) 
					element = $("#"+options.id);
				else {
					element = $("<div/>").appendTo($(options.parent));
					element.css({
						position: "absolute",
						visibility: "hidden",
						zIndex: !!options._parentPopup ? 
								(options._parentPopup.element.css("zIndex")+1)
								:options.overlay ? 3000 : 1000
					});
				}
				
				if(options.id) element.attr("id",options.id);
				
				load(url, {}, function() {
					element.fadeIn("fast", function() {
						if(options.draggable)element.draggable({containment: "window"});
						options.load();
					});
				});
			} catch(e) {
				alert("Javascript Error \n" + e.message);
				return false;
			}
		}

		/*
			팝업 페이지 로드 함수
			@return void
		*/
		function load(url, opt, callback) {
			try {

				options = $.extend(options, (opt || {}));
				
				$.ajax({
					url: url,
					data: options.data,
					type: options.type,
					error: options.error,
					success: function (data, textStatus) {
						element.html(data);
						var pageSizeMap = $Util.getPageSize();
						var pageScrollMap = $Util.getPageScroll();
						var width = (options.width ? options.width : element.children("div")[0].offsetWidth);
						var height = (options.height ? options.height : element.children("div")[0].offsetHeight);
						var toTop = pageScrollMap.top + ((pageSizeMap.windowHeight - height) / 2);
						var top = (options.top ? options.top : toTop > 0? toTop : 0);
						var toLeft = pageScrollMap.left + ((pageSizeMap.windowWidth - width) / 2);
						var left = (options.left ? options.left : toLeft > 0? toLeft : 0);
						element.find(".closePopup").css({cursor:'pointer'}).click(close);
						if(!options.overlay && ie6) $Util.hideOverlapElement(element, "select");
						element.css({
							visibility:'visible',
							width: width,
							height: height,
							top: (top || 0),
							left: (left || 0)
						});
						closeChildren();
						if(!!callback) callback();
					}
				});
				
			} catch(e) {
				alert("Javascript Error \n" + e.message);
				return false;
			}
		}

		/*
			child Popup 로드 함수
			@return childElement
		*/
		function child(childUrl, childOpt) {
			childOpt = $.extend({}, childOpt, {_parentPopup:this});
			var childElement = new $Popup(childUrl, childOpt);
			childEntry.push(childElement);
			return childElement;
		}

		/*
			child Popup 해제 함수
			@return void
		*/
		function unsetChild(childElement) {
			childEntry = $.grep(childEntry, function(entry) {
				return entry != childElement;
			});
		}

		/*
			해당하는 모든 child Popup 닫기
			@return void
		*/
		function closeChildren() {
			$(childEntry).each(function(i, entry) {
				entry.close();
			});
			childEntry = [];
		}

		/*
			Popup 닫기
			@return void
		*/
		function close() {
			try {
				if(options.overlay) {
					if(!!options._parentPopup) {
						if(parent._innerOverlayCnt > 0) parent._innerOverlayCnt--;
						if(parent._innerOverlayCnt == 0) {
							parent._innerOverlay.fadeOut("fast");
							parent._innerOverlay("fast", function() {
								parent._innerOverlay.remove();
								parent._innerOverlay = null;
							});
							if(ie6) $Util.showOverlapElement(parent._innerOverlay, "select");
						}
					} else {
						if(gOverlayCnt > 0) gOverlayCnt--;
						if(gOverlayCnt == 0) {
							gOverlay.fadeOut("fast", function() {
								gOverlay.remove();
								gOverlay = null;
							});
							if(ie6) $Util.showOverlapElement(gOverlay, "select");
						}
					}
				}
				
				if(!!parent) options._parentPopup._unsetChild(this);
				
				closeChildren();
				
				element.fadeOut("fast", function() {
					element.remove();
					options.close();
					if(!options.overlay && ie6) $Util.showOverlapElement(element, "select");
				});
			} catch(e) {
				alert("Javascript Error \n" + e.message);
				return false;
			}
		}
		
		return {
			_innerOverlayCnt: innerOverlayCnt,
			_innerOverlay: innerOverlay,
			_unsetChild: unsetChild,
			child: child,
			close: close,
			load: load,
			element: element
		};
		
	};
})(jQuery);