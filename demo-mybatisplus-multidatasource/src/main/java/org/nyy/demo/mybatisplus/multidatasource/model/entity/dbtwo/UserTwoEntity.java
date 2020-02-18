package org.nyy.demo.mybatisplus.multidatasource.model.entity.dbtwo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
* 
* </p>
*
* @author niuyy
* @since 2020-02-18
*/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_two")
public class UserTwoEntity implements Serializable {

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
