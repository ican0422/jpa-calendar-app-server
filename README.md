JPA를 활용한 일정관리 어플리케이션
==================================
## [ERD](https://dbdiagram.io/d/66ce11b5cf8e2d1d1c065d8c)
![ERD](https://github.com/user-attachments/assets/781883cc-09a1-45aa-bf0b-03f80294f72d)
- - -
## API
### [일정 API](https://documenter.getpostman.com/view/37572363/2sAXjJ4sBb)
![일정 API](https://github.com/user-attachments/assets/8be565f8-08a9-43da-9c6d-b50c94fb6892)

### [댓글 API](https://documenter.getpostman.com/view/37572363/2sAXjJ4sBd)
![댓글 API](https://github.com/user-attachments/assets/cc799ec1-2407-435d-910e-f3162b48851b)

### [유저 API](https://documenter.getpostman.com/view/37572363/2sAXjJ4sFu)
![유저 API](https://github.com/user-attachments/assets/f3c31370-cb3c-4a36-a943-7181ccb5a753)
- - -
## SQL
```mysql
-- 데이터베이스 생성
create database calendar_dev;
-- 일정 테이블 생성
create table schedule (
    id bigint not null auto_increment,
    creation_date datetime(6),
    modified_date datetime(6),
    content varchar(255),
    title varchar(255),
    user_id bigint,
    primary key (id)
)
-- 일정 테이블 fk키 설정
alter table schedule
    add constraint FKa50n59y1j4a6qwa42p8jiguds
    foreign key (user_id)
    references user (id)

-- 댓글 테이블 생성
create table comment (
    id bigint not null auto_increment,
    creation_date datetime(6),
    modified_date datetime(6),
    content varchar(255),
    name varchar(255),
    schedule_id bigint,
    primary key (id)
)
-- 댓글 테이블 fk키 설정
alter table comment
    add constraint FKsy51iks4dgapu66gfj3mnykch
    foreign key (schedule_id)
    references schedule (id)

-- 유저 테이블 생성
create table user (
    id bigint not null auto_increment,
    creation_date datetime(6),
    modified_date datetime(6),
    email varchar(255),
    name varchar(255),
    primary key (id)
)

-- 일정, 유저 중간 테이블 생성
create table schedule_assignment (
    id bigint not null auto_increment,
    creation_date datetime(6),
    modified_date datetime(6),
    schedule_id bigint,
    user_id bigint,
    primary key (id)
)
-- 일정 fk키 설정
alter table schedule_assignment
    add constraint FKl85y024xnjrfq82kivrb7wxav
    foreign key (schedule_id)
    references schedule (id)
-- 유저 fk키 설정
alter table schedule_assignment
    add constraint FKkvxvktfcpw3csts7p6a8w6xsn
    foreign key (user_id)
    references user (id)
----------------------------------------------------------------------------------------------------------------------
-- 일정 등록
insert
into
    schedule (content, creation_date, modified_date, name, title)
values
    (?, ?, ?, ?, ?)
-- 중간 테이블 다른 유저 추가 등록
insert
into
    schedule_assignment (creation_date, modified_date, schedule_id, user_id)
values
    (?, ?, ?, ?)

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
select
    s1_0.id,
    s1_0.content,
    s1_0.creation_date,
    s1_0.modified_date,
    s1_0.name,
    s1_0.title
from
    schedule s1_0
order by
    s1_0.modified_date desc
    limit ?, ?

-- 일정 수정
update schedule
set
    content=?,
    modified_date=?,
    name=?,
    title=?
where
    id=?

-- 일정 삭제
delete
from
    schedule
where
    id=?

----------------------------------------------------------------------------------------------------------------------
-- 댓글 생성
insert
into
    comment (content, creation_date, modified_date, name, schedule_id)
values
    (?, ?, ?, ?, ?)

-- 댓글 단건 조회
select
    c1_0.id,
    c1_0.content,
    c1_0.creation_date,
    c1_0.modified_date,
    c1_0.name,
    c1_0.schedule_id
from
    comment c1_0
where
    c1_0.schedule_id=?
  and c1_0.id=?

-- 댓글 다건 조회
select
    c1_0.id,
    c1_0.content,
    c1_0.creation_date,
    c1_0.modified_date,
    c1_0.name,
    c1_0.schedule_id
from
    comment c1_0
where
    c1_0.schedule_id=?

-- 댓글 수정
update comment
set
    content=?,
    modified_date=?,
    name=?,
    schedule_id=?
where
    id=?

-- 댓글 삭제
delete
from
    comment
where
    id=?
----------------------------------------------------------------------------------------------------------------------
-- 유저 생성
insert
into
    user (creation_date, email, modified_date, name)
values
    (?, ?, ?, ?)

-- 유저 단건 조회
select
    u1_0.id,
    u1_0.creation_date,
    u1_0.email,
    u1_0.modified_date,
    u1_0.name
from
    user u1_0
where
    u1_0.id=?

-- 유저 다건 조회
select
    u1_0.id,
    u1_0.creation_date,
    u1_0.email,
    u1_0.modified_date,
    u1_0.name
from
    user u1_0

-- 유저 수정
update user
set
    email=?,
    modified_date=?,
    name=?
where
    id=?
```
