 CREATE DATABASE expense;

CREATE TABLE `expense`.`expenses` (
  `expensesId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
 `reason`  VARCHAR(45) NULL,
 `amount`  DOUBLE NULL ,
 `vatAmount` DOUBLE NULL ,
 `date` DATE NULL);