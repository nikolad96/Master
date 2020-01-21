package com.example.naucnacentrala.service;

import com.example.naucnacentrala.model.Korisnik;
import com.example.naucnacentrala.model.NaucnaOblast;
import com.example.naucnacentrala.model.Role;
import com.example.naucnacentrala.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    public Korisnik findOneById(Integer id){
        return korisnikRepository.findOneById(id);
    }

    public List<Korisnik> findAll(){
        return korisnikRepository.findAll();
    }

    public Korisnik save(Korisnik korisnik){
        return korisnikRepository.save(korisnik);
    }

    public void remove(Integer id){
        korisnikRepository.deleteById(id);
    }

    public Korisnik findOneByUsername(String username) { return korisnikRepository.findOneByUsername(username); }

    public Korisnik findOneByEmail(String email) { return korisnikRepository.findOneByEmail(email); }

    public List<Korisnik> findAllByNaucneOblasti(List<NaucnaOblast> naucneOblasti, String tip){

        List<Korisnik> korisnici = new ArrayList<>();
        if(tip.equals("urednik")){
            korisnici = findAllUrednici();
        }else{
            korisnici = findAllRecenzenti();
        }

        List<Korisnik> returnList = new ArrayList<>();

        for(NaucnaOblast naucnaOblast: naucneOblasti){
            for(Korisnik korisnik: korisnici){
                if(returnList.contains(korisnik)) {
                    continue;
                }
                for(NaucnaOblast oblastKorisnika: korisnik.getNaucneOblasti()){
                    if(naucnaOblast.getNaziv().equals(oblastKorisnika.getNaziv())){
                        returnList.add(korisnik);
                        break;
                    }
                }
            }
        }
        return returnList;
    }

    public List<Korisnik> findAllUrednici(){
        List<Korisnik> urednici = new ArrayList<>();
        List<Korisnik> korisnici = korisnikRepository.findAll();

        for(Korisnik korisnik: korisnici){
            boolean isUrednik = false;
            for(Role role: korisnik.getRoles()){
                if(role.getName().equals("ROLE_UREDNIK")){
                    isUrednik = true;
                    break;
                }
            }
            if(isUrednik){
                urednici.add(korisnik);
            }
        }
        return urednici;
    }

    public List<Korisnik> findAllRecenzenti(){
        List<Korisnik> recenzenti = new ArrayList<>();
        List<Korisnik> korisnici = korisnikRepository.findAll();

        for(Korisnik korisnik: korisnici){
            boolean isRecenzent = false;
            for(Role role: korisnik.getRoles()){
                if(role.getName().equals("ROLE_RECENZENT")){
                    isRecenzent = true;
                    break;
                }
            }
            if(isRecenzent){
                recenzenti.add(korisnik);
            }
        }
        return recenzenti;
    }
}
