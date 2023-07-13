package ms.triones.backend.rest.backend.modules.asset.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.asset.dao.criteria.AssetCriteria;
import ms.triones.backend.core.modules.asset.dao.entity.Asset;
import ms.triones.backend.core.modules.asset.service.impl.AssetService;
import ms.triones.backend.rest.backend.modules.asset.controller.query.AssetQuery;
import ms.triones.backend.rest.backend.modules.asset.controller.ro.AssetRO;
import ms.triones.backend.rest.backend.modules.asset.support.AssetConstants;
import ms.triones.backend.rest.backend.modules.asset.support.AssetRestConvertMapper;
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
 * 资产(生产设备) 前端控制器
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */
@Tag(name = "资产(生产设备)")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = AssetConstants.ASSET_URI)
public class AssetController {
    private final AssetService assetService;

    @Operation(summary = "新建资产")
    @PostMapping(value = "assets")
    public void createAsset(@Validated @RequestBody AssetRO args) {
        Asset asset = AssetRestConvertMapper.INSTANT.from(args);
        assetService.create(asset);
    }

    @Operation(summary = "根据ID修改产品信息")
    @PutMapping(value = "assets/{id}")
    public void updateAssetById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody AssetRO args
    ) {
        Asset asset = AssetRestConvertMapper.INSTANT.from(args);
        asset.setId(id);
        assetService.update(asset);
    }

    @Operation(summary = "根据ID删除资产")
    @DeleteMapping(value = "assets/{id}")
    public void deleteAssetById(
            @PathVariable(value = "id") String id
    ) {
        assetService.deleteById(id);
    }

    @Operation(summary = "根据ID获取资产信息")
    @GetMapping(value = "assets/{id}")
    public Asset queryAssetById(
            @PathVariable(value = "id") String id
    ) {
        return assetService.queryById(id).orElse(null);
    }

    @Operation(summary = "查询资产列表")
    @GetMapping(value = "assets/page")
    public PageInfo<Asset> queryAssetPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            AssetQuery query
    ) {
        AssetCriteria criteria = AssetRestConvertMapper.INSTANT.from(query);
        return assetService.queryPage(pageNum, pageSize, criteria);
    }

    @Operation(summary = "获取所有资产")
    @GetMapping(value = "assets/all")
    public List<Asset> queryAllAsset() {
        return assetService.queryAllAsset();
    }

}
