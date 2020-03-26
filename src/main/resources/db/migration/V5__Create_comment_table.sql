CREATE TABLE comment
(
        id bigint AUTO_INCREMENT PRIMARY KEY,
        parent_id bigint NOT NULL,
        type int,
        commentator int,
        gmt_create bigint NOT NULL,
        gmt_modified bigint,
        like_count bigint DEFAULT 0
);