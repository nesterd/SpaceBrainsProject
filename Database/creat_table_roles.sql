CREATE TABLE `ratepersons`.`roles` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ID`));

ALTER TABLE `ratepersons`.`users`
ADD COLUMN `RoleID` INT NOT NULL AFTER `AdminID`;

ALTER TABLE `ratepersons`.`users`
ADD INDEX `FK_roles_users_idx` (`RoleID` ASC);
ALTER TABLE `ratepersons`.`users`
ADD CONSTRAINT `FK_roles_users`
  FOREIGN KEY (`RoleID`)
  REFERENCES `ratepersons`.`roles` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `ratepersons`.`persons`
ADD COLUMN `AdminID` INT NOT NULL AFTER `Name`;

ALTER TABLE `ratepersons`.`sites`
ADD COLUMN `AdminID` INT NOT NULL AFTER `Name`;

ALTER TABLE `ratepersons`.`users`
DROP COLUMN `Is_Admin`;

ALTER TABLE `ratepersons`.`persons`
ADD INDEX `FK_users_persons_idx` (`AdminID` ASC);
ALTER TABLE `ratepersons`.`persons`
ADD CONSTRAINT `FK_users_persons`
  FOREIGN KEY (`AdminID`)
  REFERENCES `ratepersons`.`users` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `ratepersons`.`sites`
ADD INDEX `FK_users_sites_idx` (`AdminID` ASC);
ALTER TABLE `ratepersons`.`sites`
ADD CONSTRAINT `FK_users_sites`
  FOREIGN KEY (`AdminID`)
  REFERENCES `ratepersons`.`users` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `ratepersons`.`users`
DROP FOREIGN KEY `FK_roles_users`;
ALTER TABLE `ratepersons`.`users`
ADD CONSTRAINT `FK_roles_users`
  FOREIGN KEY (`RoleID`)
  REFERENCES `ratepersons`.`roles` (`ID`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;


