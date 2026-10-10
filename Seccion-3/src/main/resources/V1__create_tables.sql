CREATE TABLE users(
    id_user UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE orders(
    id_order UUID PRIMARY KEY,
    status VARCHAR(255) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    id_user UUID NOT NULL,
    CONSTRAINT fk_user_order
        FOREIGN KEY (id_user) REFERENCES users(id_user)
);

CREATE TABLE products(
    id_product UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE orders_items(
    id_order_item UUID PRIMARY KEY,
    id_order UUID NOT NULL,
    id_product UUID NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_order_item_order
    FOREIGN KEY (id_order) REFERENCES orders(id_order),
    CONSTRAINT fk_order_item_product
        FOREIGN KEY (id_product) REFERENCES products(id_product)
);

CREATE TABLE idempotency_key(
    id_key UUID PRIMARY KEY,
    request_hash VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    id_user UUID NOT NULL,
    id_order UUID,
    CONSTRAINT fk_idempotency_key_user
        FOREIGN KEY (id_user) REFERENCES users(id_user),
    CONSTRAINT fk_idempotency_key_order
        FOREIGN KEY (id_order) REFERENCES orders(id_order)
);

CREATE TABLE inventory(
    id_product UUID PRIMARY KEY,
    sku VARCHAR(255) NOT NULL,
    quantity_on_hand INT NOT NULL,
    quantity_reserved INT NOT NULL,
    CONSTRAINT fk_inventory_product
        FOREIGN KEY (id_product) REFERENCES products(id_product)
);

-- USERS
INSERT INTO users (id_user, name, email, password) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Ana Pérez', 'ana@test.com', 'password-de-prueba'),
    ('22222222-2222-2222-2222-222222222222', 'Luis Gómez', 'luis@test.com', 'password-de-prueba');

-- PRODUCTS
INSERT INTO products (id_product, name, description, image, price) VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Camiseta', 'Camiseta de algodón', 'camiseta.jpg', 25.00),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Gorra', 'Gorra ajustable', 'gorra.jpg', 10.50),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Zapatos', 'Zapatos deportivos', 'zapatos.jpg', 89.99),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Mochila', 'Mochila agotada', 'mochila.jpg', 45.00);

-- INVENTORY
INSERT INTO inventory (id_product, sku, quantity_on_hand, quantity_reserved) VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'CAM-001', 100, 2),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'GOR-001', 50, 1),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'ZAP-001', 3, 0),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'MOC-001', 0, 0);

-- ORDEN DE EJEMPLO
INSERT INTO orders (id_order, status, total, id_user) VALUES
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'PENDING', 60.50, '11111111-1111-1111-1111-111111111111');

INSERT INTO orders_items (id_order_item, id_order, id_product, unit_price, quantity) VALUES
    ('f0000000-0000-0000-0000-000000000001', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 25.00, 2),
    ('f0000000-0000-0000-0000-000000000002', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 10.50, 1);