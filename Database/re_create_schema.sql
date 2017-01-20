-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ratepersons
-- -----------------------------------------------------
-- База данных для проекта по сбору статистики упоминания личностей в сети интернет
-- Проект выполняется в рамках программы стажировки в компании geekbrains.ru
-- Группа программистов SpaceBrains
DROP SCHEMA IF EXISTS `ratepersons` ;

-- -----------------------------------------------------
-- Schema ratepersons
--
-- База данных для проекта по сбору статистики упоминания личностей в сети интернет
-- Проект выполняется в рамках программы стажировки в компании geekbrains.ru
-- Группа программистов SpaceBrains
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ratepersons` DEFAULT CHARACTER SET utf8 ;
USE `ratepersons` ;

-- -----------------------------------------------------
-- Table `ratepersons`.`Persons`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`Persons` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`Persons` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` NVARCHAR(2048) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `ID_UNIQUE` ON `ratepersons`.`Persons` (`ID` ASC);


-- -----------------------------------------------------
-- Table `ratepersons`.`Keywords`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`Keywords` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`Keywords` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` NVARCHAR(2048) NULL,
  `PersonID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_persons_keywords`
    FOREIGN KEY (`PersonID`)
    REFERENCES `ratepersons`.`Persons` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_Persons_idx` ON `ratepersons`.`Keywords` (`PersonID` ASC);


-- -----------------------------------------------------
-- Table `ratepersons`.`Sites`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`Sites` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`Sites` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` NVARCHAR(2048) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratepersons`.`Pages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`Pages` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`Pages` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Url` NVARCHAR(2048) NOT NULL,
  `SiteID` INT NOT NULL,
  `FoundDateTime` DATETIME NULL,
  `LastScanDate` DATETIME NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_sites_pages`
    FOREIGN KEY (`ID`)
    REFERENCES `ratepersons`.`Sites` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ratepersons`.`PersonPageRank`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`PersonPageRank` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`PersonPageRank` (
  `PersonID` INT NOT NULL,
  `PageID` INT NOT NULL,
  `Rank` INT NOT NULL,
  CONSTRAINT `FK_persons_personpagerank`
    FOREIGN KEY (`PersonID`)
    REFERENCES `ratepersons`.`Persons` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_pages_personpagerank`
    FOREIGN KEY (`PageID`)
    REFERENCES `ratepersons`.`Pages` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `FK_persons_idx` ON `ratepersons`.`PersonPageRank` (`PersonID` ASC);

CREATE INDEX `FK_pages_idx` ON `ratepersons`.`PersonPageRank` (`PageID` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
