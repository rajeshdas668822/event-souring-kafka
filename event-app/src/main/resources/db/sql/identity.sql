

CREATE TABLE PUBLIC.T_USER (
	ID VARCHAR(64) NOT NULL,
	REV INTEGER,
	FIRST_NAME VARCHAR(255),
	LAST_NAME VARCHAR(255),
	EMAIL VARCHAR(255),
	PWD VARCHAR(255),
	PICTURE_ID VARCHAR(64),
	CONSTRAINT CONSTRAINT_T_USER PRIMARY KEY (ID)
);



CREATE TABLE PUBLIC.T_GROUP (
	ID VARCHAR(64) NOT NULL,
	REV INTEGER,
	NAME VARCHAR(255),
	"TYPE" VARCHAR(255),
	CONSTRAINT CONSTRAINT_T_GROUP PRIMARY KEY (ID)
);

CREATE UNIQUE INDEX PRIMARY_KEY_T_GROUP ON PUBLIC.T_GROUP (ID);


CREATE TABLE PUBLIC.T_MEMBERSHIP (
	USER_ID VARCHAR(64) NOT NULL,
	GROUP_ID VARCHAR(64) NOT NULL,
	CONSTRAINT CONSTRAINT_T_MEMBERSHIP PRIMARY KEY (USER_ID,GROUP_ID),
	CONSTRAINT ACT_FK_T_MEMBERSHIP_GROUP FOREIGN KEY (GROUP_ID) REFERENCES PUBLIC.T_GROUP(ID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT ACT_FK_T_MEMBERSHIP_USER FOREIGN KEY (USER_ID) REFERENCES PUBLIC.T_USER(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
);

--CREATE INDEX ACT_FK_T_MEMBERSHIP_GROUP_INDEX_9 ON PUBLIC.T_MEMBERSHIP (GROUP_ID);

--CREATE INDEX ACT_FK_T_MEMBERSHIP_USER_INDEX_9 ON PUBLIC.T_MEMBERSHIP (USER_ID);

--CREATE UNIQUE INDEX PRIMARY_KEY_94 ON PUBLIC.T_MEMBERSHIP (USER_ID,GROUP_ID);


--CREATE UNIQUE INDEX PRIMARY_KEY_9 ON PUBLIC.T_USER (ID);



CREATE TABLE PUBLIC.T_ORDER (
	ORDER_ID VARCHAR(10) NOT NULL,
	PRODUCT_TYPE VARCHAR(100) NOT NULL,
	COUNTER_PARTY VARCHAR(100),
	STATUS VARCHAR(100),
	AMOUNT DOUBLE,
	FILLAMOUNT DOUBLE,
	COST_PRICE DOUBLE,
	QUANTITY DOUBLE,
	IS_STANDALONE BOOLEAN,
	CONSTRAINT T_ORDER_PK PRIMARY KEY (ORDER_ID)
);

CREATE UNIQUE INDEX PRIMARY_KEY_85B ON PUBLIC.T_ORDER (ORDER_ID);

INSERT INTO PUBLIC.T_USER
(ID, REV, FIRST_NAME, LAST_NAME, EMAIL, PWD, PICTURE_ID)
VALUES('rajesh', 1, 'Rajesh', 'Das', 'rdas@numerix.com', 'rajesh', NULL);
INSERT INTO PUBLIC.T_USER
(ID, REV, FIRST_NAME, LAST_NAME, EMAIL, PWD, PICTURE_ID)
VALUES('gulzar', 2, 'Gonzo', 'The Great', 'gonzo@activiti.org', 'gulzar', '52518');
INSERT INTO PUBLIC.T_USER
(ID, REV, FIRST_NAME, LAST_NAME, EMAIL, PWD, PICTURE_ID)
VALUES('kermit', 2, 'Kermit', 'The Frog', 'kermit@activiti.org', 'kermit', '52507');
INSERT INTO PUBLIC.T_USER
(ID, REV, FIRST_NAME, LAST_NAME, EMAIL, PWD, PICTURE_ID)
VALUES('fozzie', 2, 'Fozzie', 'Bear', 'fozzie@activiti.org', 'fozzie', '52522');



INSERT INTO PUBLIC.T_GROUP
(ID, REV, NAME, "TYPE")
VALUES('sales', 1, 'Sales', 'assignment');
INSERT INTO PUBLIC.T_GROUP
(ID, REV, NAME, "TYPE")
VALUES('admin', 1, 'Admin', 'security-role');
INSERT INTO PUBLIC.T_GROUP
(ID, REV, NAME, "TYPE")
VALUES('user', 1, 'User', 'security-role');
INSERT INTO PUBLIC.T_GROUP
(ID, REV, NAME, "TYPE")
VALUES('engineering', 1, 'Engineering', 'assignment');
INSERT INTO PUBLIC.T_GROUP
(ID, REV, NAME, "TYPE")
VALUES('management', 1, 'Management', 'assignment');
INSERT INTO PUBLIC.T_GROUP
(ID, REV, NAME, "TYPE")
VALUES('marketing', 1, 'Marketing', 'assignment');


INSERT INTO PUBLIC.T_MEMBERSHIP
(USER_ID, GROUP_ID)
VALUES('kermit', 'user');
INSERT INTO PUBLIC.T_MEMBERSHIP
(USER_ID, GROUP_ID)
VALUES('kermit', 'marketing');