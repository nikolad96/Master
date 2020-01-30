-- admin sifra milica
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (1, "Milica", "Makaric", "Stepanovicevo", "Srbija", "makaric.milica@gmail.com", "milica", "$2a$10$eBGUHIMgZK3ncFhw7CrSTuCnh.fHNMpVBmS.cL5hJU.7hvMfIIUBm", true, 3);

-- urednici sifra taca za sve
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (2, "Tamara", "Makaric", "Stepanovicevo", "Srbija", "makarictamara@gmail.com", "taca", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (7, "Nikola", "Malencic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "mace", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (8, "Nikola", "Djordjevic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "djo", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);

-- recenzenti sifra taca za sve
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (3, "Andrijana", "Jeremic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "andrijana", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (4, "Vladimir", "Cvetanovic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "vlada", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (5, "Vukasin", "Jovic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "vule", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (6, "Milan", "Lazic", "Novi Sad", "Srbija", "makarictamara@gmail.com", "miki", "$2a$10$ifANXgSrjBgprG81FsGmLu5xuE4CkcmLXCi335AM5fDI2faPRz1dO", true, 3);

-- obican korisnik sifra milica za Milicu
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (9, "Milica", "Makaric", "Stepanovicevo", "Srbija", "makaric.milica@gmail.com", "mica", "$2a$10$eBGUHIMgZK3ncFhw7CrSTuCnh.fHNMpVBmS.cL5hJU.7hvMfIIUBm", true, 3);
insert into korisnik (id, ime, prezime, grad, drzava, email, username, password, aktiviran, recenzent)
values (10, "Nikola", "Malencic", "Novi Sad", "Srbija", "nmalencic@gmail.com", "realmace", "$2a$10$9.shidvgsEtvCwlM8WnbC.XneO/Kcxyf02TbzhXd09BQi.19iMQES", true, 3);

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
insert into korisnik_roles values (9, 1);
insert into korisnik_roles values (10, 1);

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

insert into casopis (id, naziv, issn, naplata_clanarine, aktiviran, glavni_urednik_id, cena) values (1, 'Casopis prvi', '1458-9856', 'NAPLATA_AUTORIMA', 1, 2, 300);
insert into casopis (id, naziv, issn, naplata_clanarine, aktiviran, glavni_urednik_id, cena) values (2, 'Casopis drugi', '1896-2535', 'NAPLATA_CITAOCIMA', 1, 2, 500);

insert into casopis_naucne_oblasti (casopis_id, naucne_oblasti_id) values (1, 1);
insert into casopis_naucne_oblasti (casopis_id, naucne_oblasti_id) values (1, 3);
insert into casopis_naucne_oblasti (casopis_id, naucne_oblasti_id) values (2, 1);
insert into casopis_naucne_oblasti (casopis_id, naucne_oblasti_id) values (2, 2);

insert into casopis_recenzenti (casopis_id, recenzenti_id) values (1, 3);
insert into casopis_recenzenti (casopis_id, recenzenti_id) values (1, 5);
insert into casopis_recenzenti (casopis_id, recenzenti_id) values (2, 4);
insert into casopis_recenzenti (casopis_id, recenzenti_id) values (2, 5);

insert into casopis_korisnici_platili (casopis_id, korisnici_platili_id) values (1, 9);

insert into rad (id, naslov, apstrakt, naucna_oblast_id, pdf_lokacija, prihvacen, casopis_id, kljucni_pojmovi, cena) values (1, 'Rad 1', 'apstrakt prvi', 1, 'lokacija prvog rada', 1, 1, 'nesto;neko;neki', 150);
insert into rad (id, naslov, apstrakt, naucna_oblast_id, pdf_lokacija, prihvacen, casopis_id, kljucni_pojmovi, cena) values (2, 'Rad 2', 'apstrakt drugi', 1, 'lokacija drugog rada', 1, 1, 'nesto', 100);
insert into rad (id, naslov, apstrakt, naucna_oblast_id, pdf_lokacija, prihvacen, casopis_id, kljucni_pojmovi, cena) values (3, 'Rad 3', 'apstrakt treci', 1, 'lokacija treceg rada', 1, 2, 'nesto;neko', 120);
insert into rad (id, naslov, apstrakt, naucna_oblast_id, pdf_lokacija, prihvacen, casopis_id, kljucni_pojmovi, cena) values (4, 'Rad 4', 'apstrakt cetvri', 1, 'lokacija cetvrtog rada', 1, 2, 'nesto;neko;neki', 150);
insert into rad (id, naslov, apstrakt, naucna_oblast_id, pdf_lokacija, prihvacen, casopis_id, kljucni_pojmovi, cena) values (5, 'Rad 5', 'apstrakt peti', 1, 'lokacija petog rada', 1, 2, 'nesto;neko;neki', 130);
insert into rad (id, naslov, apstrakt, naucna_oblast_id, pdf_lokacija, prihvacen, casopis_id, kljucni_pojmovi, cena) values (6, 'Rad 6', 'apstrakt sesti', 1, 'lokacija sestog rada', 1, 4, 'nesto;neko;neki', 130);
insert into rad (id, naslov, apstrakt, naucna_oblast_id, pdf_lokacija, prihvacen, casopis_id, kljucni_pojmovi, cena) values (7, 'Rad 7', 'apstrakt sedmi', 1, 'lokacija sedma rada', 1, 5, 'nesto;neko;neki', 130);
insert into rad_korisnici_platili (rad_id, korisnici_platili_id) values (3, 9);