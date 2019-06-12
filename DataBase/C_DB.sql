

DROP DATABASE IF EXISTS `creche_dar_elhadith`;
CREATE DATABASE `creche_dar_elhadith` CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `creche_dar_elhadith`;

create TABLE `creche_dar_elhadith`.`Classe` (
`num` int(4) unsigned NOT NULL AUTO_INCREMENT,
`id` varchar(20) DEFAULT NULL,
`ClassNam` varchar(45) NOT NULL,
`ClassRom` varchar(22) NOT NULL,
`maxNbrElev` varchar(30) DEFAULT NULL,
`remarque` varchar(500) DEFAULT NULL,
PRIMARY KEY (`num`)
);


CREATE TABLE `creche_dar_elhadith`.`compte` (
  `email` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`email`, `login`, `password`));

INSERT INTO `creche_dar_elhadith`.`compte` (`email`, `login`, `password`) VALUES ('darELhadith_creche@gmail.com', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997');

CREATE TABLE `creche_dar_elhadith`.`regime` (
  `idregim` INT NOT NULL,
  `typeRegim` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idregim`),
  UNIQUE INDEX `idregim_UNIQUE` (`idregim` ASC) VISIBLE);

INSERT INTO `creche_dar_elhadith`.`regime` (`idregim`, `typeRegim`) VALUES ('1', 'صباح');
INSERT INTO `creche_dar_elhadith`.`regime` (`idregim`, `typeRegim`) VALUES ('2', 'مساء');
INSERT INTO `creche_dar_elhadith`.`regime` (`idregim`, `typeRegim`) VALUES ('3', 'صباح+مساء');
INSERT INTO `creche_dar_elhadith`.`regime` (`idregim`, `typeRegim`) VALUES ('4', 'صباح+نصف داخلي');
INSERT INTO `creche_dar_elhadith`.`regime` (`idregim`, `typeRegim`) VALUES ('5', 'اليوم كامل');


create TABLE `creche_dar_elhadith`.`employe` (
  `id` int(10) unsigned NOT NULL,
  `nom` varchar(22) NOT NULL,
  `prenom` varchar(22) NOT NULL,
  `dateNaissance` Date NOT NULL,
  `lieuNaissance` varchar(20) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `numTelephone` varchar(10) DEFAULT NULL,
  `socialSecurityNumber` varchar(15) DEFAULT NULL,
  `diplome` varchar(50) DEFAULT NULL,
  `experience` varchar(40) DEFAULT NULL,
  `itar` varchar(45) DEFAULT NULL,
  `renouvlementDeContrat` varchar(20) DEFAULT NULL,
  `dateDebut` Date DEFAULT NULL,
  `fonction` varchar(30) DEFAULT NULL,
  `classe` VARCHAR(45) DEFAULT NULL,
  `regime` varchar(20) DEFAULT NULL,
  `marier` int(1) DEFAULT '0',
  `celibacyTitle` varchar(22) DEFAULT NULL,
  `nombreEnfantM` int(2) DEFAULT NULL,
  `nombreEnfantF` int(2) DEFAULT NULL,
  `remarque` varchar(100),
  PRIMARY KEY (`id`));

CREATE TABLE `creche_dar_elhadith`.`pointage` (
      `idEmp` INT REFERENCES id(employe),
      `name` VARCHAR(45) NOT NULL,
      `dateJour` Date NOT NULL,
      `timeEntre` VARCHAR(45) NOT NULL,
	  `remark` INT NOT NULL,
      `presence` INT NOT NULL,
      `jour` VARCHAR(45) NOT NULL,
      PRIMARY KEY (`idEmp`,`dateJour`));


    create TABLE creche_dar_elhadith.`eleve` (
    num int(4)  NOT NULL AUTO_INCREMENT,
    id varchar(22) DEFAULT NULL,
    gender int(2) DEFAULT NULL,
    nom varchar(22) NOT NULL,
    prenom varchar(22) NOT NULL,
    dateNaissance Date NOT NULL,
    lieuNaissance varchar(30) DEFAULT NULL,
    classe varchar(3) DEFAULT NULL,
    anneeScolaire int(2) NOT NULL,
    regime varchar(50) DEFAULT NULL,
    adresse varchar(50) DEFAULT NULL,
    phone varchar(10) DEFAULT NULL,
    prenomPere varchar(22) DEFAULT NULL,
    prenomMere varchar(22) DEFAULT NULL,
    nomMere varchar(22) DEFAULT NULL,
    travailPere varchar(20) DEFAULT NULL,
    travailMere varchar(30) DEFAULT NULL,
    maladie varchar(50) DEFAULT NULL,
    wakil varchar(150) DEFAULT NULL,
    remarque varchar(300) DEFAULT NULL,
    tranches int NOT NULL,
    montantRestant double NOT NULL,
    PRIMARY KEY (num));

create TABLE creche_dar_elhadith.`stock` (
  id varchar(8)  NOT NULL ,
  typeProduit int(3)  NOT NULL ,
  nom varchar(22) NOT NULL,
  datefab Date NOT NULL,
  dateExp Date NOT NULL,
  quantite varchar(50) DEFAULT NULL,
  prix varchar(10) DEFAULT NULL,
  fournisseur varchar(22) NOT NULL,
   prixTotale varchar(10000) DEFAULT NULL,
  PRIMARY KEY (id)
 );


create TABLE `creche_dar_elhadith`.`calendar` (
  `CalendarName` VARCHAR(200) NOT NULL,
  `StartYear` INT NULL,
  `EndYear` INT NULL,
  `StartDate` DATE NULL,
  PRIMARY KEY (`CalendarName`));

create TABLE `creche_dar_elhadith`.`events` (
  `EventDescription` VARCHAR(200) NOT NULL,
  `EventDate` DATE NOT NULL,
  `CalendarName` VARCHAR(200) NOT NULL,
  `EventTime` time NOT NULL,
  `TypeEvent` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`EventDescription`,`EventDate`,`CalendarName`,`TypeEvent`),
  constraint CI_type
  check (TypeEvent in ('رحلة','ورشة','قيلولة','عرض','ألعاب')),
  CONSTRAINT `CalendarName`
    FOREIGN KEY (`CalendarName`)
    REFERENCES `creche_dar_elhadith`.`calendar` (`CalendarName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `creche_dar_elhadith`.`colortable` (
  `idcolorevent` INT NOT NULL,
  `nameevent` VARCHAR(45) NOT NULL,
  `color` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcolorevent`));

alter table `creche_dar_elhadith`.`colortable` add constraint `CI_Type` check (`nameevent` in ('sieste','atelier','excursion','spectacle','jeux'));

INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('1', 'excurtion', '156-204-101');
INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('2', 'spectacle', '251-210-73');
INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('3', 'atelier', '0-112-255');
INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('4', 'sieste', '154-86-187');
INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('5', 'jeux', '243-30-180');

CREATE TABLE `creche_dar_elhadith`.`images` (
  `idimages` INT NOT NULL AUTO_INCREMENT,
  `imageName` VARCHAR(45) NOT NULL,
  `studentInimage` VARCHAR(45) NOT NULL,
  `imagesExtension` VARCHAR(4) NOT NULL,
  `imageClasse` VARCHAR(22) NOT NULL,
  PRIMARY KEY (`idimages`, `imageName`, `studentInimage`, `imagesExtension`));

create TABLE creche_dar_elhadith.`frais` (
    fraisEleve double DEFAULT 0,
    fraisCharity double DEFAULT 0,
    fraisEmploye double DEFAULT 0,
    fraisStock double DEFAULT 0

    );
    create TABLE creche_dar_elhadith.`tarifs` (

        tarifsSets int DEFAULT 0,
        Matin double NOT NULL,
        Aprem double NOT NULL,
        MatAprem double NOT NULL,
        Demi double NOT NULL,
        Complet double NOT NULL

        );