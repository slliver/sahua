var Dialog = Dialog || {};
Dialog = {
    channelDialog: function(userPkid, f) {
        comp.dialog({
            id: "channelDialog",
            closeButton: true,
            btnClose: "关闭",
            title: "选择渠道",
            style: "width: 1000px;",
            url: ctx + "/channel/dialog?userPkid="+userPkid
        }, f);
    }
};
