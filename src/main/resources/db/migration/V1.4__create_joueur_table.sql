CREATE TABLE Joueur (
                         joueurid int NOT NULL AUTO_INCREMENT,
                         nom varchar(255) NOT NULL,
                         prenom varchar(255),
                         dossard int,
                         personid int,
                         PRIMARY KEY (joueurid),
                         FOREIGN KEY (personid) REFERENCES Person(Personid)
);