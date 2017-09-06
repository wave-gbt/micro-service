$('#form-phone').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields:{
        phone:{
            validators: {
                regexp: {
                    regexp: /^1[34578]\d{9}$/,
                    message: '手机号输入错误'
                }
            }
        },
        orderno:{
            validators: {
                regexp: {
                    regexp: /^[0-9]+$/,
                    message: '订单号输入错误,只能输入数字'
                }
            }
        }
    },
});

$('#btn-order').click(function () {

    var bootstrapValidator = $("#form-phone").data('bootstrapValidator');
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid())
        $("#form-phone").submit();
    else return;

    var orderNo = $('#orderno').val();
    var phone = $('#phone').val();
    var channel = $('#channel').val();
    var product = $('#product').val();
    var state = $('state').val();
    var page = 1;
    var param = {
        orderNo:orderNo,
        currentPage:page,
        channel:channel,
        phone:phone,
        product:product,
        state:state
    };
    getData(param);
});

// take page data
function getData(param) {
    $('#myModal').modal();
    $.ajax({
        url:'/query',
        type:'POST',
        async:true,
        data:param,
        timeout:5000,
        dataType:'json',
        success:function(data,textStatus,jqXHR){
            $('#myModal').modal('hide');
            $('tbody').html('');
            $('#example').html('');
            console.log(data);
            var tbody = $('tbody');
            var list = data.list;
            if (list.length > 0) {
                $.each(list,function (index,item) {
                    OutputData(tbody,item);
                });
                var pageCount = data.pageCount;
                var currentPage = data.pageIndex;
                var options = {
                    bootstrapMajorVersion: 3,
                    currentPage: currentPage,
                    totalPages: pageCount,
                    numberOfPages:4,
                    itemTexts: function (type, page, current) {
                        switch (type) {
                            case "first":
                                return "首页";
                            case "prev":
                                return "上一页";
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return page;
                        }
                    },
                    onPageClicked: function (event, originalEvent, type, page) {
                        param.currentPage = page;
                        getData(param);
                    }
                };
                $('#example').bootstrapPaginator(options);
            } else {
                OutputMessage(tbody);
            }
            console.log(textStatus);
            console.log(jqXHR);
        },
        error:function(xhr,textStatus){
            $('#myModal').modal('hide');
            console.log(textStatus);
            console.log(xhr);
            $('tbody').html('');
            $('#example').html('');
            var tbody = $('tbody');
            tbody.append('<tr><th colspan ="7"><center>查询失败</center></th></tr>');
        }
    })
}
// append table
function OutputData(tbody, item) {
    tbody.append("<tr>" +
        "<td>" + item.ORDER_NO + "</td>" +
        "<td>" + item.PARTITION_ID + "</td>" +
        "<td>" + item.CHANNEL + "</td>" +
        "<td>" + item.CREATE_TIME + "</td>" +
        "<td>" + item.MOBILE + "</td>" +
        "<td>" + item.PRODUCT + "</td>" +
        "<td>" + item.STATE + "</td>" +
        "</tr>");
}
// append message
function OutputMessage(tbody) {
    tbody.append('<tr><th colspan ="7"><center>查询无数据</center></th></tr>');
}