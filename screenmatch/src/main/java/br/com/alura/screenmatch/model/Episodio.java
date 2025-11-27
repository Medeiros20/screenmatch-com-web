package br.com.alura.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacaoEpisodio;
    private LocalDate dataDeLancamento;

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numeroEpisodio();
        try{
            this.avaliacaoEpisodio = Double.valueOf(dadosEpisodio.avaliacaoEpisodio());
        } catch (NumberFormatException ex) {
            this.avaliacaoEpisodio = 0.0;
        }
        try {
            this.dataDeLancamento = LocalDate.parse(dadosEpisodio.dataDeLancamento());
        } catch (DateTimeParseException ex) {
            this.dataDeLancamento = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvaliacaoEpisodio() {
        return avaliacaoEpisodio;
    }

    public void setAvaliacaoEpisodio(Double avaliacaoEpisodio) {
        this.avaliacaoEpisodio = avaliacaoEpisodio;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacaoEpisodio=" + avaliacaoEpisodio +
                ", dataDeLancamento=" + dataDeLancamento;
    }
}
