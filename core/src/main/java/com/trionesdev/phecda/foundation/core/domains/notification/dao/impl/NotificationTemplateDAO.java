package com.trionesdev.phecda.foundation.core.domains.notification.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.NotificationTemplateCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.NotificationTemplatePO;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.mapper.NotificationTemplateMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class NotificationTemplateDAO extends ServiceImpl<NotificationTemplateMapper, NotificationTemplatePO> {
    private LambdaQueryWrapper<NotificationTemplatePO> buildQueryWrapper(NotificationTemplateCriteria criteria) {
        LambdaQueryWrapper<NotificationTemplatePO> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            wrapper.eq(StrUtil.isNotBlank(criteria.getCode()), NotificationTemplatePO::getCode, criteria.getCode());
        }
        return wrapper.orderByDesc(NotificationTemplatePO::getCreatedAt);
    }

    public List<NotificationTemplatePO> selectList(NotificationTemplateCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<NotificationTemplatePO> selectPage(NotificationTemplateCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(MpPageUtils.page(criteria), buildQueryWrapper(criteria)));
    }

}
