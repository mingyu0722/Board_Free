/*******************************************************
 * 공통 Ajax
 *******************************************************/
var ajax = {

	_jsonData : {},
	_callBack : "",
	_errorCallBack : "",
	_dataType : "json",
	_forwardData : {},
	_type : "POST",
	_async : true,
	_cache : false,
	_global : true,

	/**
	 * 비동기 통신 (Get)
	 * @example
	 *
	 * _url : URL {string}
	 * _jsonData : 파라미터 {string}
	 * _callback : 콜백 함수 {object}
	 *
	 * ajax.get({
	 *     url : "${contextPath}/pdv/pdveNextTest/selectStr.do",
	 *     params : "param1=hello&param2=world&param3=test",
	 *     callback : callbackFunction
	 * });
	 * @param {object} _jsonData
	 */
	get : function(_url, _jsonData, _callBack, _async, _global){
		this._jsonData = _jsonData;
		this._callBack = _callBack;
		this._async = _async;
		this._global = _global;

		var ajaxCallBack = this;

		$.ajax({
			type : "GET",
			url : _url,
			contentType : 'application/json',
			dataType : this._dataType,
			data : ajaxCallBack._jsonData,
			async: ajaxCallBack._async,
			cache : this._cache,
			global : this._global,
			beforeSend: function( xhr ) {

			},
			success : function (data) {
				if (ajaxCallBack._callBack) {
					ajaxCallBack._callBack(data); // 콜백 함수 호출
				}
			},
			error : function (err_data) {
				if(err_data.status != "200"){
					if(err_data.status == "400"){
						swal({
							title : '서버와 통신이 실패 하였습니다',
							html : '잠시 후 다시 시도 해주세요'
						});
					} else if(err_data.status == "500"){
						swal({
							title : '서버와 통신이 실패 하였습니다',
							html : '잠시 후 다시 시도 해주세요'
						});
					}
				}
			},
			complete : function(){

			}
		});
	},

	/**
	 * 비동기 통신 (Post)
	 * @example
	 *
	 * _url : URL {string}
	 * _jsonData : 파라미터 {string}
	 * _callback : 콜백 함수 {object}
	 *
	 * ajax.post({
	 *     url : "${contextPath}/pdv/pdveNextTest/selectStr.do",
	 *     params : "param1=hello&param2=world&param3=test",
	 *     callback : callbackFunction
	 * });
	 * @param {object} _jsonData
	 */
	post : function(_url, _jsonData, _callBack, _errorCallBack, _async, _global){

		this._url = _url;
		this._jsonData = _jsonData;
		this._callBack = _callBack;
		this._errorCallBack = _errorCallBack;
		this._async = _async;
		this._global = _global;

		var ajaxCallBack = this;

		$.ajax({
			type : "POST",
			url : _url,
			contentType : 'application/json',
			dataType : this._dataType,
			async: this._async,
			cache : this._cache,
			global : this._global,
			data : JSON.stringify(this._jsonData),
			beforeSend: function( xhr ) {

			},
			success : function (data) {
				if (ajaxCallBack._callBack) {
					ajaxCallBack._callBack(data); // 콜백 함수 호출
				}
			},
			error : function (err_data) {
				if(err_data.status != "200"){
					if(err_data.status == "400"){
						swal({
							title : '서버와 통신이 실패 하였습니다',
							html : '잠시 후 다시 시도 해주세요'
						});
					} else if(err_data.status == "500"){
						swal({
							title : '서버와 통신이 실패 하였습니다',
							html : '잠시 후 다시 시도 해주세요'
						});
					}
					if (ajaxCallBack._errorCallBack) {
						ajaxCallBack._errorCallBack(err_data); // 콜백 함수 호출
					}
				}
			},
			complete : function(){
			}
		});
	}
};