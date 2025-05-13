package com.sky.mapper;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 批量删除套餐关联菜品数据
     * @param setmealDishesIds
     */
    void deleteBatchByIds(List<Long> setmealDishesIds);


    /**
     * 根据id删除菜品
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmaelId}")
    void deleteById(Long setmealId);

    /**
     * 批量插入套餐关联菜品
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id删除菜品
     * @param ids
     */
    void deleteBatchBySetmealIds(List<Long> ids);
}
