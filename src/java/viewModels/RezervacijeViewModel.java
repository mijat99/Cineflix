package viewModels;

import beans.Rezervacija;
import java.util.ArrayList;

public class RezervacijeViewModel {
    private ArrayList<Rezervacija> rezervacije;
    public ArrayList<Rezervacija> getRezervacije(){
        return this.rezervacije;
    }
    public void setRezervacije(ArrayList<Rezervacija> rezervacije){
        this.rezervacije=rezervacije;
    }
}
