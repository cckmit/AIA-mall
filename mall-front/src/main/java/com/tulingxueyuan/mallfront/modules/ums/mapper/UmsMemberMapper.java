package com.tulingxueyuan.mallfront.modules.ums.mapper;

import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
public interface UmsMemberMapper extends BaseMapper<UmsMember> {

    boolean starCartProduct(@Param("id") Long id,@Param("memberId") Long memberId);

    boolean unstarCartProduct(@Param("id") Long id, @Param("memberId") Long memberId);
}
