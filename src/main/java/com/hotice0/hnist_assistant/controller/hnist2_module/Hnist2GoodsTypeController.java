package com.hotice0.hnist_assistant.controller.hnist2_module;

import com.hotice0.hnist_assistant.annotation.BasicLoginAuth;
import com.hotice0.hnist_assistant.annotation.Hnist2BindAuth;
import com.hotice0.hnist_assistant.annotation.PermissionAuth;
import com.hotice0.hnist_assistant.controller.BaseController;
import com.hotice0.hnist_assistant.controller.model_view.hnist2_model_view.MVHnist2GoodsType;
import com.hotice0.hnist_assistant.db.model.BasicPermission;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsType;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2GoodsTypeService;
import com.hotice0.hnist_assistant.service.hnist2_module.impl.Hnist2GoodsTypeServiceImpl;
import com.hotice0.hnist_assistant.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品类型控制器
 * @Author HotIce0
 * @Create 2019-04-09 14:37
 */
@RestController
@RequestMapping(value = "/app/hnist2/goodstype")
@Validated
public class Hnist2GoodsTypeController extends BaseController {
    @Autowired
    Hnist2GoodsTypeService goodsTypeService;

    /**
     * 后去所有商品类型
     * @return
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public CommonResult getAll() throws HAException {
        List<Hnist2GoodsType> hnist2GoodsTypeList = goodsTypeService.getAll();
        List<MVHnist2GoodsType> mvHnist2GoodsTypeList = MVHnist2GoodsType.valueOfHnist2GoodsTypeList(hnist2GoodsTypeList);
        return CommonResult.success(mvHnist2GoodsTypeList);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult add(@RequestParam String name) throws HAException {
        goodsTypeService.add(name);
        return CommonResult.success("新增类型成功");
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public CommonResult del(@RequestParam Integer id) throws HAException {
        goodsTypeService.del(id);
        return CommonResult.success("类型删除成功");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult update(@RequestParam Integer id, @RequestParam String name) throws HAException {
        goodsTypeService.update(id, name);
        return CommonResult.success("类型更新成功");
    }
}
