-- 데이터베이스 생성
create database calendar_dev;
-- 일정 테이블 생성
create table schedule
(
    id bigint not null auto_increment,
    creation_date datetime(6),
    modified_date datetime(6),
    content varchar(255),
    name varchar(255),
    title varchar(255),
    primary key (id)
)

----------------------------------------------------------------------------------------------------------------------
-- 일정 등록
insert
into
    schedule (content, creation_date, modified_date, name, title)
values
    (?, ?, ?, ?, ?)

-- 일정 단건 조회
select
    s1_0.id,
    s1_0.content,
    s1_0.creation_date,
    s1_0.modified_date,
    s1_0.name,
    s1_0.title
from
    schedule s1_0
where
    s1_0.id=?

-- 일정 다건 조회