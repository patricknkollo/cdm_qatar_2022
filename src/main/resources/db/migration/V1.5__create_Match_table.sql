CREATE TABLE Match (
                         matchid int NOT NULL AUTO_INCREMENT,
                         pays1id int,
                         pays2id int,
                         nom1 varchar(255),
                         scorepays1 int,
                         scorepays2 int,
                         nom2 varchar(255),
                         lieu varchar(255),
                         date Date,
                         arbitreid int,
                         FOREIGN KEY (arbitreid) REFERENCES Arbitre(arbitreid),
                         PRIMARY KEY (matchid)
);