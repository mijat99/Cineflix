package viewModels;
import beans.*;
import java.util.*; 

public class registerViewModel {
    private ArrayList<Bioskop> bioskopi;
    public ArrayList<Bioskop> getBioskopi(){
        return this.bioskopi;
    }
    public void setBioskopi(ArrayList<Bioskop> b){
        this.bioskopi=b;
    }
    
}
