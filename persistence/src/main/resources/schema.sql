CREATE TABLE IF NOT EXISTS `certificates_db`.`tags` (
  `id_tag` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_tag`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE TABLE IF NOT EXISTS `certificates_db`.`certificates` (
  `id_certificate` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(500) NOT NULL DEFAULT 'Gift certificate',
  `price` DECIMAL UNSIGNED NOT NULL,
  `create_date` TIMESTAMP NOT NULL,
  `last_update_date` TIMESTAMP NULL,
  `duration` INT(11) NOT NULL,
  PRIMARY KEY (`id_certificate`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE TABLE IF NOT EXISTS `certificates_db`.`certificate_tag` (
  `id_certificate` BIGINT(20) UNSIGNED NOT NULL,
  `id_tag` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_certificate`, `id_tag`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

ALTER TABLE `certificates_db`.`certificate_tag`
ADD INDEX `tag_fk_idx` (`id_tag` ASC) VISIBLE;

ALTER TABLE `certificates_db`.`certificate_tag`
ADD CONSTRAINT `tag_fk`
  FOREIGN KEY (`id_tag`)
  REFERENCES `certificates_db`.`tags` (`id_tag`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION,
ADD CONSTRAINT `certificate_fk`
  FOREIGN KEY (`id_certificate`)
  REFERENCES `certificates_db`.`certificates` (`id_certificate`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;