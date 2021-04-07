package br.unb.cic.jfuzzer.beans.suas;

import java.util.Objects;

import lombok.Data;

@Data
public class Coordenada {

    private String latitude;
    private String Longitude;

    public Coordenada() {

    }

    public Coordenada(String latitude, String longitude) {
        super();
        this.latitude = latitude;
        Longitude = longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordenada other = (Coordenada) obj;
        return Objects.equals(Longitude, other.Longitude) && Objects.equals(latitude, other.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Longitude, latitude);
    }

    @Override
    public String toString() {
        return String.format("Coordenada [latitude=%s, Longitude=%s]", latitude, Longitude);
    }

}
