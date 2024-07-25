package ms.phecda.backend.core.domains.notification.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trionesdev.commons.mybatisplus.entity.BaseLogicEntity;
import com.trionesdev.commons.mybatisplus.typehandlers.StringCollectionTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "phecda_notification_contact_group", autoResultMap = true)
public class ContactGroup extends BaseLogicEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String description;
    @TableField(typeHandler = StringCollectionTypeHandler.class)
    private List<String> contactIds;
}
