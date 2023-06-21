CREATE TABLE Person (
                         personid int NOT NULL AUTO_INCREMENT,
                         nom varchar(255) NOT NULL,
                         prenom varchar(255),
                         age int,
                         nationaliteid int,
                         PRIMARY KEY (personid),
                         FOREIGN KEY (nationaliteid) REFERENCES Pays(paysid)
);