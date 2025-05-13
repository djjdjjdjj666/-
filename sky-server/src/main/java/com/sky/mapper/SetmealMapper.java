package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 插入一条套餐数据
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 批量插入套餐包含的菜品
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id查套餐信息
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 根据套餐id查对应菜品数据
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getByIdWithDished(Long setmealId);

    /**
     * 修改套餐数据
     * @param setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    List<Setmeal> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 判断是否有起售的套餐
     * @param ids
     * @return
     */
    Boolean getByIdIfStart(List<Long> ids);
}
