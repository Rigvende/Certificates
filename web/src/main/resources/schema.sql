CREATE TABLE tags (
  `id_tag` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL UNIQUE);

CREATE TABLE certificates (
  `id_certificate` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(500) NOT NULL DEFAULT 'Gift certificate',
  `price` DECIMAL UNSIGNED NOT NULL,
  `create_date` TIMESTAMP NOT NULL,
  `last_update_date` TIMESTAMP NULL,
  `duration` INT(11) NOT NULL);

CREATE TABLE certificate_tag (
  `id_certificate` BIGINT(20) UNSIGNED NOT NULL,
  `id_tag` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_certificate`, `id_tag`));

ALTER TABLE certificate_tag
ADD CONSTRAINT `tag_fk`
  FOREIGN KEY (`id_tag`)
  REFERENCES tags (`id_tag`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION,
ADD CONSTRAINT `certificate_fk`
  FOREIGN KEY (`id_certificate`)
  REFERENCES certificates (`id_certificate`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;