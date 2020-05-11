SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS beilul;
CREATE SCHEMA beilul;
USE beilul;


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
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
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
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE role (
    role_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY role_name_unique (name),
    PRIMARY KEY (role_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;


CREATE TABLE member_role (
    member_id INT UNSIGNED NOT NULL,
    role_id TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (member_id , role_id),
    KEY idx_fk_member_id (member_id),
    CONSTRAINT fk_membership_role FOREIGN KEY (member_id)
        REFERENCES membership (member_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    KEY idx_fk_role_id (role_id),
    CONSTRAINT fk_role FOREIGN KEY (role_id)
        REFERENCES role (role_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE address (
    address_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    address NVARCHAR(50) NOT NULL,
    address2 NVARCHAR(50) DEFAULT NULL,
    district NVARCHAR(20) NOT NULL,
    country_id CHAR(3) NOT NULL,
    city_name NVARCHAR(50),
    admin_zone_name NVARCHAR(50),
    postal_code VARCHAR(10) DEFAULT NULL,
    phone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (address_id),
    KEY idx_fk_country_id (country_id),
    CONSTRAINT `fk_address_country_id` FOREIGN KEY (country_id)
        REFERENCES country (country_id_iso3)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE city (
    city_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    city_name NVARCHAR(50) NOT NULL,
    admin_zone_id SMALLINT UNSIGNED NOT NULL,
    latitude DECIMAL(6 , 4 ),
    longitude DECIMAL(6 , 2 ),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (city_id),
    KEY idx_fk_admin_zone_id (admin_zone_id),
    CONSTRAINT `fk_admin_zone` FOREIGN KEY (admin_zone_id)
        REFERENCES admin_zone (admin_zone_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE admin_zone (
    admin_zone_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    admin_zone_name NVARCHAR(60) NOT NULL,
    country_id CHAR(3) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (admin_zone_id),
    KEY idx_fk_country_id (country_id),
    CONSTRAINT `fk_admin_zone_country` FOREIGN KEY (country_id)
        REFERENCES country (country_id_iso3)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE country (
    country_id_iso3 CHAR(3) NOT NULL,
    country_id_iso2 CHAR(2) NOT NULL,
    country_name NVARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY idx_unique_iso2 (country_id_iso2),
    PRIMARY KEY (country_id_iso3)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;


CREATE TABLE content (
    content_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    title NVARCHAR(127) NOT NULL,
    body TEXT NOT NULL,
    summary NVARCHAR(255),
    created_by INT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (content_id),
    KEY idx_fk_member_id (created_by),
    CONSTRAINT fk_content_member FOREIGN KEY (created_by)
        REFERENCES membership (member_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE content_text (
    content_id INT UNSIGNED NOT NULL,
    title NVARCHAR(127) NOT NULL,
    body TEXT NOT NULL,
    summary NVARCHAR(255),
    PRIMARY KEY (content_id),
    FULLTEXT KEY idx_title_summary ( title , summary ),
    FULLTEXT KEY idx_body ( body )
)  ENGINE=MYISAM DEFAULT CHARSET=UTF8MB4;

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
    description NVARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY idx_unique_cat_desc (description),
    PRIMARY KEY (category_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;


CREATE TABLE content_category (
    content_id INT UNSIGNED NOT NULL,
    category_id INT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (content_id , category_id),
    KEY idx_fk_content_id (content_id),
    CONSTRAINT fk_content_category1 FOREIGN KEY (content_id)
        REFERENCES content (content_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    KEY idx_fk_category_id (category_id),
    CONSTRAINT fk_content_category2 FOREIGN KEY (category_id)
        REFERENCES category (category_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE comment (
    comment_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    content_id INT UNSIGNED NOT NULL,
    first_name NVARCHAR(50),
    last_names NVARCHAR(127),
    content TEXT NOT NULL,
    reply_comment_id INT,
    created_by INT UNSIGNED,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (comment_id),
    KEY idx_fk_content_id (content_id),
    CONSTRAINT fk_content_comment FOREIGN KEY (content_id)
        REFERENCES content (content_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    KEY idx_fk_created_by (created_by),
    CONSTRAINT fk_content_membership FOREIGN KEY (created_by)
        REFERENCES membership (member_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;