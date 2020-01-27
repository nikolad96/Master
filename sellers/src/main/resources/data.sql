insert into seller (id, seller_id, name, pib) values (1, 1 , 'Casopis prvi', '15896254');
insert into seller (id, seller_id, name, pib) values (2, 2 , 'Casopis drugi', '56892145');

insert into payment_method (id, name) values (1, 'bank');
insert into payment_method (id, name) values (2, 'paypal');
insert into payment_method (id, name) values (3, 'bitcoin');

insert into seller_payment_methods (seller_id, payment_methods_id) values (1, 1);
insert into seller_payment_methods (seller_id, payment_methods_id) values (1, 2);
insert into seller_payment_methods (seller_id, payment_methods_id) values (1, 3);
insert into seller_payment_methods (seller_id, payment_methods_id) values (2, 1);
insert into seller_payment_methods (seller_id, payment_methods_id) values (2, 2);