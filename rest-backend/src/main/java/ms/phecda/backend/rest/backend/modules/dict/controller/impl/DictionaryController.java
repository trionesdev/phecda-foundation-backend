package ms.phecda.backend.rest.backend.modules.dict.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.dict.dao.criteria.DictionaryCriteria;
import ms.phecda.backend.core.modules.dict.dao.entity.Dictionary;
import ms.phecda.backend.core.modules.dict.service.bo.DictionaryBO;
import ms.phecda.backend.core.modules.dict.service.impl.DictionaryService;
import ms.phecda.backend.rest.backend.modules.dict.controller.query.DictionaryQuery;
import ms.phecda.backend.rest.backend.modules.dict.controller.ro.DictionaryRO;
import ms.phecda.backend.rest.backend.modules.dict.support.DictConstants;
import ms.phecda.backend.rest.backend.modules.dict.support.DictRestConvertMapper;
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
 * 字典数据表 前端控制器
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */
@Tag(name = "字典")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DictConstants.DICT_URI)
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @Operation(summary = "新建字典值")
    @PostMapping(value = "dictionaries")
    public void createDictionary(@Validated @RequestBody DictionaryRO args) {
        Dictionary dictionary = DictRestConvertMapper.INSTANCE.from(args);
        dictionaryService.create(dictionary);
    }

    @Operation(summary = "根据ID修改字典值")
    @PutMapping(value = "dictionaries/{id}")
    public void updateDictionaryById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody DictionaryRO args
    ) {
        Dictionary dictionary = DictRestConvertMapper.INSTANCE.from(args);
        dictionary.setId(id);
        dictionaryService.update(dictionary);
    }

    @Operation(summary = "根据ID删除字典值")
    @DeleteMapping(value = "dictionaries/{id}")
    public void deleteDictionaryById(
            @PathVariable(value = "id") String id
    ) {
        dictionaryService.deleteById(id);
    }

    @Operation(summary = "根据ID获取字典值")
    @GetMapping(value = "dictionaries/{id}")
    public Dictionary queryDictionaryById(
            @PathVariable(value = "id") String id
    ) {
        return dictionaryService.queryById(id).orElse(null);
    }

    @Operation(summary = "查询字典值列表(分页)")
    @GetMapping(value = "dictionaries/page")
    public PageInfo<DictionaryBO> queryDictionaryPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            DictionaryQuery query
    ) {
        DictionaryCriteria criteria = DictRestConvertMapper.INSTANCE.from(query);
        return dictionaryService.queryPage(pageNum, pageSize, criteria);
    }

    @Operation(summary = "查询字典值列表")
    @GetMapping(value = "dictionaries/list")
    public List<Dictionary> queryDictionaryList(
            DictionaryQuery query
    ) {
        DictionaryCriteria criteria = DictRestConvertMapper.INSTANCE.from(query);
        return dictionaryService.queryList(criteria);
    }
}
