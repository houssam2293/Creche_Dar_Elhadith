

DROP DATABASE IF EXISTS `creche_dar_elhadith`;
CREATE DATABASE `creche_dar_elhadith` CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `creche_dar_elhadith`;


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
  `marier` int(1) DEFAULT '0',
  `celibacyTitle` varchar(22) DEFAULT NULL,
  `nombreEnfantM` int(2) DEFAULT NULL,
  `nombreEnfantF` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`));

create TABLE `creche_dar_elhadith`.`eleve` (
`num` int(4) unsigned NOT NULL AUTO_INCREMENT,
`id` varchar(30) DEFAULT NULL,
`nom` varchar(22) NOT NULL,
`prenom` varchar(22) NOT NULL,
`dateNaissance` Date NOT NULL,
`lieuNaissance` varchar(20) DEFAULT NULL,
`adresse` varchar(50) DEFAULT NULL,
`numTelephone` varchar(10) DEFAULT NULL,
`malad` varchar(60) ,
`nomPère` varchar(22) NOT NULL,
`nomMère` varchar(22) NOT NULL,
`prenomMère` varchar(22) NOT NULL,
`jobPère` varchar(22) NOT NULL,
`jobMère` varchar(22) NOT NULL,
`NombreDannées` int(1),
PRIMARY KEY (`num`));

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
  check (TypeEvent in ('sieste','atelier','excursion','spectacle','jeux')),
  CONSTRAINT `CalendarName`
    FOREIGN KEY (`CalendarName`)
    REFERENCES `creche_dar_elhadith`.`calendar` (`CalendarName`));

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
