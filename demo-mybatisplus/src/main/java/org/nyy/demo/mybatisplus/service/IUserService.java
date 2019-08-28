package org.nyy.demo.mybatisplus.service;

import org.nyy.demo.mybatisplus.model.entity.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author niuyy
 * @since 2019-08-28
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * 测试
     */
    void testSelect();
}
