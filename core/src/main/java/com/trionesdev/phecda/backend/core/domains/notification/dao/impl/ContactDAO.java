package com.trionesdev.phecda.backend.core.domains.notification.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import com.trionesdev.phecda.backend.core.domains.notification.dao.criteria.ContactCriteria;
import com.trionesdev.phecda.backend.core.domains.notification.dao.entity.Contact;
import com.trionesdev.phecda.backend.core.domains.notification.dao.mapper.ContactMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ContactDAO extends ServiceImpl<ContactMapper, Contact> {
    private LambdaQueryWrapper<Contact> buildQueryWrapper(ContactCriteria criteria) {
        LambdaQueryWrapper<Contact> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            queryWrapper.like(StrUtil.isNotBlank(criteria.getName()), Contact::getName, criteria.getName())
                    .eq(StrUtil.isNotBlank(criteria.getPhone()), Contact::getPhone, criteria.getPhone())
                    .eq(StrUtil.isNotBlank(criteria.getEmail()), Contact::getEmail, criteria.getEmail());

        }
        queryWrapper.orderByDesc(Contact::getCreatedAt);
        return queryWrapper;
    }

    public List<Contact> selectList(ContactCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<Contact> selectPage(ContactCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(MpPageUtils.page(criteria), buildQueryWrapper(criteria)));
    }

}
