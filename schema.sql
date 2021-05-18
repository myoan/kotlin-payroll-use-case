CREATE TABLE IF NOT EXISTS agile_salary.employees (
  id INT NOT NULL PRIMARY KEY,
  name VARCHAR(128) NULL,
  address VARCHAR(128) NULL,
  data1 VARCHAR(128) NULL,
  data2 VARCHAR(128) NULL,
  type INT NOT NULL DEFAULT 0,
  created_at TIMESTAMP NULL,
  updated_at TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS agile_salary.time_cards (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    date TIME NULL,
    working_time INT NULL,
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

