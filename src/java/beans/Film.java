package beans;

public class Film {
    private int filmId;
    private String filmNaziv;
    private String filmGodina;
    private String filmZanr;
    private String filmPoster;
    private String filmReziser;
    
    public int getFilmId(){
        return this.filmId;
    }
    public void setFilmId(int id){
        this.filmId=id;
    }
    public String getFilmNaziv(){
        return this.filmNaziv;
    }
    public void setFilmNaziv(String naziv){
        this.filmNaziv=naziv;
    }
    public String getFilmGodina(){
        return this.filmGodina;
    }
    public void setFilmGodina(String godina){
        this.filmGodina=godina;
    }
    public String getFilmZanr(){
        return this.filmZanr;
    }
    public void setFilmZanr(String zanr){
        this.filmZanr=zanr;
    }
    public String getFilmPoster(){
        return this.filmPoster;
    }
    public void setFilmPoster(String poster){
        this.filmPoster=poster;
    }
    public String getFilmReziser(){
        return this.filmReziser;
    }
    public void setFilmReziser(String reziser){
        this.filmReziser=reziser;
    }
}
