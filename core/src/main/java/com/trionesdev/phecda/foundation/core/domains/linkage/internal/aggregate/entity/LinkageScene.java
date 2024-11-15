package com.trionesdev.phecda.foundation.core.domains.linkage.internal.aggregate.entity;

import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.Scene;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionTrigger;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LinkageScene {
    private String id;
    private String name;
    private String description;
    private List<Scene> scenes;
    private ActionTrigger actionTrigger;
    private List<PhecdaAction> actions;
    private Boolean enabled;
}
