package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author djj
 * @version 1.0
 * @description: TODO
 * @date 2025/5/8 14:33
 */
@RestController
@Api(tags = "套餐相关接口")
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){

        log.info("分页查询{}", setmealPageQueryDTO);
        PageResult pageResult = setMealService.pageQuery(setmealPageQueryDTO);

        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐{}", setmealDTO);
        setMealService.save(setmealDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id){

        log.info("根据id查询套餐{}", id);

        SetmealVO setmealVO = setMealService.getById(id);

        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO){

        setMealService.update(setmealDTO);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("修改状态")
    public Result updateStatus(@PathVariable Integer status, Long id){

        log.info("修改状态");

        setMealService.updateStatus(status, id);

        return Result.success();
    }


    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除套餐{}", ids);
        setMealService.delete(ids);

        return Result.success();
    }

}
