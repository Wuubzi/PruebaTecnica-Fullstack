CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id),
  status VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS order_items (
  id SERIAL PRIMARY KEY,
  order_id INTEGER NOT NULL REFERENCES orders(id),
  product_id INTEGER NOT NULL,
  quantity INTEGER NOT NULL CHECK (quantity > 0)
);

INSERT INTO users (id, name, email)
VALUES (1, 'Usuario Demo', 'demo@example.com')
ON CONFLICT (id) DO NOTHING;

INSERT INTO orders (id, user_id, status)
VALUES (1, 1, 'pending')
ON CONFLICT (id) DO NOTHING;

INSERT INTO order_items (id, order_id, product_id, quantity)
VALUES (1, 1, 101, 2), (2, 1, 202, 1)
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('users', 'id'), GREATEST((SELECT MAX(id) FROM users), 1));
SELECT setval(pg_get_serial_sequence('orders', 'id'), GREATEST((SELECT MAX(id) FROM orders), 1));
SELECT setval(pg_get_serial_sequence('order_items', 'id'), GREATEST((SELECT MAX(id) FROM order_items), 1));
