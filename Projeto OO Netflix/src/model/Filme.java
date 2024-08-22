package model;

import java.util.ArrayList;
import java.util.List;

public class Filme {
    private String nome;
    private String genero;
    private List<Stream> streams;

    public Filme(String nome, String genero) {
        this.nome = nome;
        this.genero = genero;
        this.streams = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void addStream(Stream stream) {
        this.streams.add(stream);
    }
}
