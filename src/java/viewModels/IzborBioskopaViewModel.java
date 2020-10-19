
package viewModels;
import beans.*;
import java.util.*;

public class IzborBioskopaViewModel {
    private Film film;
    private ArrayList<Bioskop> bioskopi;
    
    public Film getFilm(){
        return this.film;
    }
    public void setFilm(Film film){
        this.film=film;
    }
    public ArrayList<Bioskop> getBioskopi(){
        return this.bioskopi;
    } 
    public void setBioskopi(ArrayList<Bioskop> bioskopi){
        this.bioskopi=bioskopi;
    }
}
