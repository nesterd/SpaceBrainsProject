use `ratepersons`;
INSERT INTO `persons` (`Name`) VALUES ('Путин');
INSERT INTO `keywords` (`Name`, `PersonID`) VALUES ('Путину', 1);
INSERT INTO `keywords` (`Name`, `PersonID`) VALUES ('Путиным', 1);
INSERT INTO `keywords` (`Name`, `PersonID`) VALUES ('Путине', 1);
INSERT INTO `sites` (`Name`) VALUES ('lenta.ru');
INSERT INTO `pages` (`Url`, `SiteID`) VALUES ('http://lenta.ru/robots.txt', 1);
INSERT INTO `personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES (1, 1, 10);

/*DELETE FROM `persons` WHERE ID=1;*/;
SELECT * FROM `keywords`;

/*DELETE FROM `sites` WHERE ID=1*/
SELECT * FROM `sites`;
SELECT * FROM `pages`;
SELECT * FROM `personpagerank`;
