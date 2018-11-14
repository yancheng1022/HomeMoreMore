package com.kaka.house.biz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.kaka.house.biz.mapper.HouseMapper;
import com.kaka.house.common.model.Community;
import com.kaka.house.common.model.House;
import com.kaka.house.common.page.PageData;
import com.kaka.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {
    @Autowired
    private HouseMapper houseMapper;

    /**
     * 1.查询小区
     * 2.添加图片服务器地址前缀
     * 3.构建分页结果
     * @param query
     * @param build
     */

    @Value("${file.prefix}")
    private String imgPrefix;
    public PageData<House> queryHouse(House query, PageParams pageParams) {
        List<House> houses = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(query.getName())) {
            Community community = new Community();
            community.setName(query.getName());
            List<Community> communities = houseMapper.selectCommunity(community);
            if (!communities.isEmpty()) {
                query.setCommunityId(communities.get(0).getId());
            }
        }
        houses = queryAndSetImg(query,pageParams);//添加图片服务器地址前缀
        Long count = houseMapper.selectPageCount(query);
        return PageData.buildPage(houses, count, pageParams.getPageSize(), pageParams.getPageNum());
    }

    public List<House> queryAndSetImg(House query, PageParams pageParams) {
        List<House> houses =   houseMapper.selectPageHouses(query, pageParams);
        houses.forEach(h ->{
            h.setFirstImg(imgPrefix + h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            h.setFloorPlanList(h.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
        });
        return houses;
    }
}
