package com.trionesdev.phecda.foundation.core.domains.notification.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactGroupCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.ContactGroupPO;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.mapper.ContactGroupMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ContactGroupDAO extends ServiceImpl<ContactGroupMapper, ContactGroupPO> {

    private LambdaQueryWrapper<ContactGroupPO> buildQueryWrapper(ContactGroupCriteria criteria) {
        LambdaQueryWrapper<ContactGroupPO> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            wrapper.le(StrUtil.isNotBlank(criteria.getName()), ContactGroupPO::getName, criteria.getName());
        }
        return wrapper.orderByDesc(ContactGroupPO::getCreatedAt);
    }

    public void removeGroupContact(String contactId) {
        baseMapper.update(new UpdateWrapper<ContactGroupPO>().setSql("contact_ids = contact_ids - '{0}'", contactId).apply("contact_ids @> '[\"{0}\"]'", contactId));
    }

    public List<ContactGroupPO> selectList(ContactGroupCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<ContactGroupPO> selectPage(ContactGroupCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(MpPageUtils.page(criteria), buildQueryWrapper(criteria)));
    }

}
