package org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.service.impl;

import org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.model.entity.UserEntity;
import org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.mapper.UserMapper;
import org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.service.IUserService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author niuyy
 * @since 2019-08-28
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    @Override
    public void testSelect() {
        //条件查询
        List<UserEntity> entityList = baseMapper.selectList(Wrappers.<UserEntity>query()
                .eq("id", 1));
        log.info("selectList: {}", entityList);

        //分页查询
        IPage<UserEntity> entityPage = baseMapper.selectPage(new Page<>(1, 2, true),
                Wrappers.<UserEntity>query()
                        .gt("age", 18));
        log.info("selectPage: {}", entityPage);

        //条件更新
        int id = baseMapper.update(new UserEntity().setAge(10), Wrappers.<UserEntity>update()
                .eq("id", "1"));
        log.info("update: {}", id);

        //条件删除
        int delete = baseMapper.delete(Wrappers.<UserEntity>query()
                .eq("id", 1));
        log.info("delete: {}", delete);
    }
}
