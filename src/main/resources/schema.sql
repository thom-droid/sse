CREATE TABLE IF NOT EXISTS members (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  nickname varchar(255) NOT NULL,
  PRIMARY KEY (id)
);