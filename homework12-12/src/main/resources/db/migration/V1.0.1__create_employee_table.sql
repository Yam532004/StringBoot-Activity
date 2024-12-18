CREATE TABLE employee
(
    id            INT   NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NULL,
    dob           date NULL,
    gender        SMALLINT NULL,
    salary DOUBLE NULL,
    phone         VARCHAR(255) NULL,
    department_id INT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);