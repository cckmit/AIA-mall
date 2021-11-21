package com.tulingxueyuan.mall.modules.pms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 相册表
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_album")
@ApiModel(value="PmsAlbum对象", description="相册表")
public class PmsAlbum implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String coverPic;

    private Integer picCount;

    private Integer sort;

    private String description;


}
