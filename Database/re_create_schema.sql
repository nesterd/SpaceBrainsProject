-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ratepersons
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ratepersons` ;

-- -----------------------------------------------------
-- Schema ratepersons
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ratepersons` DEFAULT CHARACTER SET utf8 ;
USE `ratepersons` ;

-- -----------------------------------------------------
-- Table `ratepersons`.`Persons`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`Persons` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`Persons` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(1024) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `ID_UNIQUE` ON `ratepersons`.`Persons` (`ID` ASC);

CREATE UNIQUE INDEX `Name_UNIQUE` ON `ratepersons`.`Persons` (`Name` ASC);


-- -----------------------------------------------------
-- Table `ratepersons`.`Keywords`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`Keywords` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`Keywords` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(1024) NOT NULL,
  `PersonID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_persons_keywords`
    FOREIGN KEY (`PersonID`)
    REFERENCES `ratepersons`.`Persons` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `Name_UNIQUE` ON `ratepersons`.`Keywords` (`Name` ASC);

CREATE INDEX `FK_Persons_idx` ON `ratepersons`.`Keywords` (`PersonID` ASC);


-- -----------------------------------------------------
-- Table `ratepersons`.`Sites`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`Sites` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`Sites` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(1024) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `Name_UNIQUE` ON `ratepersons`.`Sites` (`Name` ASC);


-- -----------------------------------------------------
-- Table `ratepersons`.`Pages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`Pages` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`Pages` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Url` VARCHAR(2048) NOT NULL,
  `SiteID` INT NOT NULL,
  `FoundDateTime` DATETIME NULL DEFAULT NULL,
  `LastScanDate` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_sites_pages`
    FOREIGN KEY (`ID`)
    REFERENCES `ratepersons`.`Sites` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ratepersons`.`PersonPageRank`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`PersonPageRank` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`PersonPageRank` (
  `PersonID` INT NOT NULL,
  `PageID` INT NOT NULL,
  `Rank` INT NOT NULL,
  CONSTRAINT `FK_pages_personpagerank`
    FOREIGN KEY (`PageID`)
    REFERENCES `ratepersons`.`Pages` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_persons_personpagerank`
    FOREIGN KEY (`PersonID`)
    REFERENCES `ratepersons`.`Persons` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FK_persons_idx` ON `ratepersons`.`PersonPageRank` (`PersonID` ASC);

CREATE INDEX `FK_pages_idx` ON `ratepersons`.`PersonPageRank` (`PageID` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
-- begin attached script 'script'
use `ratepersons`;
INSERT INTO `persons` (`Name`) VALUES ("Путин");
INSERT INTO `keywords` (`Name`, `PersonID`) VALUES ('Путину', 1);
INSERT INTO `keywords` (`Name`, `PersonID`) VALUES ('Путиным', 1);
INSERT INTO `keywords` (`Name`, `PersonID`) VALUES ('Путине', 1);
INSERT INTO `sites` (`Name`) VALUES ('lenta.ru');
INSERT INTO `pages` (`Url`, `SiteID`) VALUES ('http://lenta.ru/robots.txt', 1);
INSERT INTO `personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES (1, 1, 10);
-- end attached script 'script'
