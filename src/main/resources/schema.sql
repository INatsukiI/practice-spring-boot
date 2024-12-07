create table if not exists `user` (
    `id` bigint unsigned auto_increment comment 'ID',
    `name` varchar(255) not null comment '名前',
    `age` int unsigned not null comment '年齢',
    `created_at` timestamp not null default current_timestamp comment '作成日時',
    `updated_at` timestamp not null default current_timestamp on update current_timestamp comment '更新日時',

    primary key (`id`),
    unique key `name_unique` (`name`)
) engine=innodb default charset=utf8mb4 comment 'ユーザーテーブル';