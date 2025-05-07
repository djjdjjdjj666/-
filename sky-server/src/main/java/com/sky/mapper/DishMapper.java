package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    /**
     * 插入菜品数据
     *
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTOd
     * @return
     */
    List<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTOd);

    /**
     * 判断有没有起售商品
     * @param ids
     * @return
     */
    Boolean getByIdFlag(List<Long> ids);

    /**
     * 根据id删除菜品
     * @param ids
     */
    void deleteById(List<Long> ids);
}
