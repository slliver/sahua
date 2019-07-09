<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div id="applyConditions-template" style="display:none;">
    <!-- 申请条件 -->
    <div class="form-group applyConditions" id="div-applyConditions-{index}">
        <input type="hidden" name="applyConditions[{index}].type" value="1"/>
        <label for="applyConditions" class="col-sm-2 control-label"></label>
        <div class="col-sm-8" style="margin-top:5px;">
            <input type="text" class="form-control" name="applyConditions[{index}].remark" id="applyConditions[{index}].remark" maxlength="200"/>
        </div>
        <div class="col-sm-2">
            <button id="applyConditions_{index}" type="button" class="btn btn btn-danger btn-delete-applyConditions">删除</button>
        </div>
    </div>
</div>
<div id="reqMaterials-template" style="display:none;">
    <!-- 所需材料 -->
    <div class="form-group reqMaterials" id="div-reqMaterials-{index}">
        <input type="hidden" name="reqMaterials[{index}].type" value="2"/>
        <label for="reqMaterials" class="col-sm-2 control-label"></label>
        <div class="col-sm-8" style="margin-top:5px;">
            <input type="text" class="form-control" name="reqMaterials[{index}].remark" id="reqMaterials[{index}].remark" maxlength="200"/>
        </div>
        <div class="col-sm-2">
            <button id="reqMaterials{index}" type="button" class="btn btn btn-danger btn-delete-reqMaterials">删除</button>
        </div>
    </div>
</div>
<div id="earlyRepayments-template" style="display:none;">
    <!-- 提前还款说明 -->
    <div class="form-group earlyRepayments" id="div-earlyRepayments-{index}">
        <input type="hidden" name="earlyRepayments[{index}].type" value="3"/>
        <label for="earlyRepayments" class="col-sm-2 control-label"></label>
        <div class="col-sm-8" style="margin-top:5px;">
            <input type="text" class="form-control" name="earlyRepayments[{index}].remark" id="earlyRepayments[{index}].remark" maxlength="200"/>
        </div>
        <div class="col-sm-2">
            <button id="earlyRepayments{index}" type="button" class="btn btn btn-danger btn-delete-earlyRepayments">删除</button>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        // 添加申请条件
        $("#btn-applyConditions").click(function () {
            var items = $(".box-body").find(".applyConditions");
            var length = items.length;
            var nextIndex = parseInt(length) + 1;
            var content = $("#applyConditions-template").html().replace(/\{index\}/g, nextIndex);
            // console.log(content);
            if(length == 0){
                $("#div-applyConditions").after(content)
            }else{
                // 获取样式为.applyConditions的最后一个元素
                var last = $('div.box-body .applyConditions:last');
                $(last).after(content)
            }
        });

        // 删除申请条件
        $(document).on("click", ".btn-delete-applyConditions", function(e) {
            $(this).parent().parent().remove();
        });

        // 添加所需材料
        $("#btn-reqMaterials").click(function () {
            var items = $(".box-body").find(".reqMaterials");
            var length = items.length;
            var nextIndex = parseInt(length) + 1;
            var content = $("#reqMaterials-template").html().replace(/\{index\}/g, nextIndex);
            // console.log(content);
            if(length == 0){
                $("#div-reqMaterials").after(content)
            }else{
                // 获取样式为.applyConditions的最后一个元素
                var last = $('div.box-body .reqMaterials:last');
                $(last).after(content)
            }
        });

        // 删除所需材料
        $(document).on("click", ".btn-delete-reqMaterials", function(e) {
            $(this).parent().parent().remove();
        });

        // 添加提前还款说明
        $("#btn-earlyRepayments").click(function () {
            var items = $(".box-body").find(".earlyRepayments");
            var length = items.length;
            var nextIndex = parseInt(length) + 1;
            var content = $("#earlyRepayments-template").html().replace(/\{index\}/g, nextIndex);
            // console.log(content);
            if(length == 0){
                $("#div-earlyRepayments").after(content)
            }else{
                // 获取样式为.applyConditions的最后一个元素
                var last = $('div.box-body .earlyRepayments:last');
                $(last).after(content)
            }
        });

        // 删除提前还款说明
        $(document).on("click", ".btn-delete-earlyRepayments", function(e) {
            $(this).parent().parent().remove();
        });
    });
</script>