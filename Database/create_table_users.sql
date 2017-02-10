USE `ratepersons` ;

-- -----------------------------------------------------
-- Table `ratepersons`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ratepersons`.`Users` ;

CREATE TABLE IF NOT EXISTS `ratepersons`.`Users` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(75) NOT NULL,
  `Login` VARCHAR(15) NOT NULL,
  `Password` VARCHAR(255) NOT NULL,
  `Is_Admin` BOOLEAN NOT NULL,
  `AdminID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_users_adminid`
    FOREIGN KEY (`AdminID`)
    REFERENCES `ratepersons`.`Users` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FK_users_adminid` ON `ratepersons`.`Users` (`AdminID` ASC);