package viewModels;
import beans.*;
import java.util.ArrayList;
public class dodajProjekcijuViewModel {
    private Projekcija projekcija;
    private ArrayList<Sala> sale;
    private ArrayList<Film> filmovi;
    public Projekcija getProjekcija(){
        return this.projekcija;
    }
    public void setProjekcija(Projekcija projekcija){
        this.projekcija=projekcija;
    }
    public ArrayList<Sala> getSale(){
        return this.sale;
    }
    public void setSale(ArrayList<Sala> sale){
        this.sale=sale;
    }
    public ArrayList<Film> getFilmovi(){
        return this.filmovi;
    }
    public void setFilmovi(ArrayList<Film> filmovi){
        this.filmovi=filmovi;
    }
}
