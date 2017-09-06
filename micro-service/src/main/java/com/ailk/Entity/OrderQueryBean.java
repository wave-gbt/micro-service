package com.ailk.Entity;

import org.n3r.eql.EqlPage;

import java.io.Serializable;

public class OrderQueryBean implements Serializable{

    private String phone;
    private String orderNo;
    private String channel;
    private String product;
    private EqlPage page;
    private String state;

    private Integer rowsCount;

    private Integer currentPage;
    private Integer pageSize;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(Integer rowsCount) {
        this.rowsCount = rowsCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public EqlPage getPage() {
        return page;
    }

    public void setPage(EqlPage page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "OrderQueryBean{" +
                "phone='" + phone + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", channel='" + channel + '\'' +
                ", product='" + product + '\'' +
                ", page=" + page +
                ", rowsCount=" + rowsCount +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
