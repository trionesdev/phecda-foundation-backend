package com.trionesdev.phecda.foundation.core.domains.notification.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.ContactPO;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.mapper.ContactMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ContactDAO extends ServiceImpl<ContactMapper, ContactPO> {
    private LambdaQueryWrapper<ContactPO> buildQueryWrapper(ContactCriteria criteria) {
        LambdaQueryWrapper<ContactPO> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            queryWrapper.like(StrUtil.isNotBlank(criteria.getName()), ContactPO::getName, criteria.getName())
                    .eq(StrUtil.isNotBlank(criteria.getPhone()), ContactPO::getPhone, criteria.getPhone())
                    .eq(StrUtil.isNotBlank(criteria.getEmail()), ContactPO::getEmail, criteria.getEmail());

        }
        queryWrapper.orderByDesc(ContactPO::getCreatedAt);
        return queryWrapper;
    }

    public List<ContactPO> selectList(ContactCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<ContactPO> selectPage(ContactCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(MpPageUtils.page(criteria), buildQueryWrapper(criteria)));
    }

}
