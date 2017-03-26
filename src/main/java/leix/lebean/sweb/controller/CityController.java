package leix.lebean.sweb.controller;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageHelper;
import leix.lebean.sweb.domain.City;
import leix.lebean.sweb.service.CityService;

@RestController
@Api(value = "城市服务", description = "与城市数据相关的服务接口")
public class CityController {

    @Autowired
    private CityService cityService;


    @GetMapping("/cities")
    @ApiOperation(value = "城市列表", notes = "获取城市列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query", value = "页码，从1 开始", required = false, defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "size", paramType = "query", value = "数据量，每一页返回的数据条数", required = false, defaultValue = "10", dataType = "Integer")
    })
    public List<City> getCities(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        PageHelper.startPage(1, 10);
        List<City> list = new ArrayList<>();
        list.add(new City());
        list.add(new City());
        return list;
        //return this.cityService.findByState("CA");
    }

    @GetMapping("/cities/{id}")
    @ApiOperation(value = "城市详情", notes = "根据城市id获取城市详细信息")
    @ApiImplicitParam(name = "id", paramType = "path", value = "城市编号", required = true, defaultValue = "1", dataType = "Integer")
    public City getCityById(@PathVariable(value = "id") int id) {
        return new City();
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
    @ApiImplicitParam(name = "city", value = "城市信息数据实体")
    public City updateCity(@RequestBody City city) {
        return city;
    }

    @PostMapping("cities")
    @ApiOperation(value = "添加城市信息", notes = "添加一个新的城市信息")
    @ApiImplicitParam(name = "city", value = "城市信息数据实体")
    public City addCity(@RequestBody City city) {
        return city;
    }
}
