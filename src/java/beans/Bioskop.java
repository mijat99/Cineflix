package beans;

public class Bioskop {
    private int bioskopId;
    private String bioskopNaziv;
    private String bioskopAdresa;
    private String bioskopTelefon;
    private String bioskopBanner;
    private float bioskopCena2D;
    private float bioskopCena3D;
    
    public int getBioskopId(){
        return this.bioskopId;
    }
    public void setBioskopId(int id){
        this.bioskopId=id;
    }
    public String getBioskopNaziv(){
        return this.bioskopNaziv;
    }
    public void setBioskopNaziv(String naziv){
        this.bioskopNaziv=naziv;
    }
    public String getBioskopAdresa(){
        return this.bioskopAdresa;
    }
    public void setBioskopAdresa(String adresa){
        this.bioskopAdresa=adresa;
    }
    public String getBioskopTelefon(){
        return this.bioskopTelefon;
    }
    public void setBioskopTelefon(String telefon){
        this.bioskopTelefon=telefon;
    }
    public String getBioskopBanner(){
        return this.bioskopBanner;
    }
    public void setBioskopBanner(String banner){
        this.bioskopBanner=banner;
    }
    public float getBioskopCena2D(){
        return this.bioskopCena2D;
    } 
    public void setBioskopCena2D(float bioskopCena2D){
        this.bioskopCena2D=bioskopCena2D;
    }
    public float getBioskopCena3D(){
        return this.bioskopCena3D;
    } 
    public void setBioskopCena3D(float bioskopCena3D){
        this.bioskopCena3D=bioskopCena3D;
    }
    
}
