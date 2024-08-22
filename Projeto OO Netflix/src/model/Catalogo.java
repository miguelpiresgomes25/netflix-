package model;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private List<Filme> filmes;
    private List<Serie> series;

    public Catalogo() {
        filmes = new ArrayList<>();
        series = new ArrayList<>();
        inicializarCatalogo();
    }

    private void inicializarCatalogo() {
        // Filmes de terror
        filmes.add(new Filme("O Chamado", "Terror"));
        filmes.add(new Filme("It: A Coisa", "Terror"));
        filmes.add(new Filme("O Exorcista", "Terror"));
        filmes.add(new Filme("Atividade Paranormal", "Terror"));
        filmes.add(new Filme("Psicose", "Terror"));

        // Filmes de comédia
        filmes.add(new Filme("Se Beber, Não Case", "Comédia"));
        filmes.add(new Filme("Superbad", "Comédia"));
        filmes.add(new Filme("A Mentira", "Comédia"));
        filmes.add(new Filme("As Branquelas", "Comédia"));
        filmes.add(new Filme("Escola de Rock", "Comédia"));

        // Filmes de romance
        filmes.add(new Filme("Orgulho e Preconceito", "Romance"));
        filmes.add(new Filme("Titanic", "Romance"));
        filmes.add(new Filme("A Culpa é das Estrelas", "Romance"));
        filmes.add(new Filme("Diário de uma Paixão", "Romance"));
        filmes.add(new Filme("500 Dias com Ela", "Romance"));

        // Filmes de ação
        filmes.add(new Filme("Duro de Matar", "Ação"));
        filmes.add(new Filme("Matrix", "Ação"));
        filmes.add(new Filme("Mad Max: Estrada da Fúria", "Ação"));
        filmes.add(new Filme("Velozes e Furiosos", "Ação"));
        filmes.add(new Filme("Homem de Ferro", "Ação"));

        // Séries de terror
        series.add(new Serie("Stranger Things", "Terror"));
        series.add(new Serie("American Horror Story", "Terror"));
        series.add(new Serie("The Haunting of Hill House", "Terror"));
        series.add(new Serie("Bates Motel", "Terror"));
        series.add(new Serie("The Walking Dead", "Terror"));

        // Séries de comédia
        series.add(new Serie("Friends", "Comédia"));
        series.add(new Serie("The Office", "Comédia"));
        series.add(new Serie("Brooklyn Nine-Nine", "Comédia"));
        series.add(new Serie("How I Met Your Mother", "Comédia"));
        series.add(new Serie("Parks and Recreation", "Comédia"));

        // Séries de romance
        series.add(new Serie("Outlander", "Romance"));
        series.add(new Serie("Grey's Anatomy", "Romance"));
        series.add(new Serie("The Crown", "Romance"));
        series.add(new Serie("Modern Love", "Romance"));
        series.add(new Serie("To All the Boys I've Loved Before", "Romance"));

        // Séries de ação
        series.add(new Serie("Breaking Bad", "Ação"));
        series.add(new Serie("Game of Thrones", "Ação"));
        series.add(new Serie("The Witcher", "Ação"));
        series.add(new Serie("Marvel's Daredevil", "Ação"));
        series.add(new Serie("Arrow", "Ação"));
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public Filme buscarFilmePorNome(String nome) {
        for (Filme filme : filmes) {
            if (filme.getNome().equalsIgnoreCase(nome)) {
                return filme;
            }
        }
        return null;
    }

    public Serie buscarSeriePorNome(String nome) {
        for (Serie serie : series) {
            if (serie.getNome().equalsIgnoreCase(nome)) {
                return serie;
            }
        }
        return null;
    }
}
