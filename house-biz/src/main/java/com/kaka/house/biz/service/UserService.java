package com.kaka.house.biz.service;

import com.google.common.collect.Lists;
import com.kaka.house.common.model.User;
import com.kaka.house.biz.mapper.UserMapper;
import com.kaka.house.common.utils.BeanHelper;
import com.kaka.house.common.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private MailService mailService;
    @Value("${domain.name")
    private String domainName;

    //插入数据库 非激活 密码加盐MD5 保存头像到本地
    //2生成key 绑定email 发送邮件给用户
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        List<String> imgList = fileService.getImagePath(Lists.newArrayList(account.getAvatarFile()));
        if (imgList != null){
            account.setAvatar(imgList.get(0));
        }
        //设置默认值
        BeanHelper.setDefaultProp(account, User.class);
        //设置插入时间
        BeanHelper.onInsert(account);

        account.setEnable(0);
        userMapper.insert(account);
        mailService.registerNotify(account.getEmail());
        return true;
    }

    public boolean enable(String key) {
        return mailService.enable(key);
    }
}
