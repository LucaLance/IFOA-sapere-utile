package gruppo3.ifoasapereutile.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Luca on 02/04/2015.
 */
public class Corso implements Parcelable{

    private String objectId;
    private String nomeCorso;
    private String descrizione;
    private String premessa;
    private String obiettivi;
    private String destinatari;
    private String metodologiaDidattica;
    private String durata;
    private String descrizioneDurata;

    public Corso() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getPremessa() {
        return premessa;
    }

    public void setPremessa(String premessa) {
        this.premessa = premessa;
    }

    public String getObiettivi() {
        return obiettivi;
    }

    public void setObiettivi(String obiettivi) {
        this.obiettivi = obiettivi;
    }

    public String getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(String destinatari) {
        this.destinatari = destinatari;
    }

    public String getMetodologiaDidattica() {
        return metodologiaDidattica;
    }

    public void setMetodologiaDidattica(String metodologiaDidattica) {
        this.metodologiaDidattica = metodologiaDidattica;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getDescrizioneDurata() {
        return descrizioneDurata;
    }

    public void setDescrizioneDurata(String descrizioneDurata) {
        this.descrizioneDurata = descrizioneDurata;
    }

    protected Corso(Parcel in) {
        objectId = in.readString();
        nomeCorso = in.readString();
        descrizione = in.readString();
        premessa = in.readString();
        obiettivi = in.readString();
        destinatari = in.readString();
        metodologiaDidattica = in.readString();
        durata = in.readString();
        descrizioneDurata = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(nomeCorso);
        dest.writeString(descrizione);
        dest.writeString(premessa);
        dest.writeString(obiettivi);
        dest.writeString(destinatari);
        dest.writeString(metodologiaDidattica);
        dest.writeString(durata);
        dest.writeString(descrizioneDurata);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Corso> CREATOR = new Parcelable.Creator<Corso>() {
        @Override
        public Corso createFromParcel(Parcel in) {
            return new Corso(in);
        }

        @Override
        public Corso[] newArray(int size) {
            return new Corso[size];
        }
    };

}
