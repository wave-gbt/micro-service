package com.ailk.service.gbt;

import com.ailk.Entity.OrderQueryBean;
import org.n3r.eql.Eql;

import org.n3r.eql.EqlPage;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * 查询订单相关服务
 */
@Component
public class DemoService {

    /**
     * 查询订单数量
     * @param order
     * @return
     */
    public int queryOrdeCount(OrderQueryBean order) {
        int count = new Eql().selectFirst("queryOrderCount").params(order).returnType(Integer.class).execute();
        if (count == 0){
           count = new Eql().selectFirst("queryOrderCountFromHistory").params(order).returnType(Integer.class).execute();
        }
        return count;
    }
    /**
     * 查询订单
     * @param order
     * @return
     */
    public List<Map> queryOrderMessage(OrderQueryBean order) {
        EqlPage ep = new EqlPage();
        ep.setPageRows(order.getPageSize());
        ep.setCurrentPage(order.getCurrentPage());
        ep.setTotalRows(order.getRowsCount());

        List list =  new Eql().select("queryOrderMessage").limit(ep).params(order).returnType(Map.class).execute();
        if (list == null || list.isEmpty()) {
            list = new Eql().select("queryOrderMessageFromHistory").limit(ep).params(order).returnType(Map.class).execute();
        }
        return list;
    }
}

