CREATE DATABASE  IF NOT EXISTS `contacts_list_management` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `contacts_list_management`;


DROP TABLE IF EXISTS `contacts`;

CREATE TABLE `contacts` (
    `contact_id` int(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255) DEFAULT NULL,
	`last_name` VARCHAR(255) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `phone` VARCHAR(20),
    PRIMARY KEY (`contact_id`)
)ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;