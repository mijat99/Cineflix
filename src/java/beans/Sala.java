package beans;

public class Sala {
    private int salaId;
    private String salaNaziv;
    private int salaRedovi;
    private int salaKolone;
    private int bioskopId;
    private Bioskop bioskop;
    
    public int getSalaId(){
        return this.salaId;
    }
    public void setSalaId(int id){
        this.salaId=id;
    }
    public String getSalaNaziv(){
        return this.salaNaziv;
    }
    public void setSalaNaziv(String salaNaziv){
        this.salaNaziv=salaNaziv;
    }
    public int getSalaRedovi(){
        return this.salaRedovi;
    }
    public void setSalaRedovi(int redovi){
        this.salaRedovi=redovi;
    }
    public int getSalaKolone(){
        return this.salaKolone;
    }
    public void setSalaKolone(int kolone){
        this.salaKolone=kolone;
    }
    public int getBioskopId(){
        return this.bioskopId;
    }
    public void setBioskopId(int id){
        this.bioskopId=id;
    }
    public Bioskop getBioskop(){
        return this.bioskop;
    }
    public void setBioskop(Bioskop bioskop){
        this.bioskop=bioskop;
    }
}
