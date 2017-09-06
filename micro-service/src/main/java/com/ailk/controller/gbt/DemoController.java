package com.ailk.controller.gbt;

import com.ailk.service.gbt.DemoService;
import com.ailk.Entity.OrderQueryBean;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    DemoService demoService;

    @RequestMapping(value = "/tt", method = RequestMethod.GET)
    public String index() {
        return "index_t.html";
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map QueryOrderMsgByPhone(OrderQueryBean order) {
        // 当前页
        int pageIndex = (order.getCurrentPage() == null ? 1:order.getCurrentPage());

        // pageSize
        int pageSize = 5;
        // 总页数
        int pageCount = 0;
        int rowCount = 0;
        List<Map> orderMsg = new ArrayList();

        order.setPageSize(pageSize);

        rowCount = demoService.queryOrdeCount(order);
        order.setRowsCount(rowCount);

        orderMsg = demoService.queryOrderMessage(order);

        logger.info(order.toString());
        logger.info(JSON.toJSONString(orderMsg));

        // 计算页数
        if(rowCount % pageSize != 0)
        {
            pageCount = rowCount / pageSize + 1;
        } else {
            pageCount = rowCount / pageSize;
        }

        Map data = new HashMap();
        data.put("list",orderMsg);
        data.put("pageCount",pageCount);
        data.put("currentPage",pageIndex);
        return data;
    }
}
