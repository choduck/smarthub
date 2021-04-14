/*

	jQTree Form Class Beta. 2
    
    Auth : 정병태 (bentene@pulipinc.com)
    Create : 2011/01/21
    Update : 2011/11/28
	
 */ 

(function($) {
	   
	$Form = function (formName, opt) {
		
		var form, 
			entryInspector = [], 
			methods = {},
			completeAjax = true,
			defaultField = [],
			options = {
				loading : true,
				autoValidate : false,
				ajax : false,
				defaultType : {
					type : false,
					text : '',
					minLength : undefined,
					maxLength : undefined,
					require : false,
					ignores : []
				},
				async : true,
				success : function(data, textStatus, jqXHR) {
					
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(jqXHR.status+' : '+ errorThrown );
				},
				complete : function() {
					
				}
			}, 
			optionsField = {
				require : true,
				minLength : undefined,
				maxLength : undefined,
				message : undefined, 
				filter : function() {return true;}
			},
			ignoresLengthCheck = ["ssn","attachFile","attachImage","userId","userPw", "editor"],
			ignoresRequireCheck = ["editor"];
	   
		init();
	   
		// 클래스 생성자 함수
		function init() {
			$.extend(options, opt || {});
			form = $('form[name="'+formName+'"]').get(0);
			if(!form) form = document[formName];
			if(!form) form = document.getElementById(formName);
			$(form).submit(_submit);
		}
		
		// 검사할 Input을 등록한다. 	
		function setInspector(type, name, text, option) {
			var params = { type:type, name:name, text:text, option:option};
			entryInspector.push(params);
		}
		
		// 해당폼내에 등록된 Input의 값을 검사한다.
		function validate() {
			try {
				var result = true;
				$.each(entryInspector, function(i, params) {
					if(params.type == 'func') {
						var func = params.option;
						result = !!func(form);
					} else {
						var _option = $.extend({}, optionsField, params.option);
						params.option = _option;
						
						// 1. filter check
						var _filted = true;
						if(typeof _option.filter == "function") _filted = _option.filter(form);
						if(_filted) {
							// 2. required check
							if(!_option.require && $.inArray(params.type, ignoresRequireCheck) < 0) {
								
								if($.isArray(params.name)) {
									var _break = true;
									for(i=0; i < params.name.length; i++) {
										var _params = $.extend({}, params, {name:params.name[i]});
										
										if(!$_FormInspector.require(form,_params)) {
											_break = false;
											break;
										}
									}
									if(_break) {
										result = true;
										return result;
									}
								} else {
									if(!$_FormInspector.require(form,params)) {
										result = !_option.require;
										return result;
									}
								}
							}
							
							// 3. length check
							if((!!_option.minLength || !!_option.maxLength) 
									&& $.inArray(params.type,ignoresLengthCheck) < 0) {
								if(!$_FormInspector.charLength(form,params)) {
									result = false;
									return result;
								}
							}
							
							
							// 4. default inspect
							result = $_FormInspector[params.type](form,params);
							
						}
						
					}
					
					return result;
				});
				
				return result;
				
			} catch(e) {
				//if(console) console.error(e);
				alert("Javascript Error \n" + e.message);
				return false;
			}
		}
		
		// validate 및 ajaxSubmit 처리 
		function _submit(params) {
			
				var optionsSubmit = {};
		
				$.extend(optionsSubmit, options, params);
				
				if(!optionsSubmit.autoValidate || validate()) {
					if(!!optionsSubmit.defaultType.type) {
						if(optionsSubmit.defaultType.type == 'money') {
							$.each(defaultField, function(i, name){
								var item = form[name];
								item.value = $Util.removeComma(item.value);
							});
						}
					}
				
					if(optionsSubmit.ajax) {
//						var data = $(form).serialize();
						var data = {};
						if($.browser.webkit) {
							$(form).each(function(i,data){
								console.info(data);
							});
						}
						if(completeAjax) {
							completeAjax = false;
//							$.ajax({
							if(optionsSubmit.loading) $Util.showOverlay();
							$(form).ajaxSubmit({
								url:form.action||'',
//								data:data,
								async: optionsSubmit.async,
								dataType : optionsSubmit.dataType || '',
								success : function(data, textStatus, jqXHR) {
									if(optionsSubmit.loading) $Util.hideOverlay();
									completeAjax = true;
									optionsSubmit.success(data, textStatus, jqXHR);
								},
								error : function(jqXHR,textStatus, errorThrown) {
									if(optionsSubmit.loading) $Util.hideOverlay();
									completeAjax = true;
									optionsSubmit.error(jqXHR,textStatus, errorThrown);
								},
								complete : function(jqXHR, textStatus) {
									completeAjax = true;
									optionsSubmit.complete(jqXHR, textStatus);
								}
							});
						} else {
							alert('처리중입니다. 잠시만 기다려 주세요.');
						}
						return false;
					}
				} else {
					return false;
				}
			return true;
		}

		function onEnter(name, func) {
			$(form[name]).keydown(function(event){
				if(event.keyCode == '13'){
					if(!!func) {
						func();
					}
					event.preventDefault();
				}
			});
		}

		// 소스상에서 submit을 처리하기 위한 함수
		function submit(params) {
			if(_submit(params)) {
				form.submit();
			}
		}
		
		function get(name) {
			var element = form[name];
			return element;
		}
		
		function obj(name) {
			var element = form[name];
			return $(element);
		}

		
		function on(eventName, name, func) { 
			$(form[name])[eventName](function(e, o) {
				if(!!func) { 
					func(e, {form : form, element : e.target} );
				}
			});
		}
		
		function trigger(eventName, name) { 
			$(form[name]).trigger(eventName);
		}
		
		function getValue(name) {
			var element = form[name];
			return $(element).val();
		}
		
		function setValue(name, value) {
			var element = form[name];
			element.value = value;
			return element;
		}
		
		function setAction(value) {
			//console.log("value="+value);			
			form.action = value;
			return form.action;
		}
		
		function setMethod(value) {
			form.method = value;
			return form.method;
		}		

		function setTarget(value) {
			form.target = value;
			return form.target;
		}
		
		function setEditorValue(name, value) {
			var element = form[name];
			element.value = value;
			editor.setHTML(value);
			return element;
		}
		
		function getEditorValue(name) {
			var element = form[name];
			element.value = editor.getHTML();
			return element.value;
		}
		
		function json() {
			return $(form).json();
		}
		
		// controller method 화 
		$.each($_FormContoller, function(controllerName, func) {
			methods[controllerName] = function(name, text, params) {
				var _option = $.extend({}, optionsField, params || {});
				if($_FormInspector[controllerName]) {
					var _option = $.extend({}, optionsField, params);
					if(!!_option.maxLength 
							&& !$.isArray(name) 
							&& $.inArray(controllerName, ignoresLengthCheck) < 0)
						$(form[name]).attr('maxLength', _option.maxLength);
					
					setInspector(controllerName, name, text, _option);
				}
				
				$_FormContoller[controllerName](form, name, text, _option);
			};
		});
		
		// inspector method 화 
		$.each($_FormInspector, function(inspectorName, func) {
			if(!$_FormContoller[inspectorName]) {
				methods[inspectorName] = function(name, text, params) {
					var _option = $.extend({}, optionsField, params);
					if(!!_option.maxLength 
							&& !$.isArray(name) 
							&& $.inArray(inspectorName, ignoresLengthCheck) < 0)
						$(form[name]).attr('maxLength', _option.maxLength);
					
					setInspector(inspectorName, name, text, _option);
				};
			}
		});
		
		// 확장 function 등록
		methods.func = function(func) {
			setInspector("func", "extFunction", "extFunction", func);
		};

		// message handler 변경 함수 등록
		methods.messageHandler = function(handler) {
			$_FormMsg = handler;
		};
		
		methods.validate = validate;
		methods.submit = submit;
		methods.onEnter = onEnter;
		methods.get = get;
		methods.obj = obj;
		methods.getValue = getValue;
		methods.setValue = setValue;
		methods.setAction = setAction;
		methods.setMethod = setMethod;
		methods.setTarget = setTarget;
		methods.getEditorValue = getEditorValue;
		methods.setEditorValue = setEditorValue;
		methods.json = json;
		methods.element = form;
		
		if(!!options.defaultType.type) {
			
			var _defaultType = $.extend({}, {
				type : false,
				text : '',
				minLength : undefined,
				maxLength : undefined,
				require : false,
				ignores : []
			}, options.defaultType);
			
			$(form).find('input[type="text"]').each(function(i, item) {
				if($.inArray(item.name, _defaultType.ignores) < 0) {
					defaultField.push(item.name);
					methods[_defaultType.type](item.name, _defaultType.text, {
						minLength : _defaultType.minLength,
						maxLength : _defaultType.maxLength,
						require : _defaultType.require
					});
				}
			});
		}
		
		return methods;
	};

	// inspector 내에서 alert을 띄우는 함수
	$_FormMsg = function(input,text) {
		alert(text);
		if(input.focus && input.type != 'hidden' 
			&& !input.disabled && input.style.display != 'none') input.focus();
	};
	
	// field 에 validate 하는 함수
	$_FormInspector = {
			
		// 영문 알파벳으로만 입력가능
		alphabet : function(form, params) { 
			var input = form[params.name];
			if(!$Validate.isAlphabet(input.value)) {
				$_FormMsg(input, 
							params.option.message 
							|| (params.text + $Util.getAux(params.text,'은'))+" 영문 알파벳으로만 입력해 주세요.");
				return false;
			}
			return true;
		},
			
		// file 타입의 확장자가 실행파일인지 검사
		attachFile : function(form, params) { 
			var input = form[params.name];
			if(input.type.toLowerCase() != 'hidden') {
				if($Validate.isExeFile(input.value)) {
					$_FormMsg(input, 
							params.option.message 
							||params.text + $Util.getAux(params.text,'이') +' 첨부가 허용되지않는 확장자이거나, 파일명에 특수문자가 포함되어 있습니다.');
					return false;
				}
			}
			return true;
		},
			
		// file 타입의 확장자가 이미지 파일인지를 검사.
		attachImage : function(form, params) { 
			var input = form[params.name];
			if(!$Validate.isImageFile(input.value)) {
				$_FormMsg(input, 
						params.option.message 
						||params.text + $Util.getAux(params.text,'이') +' 이미지 파일이 아니거나, 파일명에 특수문자가 포함되어 있습니다.');
				return false;
			}
			return true;
		},
	
		// 이메일 유효성 검사
		email : function(form,params) {
			var input = new Array();
			var value = '';
			if($.isArray(params.name)) {
				for (i = 0; i < 2; i++) {
					input[input.length] = form[params.name[i]];
					value += (i==1?'@':'') + input[input.length-1].value;
				}
			} else {
				input[0] = form[params.name];
				value = input[0].value;
			}
	
			if(!$Validate.isEmail(value)) {
				$_FormMsg(input[0], 
						params.option.message 
						|| '유효하지 않은 이메일 주소입니다. 다시한번 정확히 입력하세요.');
				return false;
			}
		
			return true;
		},
	
		// FCKEditor 타입의 공백체크
		fck : function(form, params) {
			var oEditor = FCKeditorAPI.GetInstance(params.name);
			if(oEditor.GetXHTML() == '') {
				$_FormMsg(oEditor, 
						params.option.message 
						|| params.text + $Util.getAux(params.text,'을') +' 입력하세요.');
				oEditor.Focus();
				return false;
			}
			return true;
		},
		
		// 필수 입력 검사
		require : function(form,params) {
			var name = $.isArray(params.name) ? params.name[0] : params.name;
			var input = form[name].tagName ? form[name] : form[name][0];
			var type = input.tagName == 'INPUT' ? input.type : input.tagName;
			switch(type.toUpperCase()) {
				case 'SELECT' : 
					var input = form[params.name];
					if ($Validate.isNull(input.options[input.options.selectedIndex].value)) {
						if(params.option.require) 
							$_FormMsg(input, 
										params.option.message 
										||params.text + $Util.getAux(params.text,'을') +' 선택하세요.');
						return false;
					}
					return true;
				break;
				
				case 'RADIO' : 
					var input = form[params.name];
						if(input.length > 1) {
							for (i = 0; i < input.length; i++) {
								if (input[i].checked) return true;
							}
						} else {
							if (input.checked) return true; 
						}
						if(params.option.require) 
							$_FormMsg((input[0]||input), 
								params.option.message 
								||params.text + $Util.getAux(params.text,'을') +' 선택하세요.');
						return false;
				break;
				
				case 'CHECKBOX' : 
					var input = [];
						if($.isArray(params.name)) {
							for (i = 0; i < params.name.length; i++) {
								input[input.length] = form[params.name[i]];
							}
						} else {
							if(form[params.name].length > 1) {
								input = form[params.name];
							} else {
								input[0] = form[params.name];
							}
						}
					
						for(i=0;i<input.length;i++) if(input[i].checked) return true;
						if(params.option.require) 
							$_FormMsg(input[0], 
								params.option.message 
								||params.text + $Util.getAux(params.text,'을') +' 선택하세요.');
						return false;
				break;
				
				default : 
					if($.isArray(params.name)) {
						for (i = 0; i < 2; i++) {
							var input = form[params.name[i]];
							if ($Validate.isNull(input.value)) {
								if(params.option.require) 
									$_FormMsg(input, 
										params.option.message 
										||params.text + $Util.getAux(params.text,'을') +(i!=0?' 정확히':'')+' 입력하세요.');
								return false;
							}
						}
					} else {
						var input = form[params.name];
						if ($Validate.isNull(input.value)) {
							if(params.option.require) 
								$_FormMsg(input, 
									params.option.message 
									||params.text + $Util.getAux(params.text,'을') +' 입력하세요.');
							return false;
						}
					}
					return true;
			}
		},
	
		// 한글로만 입력가능
		korean : function(form, params) { 
			var input = form[params.name];
			if(!$Validate.isKorean(input.value)) {
				$_FormMsg(input, 
						params.option.message 
						||params.text + $Util.getAux(params.text,'은') +' 한글로만 입력해 주세요.');
				return false;
			}
			return true;
		},
	
		// 날짜 입력
		date : function(form, params) { 
			var input = form[params.name];
			if($Validate.isNull(input.value)) {
				$_FormMsg(input, 
						params.option.message 
						||params.text + $Util.getAux(params.text,'을') +' 입력하세요.');
				return false;
			}
			return true;
		},
	
		// Editor 입력
		editor : function(form, params) { 
			if(params.option.require) {
				var input = form[params.name];
				
				input.value = editor.getHTML();
				
				if($Validate.isNull(input.value) || $Util.trim(input.value) == '<p/>') {
					$_FormMsg(input, 
							params.option.message 
							||params.text + $Util.getAux(params.text,'을') +' 입력하세요.');
					editor.focusEditor();
					return false;
				}
			}
			return true;
		},
	
		// 3자리마다 ','가 들어가는 숫자만 입력가능
		money : function(form,params) {
			var option = $.extend({
				minRange : undefined,
				maxRange : undefined
			}, params.option);
			var input = form[params.name];
			
			var minRange = !option.minRange ? $Util.removeComma(option.minRange) : option.minRange;
			var maxRange = !option.maxRange ? $Util.removeComma(option.maxRange) : option.maxRange;
			var value = $Util.removeComma(input.value);
			
			if(isFinite(minRange) || isFinite(maxRange)) {
				if(!$Validate.checkRange(value, 
								isFinite(minRange)?minRange:Number.MIN_VALUE, 
								isFinite(maxRange)?maxRange:Number.MAX_VALUE)) {
					var msg = params.text + $Util.getAux(params.text,'은') +' ';
					if(isFinite(minRange) && isFinite(maxRange))
						msg += minRange+' ~ '+maxRange+' 사이로 입력하세요.';
					else if(isFinite(minRange)) 
						msg += minRange+' 이상의 수로 입력하세요.';
					else if(isFinite(maxRange))
						msg += maxRange+' 이하의 수로 입력하세요.';
					
					$_FormMsg(input, params.option.message || msg);
					return false;
				}
			}
			if(!$Validate.isMoney(input.value)) {
				$_FormMsg(input, 
						params.option.message 
						||params.text + $Util.getAux(params.text,'은') +' 숫자와 자릿수 구분(,.)만 입력해 주세요.');
				return false;
			}
			
			return true;
		},
	
		// 숫자입력 검사
		number : function(form,params) {
			var option = $.extend({
				minRange : undefined,
				maxRange : undefined
			}, params.option);
			var input = form[params.name];
			
			if(isFinite(option.minRange) || isFinite(option.maxRange)) {
				if(!$Validate.checkRange(input.value, 
								isFinite(option.minRange)?option.minRange:Number.MIN_VALUE, 
								isFinite(option.maxRange)?option.maxRange:Number.MAX_VALUE)) {
					var msg = params.text + $Util.getAux(params.text,'은') +' ';
					if(isFinite(option.minRange) && isFinite(option.maxRange))
						msg += option.minRange+' ~ '+option.maxRange+' 사이로 입력하세요.';
					else if(isFinite(option.minRange)) 
						msg += option.minRange+' 이상의 수로 입력하세요.';
					else if(isFinite(option.maxRange))
						msg += option.maxRange+' 이하의 수로 입력하세요.';
					
					$_FormMsg(input, params.option.message || msg);
					return false;
				}
			}
			
			if(!$Validate.isNumber(input.value)) {
				$_FormMsg(input, 
						params.option.message 
						||params.text + $Util.getAux(params.text,'은') +' 숫자로만 입력해 주세요.');
				return false;
			}
			
			return true;
		},
		
		// 문자열 길이 검사
		charLength : function(form,params) {
			var option = params.option;
			var input = form[params.name];
			if(!$Validate.checkLength(input.value, option.minLength, option.maxLength)) {
				var msg = params.text + $Util.getAux(params.text,'은') +' ';
				if(!!option.minLength && !!option.maxLength)
					msg += option.minLength+'자 ~ '+option.maxLength+'자 사이로 입력하세요.';
				else if(!!option.minLength) 
					msg += option.minLength+'자 이상 입력하세요.';
				else if(!!option.maxRange)
					msg += option.maxLength+'자 이하로 입력하세요.';
				
				$_FormMsg(input, params.option.message || msg);
				return false;
			}
			return true;
		},
	
		// 특수문자 유효성 검사
		specialChar : function(form,params) {
			var input = form[params.name];
			if(!$Validate.checkSpecialChar(input.value)) {
				$_FormMsg(input, 
						params.option.message 
						||params.text +'에 허용되지않는 특수문자가 포함되어있습니다.');
				return false;
			}
			return true;
		},
	
		// 주민번호 유효성 검사 (주민등록번호이거나 외국인 번호면 TRUE 아니면 FALSE)
		ssn : function(form,params) {
			var input = [];
			var value = '';
			if($.isArray(params.name)) {
				for (i = 0; i < 2; i++) {
					input.push(form[params.name[i]]);
					value += form[params.name[i]].value;
				}
			} else {
				input.push(form[params.name]);
				value = form[params.name].value;
			}
			
			if(!$Validate.isSSN(value) && !$Validate.isFGN(value)) {
				$_FormMsg(input[0], 
						params.option.message 
						|| params.text + $Util.getAux(params.text,'이') + ' 형식에 유효하지 않습니다. 다시한번 정확히 입력하세요.\n');
				return false;
			}
		
			return true;
		},
	
		// 회원아이디 유효성 검사
		userId : function(form,params) {
			var input = form[params.name];
			var min = params.option.minLength || 5;
			var max = params.option.maxLength || 12;
			if(!$Validate.isUserId(input.value,min,max)) {
				$_FormMsg(input, 
						params.option.message 
						||params.text + $Util.getAux(params.text,'이') 
							+' 유효하지 않습니다.\n영문 소문자로 시작하는 숫자 조합의 문자를 '+min+'~'+max+'자 이내로 입력하세요.');
				return false;
			}
			return true;
		},
	
		// 사용자 비밀번호 유효성 검사
		userPw : function(form,params) {
			var option = $.extend({
				compare : null
			}, params.option);
			var input = form[params.name];
			var min = params.option.minLength || 10;
			var max = params.option.maxLength || 16;
			if(!$Validate.isUserPw(input.value,min,max)) {
				$_FormMsg(input, 
						params.option.message 
						||params.text + $Util.getAux(params.text,'이') +' 유효하지 않습니다.\n영문소문자와 숫자 조합 '+min+'~'+max+'자 이내로 입력하세요.');
				return false;
			}
			if(!!option.compare) {
				if(input.value != form[option.compare].value) {
					$_FormMsg(form[option.compare],params.text + $Util.getAux(params.text,'이') +' 동일하지 않습니다.\n다시 한번 정확히 입력하세요.');
					return false;
				}
			}
			return true;
		}, 
		
		// 관리자 비밀번호 유효성 검사
		mngrPw : function(form,params) {
			var option = $.extend({
				compare : null
			}, params.option);
			var input = form[params.name];
			var min = params.option.minLength || 10;
			var max = params.option.maxLength || 16;
			if(!$Validate.isMngrPw(input.value,min,max)) {
				$_FormMsg(input, 
						params.option.message 
						||params.text + $Util.getAux(params.text,'이') +' 유효하지 않습니다.\n영문소문자와 숫자 조합 '+min+'~'+max+'자 이내로 입력하세요.');
				return false;
			}
			if(!!option.compare) {
				if(input.value != form[option.compare].value) {
					$_FormMsg(form[option.compare],params.text + $Util.getAux(params.text,'이') +' 동일하지 않습니다.\n다시 한번 정확히 입력하세요.');
					return false;
				}
			}
			return true;
		}, 
		
		// 전화번호 체크
		phone : function(form,params) {
			var input = [];
			var value = '';
			var value2 = '';
			if($.isArray(params.name)) {
				for (i = 0; i < 3; i++) {
					input.push(form[params.name[i]]);
					value += (i!=0?"-":"")+form[params.name[i]].value;
					if(i != 0) value2 += form[params.name[i]].value;
				}
			} else {
				input.push(form[params.name]);
				value = form[params.name].value;
				value2 = form[params.name].value.split('-');
			}

			if($.inArray(value2, eval(INPT_UNBL_TPHN_NO)) > -1) {
				$_FormMsg(input[0], 
						params.option.message 
						||params.text + $Util.getAux(params.text,'이') + ' 형식에 유효하지 않습니다. 다시한번 정확히 입력하세요.');
				return false;
			}
			
			if(!$Validate.isPhoneNumber(value)) {
				$_FormMsg(input[0], 
						params.option.message 
						||params.text + $Util.getAux(params.text,'이') + ' 형식에 유효하지 않습니다. 다시한번 정확히 입력하세요.');
				return false;
			}
			
			return true;
		}, 
		
		// 모바일  체크
		mobile : function(form,params) {
			var input = [];
			var value = '';
			var value2 = '';
			if($.isArray(params.name)) {
				for (i = 0; i < 3; i++) {
					input.push(form[params.name[i]]);
					value += (i!=0?"-":"")+form[params.name[i]].value;
					if(i != 0) value2 += form[params.name[i]].value;
				}
			} else {
				input.push(form[params.name]);
				value = form[params.name].value;
				value2 = form[params.name].value.split('-');
			}
			if($.inArray(value2, eval(INPT_UNBL_TPHN_NO)) > -1) {
				$_FormMsg(input[0], 
						params.option.message 
						||params.text + $Util.getAux(params.text,'이') + ' 형식에 유효하지 않습니다. 다시한번 정확히 입력하세요.');
				return false;
			}
			
			if(!$Validate.isPhoneNumber(value)) {
				$_FormMsg(input[0], 
						params.option.message 
						||params.text + $Util.getAux(params.text,'이') + ' 형식에 유효하지 않습니다. 다시한번 정확히 입력하세요.');
				return false;
			}
			
			return true;
		}, 
		
		// 우편번호 체크
		zipcode : function(form,params) {
			var input = [];
			var value = '';
			if($.isArray(params.name)) {
				for (i = 0; i < 2; i++) {
					input.push(form[params.name[i]]);
					value += (i!=0?"-":"")+form[params.name[i]].value;
				}
			} else {
				input.push(form[params.name]);
				value = form[params.name].value;
			}
			
			if(!$Validate.isZipcode(value, '-')) {
				$_FormMsg(input[0], 
						params.option.message 
						||params.text + $Util.getAux(params.text,'이') + ' 형식에 유효하지 않습니다. 다시한번 정확히 입력하세요.');
				return false;
			}
			
			return true;
		}
	};
	
	// field 에 자동 기능 속성을 셋팅하는 함수
	$_FormContoller = {
		
		// 숫자만 입력가능하게
		number : function(form, name, text, params) {
			$(form[name]).keydown(function(e) {
				var key = e.keyCode;
				if ((key >= 48 && key <= 57) // 키보드 상단 숫자키
						|| (key >= 96 && key <= 105) // 키패드 숫자키
						|| $.inArray(key, [8,37,39,46,13,9,51,52,188,190,110,107,109,189]) >= 0)
					return true;
				else 
					return false;
			});
		},
		
		// 배열 순서에 맞춰 자동으로 focus 이동
		autoFocus : function(form, name, text, params) {
			var arrLength = name.length;
			$(name).each(function(index, node) {
				var nextNode = form[name[index+1]];
				var prevNode = form[name[index-1]];
				var type = form[node].tagName == 'INPUT' ? form[node].type : form[node].tagName;
				if(type.toUpperCase() == "TEXT") {
					if(!!nextNode && !!$(form[node]).attr('maxLength')) {
						$(form[node]).keyup(function(e) {
							var key = e.keyCode;
							var obj = e.srcElement; 
							if(obj.value.length >=  $(obj).attr('maxLength')
									&& $.inArray(key, [0,8,9,16,17,18,37,38,39,40,46]) < 0) {
								nextNode.focus();
							}
						});
					}
					
					if(!!prevNode) {
						$(form[node]).keydown(function(e) {
							var key = e.keyCode;
							var obj = e.srcElement; 
							if(obj.value.length == 0 && key == 8){
								prevNode.focus();
								return false;
							}
						});
					}
				} else if(type.toUpperCase() == "SELECT") {
					if(!!nextNode) {
						$(form[node]).change(function(e){
							nextNode.focus();
						}); 
					}
				}
			});
		},
		
		editor : function(form, name, text, params) {
			var option = $.extend({
				load: function() {}
			}, params);
			HTMLArea.init(); 
			HTMLArea.onload = function() {
				initEditor(name);
				option.load();
			}
			
		},
		
		// 주민등록번호 자동 속성 부여
		ssn : function(form, name, text, params) {
			if($.isArray(name)) {
				$(name).each(function(index, node){
					$(form[node]).attr('maxLength',(index == 0?6:7));
					$_FormContoller.number(form, node, text, params);
				});
				$_FormContoller.autoFocus(form, name, text, params);
			} else {
				$(form[name]).attr('maxLength', params.maxLength || 13);
				$_FormContoller.number(form, name, text, params);
			}
		},
		
		userId : function(form, name, text, params) {
			form[name].style.imeMode="disabled";
		},
		
		// 전화번호 속성 자동 부여
		phone : function(form, name, text, params) {
			if($.isArray(name)) {
				$(name).each(function(index, node){
					$(form[node]).attr('maxLength',(index == 0?3:4));
					$_FormContoller.number(form, node, text, params);
				});
				$_FormContoller.autoFocus(form, name, text, params);
			} else {
				$(form[name]).attr('maxLength', params.maxLength || 13);
				$(form[name]).keydown(function(e) {
					var key = e.keyCode;
					if ((key >= 48 && key <= 57) // 키보드 상단 숫자키
							|| (key >= 96 && key <= 105) // 키패드 숫자키
							|| $.inArray(key, [8,37,39,46,13,9,187,189]) >= 0)
						return true;
					else 
						return false;
				});
			}
		},
		
		// 전화번호 속성 자동 부여
		mobile : function(form, name, text, params) {
			if($.isArray(name)) {
				$(name).each(function(index, node){
					$(form[node]).attr('maxLength',(index == 0?3:4));
					$_FormContoller.number(form, node, text, params);
				});
				$_FormContoller.autoFocus(form, name, text, params);
			} else {
				$(form[name]).attr('maxLength', params.maxLength || 13);
				$(form[name]).keydown(function(e) {
					var key = e.keyCode;
					if ((key >= 48 && key <= 57) // 키보드 상단 숫자키
							|| (key >= 96 && key <= 105) // 키패드 숫자키
							|| $.inArray(key, [8,37,39,46,13,9,187,189]) >= 0)
						return true;
					else 
						return false;
				});
			}
		},
		
		// 우편번호 속성 자동 부여
		zipcode : function(form, name, text, params) {
			if($.isArray(name)) {
				$(name).each(function(index, node){
					$(form[node]).attr('maxLength',3);
					$_FormContoller.number(form, node, text, params);
				});
				$_FormContoller.autoFocus(form, name, text, params);
			} else {
				$(form[name]).attr('maxLength', params.maxLength || 7);
				$(form[name]).keydown(function(e) {
					var key = e.keyCode;
					if ((key >= 48 && key <= 57) // 키보드 상단 숫자키
							|| (key >= 96 && key <= 105) // 키패드 숫자키
							|| $.inArray(key, [8,37,39,46,13,9,189]) >= 0)
						return true;
					else 
						return false;
				});
			}
		},
		
		// 자릿수(,) 포함한 숫자 (, 자동 부여)
		money : function(form, name, text, params) { 
			$(form[name]).keydown(function(e) {
				var key = e.keyCode;
				if ((key >= 48 && key <= 59) // 키보드 상단 숫자키
						|| (key >= 96 && key <= 105) // 키패드 숫자키
						|| $.inArray(key, [8,37,39,46,52,13,9,188,190,110,107,109,189]) >= 0) 
					return true;
				else 
					return false;
			});
			form[name].value = $Util.insertComma(form[name].value);			
			$(form[name]).keyup(function(e) {
				form[name].value = $Util.insertComma(form[name].value);
			});
		},
		
		// date
		date : function(form, name, text, params) {
			var option = $.extend({
				datepicker : true,
				dateFormat : 'yymmdd',
				showDefaultDate : true,
				defaultDate: 0,
				showOn : 'both',
				buttonImage : '/images/common/button/btn_calendar.gif',
				changeMonth: true,
				changeYear: true,
				monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				dayNamesMin:['일','월','화','수','목','금','토']
			}, params);
			
			
			if(option.datepicker) {
				$(form[name]).datepicker(option);
				if(form[name].value == '') {
					if(option.showDefaultDate && option.defaultDate == 0) $(form[name]).datepicker("setDate", new Date);
					else if(option.showDefaultDate) $(form[name]).datepicker("setDate", option.defaultDate);
				}
			}
		}
	};

	$.fn.json = function(params) {   
		var arrayData, objectData;   
		arrayData = this.serializeArray();   
		objectData = params || {};    
		$.each(arrayData, function() {     
			var value;      
			if (this.value != null) {       
				value = this.value;     
			} else {      
				value = '';     
			}      
			
			if (objectData[this.name] != null) {
				if (!objectData[this.name].push) {         
					objectData[this.name] = [objectData[this.name]];       
				}
	
				objectData[this.name].push(value);     
			} else {       
				objectData[this.name] = value;     
			}   
		});    
		
		return objectData; 
	}; 
		
})(jQuery);