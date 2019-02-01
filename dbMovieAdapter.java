package nton.is.best.apps;


import java.io.Serializable;

public class dbMovieAdapter {
    private String Sinopsis;
    private String Thumbnail;
    //private String Episode;
    private String TglNewEpisode;
    private String TglTayang;
   // private String Pemeran;
    private String AkhirEpisode;
    private String Judul;
    private String Media;
    private String Tujuan;
    private String Video;
    private String List;
    private String Genre;
   // private Arra Episode;

    public dbMovieAdapter(String sinopsis,String thumbnail, String tglnewepisode ,String tgltayang,String akhirepisode, String judul, String media, String tujuan,String video, String list,String negara, Long tahun, String kategori) {
        this.Sinopsis = sinopsis;
        this.Thumbnail = thumbnail;
        //this.Episode = episode;
        this.TglNewEpisode = tglnewepisode;
        this.TglTayang = tgltayang;
        //this.Pemeran = pemeran;
        this.AkhirEpisode = akhirepisode;
        this.Judul = judul;
        this.Media = media;
        this.Tujuan = tujuan;
        this.Video = video;
        this.List = list;


    }
    public dbMovieAdapter(){

    }
    public String getSinopsis() {
        return Sinopsis;
    }
    public void setSinopsis(String sinopsis) {
        this.Sinopsis = sinopsis;
    }

    //public String getEpisode() {      return Episode;    }
    // public void setEpisode(String episode) {      this.Episode = episode;    }

    public String getTglNewEpisode() {
        return TglNewEpisode;
    }
    public void setTglNewEpisode(String tglnewepisode) {
        this.TglNewEpisode = tglnewepisode;
    }

    public String getTglTayang() {
        return TglTayang;
    }
    public void setTglTayang(String tgltayang) {
        this.TglTayang = tgltayang;
    }

    //public String getPemeran() {  return Pemeran;  }
    //public void setPemeran(String pemeran) { this.Pemeran = pemeran; }

    public String getAkhirEpisode() {
        return AkhirEpisode;
    }
    public void setAkhirEpisode(String akhirepisode) {
        this.AkhirEpisode = akhirepisode;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        this.Judul = judul;
    }

    public String getMedia() {
        return Media;
    }

    public void setThumbnail(String thumbnail) {
        this.Thumbnail = thumbnail;
    }
    public String getThumbnail() {
        return Thumbnail;
    }

    public void setMedia(String media) {
        this.Media = media;
    }
    public String getTujuan() {
        return Tujuan;
    }

    public void setTujuan(String tujuan) {
        this.Tujuan = tujuan;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {this.Video = video;
    }
    public String getList() {
        return List;
    }

    public void setList(String list) {this.List = list;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        this.Genre = genre;
    }


}
