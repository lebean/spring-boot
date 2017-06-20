package leix.lebean.sweb.city;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import leix.lebean.sweb.common.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@Api(value = "城市服务", description = "与城市数据相关的服务")
public class CityController extends BaseController {

    @Autowired
    private CityService cityService;

    @GetMapping("/cities")
    @ApiOperation(value = "城市列表", notes = "获取城市列表数据")

    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization",paramType = "header"),
            @ApiImplicitParam(name = "page", paramType = "query", value = "页码，从1 开始", required = false, defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "size", paramType = "query", value = "数据量，每一页返回的数据条数", required = false, defaultValue = "10", dataType = "Integer")
    })
    public List<City> getCities(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        List<City> list = new ArrayList<>();
        list.add(new City());
        list.add(new City());
        return list;
    }

    @GetMapping("/cities/{id}")
    @ApiOperation(value = "城市详情", notes = "根据城市id获取城市详细信息")
    @ApiImplicitParam(name = "id", paramType = "path", value = "城市编号", required = true, defaultValue = "1", dataType = "Integer")
    public City getCityById(@PathVariable(value = "id") int id) {
        return cityService.findById(id);
    }

    @GetMapping("cities/{id}/children")
    @ApiOperation(value = "下一级城市列表", notes = "根据城市id获取下一级城市列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "城市编号", required = true, defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "page", paramType = "query", value = "页码，从1 开始", required = false, defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "size", paramType = "query", value = "数据量，每一页返回的数据条数", required = false, defaultValue = "10", dataType = "Integer")
    })
    public List<City> getcityChildren(@PathVariable(name = "id") int id, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        List<City> list = new ArrayList<>();
        list.add(new City());
        list.add(new City());
        return list;
    }

    @DeleteMapping("/cities/{id}")
    @ApiOperation(value = "删除城市", notes = "通过城市id删除城市信息")
    @ApiImplicitParam(name = "id", value = "城市编号", paramType = "path", required = true, defaultValue = "1", dataType = "Integer")
    public boolean deleteCityById(@PathVariable(value = "id") int id) {
        return true;
    }

    @PutMapping("/cities")
    @ApiOperation(value = "更新城市信息", notes = "通过城市实体更新城市的信息")
    @ApiImplicitParam(name = "city", value = "城市信息数据实体",dataType = "City")
    public City updateCity(@RequestBody City city) {
        return city;
    }

    @PostMapping("/cities")
    @ApiOperation(value = "添加城市信息", notes = "添加一个新的城市信息")
    @ApiImplicitParam(name = "city", value = "城市信息数据实体",dataType = "City")
    public City addCity(@RequestBody City city) {
        return city;
    }
}
