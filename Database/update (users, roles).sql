ALTER TABLE `ratepersons`.`roles`
ADD UNIQUE INDEX `Name_UNIQUE` (`Name` ASC);

ALTER TABLE `ratepersons`.`users`
ADD UNIQUE INDEX `Login_UNIQUE` (`Login` ASC);

ALTER TABLE `ratepersons`.`sites`
DROP FOREIGN KEY `FK_users_sites`;
ALTER TABLE `ratepersons`.`sites`
ADD CONSTRAINT `FK_users_sites`
  FOREIGN KEY (`AdminID`)
  REFERENCES `ratepersons`.`users` (`ID`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

ALTER TABLE `ratepersons`.`persons`
DROP FOREIGN KEY `FK_users_persons`;
ALTER TABLE `ratepersons`.`persons`
ADD CONSTRAINT `FK_users_persons`
  FOREIGN KEY (`AdminID`)
  REFERENCES `ratepersons`.`users` (`ID`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

ALTER TABLE `ratepersons`.`users`
ADD COLUMN `Email` VARCHAR(255) NOT NULL AFTER `RoleID`;

UPDATE `ratepersons`.`users` SET `Email`='mail@mail.ru' WHERE `ID`='1';
UPDATE `ratepersons`.`users` SET `Email`='admin@mail.ru' WHERE `ID`='2';

ALTER TABLE `ratepersons`.`users`
ADD UNIQUE INDEX `Email_UNIQUE` (`Email` ASC);