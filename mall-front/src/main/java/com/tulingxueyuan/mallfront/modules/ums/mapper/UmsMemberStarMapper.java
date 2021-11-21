package com.tulingxueyuan.mallfront.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberStar;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 用户标签表 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2021-11-20
 */
public interface UmsMemberStarMapper extends BaseMapper<UmsMemberStar> {

    boolean updateStarStore(@Param("memberId") Long memberId,@Param("storeId") Long storeId);
}
