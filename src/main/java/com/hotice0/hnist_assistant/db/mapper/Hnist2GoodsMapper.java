package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.controller.hnist2_module.Hnist2GoodsController;
import com.hotice0.hnist_assistant.db.model.Hnist2Goods;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsDetailView;
import com.hotice0.hnist_assistant.utils.cache.CacheConstant;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface Hnist2GoodsMapper {
    @Select("SELECT * FROM t_hnist2_goods")
    List<Hnist2Goods> getAll();

    @Select("SELECT * FROM t_hnist2_goods WHERE id = #{id} ")
    Hnist2Goods findByID(@Param("id") Integer id);

    @Select("<script>" +
            "SELECT * FROM v_hnist2_goods_detail " +
            "<where>" +
            "<if test='goods_type_id!=-100'>goods_type_id=#{goods_type_id}</if>" +
            "</where>" +
            " ORDER BY" +
            "<if test='sort_type==" + Hnist2GoodsController.SORT_TYPE_HOT + "'>visit_amount</if>" +
            "<if test='sort_type==" + Hnist2GoodsController.SORT_TYPE_TIME + "'>created_at</if>" +
            " DESC" +
            " LIMIT #{index},#{pageSize}" +
            "</script>")
    List<Hnist2GoodsDetailView> getByGoodsType(
            @Param("goods_type_id") Integer goods_type_id,
            @Param("sort_type") Integer sort_type,
            @Param("index") Integer index,
            @Param("pageSize") Integer pageSize
    );

    @Select("SELECT * FROM t_hnist2_goods WHERE owner_id=#{hnist2_id} ORDER BY created_at DESC LIMIT #{index},#{pageSize}")
    List<Hnist2Goods> getGoodsByHnist2ID(
            @Param("hnist2_id") Integer hnist2_id,
            @Param("index") Integer index,
            @Param("pageSize") Integer pageSize
    );


    @Select("SELECT * FROM v_hnist2_goods_detail WHERE id=#{goods_id}")
    Hnist2GoodsDetailView getGoodsDetailByGoodsID(@Param("goods_id") Integer goods_id);

    /**
     * 关键词匹配
     * @param keyword
     * @param index
     * @param pageSize
     * @return
     */
    @Select("SELECT * FROM v_hnist2_goods_detail WHERE title REGEXP #{keyword} OR description REGEXP #{keyword} ORDER BY created_at DESC LIMIT #{index},#{pageSize}")
    List<Hnist2GoodsDetailView> searchGoodsWithKeyword(
            @Param("keyword") String keyword,
            @Param("index") Integer index,
            @Param("pageSize") Integer pageSize
    );

    /**
     * 通过ID查询商品详细信息列表
     *
     * @param goods_ids
     * @return
     */
    @Select("SELECT * FROM v_hnist2_goods_detail WHERE id IN(${goods_ids}) ORDER BY created_at DESC")
    List<Hnist2GoodsDetailView> getGoodsDetailByIDs(@Param("goods_ids") String goods_ids);


    @Insert("INSERT INTO t_hnist2_goods(title,description,picture,is_new,price,purchase_price,contact_me,goods_type_id,is_free,free_require,owner_id,collection_amount,visit_amount) VALUES(#{title},#{description},#{picture},#{is_new},#{price},#{purchase_price},#{contact_me},#{goods_type_id},#{is_free},#{free_require},#{owner_id},#{collection_amount},#{visit_amount})")
    @Options(useGeneratedKeys = true)
    void insert(Hnist2Goods hnist2Goods);

//    @CacheEvict(value = CacheConstant.HNIST2_GOODS_DETAIL_GET_BY_GOODS_TYPE, allEntries=true)
    @Update("UPDATE t_hnist2_goods SET title=#{title},description=#{description},picture=#{picture},is_new=#{is_new},price=#{price},purchase_price=#{purchase_price},contact_me=#{contact_me},goods_type_id=#{goods_type_id},is_free=#{is_free},free_require=#{free_require},owner_id=#{owner_id},collection_amount=#{collection_amount},visit_amount=#{visit_amount} where id=#{id}")
    int update(Hnist2Goods hnist2Goods);

//    @CacheEvict(value = CacheConstant.HNIST2_GOODS_DETAIL_GET_BY_GOODS_TYPE, allEntries=true)
    @Update("UPDATE t_hnist2_goods SET visit_amount=visit_amount+1 WHERE id=#{id}")
    int visit_amount_add(Integer id);

//    @CacheEvict(value = CacheConstant.HNIST2_GOODS_DETAIL_GET_BY_GOODS_TYPE, allEntries=true)
    @Update("UPDATE t_hnist2_goods SET collection_amount=collection_amount-1 WHERE id=#{id} AND collection_amount>0")
    int collection_amount_decrease(Integer id);

//    @CacheEvict(value = CacheConstant.HNIST2_GOODS_DETAIL_GET_BY_GOODS_TYPE, allEntries=true)
    @Update("UPDATE t_hnist2_goods SET collection_amount=collection_amount+1 WHERE id=#{id}")
    int collection_amount_add(Integer id);

//    @CacheEvict(value = CacheConstant.HNIST2_GOODS_DETAIL_GET_BY_GOODS_TYPE, allEntries=true)
    @Delete("DELETE FROM t_hnist2_goods WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);
}
