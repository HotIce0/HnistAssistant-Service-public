package com.hotice0.hnist_assistant.controller.hnist2_module;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hotice0.hnist_assistant.annotation.BasicLoginAuth;
import com.hotice0.hnist_assistant.annotation.Hnist2BindAuth;
import com.hotice0.hnist_assistant.annotation.PermissionAuth;
import com.hotice0.hnist_assistant.controller.BaseController;
import com.hotice0.hnist_assistant.db.model.*;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.service.basic_module.FileUploadService;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2CollectionService;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2GoodsMsgService;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2GoodsService;
import com.hotice0.hnist_assistant.utils.result.CommonResult;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-04-09 14:52
 */
@RestController
@RequestMapping(value = "/app/hnist2/goods")
@Validated
public class Hnist2GoodsController extends BaseController {
    @Autowired
    Hnist2GoodsService hnist2GoodsService;
    @Autowired
    Hnist2GoodsMsgService hnist2GoodsMsgService;
    @Autowired
    Hnist2CollectionService hnist2CollectionService;

    public static final int SORT_TYPE_TIME = 0;
    public static final int SORT_TYPE_HOT = 1;

    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public CommonResult searchGoods(
            @NotBlank(message = "搜索关键词不可以为空")
            String keyword,
            @NotNull(message = "index不能为空")
            @Min(value = 0, message = "index最小值为0")
                    Integer index,
            @NotNull(message = "pageSize不能为空")
            @Max(value = 90, message = "页大小不可以超过90")
            @Min(value = 1, message = "页大小最小为1")
                    Integer pageSize
    ) throws HAException {
        List<Hnist2GoodsDetailView> hnist2GoodsDetailViewList = hnist2GoodsService.searchByKeyword(keyword, index, pageSize);
        return CommonResult.success(hnist2GoodsDetailViewList);
    }

