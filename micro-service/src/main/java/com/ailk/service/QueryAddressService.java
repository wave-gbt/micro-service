package com.ailk.service;

import org.n3r.eql.Eql;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class QueryAddressService {

    public List<Map> queryProvinceNameAndProvinceCode() {
        return new Eql().select("queryProvinceNameAndProvinceCode").returnType(Map.class).execute();
    }

    public List<Map> queryCityNameAndCityCode(String provinceCode) {
        return new Eql().select("queryCityNameAndCityCode").params(provinceCode).returnType(Map.class).execute();
    }

    public List<Map> queryDistrictNameAndDistrictCode(String cityCode) {
        return new Eql().select("queryDistrictNameAndDistrictCode").params(cityCode).returnType(Map.class).execute();
    }

}