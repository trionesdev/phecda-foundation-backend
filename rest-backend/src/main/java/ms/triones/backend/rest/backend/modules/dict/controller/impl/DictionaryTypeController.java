package ms.triones.backend.rest.backend.modules.dict.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryTypeCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.DictionaryType;
import ms.triones.backend.core.modules.dict.service.impl.DictionaryTypeService;
import ms.triones.backend.rest.backend.modules.dict.controller.query.DictionaryTypeQuery;
import ms.triones.backend.rest.backend.modules.dict.controller.ro.DictionaryTypeRO;
import ms.triones.backend.rest.backend.modules.dict.support.DictConstants;
import ms.triones.backend.rest.backend.modules.dict.support.DictRestConvertMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */
@Tag(name = "字典类型")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DictConstants.DICT_URI)
public class DictionaryTypeController {
    private final DictionaryTypeService dictionaryTypeService;

    @Operation(summary = "新建字典类型")
    @PostMapping(value = "dictionaryType")
    public void create(@Validated @RequestBody DictionaryTypeRO ro) {
        DictionaryType entity = DictRestConvertMapper.INSTANCE.from(ro);
        dictionaryTypeService.create(entity);
    }

    @Operation(summary = "删除字典类型")
    @Parameter(name = "ids", description = "以,分割的id字符串，形如：1,2,3")
    @DeleteMapping(value = "dictionaryType/{ids}")
    public void deleteByIds(@PathVariable(value = "ids") List<String> ids) {
        dictionaryTypeService.deleteByIds(ids);
    }

    @Operation(summary = "根据id更新字典类型")
    @PutMapping(value = "dictionaryType/{id}")
    public void updateById(@PathVariable(value = "id") String id,
                           @Validated @RequestBody DictionaryTypeRO ro) {
        DictionaryType entity = DictRestConvertMapper.INSTANCE.from(ro);
        entity.setId(id);
        dictionaryTypeService.updateById(entity);
    }

    @Operation(summary = "根据id查询字典类型")
    @GetMapping(value = "dictionaryType/{id}")
    public DictionaryType queryById(@PathVariable(value = "id") String id) {
        return dictionaryTypeService.queryById(id).orElse(null);
    }

    @Operation(summary = "分页查询")
    @GetMapping(value = "dictionaryType/page")
    public PageInfo<DictionaryType> page(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            DictionaryTypeQuery query) {
        DictionaryTypeCriteria criteria = DictRestConvertMapper.INSTANCE.from(query);
        return dictionaryTypeService.queryPage(pageNum, pageSize, criteria);
    }

}
