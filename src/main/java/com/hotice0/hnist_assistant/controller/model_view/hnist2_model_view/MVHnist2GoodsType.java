package com.hotice0.hnist_assistant.controller.model_view.hnist2_model_view;

import com.hotice0.hnist_assistant.db.model.Hnist2GoodsType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-23 15:26
 */
@Data
@NoArgsConstructor
public class MVHnist2GoodsType {
    private Integer id;
    private String type_name;

    public static MVHnist2GoodsType valueOfHnist2GoodsType(Hnist2GoodsType hnist2GoodsType) {
        MVHnist2GoodsType mvHnist2GoodsType = new MVHnist2GoodsType();
        BeanUtils.copyProperties(hnist2GoodsType, mvHnist2GoodsType);
        return mvHnist2GoodsType;
    }

    public static List<MVHnist2GoodsType> valueOfHnist2GoodsTypeList(List<Hnist2GoodsType> hnist2GoodsTypeList) {
        List<MVHnist2GoodsType> mvHnist2GoodsTypeList = new ArrayList<>();
        for (Hnist2GoodsType hnist2GoodsType: hnist2GoodsTypeList) {
            mvHnist2GoodsTypeList.add(valueOfHnist2GoodsType(hnist2GoodsType));
        }
        return mvHnist2GoodsTypeList;
    }
}
