create table comment
(
    id bigint auto_increment,
    parent_id bigint not null,
    type int not null,
    commentator int not null,
    gmt_create bigint not null,
    gmt_modified bigint not null,
    like_count bigint default 0
);

comment on column comment.parent_id is '父类ID';

comment on column comment.type is '父类类型';

comment on column comment.commentator is '评论人ID';

create unique index comment_id_uindex
    on comment (id);

alter table comment
    add constraint comment_pk
        primary key (id);