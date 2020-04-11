# 数据库 dbone
CREATE TABLE IF NOT EXISTS user(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  age INT(11) NULL DEFAULT NULL COMMENT '年龄',
  email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (id)
);

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');

# 数据库 dbtwo

CREATE TABLE IF NOT EXISTS user_two(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

INSERT INTO user_two (id, name, age, email) VALUES
(1, 'Jone', 18, '2test1@baomidou.com'),
(2, 'Jack', 20, '2test2@baomidou.com'),
(3, 'Tom', 28, '2test3@baomidou.com'),
(4, 'Sandy', 21, '2test4@baomidou.com'),
(5, 'Billie', 24, '2test5@baomidou.com');
