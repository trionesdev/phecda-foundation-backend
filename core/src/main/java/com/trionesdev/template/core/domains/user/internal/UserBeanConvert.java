package com.trionesdev.template.core.domains.user.internal;

import com.trionesdev.template.core.domains.user.dao.po.UserPO;
import com.trionesdev.template.core.domains.user.dto.UserBindCmd;
import com.trionesdev.template.core.domains.user.dto.UserCreateCmd;
import com.trionesdev.template.core.domains.user.dto.UserDTO;
import com.trionesdev.template.core.domains.user.internal.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("userBeanConvert")
public interface UserBeanConvert {

    User from(UserCreateCmd userCreateDTO);

    User from(UserBindCmd userBindDTO);

    User from(UserPO userPO);

    UserPO entityToPO(User user);

    UserDTO userPoToDTO(UserPO userPO);
    UserDTO userEntityToDTO(User userPO);
}
