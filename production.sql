#DB 생성
DROP DATABASE IF EXISTS textBoard;
CREATE DATABASE textBoard;
USE textBoard;

#게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` TEXT NOT NULL,
    userId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL
);

# 회원 테이블 생성
CREATE TABLE `user` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    accountName CHAR(30) NOT NULL,
    accountPw VARCHAR(50) NOT NULL,
    `name` CHAR(30) NOT NULL
);

# 게시판 테이블 생성
CREATE TABLE `board` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(30) NOT NULL,
    `code` CHAR(30) NOT NULL
);

#운영 시작
#공지사항 게시판 추가
INSERT INTO `board`
SET regDate = NOW(),
updateDate = NOW(),
`name` = '공지사항',
`code` = 'notice';

# 자유게시물 게시판 추가
INSERT INTO `board`
SET regDate = NOW(),
updateDate = NOW(),
`name` = '자유',
`code` = 'free';

# it 게시판 추가
INSERT INTO `board`
SET regDate = NOW(),
updateDate = NOW(),
`name` = 'Java',
`code` = 'it';

# 사용자 추가
INSERT INTO `user`
SET regDate = NOW(),
updateDate = NOW(),
accountName = 'admin',
accountPw = 'admin',
`name` = '천승우';

SELECT * FROM `article`;
SELECT * FROM `user`;
SELECT * FROM `board`;