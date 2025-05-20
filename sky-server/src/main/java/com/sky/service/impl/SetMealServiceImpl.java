package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author djj
 * @version 1.0
 * @description: TODO
 * @date 2025/5/8 14:40
 */
@Service
@Slf4j
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐
     * @param setmealDTO
     */
    public void save(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //先插入一条套餐数据
        setmealMapper.insert(setmeal);

        //获取insert语句生成的主键值
        Long id = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && setmealDishes.size() > 0){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(id);
            });
        }

        //再插入套餐和菜品关系数据
        setmealMapper.insertBatch(setmealDishes);


    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    public SetmealVO getById(Long id) {

        //获取套餐数据
        Setmeal setmeal = setmealMapper.getById(id);

        //获取套餐的菜品数据
        List<SetmealDish> setmealDishes = setmealMapper.getByIdWithDished(id);

        //创建VO
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO
     */
    public void update(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //修改套餐数据
        setmealMapper.update(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //先删除关联菜品数据再插入
        setmealDishMapper.deleteById(setmealDTO.getId());

        if(setmealDishes != null && setmealDishes.size() > 0){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealDTO.getId());
            });
        }
        setmealDishMapper.insertBatch(setmealDishes);

    }

    /**
     * 根据分类id或者套餐名字分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {

        //页码从0开始
        setmealPageQueryDTO.setPage(setmealPageQueryDTO.getPage() - 1);

        List<Setmeal> setmeals = setmealMapper.pageQuery(setmealPageQueryDTO);

        //封装结果
        PageResult pageResult = new PageResult();
        pageResult.setTotal(setmeals.size());
        pageResult.setRecords(setmeals);

        return pageResult;
    }

    /**
     * 修改套餐状态
     * @param status
     */
    public void updateStatus(Integer status, Long id) {

        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        setmeal.setId(id);

        setmealMapper.update(setmeal);
    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Transactional
    public void delete(List<Long> ids) {

        //判断有没有起售的套餐
        Boolean flag = setmealMapper.getByIdIfStart(ids);
        if(flag){
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }

        //删除套餐
        setmealMapper.delete(ids);

        //删除相关联菜品
        setmealDishMapper.deleteBatchBySetmealIds(ids);

    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }


}
