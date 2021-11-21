package com.tulingxueyuan.mallfront.modules.ums.service.impl;

import com.tulingxueyuan.mallfront.modules.ums.model.UmsAdmin;
import com.tulingxueyuan.mallfront.modules.ums.mapper.UmsAdminMapper;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {


}
