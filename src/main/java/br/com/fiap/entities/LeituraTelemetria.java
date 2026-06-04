package br.com.fiap.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LeituraTelemetria {

    private int idLeitura;
    private int idEstacao;
    private LocalDateTime horarioLeitura;
    private double sst;
    private double waveHeight;
    private double wavePeriod;
    private double windSpeed;
    private double windDirection;
    private double earthquakeMagnitude;
    private double focalDepth;

    public LeituraTelemetria() {}

    public LeituraTelemetria(int idLeitura, int idEstacao, LocalDateTime horarioLeitura,
                              double sst, double waveHeight, double wavePeriod,
                              double windSpeed, double windDirection,
                              double earthquakeMagnitude, double focalDepth) {
        this.idLeitura = idLeitura;
        this.idEstacao = idEstacao;
        this.horarioLeitura = horarioLeitura;
        this.sst = sst;
        this.waveHeight = waveHeight;
        this.wavePeriod = wavePeriod;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.earthquakeMagnitude = earthquakeMagnitude;
        this.focalDepth = focalDepth;
    }

    public int getIdLeitura() { return idLeitura; }
    public void setIdLeitura(int idLeitura) { this.idLeitura = idLeitura; }

    public int getIdEstacao() { return idEstacao; }
    public void setIdEstacao(int idEstacao) { this.idEstacao = idEstacao; }

    public LocalDateTime getHorarioLeitura() { return horarioLeitura; }
    public void setHorarioLeitura(LocalDateTime horarioLeitura) { this.horarioLeitura = horarioLeitura; }

    public double getSst() { return sst; }
    public void setSst(double sst) { this.sst = sst; }

    public double getWaveHeight() { return waveHeight; }
    public void setWaveHeight(double waveHeight) { this.waveHeight = waveHeight; }

    public double getWavePeriod() { return wavePeriod; }
    public void setWavePeriod(double wavePeriod) { this.wavePeriod = wavePeriod; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public double getWindDirection() { return windDirection; }
    public void setWindDirection(double windDirection) { this.windDirection = windDirection; }

    public double getEarthquakeMagnitude() { return earthquakeMagnitude; }
    public void setEarthquakeMagnitude(double earthquakeMagnitude) { this.earthquakeMagnitude = earthquakeMagnitude; }

    public double getFocalDepth() { return focalDepth; }
    public void setFocalDepth(double focalDepth) { this.focalDepth = focalDepth; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Leitura #" + idLeitura + " | Estacao #" + idEstacao +
               " | " + (horarioLeitura != null ? horarioLeitura.format(fmt) : "-") +
               "\n  SST: " + sst + " C  |  Onda: " + waveHeight + " m / " + wavePeriod + " s" +
               "\n  Vento: " + windSpeed + " km/h @ " + windDirection + " graus" +
               "\n  Magnitude: " + earthquakeMagnitude + " Richter  |  Prof. Focal: " + focalDepth + " km";
    }
}
