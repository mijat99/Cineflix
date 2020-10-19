package beans;
public class Korisnik {
    private int korisnikId;
    private String korisnikUsername;
    private String korisnikPassword;
    private String korisnikRole;
    private int bioskopId;
    
    public int getKorisnikId(){
        return this.korisnikId;
    }
    public void setKorisnikId(int id){
        this.korisnikId=id;
    }
    public String getKorisnikUsername(){
        return this.korisnikUsername;
    }
    public void setKorisnikUsername(String username){
        this.korisnikUsername=username;
    }
    public String getKorisnikPassword(){
        return this.korisnikPassword;
    }
    public void setKorisnikPassword(String password){
        this.korisnikPassword=password;
    }
    public String getKorisnikRole(){
        return this.korisnikRole;
    }
    public void setKorisnikRole(String role){
        this.korisnikRole=role;
    }
    public int getBioskopId(){
        return this.bioskopId;
    }
    public void setBioskopId(int bioskopId){
        this.bioskopId=bioskopId;
    }
    
}
