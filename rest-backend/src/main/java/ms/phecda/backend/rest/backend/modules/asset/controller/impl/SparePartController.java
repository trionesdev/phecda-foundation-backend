package ms.phecda.backend.rest.backend.modules.asset.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.asset.dao.criteria.SparePartCriteria;
import ms.phecda.backend.core.modules.asset.dao.entity.SparePart;
import ms.phecda.backend.core.modules.asset.service.impl.SparePartService;
import ms.phecda.backend.rest.backend.modules.asset.controller.query.SparePartQuery;
import ms.phecda.backend.rest.backend.modules.asset.controller.ro.SparePartRO;
import ms.phecda.backend.rest.backend.modules.asset.support.AssetConstants;
import ms.phecda.backend.rest.backend.modules.asset.support.AssetRestConvertMapper;
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
 * 资产配件 前端控制器
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */
@Tag(name = "配件")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = AssetConstants.ASSET_URI)
public class SparePartController {
    private final SparePartService sparePartService;

    @Operation(summary = "新建配件")
    @PostMapping(value = "spareParts")
    public void create(@Validated @RequestBody SparePartRO ro) {
        SparePart entity = AssetRestConvertMapper.INSTANT.from(ro);
        sparePartService.create(entity);
    }

    @Operation(summary = "删除配件")
    @Parameter(name = "ids", description = "以,分割的id字符串，形如：1,2,3")
    @DeleteMapping(value = "spareParts/{ids}")
    public void deleteByIds(@PathVariable(value = "ids") List<String> ids) {
        sparePartService.deleteByIds(ids);
    }

    @Operation(summary = "根据id更新配件")
    @PutMapping(value = "spareParts/{id}")
    public void updateById(@PathVariable(value = "id") String id,
                           @Validated @RequestBody SparePartRO ro) {
        SparePart entity = AssetRestConvertMapper.INSTANT.from(ro);
        entity.setId(id);
        sparePartService.updateById(entity);
    }

    @Operation(summary = "根据id查询配件")
    @GetMapping(value = "spareParts/{id}")
    public SparePart queryById(@PathVariable(value = "id") String id) {
        return sparePartService.queryById(id).orElse(null);
    }

    @Operation(summary = "分页查询")
    @GetMapping(value = "spareParts/page")
    public PageInfo<SparePart> page(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            SparePartQuery query) {
        SparePartCriteria criteria = AssetRestConvertMapper.INSTANT.from(query);
        return sparePartService.queryPage(pageNum, pageSize, criteria);
    }

}
