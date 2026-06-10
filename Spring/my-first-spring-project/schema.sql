-- school database
-- student, teacher, subject, counselor tables
-- student M:M subject
-- subject 1:1 teacher
-- student M:1 counselor

CREATE SCHEMA `school`;

CREATE TABLE `school`.`counselor` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(32) NOT NULL,
    `last_name` VARCHAR(32) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `school`.`student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(32) NOT NULL,
  `last_name` VARCHAR(32) NOT NULL,
  `counselor_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `student_counselor_idx` (`counselor_id` ASC) VISIBLE,
  CONSTRAINT `student_counselor`
    FOREIGN KEY (`counselor_id`)
    REFERENCES `school`.`counselor` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);
    
CREATE TABLE `school`.`subject` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `school`.`teacher` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(32) NOT NULL,
  `last_name` VARCHAR(32) NOT NULL,
  `subject_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `teacher_subject_idx` (`subject_id` ASC) VISIBLE,
  CONSTRAINT `teacher_subject`
    FOREIGN KEY (`subject_id`)
    REFERENCES `school`.`subject` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `school`.`enrollment` (
  `student_id` INT NOT NULL,
  `subject_id` INT NOT NULL,
  PRIMARY KEY (`student_id`, `subject_id`),
  INDEX `enrollment_subject_idx` (`subject_id` ASC) VISIBLE,
  CONSTRAINT `enrollment_student`
    FOREIGN KEY (`student_id`)
    REFERENCES `school`.`student` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `enrollment_subject`
    FOREIGN KEY (`subject_id`)
    REFERENCES `school`.`subject` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
  
  
  
  
  
  
  
  
  
  