package org.nyy.demo.mybatisplus.multidatasource.service.dbtwo.impl;

import org.nyy.demo.mybatisplus.multidatasource.model.entity.dbtwo.UserTwoEntity;
import org.nyy.demo.mybatisplus.multidatasource.mapper.dbtwo.UserTwoMapper;
import org.nyy.demo.mybatisplus.multidatasource.service.dbtwo.IUserTwoService;
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
public class UserTwoServiceImpl extends ServiceImpl<UserTwoMapper, UserTwoEntity> implements IUserTwoService {

}
