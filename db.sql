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
    memberId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL
);

#임시 게시물 데이터 3개 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1',
memberId = 2,
boardId = 2;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2',
memberId = 2,
boardId = 2;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3',
memberId = 1,
boardId = 1;

# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    accountName CHAR(30) NOT NULL,
    accountPw VARCHAR(50) NOT NULL,
    `name` CHAR(30) NOT NULL
);

# 회원 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
accountName = 'test1',
accountPw = 'test1',
`name` = '테스터1';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
accountName = 'test2',
accountPw = 'test2',
`name` = '테스터2';

# 게시판 테이블 생성
CREATE TABLE `board` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(30) NOT NULL,
    `code` CHAR(30) NOT NULL
);

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

# 램덤 게시물 생성
/*
INSERT INTO article
SET regDate = NOW(),
updateDate =NOW(),
title = CONCAT("제목_", RAND()),
`body` = CONCAT("내용_", RAND()),
memberId = FLOOR (RAND()*2)+1,
boardId = FLOOR (RAND()*2)+1;

INSERT INTO article
SET regDate = NOW(),
updateDate =NOW(),
title = CONCAT("제목_", RAND()),
`body` = CONCAT("내용_", RAND()),
memberId = FLOOR (RAND()*2)+1,
boardId = FLOOR (RAND()*2)+1;

INSERT INTO article
SET regDate = NOW(),
updateDate =NOW(),
title = CONCAT("제목_", RAND()),
`body` = CONCAT("내용_", RAND()),
memberId = FLOOR (RAND()*2)+1,
boardId = FLOOR (RAND()*2)+1;

INSERT INTO article
SET regDate = NOW(),
updateDate =NOW(),
title = CONCAT("제목_", RAND()),
`body` = CONCAT("내용_", RAND()),
memberId = FLOOR (RAND()*2)+1,
boardId = FLOOR (RAND()*2)+1;
*/

# 3번글 내용 마크다운 문법으로 수정
UPDATE `article` 
SET `body` = '#공지사항\r\n\r\n이곳은 제 글 연재 공간 입니다.\r\n\r\n---\r\n\r\n블로그 특징\r\n\r\n- A\r\n- B\r\n- C' 
WHERE `id` = '3'; 

# 2번글 내용에 자바소스코드 넣기
UPDATE `article` 
SET `body` = '# 자바기분문법\r\n```java\r\nint a = 10;\r\nint b = 20;\r\nint c = a + b;\r\n```' 
WHERE `id` = '2'; 

# 운영 시작

TRUNCATE `article`;
TRUNCATE `member`;

# it 게시판 추가
INSERT INTO `board`
SET regDate = NOW(),
updateDate = NOW(),
`name` = 'Java',
`code` = 'it';

# 사용자 추가
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
accountName = 'fourleaves8',
accountPw = 'admin',
`name` = '천승우';

INSERT INTO `textboard`.`article` (`regDate`, `updateDate`, `title`, `body`, `memberId`, `boardId`) VALUES (NOW(), NOW(), '# 공지사항', '---\r\n# 초보 개발자 MilkPotato의 개인 블로그 입니다.\r\n---\r\n', 'fourleaves8', '1'); 

SELECT * FROM article;
SELECT * FROM board;
SELECT * FROM `member`;