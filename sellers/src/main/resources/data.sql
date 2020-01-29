insert into seller (id, seller_id, name) values (1, 1 , 'Casopis prvi');
insert into seller (id, seller_id, name) values (2, 2 , 'Casopis drugi');

insert into payment_method (id, name) values (1, 'bank');
insert into payment_method (id, name) values (2, 'paypal');
insert into payment_method (id, name) values (3, 'bitcoin');

insert into seller_payment (id, payment_method_id, potvrdjeno) values (1, 1, true);
insert into seller_payment (id, payment_method_id, potvrdjeno) values (2, 2, true);
insert into seller_payment (id, payment_method_id, potvrdjeno) values (3, 3, true);
insert into seller_payment (id, payment_method_id, potvrdjeno) values (4, 1, true);
insert into seller_payment (id, payment_method_id, potvrdjeno) values (5, 2, true);

insert into seller_payment_methods (seller_id, payment_methods_id) values (1, 1);
insert into seller_payment_methods (seller_id, payment_methods_id) values (1, 2);
insert into seller_payment_methods (seller_id, payment_methods_id) values (1, 3);
insert into seller_payment_methods (seller_id, payment_methods_id) values (2, 4);
insert into seller_payment_methods (seller_id, payment_methods_id) values (2, 5);