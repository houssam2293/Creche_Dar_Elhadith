create TABLE `employe` (
  `num` int(4) unsigned NOT NULL AUTO_INCREMENT,
  `id` varchar(30) DEFAULT NULL,
  `nom` varchar(22) NOT NULL,
  `prenom` varchar(22) NOT NULL,
  `dateNaissance` EventDate NOT NULL,
  `lieuNaissance` varchar(20) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `numTelephone` varchar(10) DEFAULT NULL,
  `socialSecurityNumber` varchar(15) DEFAULT NULL,
  `diplome` varchar(50) DEFAULT NULL,
  `experience` varchar(40) DEFAULT NULL,
  `itar` varchar(45) DEFAULT NULL,
  `renouvlementDeContrat` varchar(20) DEFAULT NULL,
  `dateDebut` EventDate DEFAULT NULL,
  `fonction` varchar(30) DEFAULT NULL,
  `celibacyTitle` varchar(22) DEFAULT NULL,
  `nombreEnfantM` int(2) DEFAULT NULL,
  `nombreEnfantF` int(2) DEFAULT NULL,
  PRIMARY KEY (`num`)
  ON DELETE CASCADE
  ON UPDATE CASCADE);

create TABLE `creche_dar_elhadith`.`calendar` (
  `CalendarName` VARCHAR(200) NOT NULL,
  `StartYear` INT NULL,
  `EndYear` INT NULL,
  `StartDate` DATE NULL,
  PRIMARY KEY (`CalendarName`)
  ON delete CASCADE
  ON update CASCADE);

create TABLE `creche_dar_elhadith`.`events` (
  `EventDescription` VARCHAR(200) NOT NULL,
  `EventDate` DATE NOT NULL,
  `CalendarName` VARCHAR(200) NOT NULL,
  `EventTime` time NOT NULL,
  `TypeEvent` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`EventDescription`),
  constraint CI_type
  check (TypeEvent in ('sieste','atelier','excursion','spectacle','jeux')),
  CONSTRAINT `CalendarName`
    FOREIGN KEY (`CalendarName`)
    REFERENCES `creche_dar_elhadith`.`calendar` (`CalendarName`)
    ON delete CASCADE
    ON update CASCADE);

CREATE TABLE `creche_dar_elhadith`.`colortable` (
  `idcolorevent` INT NOT NULL,
  `nameevent` VARCHAR(45) NOT NULL,
  `color` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcolorevent`));

alter table `creche_dar_elhadith`.`colortable` add constraint `CI_Type` check (`nameevent` in ('sieste','atelier','excursion','spectacle','jeux'));

INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('1', 'excurtion', '244-244-242');
INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('2', 'spectacle', '186-155-160');
INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('3', 'atelier', '116-84-61');
INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('4', 'sieste', '199-120-28');
INSERT INTO `creche_dar_elhadith`.`colortable` (`idcolorevent`, `nameevent`, `color`) VALUES ('5', 'jeux', '243-30-180');
