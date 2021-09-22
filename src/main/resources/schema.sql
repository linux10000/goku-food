DROP TABLE IF EXISTS domain;

CREATE TABLE domain (
  domnid BIGINT PRIMARY KEY,
  domctable VARCHAR(250) NOT NULL,
  domcfield VARCHAR(250) NOT NULL,
  domcvalue VARCHAR(250) NOT NULL,
  domnorder SMALLINT NOT NULL
);

DROP TABLE IF EXISTS person;

CREATE TABLE person (
  pesnid BIGINT AUTO_INCREMENT PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  pescfirstname VARCHAR(250) NOT NULL,
  pesclastname VARCHAR(250) NOT NULL,
  pesndomlegaltype BIGINT NOT NULL,
  pesbuser BOOLEAN NOT NULL,
  foreign key (pesndomlegaltype) references domain(domnid),
  foreign key (pesndomlegaltype) references domain(domnid)
);

DROP TABLE IF EXISTS country;

CREATE TABLE country (
  counid BIGINT AUTO_INCREMENT PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  coucname VARCHAR(250) NOT NULL,
  coucisocode VARCHAR(50) NULL
);

DROP TABLE IF EXISTS state;

CREATE TABLE state (
  stanid BIGINT AUTO_INCREMENT PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  stacname VARCHAR(250) NOT NULL,
  stancountry BIGINT NOT NULL,
  foreign key (stancountry) references country(counid)
);

DROP TABLE IF EXISTS city;

CREATE TABLE city (
  citnid BIGINT AUTO_INCREMENT PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  citcname VARCHAR(250) NOT NULL,
  citnstate BIGINT NOT NULL,
  foreign key (citnstate) references state(stanid)
);

DROP TABLE IF EXISTS address;

CREATE TABLE address (
  addnid BIGINT AUTO_INCREMENT PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  addcline1 VARCHAR(250) NOT NULL,
  addcline2 VARCHAR(250) NULL,
  addccity BIGINT NOT NULL,
  addczip VARCHAR(50) NULL,
  addcneighborhood VARCHAR(250) NULL,
  foreign key (addccity) references city(citnid)
);

DROP TABLE IF EXISTS user;

CREATE TABLE user (
  pesnid BIGINT PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  usrclogin VARCHAR(250) NOT NULL,
  usrcpassword VARCHAR(250) NOT NULL,
  foreign key (pesnid) references person(pesnid),
  UNIQUE INDEX unq_usrclogin (usrclogin)
);

DROP TABLE IF EXISTS resource;

CREATE TABLE resource (
  rscnid BIGINT PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  rsccdescription VARCHAR(250) NULL
);

DROP TABLE IF EXISTS user_resource;

CREATE TABLE user_resource (
  ucenid BIGINT AUTO_INCREMENT PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  ucenresource BIGINT NOT NULL,
  ucenperson BIGINT NOT NULL,
  foreign key (ucenperson) references user(pesnid),
  foreign key (ucenresource) references resource(rscnid)
);


DROP TABLE IF EXISTS person_address;

CREATE TABLE person_address (
  pednid BIGINT AUTO_INCREMENT PRIMARY KEY,
  ts TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  active BOOLEAN NOT NULL,
  pednpes BIGINT NOT NULL,
  pedcline1 VARCHAR(250) NOT NULL,
  pedcline2 VARCHAR(250) NULL,
  pedccity BIGINT NOT NULL,
  pedczip VARCHAR(50) NULL,
  pedcneighborhood VARCHAR(250) NULL,
  pedndomtype BIGINT NOT NULL,
  foreign key (pedndomtype) references domain(domnid),
  foreign key (pednpes) references person(pesnid),
  foreign key (pedccity) references city(citnid)
);

