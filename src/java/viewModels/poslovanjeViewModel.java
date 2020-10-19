package viewModels;
import beans.*;
import java.util.*;
public class poslovanjeViewModel {
    private Bioskop bioskop;
    private int brojRezervacija;
    private float ukupnoRezervacije;
    private ArrayList<Film> filmovi;
    ArrayList<Integer> brojProjekcijaPoFilmu=new ArrayList<>();
    ArrayList<Float> sumaPoFilmu=new ArrayList<>();
    
    public Bioskop getBioskop(){
        return this.bioskop;
    }
    public void setBioskop(Bioskop b){
        this.bioskop=b;
    }
    public int getBrojRezervacija(){
        return this.brojRezervacija;
    }
    public void setBrojRezervacija(int i){
        this.brojRezervacija=i;
    }
    public float getUkupnoRezervacije(){
        return this.ukupnoRezervacije;
    }
    public void setUkupnoRezervacije(float f){
        this.ukupnoRezervacije=f;
    }
    public ArrayList<Film> getFilmovi(){
        return this.filmovi;
    }
    public void setFilmovi(ArrayList<Film> filmovi){
        this.filmovi=filmovi;
    }
    public ArrayList<Integer> getBrojProjekcijaPoFilmu(){
        return this.brojProjekcijaPoFilmu;
    }
    public void setBrojProjekcijaPoFilmu(ArrayList<Integer> i){
        this.brojProjekcijaPoFilmu=i;
    }
    public ArrayList<Float> getSumaPoFilmu(){
        return this.sumaPoFilmu;
    }
    public void setSumaPoFilmu(ArrayList<Float> s){
        this.sumaPoFilmu=s;
    }
}
