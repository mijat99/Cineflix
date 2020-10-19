package beans;

import java.util.*;

public class Rezervacija {
    private int rezervacijaId;
    private float rezervacijaCena;
    private int projekcijaId;
    private int korisnikId;
    private Projekcija projekcija;
    private ArrayList<Integer> redovi;
    private ArrayList<Integer> kolone;
    
    public int getRezervacijaId(){
        return this.rezervacijaId;
    }
    public void setRezervacijaId(int id){
        this.rezervacijaId=id;
    }
    public float getRezervacijaCena(){
        return this.rezervacijaCena;
    }
    public void setRezervacijaCena(float cena){
        this.rezervacijaCena=cena;
    }
    public int getProjekcijaId(){
        return this.projekcijaId;
    }
    public void setProjekcijaId(int id){
        this.projekcijaId=id;
    }public int getKorisnikId(){
        return this.korisnikId;
    }
    public void setKorisnikId(int id){
        this.korisnikId=id;
    }
    public Projekcija getProjekcija(){
        return this.projekcija;
    }
    public void setProjekcija(Projekcija projekcija){
        this.projekcija=projekcija;
    }
    public ArrayList<Integer> getRedovi(){
        return this.redovi;
    }
    public void setRedovi(ArrayList<Integer> redovi){
        this.redovi=redovi;
    }
    public ArrayList<Integer> getKolone(){
        return this.kolone;
    }
    public void setKolone(ArrayList<Integer> kolone){
        this.kolone=kolone;
    }
}
