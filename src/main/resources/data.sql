USE oakdb;

INSERT INTO roles(id,role) VALUES (1,'ADMIN'), (2,'USER');

INSERT INTO users VALUE (1,'admin@mail','Oak','Admin','45a6b99d5231a81620cff1098e717605bb7dadf69fa0d0237c7c422ca06d969b4263675124dc361acf2c641621a7fc14');

INSERT INTO users_roles VALUE (1,1);

INSERT INTO rooms VALUES (1,2,'101','DOUBLE'),
                         (2,2,'102','DOUBLE'),
                         (3,2,'103','DOUBLE'),
                         (4,2,'104','DOUBLE'),
                         (5,2,'105','DOUBLE'),
                         (6,2,'106','DOUBLE'),
                         (7,2,'107','DOUBLE'),
                         (8,2,'108','DOUBLE'),
                         (9,2,'109','DOUBLE'),
                         (10,2,'110','DOUBLE'),
                         (11,3,'201','TRIPLE'),
                         (12,3,'202','TRIPLE'),
                         (13,3,'203','TRIPLE'),
                         (14,3,'204','TRIPLE'),
                         (15,3,'205','TRIPLE'),
                         (16,3,'206','TRIPLE'),
                         (17,3,'207','TRIPLE'),
                         (18,3,'208','TRIPLE'),
                         (19,3,'209','TRIPLE'),
                         (20,3,'301','TRIPLE'),
                         (21,3,'302','TRIPLE'),
                         (22,3,'303','TRIPLE'),
                         (23,4,'304','APARTMENT'),
                         (24,4,'305','APARTMENT'),
                         (25,4,'306','APARTMENT'),
                         (26,4,'307','APARTMENT'),
                         (27,4,'308','APARTMENT'),
                         (28,4,'309','APARTMENT'),
                         (29,4,'401','APARTMENT'),
                         (30,4,'402','APARTMENT'),
                         (31,4,'403','APARTMENT'),
                         (32,4,'404','APARTMENT'),
                         (33,4,'405','APARTMENT'),
                         (34,4,'406','APARTMENT'),
                         (35,4,'407','APARTMENT');
