SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS beilul;
CREATE SCHEMA beilul;
USE beilul;

--
-- Table structure for table `member`
--
DROP TABLE IF EXISTS flyway_schema_history;
SET character_set_client = utf8mb4 ;
CREATE TABLE flyway_schema_history (
    installed_rank INT(11) NOT NULL,
    version VARCHAR(50) DEFAULT NULL,
    description VARCHAR(200) NOT NULL,
    type VARCHAR(20) NOT NULL,
    script VARCHAR(1000) NOT NULL,
    checksum INT(11) DEFAULT NULL,
    installed_by VARCHAR(100) NOT NULL,
    installed_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    execution_time INT(11) NOT NULL,
    success TINYINT(1) NOT NULL,
    PRIMARY KEY (installed_rank),
    KEY flyway_schema_history_s_idx (success)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

CREATE TABLE membership (
    member_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(45) NOT NULL,
    first_name_tig NVARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    last_name_tig NVARCHAR(45) NOT NULL,
    address_id INT UNSIGNED NOT NULL,
    picture VARCHAR(255) DEFAULT NULL,
    picture_thumb VARCHAR(255) DEFAULT NULL,
    email VARCHAR(50) DEFAULT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    username VARCHAR(16) NOT NULL,
    password VARCHAR(40)BINARY DEFAULT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (member_id),
    KEY idx_fk_address_id (address_id),
    CONSTRAINT fk_staff_address FOREIGN KEY (address_id)
        REFERENCES address (address_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE role (
    role_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY role_name_unique (name),
    PRIMARY KEY (role_id)
);


CREATE TABLE member_role (
    member_id INT REFERENCES user (member_id),
    role_id TINYINT REFERENCES role (role_id),
    PRIMARY KEY (member_id , role_id)
);

CREATE TABLE address (
    address_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    address VARCHAR(50) NOT NULL,
    address2 VARCHAR(50) DEFAULT NULL,
    district VARCHAR(20) NOT NULL,
    city_id SMALLINT UNSIGNED NOT NULL,
    postal_code VARCHAR(10) DEFAULT NULL,
    phone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (address_id),
    KEY idx_fk_city_id (city_id),
    CONSTRAINT `fk_address_city` FOREIGN KEY (city_id)
        REFERENCES city (city_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE city (
    city_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    city VARCHAR(50) NOT NULL,
    country_id SMALLINT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (city_id),
    KEY idx_fk_country_id (country_id),
    CONSTRAINT `fk_city_country` FOREIGN KEY (country_id)
        REFERENCES country (country_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE TABLE country (
    country_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    country VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (country_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE content (
    content_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    title NVARCHAR(127) NOT NULL,
    body TEXT NOT NULL,
    summary VARCHAR(255),
    created_by INT NOT NULL REFERENCES member_id (member_id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (content_id),
    KEY idx_fk_member_id (created_by),
    CONSTRAINT fk_content_member FOREIGN KEY (created_by)
        REFERENCES member_id (member_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE content_text (
    content_id INT UNSIGNED NOT NULL,
    title NVARCHAR(127) NOT NULL,
    body TEXT NOT NULL,
    summary VARCHAR(255),
    PRIMARY KEY (content_id),
    FULLTEXT KEY idx_title_description ( title , body , summary )
)  ENGINE=MYISAM DEFAULT CHARSET=UTF8;

--
-- Triggers for loading content_text from content
--

DELIMITER ;;
CREATE TRIGGER `insert_content_text` AFTER INSERT ON `content` FOR EACH ROW BEGIN
    INSERT INTO content_text (content_id, title, body, summary)
        VALUES (new.content_id, new.title, new.body, new.summary);
  END;;


CREATE TRIGGER `update_content_text` AFTER UPDATE ON `content` FOR EACH ROW BEGIN
    UPDATE content_text
        SET title=new.title,
            body=new.body,
            summary=new.summary
    WHERE content_id=old.content_id;
END;;


CREATE TRIGGER `delete_content_text` AFTER DELETE ON `content` FOR EACH ROW BEGIN
    DELETE FROM content_text WHERE content_id = old.content_id;
  END;;

DELIMITER ;


CREATE TABLE category (
    category_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY idx_unique_cat_desc (description),
    PRIMARY KEY (category_id)
);


CREATE TABLE content_category (
    content_id INT NOT NULL REFERENCES content (content_id),
    category_id INT NOT NULL REFERENCES category_id (category_id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (content_id , category_id)
);

CREATE TABLE comment (
    comment_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    content_id INT NOT NULL REFERENCES content (content_id),
    first_name VARCHAR(50),
    last_names VARCHAR(127),
    content TEXT NOT NULL,
    reply_comment_id INT,
    created_by INT NOT NULL REFERENCES member_id (member_id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (comment_id),
    CONSTRAINT fk_comment_member FOREIGN KEY (created_by)
        REFERENCES member_id (member_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;