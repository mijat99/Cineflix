package beans;

import java.sql.*;

public class Projekcija {
    private int projekcijaId;
    private int filmId;
    private Date projekcijaDatum;
    private Time projekcijaVreme;
    private boolean projekcijaJe3D;
    private Sala sala;
    private Film film;
    
    public int getProjekcijaId(){
        return this.projekcijaId;
    }
    public void setProjekcijaId(int id){
        this.projekcijaId=id;
    }
    public Sala getSala(){
        return this.sala;
    }
    public void setSala(Sala sala){
        this.sala=sala;
    }
    public Film getFilm(){
        return this.film;
    }
    public void setFilm(Film film){
        this.film=film;
    }
    public int getFilmId(){
        return this.filmId;
    }
    public void setFilmId(int id){
        this.filmId=id;
    } 
    public Date getProjekcijaDatum(){
        return this.projekcijaDatum;
    }
    public void setProjekcijaDatum(Date datum){
        this.projekcijaDatum=datum;
    }
    public Time getProjekcijaVreme(){
        return this.projekcijaVreme;
    }
    public void setProjekcijaVreme(Time vreme){
        this.projekcijaVreme=vreme;
    }
    public boolean projekcijaJe3D(){
        return this.projekcijaJe3D;
    }
    public void setProjekcijaJe3D(boolean b)
    {
        this.projekcijaJe3D=b;
    }    
}
