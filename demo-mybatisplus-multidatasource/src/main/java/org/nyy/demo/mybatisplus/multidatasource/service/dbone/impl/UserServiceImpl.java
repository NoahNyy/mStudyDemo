package org.nyy.demo.mybatisplus.multidatasource.service.dbone.impl;

import org.nyy.demo.mybatisplus.multidatasource.model.entity.dbone.UserEntity;
import org.nyy.demo.mybatisplus.multidatasource.mapper.dbone.UserMapper;
import org.nyy.demo.mybatisplus.multidatasource.service.dbone.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author niuyy
 * @since 2020-02-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

}
