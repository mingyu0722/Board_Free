CREATE SCHEMA `board` DEFAULT CHARACTER SET utf8 ;

-- ------------------------------------------------
-- Table 'board_free'
-- ------------------------------------------------
CREATE TABLE IF NOT EXISTS board.board_free
 (
	bf_idx INT(10) NOT NULL UNIQUE AUTO_INCREMENT COMMENT '일련번호',
    bf_cate_idx INT(10) NOT NULL default 1 COMMENT '카테고리 idx',
    bf_title VARCHAR(100) NOT NULL DEFAULT '제목없음' COMMENT '제목',
    bf_contents TEXT NULL COMMENT '본문',
    mem_idx INT(10) NOT NULL COMMENT '회원번호',
    reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
    mod_date DATETIME NULL DEFAULT NULL COMMENT '수정일자',
    view_cnt INT(10) NOT NULL DEFAULT 1 COMMENT '조회수',
    reply_cnt int(10) not null default 0 comment '댓글수',
    rec_cnt int(10) not null default 0 comment '추천수',
    use_sec VARCHAR(1) NOT NULL DEFAULT 'N' COMMENT '비밀글 여부',
    use_flag VARCHAR(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
    PRIMARY KEY (bf_idx),
    foreign key (mem_idx) references board.member (mem_idx)
) ENGINE = InnoDB;

-- ------------------------------------------------
-- Table 'member'
-- ------------------------------------------------
create table if not exists board.member
(
	mem_idx int(10) not null auto_increment primary key unique comment '회원번호',
    mem_name varchar(20) comment '회원이름',
    mem_pw varchar(20) not null comment '비밀번호'
) ENGINE = InnoDB;

CREATE DATABASE board DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- ------------------------------------------------
-- Table 'board_free_reply'
-- ------------------------------------------------
CREATE TABLE IF NOT EXISTS board.board_free_reply
 (
	bfr_idx int(10) not null unique auto_increment comment '댓글 일련번호',
	bf_idx INT(10) NOT NULL COMMENT '일련번호',
    bfr_contents varchar(300) NULL COMMENT '본문',
    mem_idx INT(10) NOT NULL COMMENT '회원번호',
    reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
    mod_date DATETIME NULL DEFAULT NULL COMMENT '수정일자',
    use_flag VARCHAR(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
    PRIMARY KEY (bfr_idx),
    foreign key (bf_idx) references board.board_free(bf_idx),
    foreign key (mem_idx) references board.member(mem_idx)
) ENGINE = InnoDB;

-- ------------------------------------------------
-- Table 'view_count'
-- ------------------------------------------------
CREATE TABLE IF NOT EXISTS board.view_count
(	
	view_cnt_idx INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    bf_idx INT(10) NOT NULL  COMMENT '게시글 idx',
	mem_idx int(10) NOT NULL COMMENT '회원번호',
    FOREIGN KEY (bf_idx) REFERENCES board.board_free(bf_idx),
    FOREIGN KEY (mem_idx) REFERENCES board.member(mem_idx)
) ENGINE = InnoDB;

-- ------------------------------------------------
-- Table 'recommand'
-- ------------------------------------------------
CREATE TABLE IF NOT EXISTS board.recommend
(	
	rec_cnt_idx INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    bf_idx INT(10) NOT NULL  COMMENT '게시글 idx',
	mem_idx int(10) NOT NULL COMMENT '회원번호',
    FOREIGN KEY (bf_idx) REFERENCES board.board_free(bf_idx),
    FOREIGN KEY (mem_idx) REFERENCES board.member(mem_idx)
) ENGINE = InnoDB;

-- ------------------------------------------------
-- Table 'board_free_file'
-- ------------------------------------------------
CREATE TABLE IF NOT EXISTS board.board_free_file
(	
	bff_idx INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE COMMENT '파일 idx',
    bf_idx INT(10) NOT NULL  COMMENT '게시글 idx',
    mem_idx INT(10) NOT NULL COMMENT '회원 idx',
	file_path VARCHAR(100) NOT NULL COMMENT '파일 경로',
	file_name VARCHAR(50) NOT NULL COMMENT '파일 이름',
    file_size INT(10) NOT NULL COMMENT '파일 size',
    use_flag VARCHAR(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
    FOREIGN KEY (bf_idx) REFERENCES board.board_free(bf_idx),
    FOREIGN KEY (mem_idx) REFERENCES board.member(mem_idx)
) ENGINE = InnoDB;