package com.trionesdev.phecda.backend.rest.backend.domains.dict.controller.query;

import lombok.Data;

/**
* <p>
* 字典类型表
* </p>
*
* @author jscoe
* @since 2023-06-30
*/
@Data
public class DictionaryTypeQuery {

    private String id;

    /**
    * 名称
    */
    private String name;

    /**
    * 编号
    */
    private String code;


    /**
    * 备注
    */
    private String remark;


}