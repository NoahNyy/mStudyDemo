package org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.nyy.demo.mybatisplus.model.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
*
* </p>
*
* @author niuyy
* @since 2019-08-28
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId("id")
    private Long id;

    /**
    * 姓名
    */
    @TableField("name")
    private String name;

    /**
    * 年龄
    */
    @TableField("age")
    private Integer age;

    /**
    * 邮箱
    */
    @TableField("email")
    private String email;


}
