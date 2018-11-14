package com.kaka.house.web.controller;


import com.kaka.house.biz.service.HouseService;
import com.kaka.house.common.model.House;
import com.kaka.house.common.page.PageData;
import com.kaka.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HouseController {
    @Autowired
    HouseService houseService;
    //1.实现分页 2.支持小区搜索 类型搜索
    //3.支持排序 4.支持展示图片，价格，标题，地址等信息
    @RequestMapping("/house/list")
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap){
        PageData<House> ps = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
        modelMap.put("ps", ps);
        modelMap.put("vo", query);
        return "house/listing";

    }
}
