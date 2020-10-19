package viewModels;
import beans.*;
import java.util.*;

public class projekcijeViewModel {
    private Film film;
    private Bioskop bioskop;
    private ArrayList<Projekcija> projekcije;
    
    public Film getFilm(){
        return this.film;
    }
    public void setFilm(Film film){
        this.film=film;
    }
    public Bioskop getBioskop(){
        return this.bioskop;
    } 
    public void setBioskop(Bioskop bioskop){
        this.bioskop=bioskop;
    }    
    public ArrayList<Projekcija> getProjekcije(){
        return this.projekcije;
    }
    public void setProjekcije(ArrayList<Projekcija> projekcije){
        this.projekcije=projekcije;
    }
}
