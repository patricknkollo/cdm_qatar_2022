CREATE TABLE Buteur (
                         buteurid int NOT NULL AUTO_INCREMENT,
                         joueurid int,
                         nom varchar(255) NOT NULL,
                         prenom varchar(255),
                         buts int,
                         PRIMARY KEY (buteurid),
                         FOREIGN KEY (joueurid) REFERENCES Joueur(joueurid)
);