    /**
     * 列表获取商品
     *
     * @param sort_type
     * @param goods_type_id
     * @param index
     * @param pageSize
     * @return
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult getGoods(
            @NotNull(message = "排序类型不能为空")
            @Max(value = SORT_TYPE_HOT, message = "sort_type有效值为0,1")
            @Min(value = SORT_TYPE_TIME, message = "sort_type有效值为0,1")
                    Integer sort_type, // 0 按照时间先后排序 1 按照热度排序
            @NotNull(message = "商品类型不能为空")
                    Integer goods_type_id, // -100 代表全部的类型
            @NotNull(message = "index不能为空")
            @Min(value = 0, message = "index最小值为0")
                    Integer index,
            @NotNull(message = "pageSize不能为空")
            @Max(value = 90, message = "页大小不可以超过90")
            @Min(value = 1, message = "页大小最小为1")
                    Integer pageSize
    ) throws HAException {
        return CommonResult.success(hnist2GoodsService.getByGoodsType(goods_type_id, sort_type, index, pageSize));
    }

    /**
     * 通过商品ID数组获取商品详细信息列表
     * @param goods_ids
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/getByGoodsIDs", method = RequestMethod.POST)
    public CommonResult getGoodsByIDs(
            @NotBlank(message = "商品ID json数组不可以为空")
            String goods_ids
    ) throws HAException {
        List<Integer> idList = JSON.parseArray(goods_ids, Integer.class);
        if (idList.size() < 1) {
            return CommonResult.success(new ArrayList<>());
        }
        List<Hnist2GoodsDetailView> hnist2GoodsDetailViewList = hnist2GoodsService.getGoodsDetailByIDs(idList);
        return CommonResult.success(hnist2GoodsDetailViewList);
    }

    /**
     * 获取个人发布的商品
     *
     * @param request
     * @param index
     * @param pageSize
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/getMy", method = RequestMethod.POST)
    public CommonResult getMyGoods(
            HttpServletRequest request,
            @NotNull(message = "index不能为空")
            @Min(value = 0, message = "index最小值为0")
                    Integer index,
            @NotNull(message = "pageSize不能为空")
            @Max(value = 90, message = "页大小不可以超过90")
            @Min(value = 1, message = "页大小最小为1")
                    Integer pageSize
    ) throws HAException {
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        return CommonResult.success(hnist2GoodsService.getGoodsByHnist2ID(hnist2_id, index, pageSize));
    }

    /**
     * 收藏商品
     *
     * @param request
     * @param goods_id
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/collect", method = RequestMethod.POST)
    public CommonResult collectGoods(
            HttpServletRequest request,
            @NotNull(message = "商品id不能为空")
                    Integer goods_id
    ) throws HAException {
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        hnist2CollectionService.addToCollection(hnist2_id, goods_id);
        return CommonResult.success("商品收藏成功");
    }

    /**
     * 取消商品收藏
     * @param request
     * @param goods_id
     * @return
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/cancelCollect", method = RequestMethod.POST)
    public CommonResult cancelGoodsCollect(
            HttpServletRequest request,
            @NotNull(message = "商品id不能为空")
                    Integer goods_id
    ) throws HAException {
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        hnist2CollectionService.dropFromCollection(hnist2_id, goods_id);
        return CommonResult.success("商品收藏取消成功");
    }

    /**
     * 获取商品详细信息
     *
     * @param goods_id
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public CommonResult getGoodsDetail(
            @NotNull(message = "商品id不能为空")
                    Integer goods_id
    ) throws HAException {
        Hnist2GoodsDetailView hnist2GoodsDetailView = hnist2GoodsService.getGoodsDetailByID(goods_id);
        return CommonResult.success(hnist2GoodsDetailView);
    }

    /**
     * 获取收藏的商品ID列表
     * @param request
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/getMyCollections", method = RequestMethod.POST)
    public CommonResult getMyCollections(
            HttpServletRequest request
    ) throws HAException {
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        List<Hnist2Collection> hnist2CollectionList = hnist2CollectionService.getCollectionByHnist2ID(hnist2_id);
        return CommonResult.success(hnist2CollectionList);
    }

    /**
     * 新增商品
     *
     * @param request
     * @param title
     * @param description
     * @param picture
     * @param is_new
     * @param price
     * @param purchase_price
     * @param goods_type_id
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "发布商品的权限", value = {BasicPermission.HNIST2_PERMISSION, BasicPermission.HNIST2_PUBLISH_GOODS_PERMISSION})
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public CommonResult publishGoods(
            HttpServletRequest request,
            @NotBlank(message = "标题不可以为空")
                    String title,
            @NotBlank(message = "商品描述不可以为空")
                    String description,
            String picture,
            @NotNull(message = "是否全新不可以为空")
            @Max(value = 1, message = "是否全新有效值为0或1")
            @Min(value = 0, message = "是否全新有效值为0或1")
                    Integer is_new,
            @NotNull(message = "售卖价格不可以为空")
                    BigDecimal price,
            @NotNull(message = "入手价不可以为空")
                    BigDecimal purchase_price,
            @NotBlank(message = "联系方式不可以为空")
                    String contact_me,
            @NotNull(message = "商品类型不可以为空")
                    Integer goods_type_id,
            @NotNull(message = "是否是免费送不可以为空")
            @Max(value = 1, message = "是否免费有效值为0或1")
            @Min(value = 0, message = "是否免费有效值为0或1")
                    Integer is_free,
            String free_require
    ) throws HAException {
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        if (is_free.equals(Hnist2Goods.IS_FREE_YES)) {
            // 免费送时价格为0
            price = new BigDecimal(0);
            // 如果是免费，要求不可以填写为空
            if (!(free_require != null && free_require.length() > 0)) {
                throw new HAException(HAError.HNIST2_GOODS_FREE_REQUIRE_CANNOT_EMPYTY);
            }
        } else if (is_free.equals(Hnist2Goods.IS_FREE_NO)) {
            free_require = "";
        }
        Hnist2Goods hnist2Goods = new Hnist2Goods(
                title,
                description,
                picture,
                is_new,
                price,
                purchase_price,
                contact_me,
                goods_type_id,
                is_free,
                free_require,
                hnist2_id,
                0,
                0
        );
        hnist2GoodsService.add(hnist2Goods);
        return CommonResult.success("商品上架成功");
    }

    /**
     * 删除商品
     *
     * @param request
     * @param goods_id
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public CommonResult del(
            HttpServletRequest request,
            @NotNull(message = "商品id不能为空")
                    Integer goods_id
    ) throws HAException {
        // 判断该用户是否有删除权限
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        Hnist2Goods hnist2Goods = hnist2GoodsService.getByID(goods_id);
        if (!hnist2Goods.getOwner_id().equals(hnist2_id)) {
            throw new HAException(HAError.HNIST2_OP_PERMISSION_DENIED);
        }
        hnist2GoodsService.del(hnist2Goods);
        return CommonResult.success("商品删除成功");
    }

    /**
     * 更新商品信息
     *
     * @param request
     * @param goods_id
     * @param title
     * @param description
     * @param picture
     * @param is_new
     * @param price
     * @param purchase_price
     * @param goods_type_id
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult update(
            HttpServletRequest request,
            @NotNull(message = "商品ID不可为空")
                    Integer goods_id,
            @NotBlank(message = "标题不可以为空")
                    String title,
            @NotBlank(message = "商品描述不可以为空")
                    String description,
            String picture,
            @NotNull(message = "是否全新不可以为空")
                    Integer is_new,
            @NotNull(message = "售卖价格不可以为空")
                    BigDecimal price,
            @NotNull(message = "入手价不可以为空")
                    BigDecimal purchase_price,
            @NotBlank(message = "联系方式不可以为空")
                    String contact_me,
            @NotNull(message = "商品类型不可以为空")
                    Integer goods_type_id,
            @NotNull(message = "是否是免费送不可以为空")
            @Max(value = 1, message = "是否免费有效值为0或1")
            @Min(value = 0, message = "是否免费有效值为0或1")
                    Integer is_free,
            String free_require
    ) throws HAException {
        // 判断该用户是否有更新权限
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        if (is_free.equals(Hnist2Goods.IS_FREE_YES)) {
            // 免费送时价格为0
            price = new BigDecimal(0);
            // 如果是免费，要求不可以填写为空
            if (!(free_require != null && free_require.length() > 0)) {
                throw new HAException(HAError.HNIST2_GOODS_FREE_REQUIRE_CANNOT_EMPYTY);
            }
        } else if (is_free.equals(Hnist2Goods.IS_FREE_NO)) {
            free_require = "";
        }
        Hnist2Goods hnist2Goods = hnist2GoodsService.getByID(goods_id);
        if (!hnist2Goods.getOwner_id().equals(hnist2_id)) {
            throw new HAException(HAError.HNIST2_OP_PERMISSION_DENIED);
        }
        String originPicture = hnist2Goods.getPicture();
        hnist2Goods.setTitle(title);
        hnist2Goods.setDescription(description);
        hnist2Goods.setPicture(picture);
        hnist2Goods.setIs_new(is_new);
        hnist2Goods.setPrice(price);
        hnist2Goods.setPurchase_price(purchase_price);
        hnist2Goods.setGoods_type_id(goods_type_id);
        hnist2Goods.setContact_me(contact_me);
        hnist2Goods.setIs_free(is_free);
        hnist2Goods.setFree_require(free_require);
        hnist2GoodsService.update(hnist2Goods, originPicture);
        return CommonResult.success("商品信息修改成功");
    }

    /**
     * 商品留言
     *
     * @param request
     * @param goods_id
     * @param msg_id
     * @param contant
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2商品留言权限", value = {BasicPermission.HNIST2_PERMISSION, BasicPermission.HNIST2_LEAVE_GOODS_MSG_PERMISSION})
    @RequestMapping(value = "/leaveGoodsMsg", method = RequestMethod.POST)
    public CommonResult leaveGoodsMsg(
            HttpServletRequest request,
            @NotNull(message = "商品ID不可为空")
                    Integer goods_id,
            Integer msg_id,
            @NotBlank(message = "留言内容不可以为空")
                    String contant
    ) throws HAException {
        if (msg_id == -100) {
            msg_id = null;
        }
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        hnist2GoodsMsgService.leaveGoodsMsg(goods_id, hnist2_id, msg_id, contant);
        return CommonResult.success("留言成功");
    }

    /**
     * 获取商品的留言
     * @param goods_id
     * @return
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/getGoodsMsg", method = RequestMethod.POST)
    public CommonResult getGoodsMsg(
            @NotNull(message = "商品ID不可为空")
                    Integer goods_id
    ) throws HAException {
        List<Hnist2GoodsMsgView> hnist2GoodsMsgList = hnist2GoodsMsgService.getMsgsByGoodsID(goods_id);
        return CommonResult.success(hnist2GoodsMsgList);
    }

    /**
     * 删除留言
     * @param request
     * @param msg_id
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/deleteGoodsMsg", method = RequestMethod.POST)
    public CommonResult deleteGoodsMsg(
            HttpServletRequest request,
            @NotNull(message = "留言ID不可以为空")
            Integer msg_id
    ) throws HAException {
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        hnist2GoodsMsgService.deleteGoodsMsg(msg_id, hnist2_id);
        return CommonResult.success("留言删除成功");
    }

    /**
     * 获取关注的人发布的商品
     * @param request
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/getFollowGoods", method = RequestMethod.POST)
    public CommonResult getFollowGoods(
            HttpServletRequest request,
            @NotNull(message = "index不能为空")
            @Min(value = 0, message = "index最小值为0")
                    Integer index,
            @NotNull(message = "pageSize不能为空")
            @Max(value = 90, message = "页大小不可以超过90")
            @Min(value = 1, message = "页大小最小为1")
                    Integer pageSize
    ) throws HAException {
        Integer hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        List<Hnist2GoodsDetailView> hnist2FollowGoodsList = hnist2GoodsService.getFollowGoods(hnist2_id, index, pageSize);
        return CommonResult.success(hnist2FollowGoodsList);
    }
}
