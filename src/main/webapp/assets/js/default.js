var DATA_TABLES = {
	DEFAULT_OPTION : { // DataTables初始化选项
		language : {
			"sProcessing" : "处理中...",
			"sLengthMenu" : "每页 _MENU_ 项",
			"sZeroRecords" : "没有匹配结果",
			"sInfo" : "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
			"sInfoEmpty" : "当前显示第 0 至 0 项，共 0 项",
			"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
			"sInfoPostFix" : "",
			"sSearch" : "搜索:",
			"sUrl" : "",
			"sEmptyTable" : "表中数据为空",
			"sLoadingRecords" : "载入中...",
			"sInfoThousands" : ",",
			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : "上页",
				"sNext" : "下页",
				"sLast" : "末页",
				"sJump" : "跳转"
			},
			"oAria" : {
				"sSortAscending" : ": 以升序排列此列",
				"sSortDescending" : ": 以降序排列此列"
			}
		},
		autoWidth : false, // 禁用自动调整列宽
		stripeClasses : [ "odd", "even" ],// 为奇偶行加上样式，兼容不支持CSS伪类的场合
		order : [], // 取消默认排序查询,否则复选框一列会出现小箭头
		// processing: true, //隐藏加载提示,自行处理
		serverSide : true, // 启用服务器端分页
		pageLength : 10,
		pagingType : "full_numbers",
		stateSave : true,
		lengthChange : false,
		searching : false
	// 用原生搜索
	},
	COLUMN : {
		CHECKBOX : { // 复选框单元格
			className : "td-checkbox",
			orderable : false,
			width : "30px",
			data : null,
			render : function(data, type, row, meta) {
				return '<input type="checkbox" class="iCheck">';
			}
		}
	},
	RENDER : { // 常用render可以抽取出来，如日期时间、头像等
		ELLIPSIS : function(data, type, row, meta) {
			data = data || "";
			return '<span title="' + data + '">' + data + '</span>';
		}
	}
};


(function(factory) {
	if (typeof exports == 'object') {
		// CommonJS
		factory(require('jquery'), require('spin.js'));
	} else if (typeof define == 'function' && define.amd) {
		// AMD, register as anonymous module
		define([ 'jquery', 'spin' ], factory);
	} else {
		// Browser globals
		if (!window.Spinner)
			throw new Error('Spin.js not present');
		factory(window.jQuery, window.Spinner);
	}
}(function($, Spinner) {
	$.fn.spin = function(opts, color, modal) {
		return this.each(function() {
			var $this = $(this), data = $this.data();
			if (data.spinner) {
				data.spinner.stop();
				delete data.spinner;
			}
			if (opts !== false) {
				opts = $.extend({
					color : color || $this.css('color')
				}, $.fn.spin.presets[opts] || opts);

				data.spinner = new Spinner(opts).spin(this);
			}
		});
	};
	$.fn.spin.presets = {
		tiny : {
			lines : 8,
			length : 2,
			width : 2,
			radius : 3
		},
		small : {
			lines : 8,
			length : 4,
			width : 3,
			radius : 5
		},
		large : {
			lines : 10,
			length : 8,
			width : 4,
			radius : 8
		}
	};
	$.fn.spinModal = function(opts, color) {
		return this.each(function() {
			var $this = $(this), data = $this.data();

			if (data.spinner) {
				data.spinner.stop();
				data.overlay.remove();
				delete data.spinner;
				delete data.overlay;
			}
			if (opts !== false) {
				opts = $.extend({
					color : color || $this.css('color')
				}, $.fn.spin.presets[opts] || opts);
				var overlay = $("<div />", {
					"class" : "overlay"
				});
				$this.append(overlay);
				data.spinner = new Spinner(opts).spin(this);
				data.overlay = overlay;
			}
		});
	};
}));

