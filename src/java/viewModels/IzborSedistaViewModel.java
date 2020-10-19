package viewModels;
import java.util.*;
import beans.*;
public class IzborSedistaViewModel {
    private Projekcija projekcija;
    private ArrayList<Integer> kolone;
    private ArrayList<Integer> redovi;
    
    public Projekcija getProjekcija(){
        return this.projekcija;
    }
    public void setProjekcija(Projekcija projekcija){
        this.projekcija=projekcija;
    }
    public ArrayList<Integer> getKolone(){
        return this.kolone;
    }
    public void setKolone(ArrayList<Integer> kolone){
        this.kolone=kolone;
    }
    public ArrayList<Integer> getRedovi(){
        return this.redovi;
    }
    public void setRedovi(ArrayList<Integer> redovi){
        this.redovi=redovi;
    }
}
