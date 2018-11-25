package br.com.serttel.semaforosrecife;

public class Semaforo {

    private String utilizacao;
    private String localizacao1;
    private String localizacao2;
    private String funcionamento;
    private String sinalSonoro;
    private int semaforo;
    private String sinalizadorCiclista;
    private double longitude;
    private double latidude;
    private int _id;

    public Semaforo(String utilizacao, String localizacao1, String localizacao2,
                    String funcionamento, String sinalSonoro, int semaforo,
                    String sinalizadorCiclista, double longitude, double latidude, int _id){

        this.utilizacao = utilizacao;
        this.localizacao1 = localizacao1;
        this.localizacao2 = localizacao2;
        this.funcionamento = funcionamento;
        this.sinalSonoro = sinalSonoro;
        this.semaforo= semaforo;
        this.sinalizadorCiclista = sinalizadorCiclista;
        this.longitude = longitude;
        this.latidude = latidude;
        this._id = _id;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public double getLatidude() {
        return latidude;
    }

    public void setLatidude(double latidude) {
        this.latidude = latidude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSinalizadorCiclista() {
        return sinalizadorCiclista;
    }

    public void setSinalizadorCiclista(String sinalizadorCiclista) {
        this.sinalizadorCiclista = sinalizadorCiclista;
    }

    public int getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(int semaforo) {
        this.semaforo = semaforo;
    }

    public String getSinalSonoro() {
        return sinalSonoro;
    }

    public void setSinalSonoro(String sinalSonoro) {
        this.sinalSonoro = sinalSonoro;
    }

    public String getFuncionamento() {
        return funcionamento;
    }

    public void setFuncionamento(String funcionamento) {
        this.funcionamento = funcionamento;
    }

    public String getLocalizacao2() {
        return localizacao2;
    }

    public void setLocalizacao2(String localizacao2) {
        this.localizacao2 = localizacao2;
    }

    public String getLocalizacao1() {
        return localizacao1;
    }

    public void setLocalizacao1(String localizacao1) {
        this.localizacao1 = localizacao1;
    }

    public String getUtilizacao() {
        return utilizacao;
    }

    public void setUtilizacao(String utilizacao) {
        this.utilizacao = utilizacao;
    }
}
