INSERT INTO cart_item (id, shopping_cart_id, book_id, quantity)
VALUES (1, 1, 1, 10);

INSERT INTO shopping_carts (id, user_id, is_deleted)
VALUES (1, 1, FALSE);

INSERT INTO users (id, email, password, repeat_password, first_name, last_name, shipping_address, is_deleted)
VALUES (1, 'bob@example.com', 12345678, 12345678, 'Bob', 'Bobson', 'Kyiv', FALSE);