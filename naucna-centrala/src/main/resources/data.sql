-- admin
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (1, "Milica", "Makaric", "Stepanovicevo", "Srbija", "makaric.milica@gmail.com", "milica", "$2a$10$eBGUHIMgZK3ncFhw7CrSTuCnh.fHNMpVBmS.cL5hJU.7hvMfIIUBm", true, 3);

-- urednici
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (2, "Tamara", "Makaric", "Stepanovicevo", "Srbija", "makarictamara@gmail.com", "taca", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (7, "Nikola", "Malencic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "mace", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (8, "Nikola", "Djordjevic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "djo", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);

-- recenzenti
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (3, "Andrijana", "Jeremic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "andrijana", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (4, "Vladimir", "Cvetanovic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "vlada", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (5, "Vukasin", "Jovic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "vule", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (6, "Milan", "Lazic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "miki", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);

insert into role values (1, 'ROLE_KORISNIK');
insert into role values (2, 'ROLE_ADMIN');
insert into role values (3, 'ROLE_UREDNIK');
insert into role values (4, 'ROLE_RECENZENT');
insert into role values (5, 'ROLE_AUTOR');

insert into privilege values (1, 'POTVRDA_RECENZENTA');
insert into privilege values (2, 'KREIRANJE_NOVOG_CASOPISA');
insert into privilege values (3, 'PROVERA_CASOPISA');

insert into roles_privileges values (2, 1);
insert into roles_privileges values (3, 2);
insert into roles_privileges values (2, 3);

insert into korisnik_roles values (1, 2);
insert into korisnik_roles values (2, 3);
-- insert into korisnik_roles values (2, 4);
insert into korisnik_roles values (3, 4);
insert into korisnik_roles values (4, 4);
insert into korisnik_roles values (5, 4);
insert into korisnik_roles values (6, 4);
insert into korisnik_roles values (7, 3);
insert into korisnik_roles values (8, 3);
-- insert into korisnik_roles values (7, 4);
-- insert into korisnik_roles values (8, 4);

insert into naucna_oblast values (1, 'prirodne_nauke');
insert into naucna_oblast values (2, 'inzenjerstvo_i_tehnologija');
insert into naucna_oblast values (3, 'medicinske_i_zdravstvene_nauke');
insert into naucna_oblast values (4, 'poljoprivredne_nauke');

insert into korisnik_naucne_oblasti (korisnik_id, naucne_oblasti_id) values (2, 3);
insert into korisnik_naucne_oblasti (korisnik_id, naucne_oblasti_id) values (2, 4);
insert into korisnik_naucne_oblasti (korisnik_id, naucne_oblasti_id) values (3, 1);
insert into korisnik_naucne_oblasti (korisnik_id, naucne_oblasti_id) values (4, 1);
insert into korisnik_naucne_oblasti (korisnik_id, naucne_oblasti_id) values (5, 2);
insert into korisnik_naucne_oblasti (korisnik_id, naucne_oblasti_id) values (6, 3);
insert into korisnik_naucne_oblasti (korisnik_id, naucne_oblasti_id) values (7, 2);
insert into korisnik_naucne_oblasti (korisnik_id, naucne_oblasti_id) values (8, 3);
