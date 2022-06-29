DROP TABLE IF EXISTS secrets;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS userinfo;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS  authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE IF NOT EXISTS userinfo (
    username VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);

-- Mega-table approach
-- Need to figure out new way of holding secret data. May need to make a service
CREATE TABLE IF NOT EXISTS secrets (
    secretid VARCHAR(36) NOT NULL,
    username VARCHAR(50) NOT NULL,
    secretname VARCHAR(50) NOT NULL,
    filetype VARCHAR(50) NOT NULL,
    creationdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    secretdata MEDIUMBLOB NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username),
    PRIMARY KEY (secretid)
);
-- CREATE UNIQUE  INDEX ix_auth_username on authorities (username,authority);

-- News and Events
CREATE TABLE IF NOT EXISTS news (
    newsid BIGINT(255) NOT NULL,
    title VARCHAR(100) NOT NULL,
    bodytext LONGTEXT NOT NULL,
    newsdate VARCHAR(36) NOT NULL,
    timepublished INT(255) NOT NULL,
    author VARCHAR(100) NOT NULL
);