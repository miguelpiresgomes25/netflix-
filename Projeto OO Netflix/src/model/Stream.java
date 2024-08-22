package model;

public class Stream {
    private String nome;
    private String link; // URL ou caminho para o stream

    public Stream(String nome, String link) {
        this.nome = nome;
        this.link = link;
    }

    public String getNome() {
        return nome;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "nome='" + nome + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
