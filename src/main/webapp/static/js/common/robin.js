var ROBIN = ROBIN || {};

ROBIN = {
    Object: {
        notNull: function (obj) {//判断某对象不为空..返回true 否则 false
            if (obj === null) return false; else if (obj === undefined) return false; else if (obj === "undefined") return false; else if (obj === "") return false; else if (obj === "[]") return false; else if (obj === "{}") return false; else return true;
        },
        notEmpty: function (obj) {//判断某对象不为空..返回obj 否则 ""
            if (obj === null) return ""; else if (obj === undefined) return ""; else if (obj === "undefined") return ""; else if (obj === "") return ""; else if (obj === "[]") return ""; else if (obj === "{}") return ""; else return obj;
        },
        serialize: function (form) {
            var o = {};
            $.each(form.serializeArray(), function (index) {
                if (o[this['name']]) {
                    o[this['name']] = o[this['name']] + "," + this['value'];
                } else {
                    o[this['name']] = this['value'];
                }
            });
            return o;
        },
        //组合变量传递keys,values,types形式// 转换JSON为字符串
        comVar: function (variables) {
            var keys = "", values = "", types = "", vars = {};
            if (variables) {
                $.each(variables, function () {
                    if (keys != "") {
                        keys += ",";
                        values += ",";
                        types += ",";
                    }
                    keys += this.key;
                    values += this.value;
                    types += this.type;
                });
            }
            vars = {keys: keys, values: values, types: types};
            return vars;
        },
        repack: function (tableid) {
            var tmpid = tableid || "";
            if (tmpid === "") {
                return;
            }
            var tableids = [];
            if (tableid instanceof Array) {
                // 数组时，逐个处理
                tableids = tableid;
            } else {
                if ($("#" + tableid).is("form")) {
                    $("#" + tableid + " table").each(function () {
                        // form下所有table逐个处理
                        tableids.push($(this).attr("id"));
                    });
                } else {
                    tableids[0] = tableid;
                }
            }
            for (var i = 0; i < tableids.length; i++) {
                // 逐个Table进行压缩
                var id = tableids[i];
                $("#" + id + " tbody tr").each(function (index) {
                    // 逐行处理各个控件
                    var tr = $(this);
                    // input或select改名
                    tr.find("input, select").each(function () {
                        // 获取name属性，如果没有则不处理
                        var that = $(this);
                        var name = that.attr("name") || "";
                        if (name !== "") {
                            // 修改名称下标
                            name = name.replace(/\[\d\]/, "[" + index + "]");
                            that.attr("name", name);
                        }
                    });
                });
            }
        },
        isString: function (str) {
            return (typeof str == 'string') && str.constructor == String;
        },
        isArray: function (obj) {
            return (typeof obj == 'object') && obj.constructor == Array;
        },
        isNumber: function (obj) {
            return (typeof obj == 'number') && obj.constructor == Number;
        },
        isDate: function (obj) {
            return (typeof obj == 'object') && obj.constructor == Date;
        },
        isFunction: function (obj) {
            return (typeof obj == 'function') && obj.constructor == Function;
        },
        isObject: function (obj) {
            return (typeof obj == 'object') && obj.constructor == Object;
        }
    },
    error: function (str, title) {
        var arr_html = '';
        var arr = Str.split(",");
        arr_html += "<ul class='custom-bootbox'>";
        $.each(arr, function (i, val) {
            arr_html += '<li>' + val + '</li>'
        });
        arr_html += "</ul>";
        bootbox.dialog({
            message: arr_html,
            title: ROBIN.Object.notNull(title) ? title : "警告信息",
            buttons: {
                OK: {}
            }
        });
    }
};