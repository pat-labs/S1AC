CREATE TABLE product (
    product_id VARCHAR(16) PRIMARY KEY,
    description VARCHAR(200) NOT NULL,
    status SMALLINT NOT NULL
);
INSERT INTO product (product_id, description, status)
VALUES
    ('2024112611300001', 'New Product', 1);