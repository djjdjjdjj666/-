package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 动态查询购物车数据
     * @return
     */
    public List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新数据
     */
    public void update(ShoppingCart shoppingCart);

    /**
     *  插入购物车数据
     */
    @Insert("INSERT INTO shopping_cart (NAME, IMAGE, USER_ID, DISH_ID, SETMEAL_ID, DISH_FLAVOR, NUMBER, AMOUNT, CREATE_TIME) " +
            "VALUES (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{createTime})")
    void insert(ShoppingCart shoppingCart);
}