(function($) {
	$.extend({
		loadAllObject : function(url, handleData) {
			$.ajax({
				type : "GET",
				url : url,
				dataType : "json",
				data : data,
				timeout : 1000,
				beforeSend : function() {
					// $('.loading').showLoading();
				},
				success : handleData,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown);
				}
			});
			return false;
		},
		addNewJSONObject: function (url, jsonData, handleData) {
            $.ajax({
                type: "POST",
                url: url,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(jsonData),
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        updateJSONOObject: function (url, id, jsonData, handleData) {
            $.ajax({
                type: "PUT",
                url: url + "/" + id,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(jsonData),
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        uploadFormObject: function (url, formData, handleData) {
            $.ajax({
                type: "POST",
                url: url,
                processData: false,
                contentType: false,
                data: formData,
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
		deleteObject: function (url, id, handleData) {
            $.ajax({
                type: "DELETE",
                url: url + "/" + id,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        deleteMultiObject: function (url, jsonData, handleData) {
            $.ajax({
                type: "POST",
                url: url,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: jsonData,
                success: handleData,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
            return false;
        },
        toastSuccess: function (msg) {
            toastr.options = {
                closeButton: true,
                debug: false,
                positionClass: "toast-top-right",
                onclick: null,
                showDuration: "300",
                hideDuration: "1000",
                timeOut: "2000",
                extendedTimeOut: "1000",
                showEasing: "swing",
                hideEasing: "linear",
                showMethod: "fadeIn",
                hideMethod: "fadeOut"
            };
            toastr.success(msg);
        },
        toastError: function (msg) {
            toastr.options = {
                closeButton: true,
                debug: false,
                positionClass: "toast-top-right",
                onclick: null,
                showDuration: "300",
                hideDuration: "1000",
                timeOut: "2000",
                extendedTimeOut: "1000",
                showEasing: "swing",
                hideEasing: "linear",
                showMethod: "fadeIn",
                hideMethod: 'fadeOut'
            };
            toastr.error(msg);
        },
		table_ajax : function(wrapper, url, objectManager) {
			return function(data, callback, settings) {// ajax配置为function,手动调用异步查询
				// 手动控制遮罩
				wrapper.spinModal();
				// 封装请求参数
				var param = objectManager.getQueryCondition(data);
				$.ajax({
					type : "GET",
					url : url,
					cache : false, // 禁用缓存
					data : param, // 传入已封装的参数
					dataType : "json",
					success : function(result) {
						// setTimeout仅为测试遮罩效果
						setTimeout(function() {
							// 异常判断与处理
							if (result.errorCode) {
								// $.dialog.alert("查询失败。错误码：" +
								// result.errorCode);
								return;
							}
							// 封装返回数据，这里仅演示了修改属性名
							var returnData = {};
							returnData.draw = data.draw;// 这里直接自行返回了draw计数器,应该由后台返回
							returnData.recordsTotal = result.total;
							returnData.recordsFiltered = result.total;// 后台不实现过滤功能，每次查询均视作全部结果
							returnData.data = result.pageData;
							// 关闭遮罩
							wrapper.spinModal(false);
							// 调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
							// 此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
							callback(returnData);
						}, 200);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						// $.dialog.alert("查询失败");
						wrapper.spinModal(false);
					}
				});
			};
		},
		drawCallback : function(wrapper, table) {
			return function(settings) {
				// 渲染完毕后的回调
				// 清空全选状态
				$(":checkbox[name='cb-check-all']", wrapper).prop('checked',
						false);
				// 默认选中第一行
				$("tbody tr", table).eq(0).click();
			};
		}
	});
})(jQuery);

jQuery.fn.extend({
	loadJson: function(jsonValue) {
	    var obj = this;
	    $.each(jsonValue, function(name, ival) {
	        var $oinput = obj.find(":input [name=" + name + "]");
	        if ($oinput.attr("type") == "radio" || $oinput.attr("type") == "checkbox") {
	            $oinput.each(function() {
	                if (Object.prototype.toString.apply(ival) == '[object Array]') {//是复选框，并且是数组         
	                    for (var i = 0; i < ival.length; i++) {
	                        if ($(this).val() == ival[i])
	                            $(this).attr("checked", "checked");
	                    }
	                } else {
	                    if ($(this).val() == ival)
	                        $(this).attr("checked", "checked");
	                }
	            });
	        } else if ($oinput.attr("type") == "textarea") {//多行文本框            
	            obj.find("[name=" + name + "]").html(ival);
	        } else {
	            obj.find("[name=" + name + "]").val(ival);
	        }
	    });
	}
});

(function () {
    function Validator($element) {
        this.$container = $element;
        this.$bsCalloutInfo = this.$container.find('.bs-callout-info');
        this.$bsCalloutWarnin = this.$container.find('bs-callout-warnin');
    }

    Validator.prototype = {
        constructor: Validator,

        validate: function () {
            this.$container.parsley().validate();
            if (true === this.$container.parsley().isValid()) {
                this.$bsCalloutInfo.removeClass('hidden');
                this.$bsCalloutWarnin.addClass('hidden');
                return true;
            } else {
                this.$bsCalloutInfo.addClass('hidden');
                this.$bsCalloutWarnin.removeClass('hidden');
                return false;
            }
        }
    };
    $.fn.validate = function () {
        var validator = new Validator(this);
        return validator.validate();
    };
})();

function deleteTableRow(selectedItems, url, table) {
	var message;
	var $this=table;
	if (selectedItems && selectedItems.length) {
		if (selectedItems.length === 1) {
			message = "确定要删除 '" + selectedItems[0].username + "' 吗?";
			bootbox.confirm({
				size : "small",
				title : "提示",
			    message: message,
			    buttons: {
 			        confirm: { label: '确定', className: 'btn-success' },
 			        cancel: { label: '取消', className: 'btn-danger' }
 			    },
			    callback: function (result) {
			    	if(result) {
			    		$.deleteObject(url, selectedItems[0].id, function(data) {
							$.toastSuccess("删除" + data.obj + "项纪录成功!!!");
							$this.draw();
						});
			    	}
			    }
			});
		 } else {
             message = "确定要删除选中的" + selectedItems.length + "项记录吗?";
             bootbox.confirm({
 				size : "small",
 				title : "提示",
 			    message: message,
 			    buttons: {
 			        confirm: { label: '确定', className: 'btn-success' },
 			        cancel: { label: '取消', className: 'btn-danger' }
 			    },
 			    callback: function (result) {
 			    	if(result) {
 			    		var saveDataAry=[];
 		                for (var i = 0; i < selectedItems.length; i++) {
 		                    saveDataAry.push(selectedItems[i].id);
 		                }
 		                $.deleteMultiObject(url + '/batch', JSON.stringify(saveDataAry), function (data) {
 		                    $.toastSuccess("删除" + data.obj + "项纪录成功!!!");
 		                    $this.draw();
 		                });
 			    	}
			    	
			    }    
             });
         }
	} else {
		bootbox.alert("请先选中要操作的行");
	}
}

function uuid() {  
    var s = [];  
    var hexDigits = "0123456789abcdef";  
    for (var i = 0; i < 36; i++) {  
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);  
    }  
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010  
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01  
    s[8] = s[13] = s[18] = s[23] = "-";  
   
    var uuid = s.join("");  
    return uuid;  
}

function initEmp() {
	if (typeof ($.fn.DataTable) === 'undefined') {
		return;
	}
	if (typeof (bootbox) === 'undefined') {
		return;
	}
	if (typeof (toastr) === 'undefined') {
        return;
    }
    if (typeof (parsley) === 'undefined') {
        return;
    }
    if (typeof ($.fn.fileinput) === 'undefined') {
        return;
    }

	var $wrapper = $("#emp-container"), $table = $("#emp-table");
	
	$("#uploadExcel").fileinput({
        showPreview: false,
        showUpload: false,
        elErrorContainer: '#kartik-file-errors',
        allowedFileExtensions: ["xls", "xlsx", "xlsm"]
    });

	var _table = $table.DataTable(
		$.extend(true, {}, DATA_TABLES.DEFAULT_OPTION, {
			ajax : $.table_ajax($wrapper, 'api/emps', {
				currentItem : null,
				fuzzySearch : true,
				getQueryCondition : function(data) {
					var param = {};
					// 组装排序参数
					if (data.order && data.order.length && data.order[0]) {
						switch (data.order[0].column) {
						case 2:
							param.orderColumn = "userid";
							break;
						case 3:
							param.orderColumn = "username";
							break;
						default:
							param.orderColumn = "id";
							break;
						}
						param.orderDir = data.order[0].dir;
					}
					// 组装分页参数
					param.startIndex = data.start;
					param.pageSize = data.length;
					param.draw = data.draw;
					return param;
				}
			}),
			columns : [
			    DATA_TABLES.COLUMN.CHECKBOX,
			    {data : "id",visible : false}, 
			    {className : "ellipsis", data : "userid",orderable : false, render : DATA_TABLES.RENDER.ELLIPSIS}, 
				{className : "ellipsis", data : "username",render : DATA_TABLES.RENDER.ELLIPSIS,width : "50px"}, {
					className : "ellipsis",
					data : "partment",
					render : DATA_TABLES.RENDER.ELLIPSIS,
					width : "80px"
				}, {
					className : "ellipsis",
					data : "tel",
					orderable : false,
					render : DATA_TABLES.RENDER.ELLIPSIS,
					width : "80px"
				}, {
					className : "ellipsis",
					data : "email",
					orderable : false,
					render : DATA_TABLES.RENDER.ELLIPSIS,
					width : "80px"
				}, {
	                className: "td-operation",
	                data: null,
	                defaultContent: "",
	                orderable: false,
	                width: "80px"
	            }
			],
			"createdRow" : function(row, data, index) {
				// 行渲染回调,在这里可以对该行dom元素进行任何操作
				// 给当前行加样式
				if (data.role) {
					$(row).addClass("info");
				}
				var $btnEdit = $('<span><button type="button" class="btn btn-xs btn-success btn-edit btn-opt" data-toggle="modal" data-target="#emp-modal-dialog">修改</button></span>');
	            var $btnDel = $('<span><button type="button" class="btn btn-xs btn-danger btn-del btn-opt">删除</button></span>');
	            $('td', row).eq(6).append($btnEdit).append($btnDel);
			},
			"drawCallback" : $.drawCallback($wrapper, $table)}
		)
	);
	
	$table.on("change",":checkbox", function() {
		if ($(this).is("[name='cb-check-all']")) {
			// 全选
			$(":checkbox", $table).prop("checked", $(this).prop("checked"));
		} else {
			// 一般复选
			var checkbox = $("tbody :checkbox", $table);
			$(":checkbox[name='cb-check-all']", $table).prop(
					'checked',checkbox.length === checkbox.filter(':checked').length);
		}
	}).on("click",".td-checkbox", function(event) {
		// 点击单元格即点击复选框
		!$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
	}).on("click", ".btn-edit", function () {
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        if (!item) {
            return;
        }
        $('#emp-form').loadJson(item);
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var item = _table.row($(this).closest('tr')).data();
        deleteTableRow([item], 'api/emps', _table);
    });
	
	$wrapper.on("click", ".btn-delete", function() {
		var checkbox = $("tbody :checkbox", $table);
        var items = [];
        checkbox.filter(':checked').each(function () {
            items.push(_table.row($(this).closest('tr')).data());
        });
        if(items.length > 0) {
        	deleteTableRow(items, 'api/emps', _table);
        }
	}).on("click", ".btn-export", function() {
		window.location.href="exportEmp";
	});
	
	$('#submit_upload_emp').on("click", function () {
		$myModal = $('#emp-upload-dialog');
		var url = "importEmp";
		var formData = new FormData();
	    formData.append('uploadExcel', $('#uploadExcel')[0].files[0]);
	    $.uploadFormObject(url, formData, function (data) {
            if (data.success) {
            	$.toastSuccess("上传成功!!!");
            } else {
            	$.toastError("上传失败!!!");
            }
            $myModal.modal('hide');
            _table.draw();
        });
	});
	
	$('#submit_emp').on("click", function () {
		var $myForm = $('#emp-form'),
			url = "api/emps",
	    	$emp_id = $('#hidden_id'),
	    	$myModal = $('#emp-modal-dialog');
		
		if (!$myForm.validate()) {
            return false;
        }
	    
	    var jsonObj = $myForm.serializeObject();
	    
	    if ($emp_id.val()) {
	    	var id = $emp_id.val();
	        $.updateJSONObject(url, id, jsonObj, function (data) {
	            if (data.obj) {
	            	$myForm[0].reset();
	            	$.toastSuccess("更新类别成功!!!");
	            } else {
	            	$.toastError("更新类别失败!!!");
	            }
	            $myModal.modal('hide');
	            _table.draw();
	        });
	    } else {
	        $.addNewJSONObject(url, jsonObj, function (data) {
	            if (data.obj) {
	            	$myForm[0].reset();
	            	$.toastSuccess("添加类别成功!!!");
	            } else {
	            	 $.toastError("添加类别失败!!!");
	            }
	            $myModal.modal('hide');
	            _table.draw();
	        });
	    }
	});

	$('#page').val(_table.page.len());

	$('#page').change(function() {
		var pageLength = $('#page').val();
		_table.page.len(pageLength).draw();
	});

}

function logout() {
	if (typeof (bootbox) === 'undefined') {
		return;
	}
	bootbox.confirm({
		size : "small",
		title : "提示",
		message : "确定要退出?",
		buttons : {
			confirm : {
				label : '确定',
				className : 'btn-success'
			},
			cancel : {
				label : '取消',
				className : 'btn-danger'
			}
		},
		callback : function(result) {
			if (result) {
				// progressLoad();
				$.post('logout', function(result) {
					if (result.success) {
						window.location.href = 'login';
					}
				}, 'json');
			}
		}
	});
}

var CURRENT_URL = window.location.href.split('#')[0].split('?')[0],
	$BODY = $('body'),
	$SIDEBAR_MENU = $('.sidebar-menu');

//Sidebar
function init_sidebar() {

	$SIDEBAR_MENU.find('a').on('click', function (ev) {
		var $li = $(this).parents('li.treeview');
		if (!$li.is('.menu-open')) {
			$li.siblings(".treeview").removeClass("active");
			$li.addClass('active');
		}
	});
	
	// check active menu
	//$SIDEBAR_MENU.find('a[href="' + CURRENT_URL + '"]').parent('li').addClass('active');
	$SIDEBAR_MENU.find('a').filter(function () {
	    return this.href == CURRENT_URL;
	}).parents('li.treeview').addClass('active');

}
///Sidebar

//NProgress
if (typeof NProgress != 'undefined') {
    $(document).ready(function () {
        NProgress.start();
    });

    $(window).load(function () {
        NProgress.done();
    });
}

$(document).ready(function() {
	initEmp();
	init_sidebar();
});