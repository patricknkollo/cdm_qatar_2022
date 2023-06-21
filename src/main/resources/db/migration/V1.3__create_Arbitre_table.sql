CREATE TABLE Arbitre (
                         arbitreid int NOT NULL AUTO_INCREMENT,
                         nom varchar(255) NOT NULL,
                         prenom varchar(255),
                         Age int,
                         personid int,
                         PRIMARY KEY (arbitreid),
                         FOREIGN KEY (personid) REFERENCES Person(Personid)
);