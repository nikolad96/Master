insert into customer (id, merchant_id, merchant_password, name) values (1, 'milica', 'milica', 'Casopis prvi');
insert into customer (id, merchant_id, merchant_password, name) values (2, 'taca', 'taca', 'Casopis drugi');

insert into bank (id, name) values (1, 'Erste bank');
insert into bank (id, name) values (2, 'UniCredit bank');

insert into account (id, pan, security_code, cardholder_name, expiration_date, balance, reserved, customer_id, bank_id)
values (1, '123', '889', 'Milica Makaric', '09/2022', 1000, 0, 1, 2);
insert into account (id, pan, security_code, cardholder_name, expiration_date, balance, reserved, customer_id, bank_id)
values (2, '456', '899', 'Tamara Makaric', '03/2022', 500, 0, 2, 1);