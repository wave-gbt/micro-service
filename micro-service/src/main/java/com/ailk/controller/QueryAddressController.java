package com.ailk.controller;

import com.ailk.service.QueryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/queryaddress")
public class QueryAddressController {

    @Autowired
    private QueryAddressService service;

    @RequestMapping("/queryadinfo")
    public List<Map> queryAddressInfo(String queryType, String queryCode) {

        if ("province".equals(queryType)) {
            return service.queryProvinceNameAndProvinceCode();
        } else if ("city".equals(queryType)) {
            return service.queryCityNameAndCityCode(queryCode);
        }
        return service.queryDistrictNameAndDistrictCode(queryCode);
    }
}
