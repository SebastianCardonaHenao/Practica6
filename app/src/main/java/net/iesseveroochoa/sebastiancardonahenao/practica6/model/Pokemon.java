package net.iesseveroochoa.sebastiancardonahenao.practica6.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = Pokemon.TABLE_NAME,
        indices = {@Index(value = {Pokemon.NOMBRE},unique = true)})
public class Pokemon implements Parcelable {
    public static final String TABLE_NAME="pokemon";
    public static final String ID= BaseColumns._ID;
    public static final String NOMBRE="nombre";
    public static final String URL="url";
    public static final String FECHA_COMPRA="fechacompra";
    public static final String urlIMAGEN="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name=ID)

    private int id;
    @ColumnInfo(name = NOMBRE)
    @NonNull
    //@SerializedName("name")//retrofit
    private String nombre;
    @ColumnInfo(name = URL)
    @NonNull
    private String url;
    @ColumnInfo(name = FECHA_COMPRA)
    @NonNull
    private Date fechaCompra;

    public Pokemon(@NonNull String nombre, @NonNull String url, Date fechaCompra) {
        this.nombre = nombre;
        this.url = url;
        this.fechaCompra = fechaCompra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(@NonNull Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
// OWN METHODS

    public String getFechaCompraFormatoLocal(){
        //para mostrar la fecha en formato del idioma del dispositivo
        if(fechaCompra!=null) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM,
                    Locale.getDefault());
            return df.format(fechaCompra);
        }else{//si es un pokemon de internet no tenemos fecha
            return "";
        }
    }


    // PARCELABLE METHODS

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.url);
        dest.writeLong(this.fechaCompra != null ? this.fechaCompra.getTime() : -1);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.nombre = source.readString();
        this.url = source.readString();
        long tmpFechaCompra = source.readLong();
        this.fechaCompra = tmpFechaCompra == -1 ? null : new Date(tmpFechaCompra);
    }

    public Pokemon() {
    }

    protected Pokemon(Parcel in) {
        this.id = in.readInt();
        this.nombre = in.readString();
        this.url = in.readString();
        long tmpFechaCompra = in.readLong();
        this.fechaCompra = tmpFechaCompra == -1 ? null : new Date(tmpFechaCompra);
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Parcelable.Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };
}
