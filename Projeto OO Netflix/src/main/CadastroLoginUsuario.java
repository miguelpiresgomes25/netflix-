package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import model.Catalogo;
import model.Filme;
import model.Serie;

public class CadastroLoginUsuario {
    private String[] usuarios;
    private String[] senhas;
    private String[] emails;
    private int tamanhoMaximo;
    private int totalUsuarios;
    private boolean usuarioLogado;
    private int indiceUsuarioLogado;
    private Catalogo catalogo;

    private static final String ARQUIVO_USUARIOS = "usuarios.txt";

    public CadastroLoginUsuario(int tamanhoMaximo) {
        this.setTamanhoMaximo(tamanhoMaximo);
        this.usuarios = new String[tamanhoMaximo];
        this.senhas = new String[tamanhoMaximo];
        this.emails = new String[tamanhoMaximo];
        this.totalUsuarios = 0;
        this.usuarioLogado = false;
        this.indiceUsuarioLogado = -1;
        this.catalogo = new Catalogo();

        carregarUsuariosDoArquivo();
    }

    public boolean cadastrarUsuario(String usuario, String senha, String email) {
        if (buscarUsuario(usuario) == -1) {
            usuarios[totalUsuarios] = usuario;
            senhas[totalUsuarios] = senha;
            emails[totalUsuarios] = email;
            totalUsuarios++;
            System.out.println("Usuário cadastrado com sucesso!");
            return true;
        } else {
            System.out.println("Erro: Usuário já cadastrado.");
            return false;
        }
    }

    public boolean fazerLogin(String usuarioOuEmail, String senha) {
        int indiceUsuario = buscarUsuario(usuarioOuEmail);

        if (indiceUsuario != -1 && senhas[indiceUsuario] != null && senhas[indiceUsuario].equals(senha)) {
            System.out.println("Login bem-sucedido!");
            this.usuarioLogado = true;
            this.indiceUsuarioLogado = indiceUsuario;
            return true;
        } else {
            System.out.println("Erro: Usuário ou senha incorretos. Tente novamente.");
            this.usuarioLogado = false;
            return false;
        }
    }

    public boolean excluirConta(String senha) {
        if (usuarioLogado) {
            if (senhas[indiceUsuarioLogado].equals(senha)) {
                for (int i = indiceUsuarioLogado; i < totalUsuarios - 1; i++) {
                    usuarios[i] = usuarios[i + 1];
                    senhas[i] = senhas[i + 1];
                    emails[i] = emails[i + 1];
                }

                totalUsuarios--;

                System.out.println("Conta excluída com sucesso!");
                salvarUsuariosEmArquivo();
                this.usuarioLogado = false;
                return true;
            } else {
                System.out.println("Erro: Senha incorreta. Não foi possível excluir a conta.");
                return false;
            }
        } else {
            System.out.println("Erro: Você precisa fazer login primeiro.");
            return false;
        }
    }

    public void alterarSenha(String senhaAtual, String novaSenha) {
        if (usuarioLogado) {
            if (senhas[indiceUsuarioLogado].equals(senhaAtual)) {
                senhas[indiceUsuarioLogado] = novaSenha;
                System.out.println("Senha alterada com sucesso!");
                salvarUsuariosEmArquivo();
            } else {
                System.out.println("Erro: Senha atual incorreta. Não foi possível alterar a senha.");
            }
        } else {
            System.out.println("Erro: Você precisa fazer login primeiro.");
        }
    }

    public void alterarNome(String novoNome) {
        if (usuarioLogado) {
            usuarios[indiceUsuarioLogado] = novoNome;
            System.out.println("Nome alterado com sucesso!");
            salvarUsuariosEmArquivo();
        } else {
            System.out.println("Erro: Você precisa fazer login primeiro.");
        }
    }

    public void alterarEmail(String novoEmail) {
        if (usuarioLogado) {
            emails[indiceUsuarioLogado] = novoEmail;
            System.out.println("E-mail alterado com sucesso!");
            salvarUsuariosEmArquivo();
        } else {
            System.out.println("Erro: Você precisa fazer login primeiro.");
        }
    }

    private int buscarUsuario(String usuarioOuEmail) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i].equals(usuarioOuEmail) || emails[i].equals(usuarioOuEmail)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isUsuarioLogado() {
        return usuarioLogado;
    }

    private void carregarUsuariosDoArquivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    cadastrarUsuario(partes[0], partes[1], partes[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários do arquivo: " + e.getMessage());
        }
    }

    private void salvarUsuariosEmArquivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (int i = 0; i < totalUsuarios; i++) {
                writer.println(usuarios[i] + "," + senhas[i] + "," + emails[i]);
            }
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private void exibirCatalogo() {
        List<Filme> filmes = catalogo.getFilmes();
        List<Serie> series = catalogo.getSeries();

        System.out.println("Escolha uma opção:");
        System.out.println("1. Apenas Filmes");
        System.out.println("2. Apenas Séries");
        System.out.println("3. Filmes e Séries");
        System.out.println("0. Voltar para o menu");

        Scanner scanner = new Scanner(System.in);
        int opcaoCatalogo = scanner.nextInt();
        scanner.nextLine();

        switch (opcaoCatalogo) {
            case 1:
                exibirFilmes(filmes);
                break;

            case 2:
                exibirSeries(series);
                break;

            case 3:
                System.out.println("Escolha uma opção:");
                System.out.println("1. Mostrar todos os filmes e séries");
                System.out.println("2. Filtrar por gênero");
                System.out.println("0. Voltar para o menu");

                int opcaoFilmesSeries = scanner.nextInt();
                scanner.nextLine();

                switch (opcaoFilmesSeries) {
                    case 1:
                        exibirFilmes(filmes, 1);  // 1 indica que são filmes
                        exibirSeries(series, filmes.size() + 1);  // O número inicial para séries começa após os filmes
                        break;

                    case 2:
                        System.out.print("Digite o gênero desejado: ");
                        String generoFiltro = scanner.nextLine().toLowerCase();

                        System.out.println("Filmes e séries disponíveis do gênero '" + generoFiltro + "':");

                        // Filtrar e exibir filmes
                        for (int i = 0; i < filmes.size(); i++) {
                            Filme filme = filmes.get(i);
                            if (filme.getGenero().toLowerCase().equals(generoFiltro)) {
                                System.out.println((i + 1) + ". Filme: " + filme.getNome() + " - " + filme.getGenero());
                            }
                        }

                        // Filtrar e exibir séries
                        for (int i = 0; i < series.size(); i++) {
                            Serie serie = series.get(i);
                            if (serie.getGenero().toLowerCase().equals(generoFiltro)) {
                                int numeroSerie = i + filmes.size() + 1;
                                System.out.println(numeroSerie + ". Série: " + serie.getNome() + " - " + serie.getGenero());
                            }
                        }
                        break;

                    case 0:
                        // Voltar para o menu principal
                        break;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
                break;

            case 0:
                // Voltar para o menu principal
                break;

            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    private void buscarFilmePorNome() {
        if (usuarioLogado) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o nome do filme: ");
            String nomeFilme = scanner.nextLine();
            Filme filmeEncontrado = catalogo.buscarFilmePorNome(nomeFilme);
            if (filmeEncontrado != null) {
                System.out.println("Filme encontrado: " + filmeEncontrado.getNome() + " - " + filmeEncontrado.getGenero());
            } else {
                System.out.println("Filme não encontrado.");
            }
        } else {
            System.out.println("Erro: Você precisa fazer login primeiro.");
        }
    }
    
    // Adicione esses métodos sobrecarregados para exibirFilmes e exibirSeries com um número inicial específico:
    private void exibirFilmes(List<Filme> filmes, int numeroInicial) {
        System.out.println("Filmes disponíveis:");
        for (int i = 0; i < filmes.size(); i++) {
            System.out.println((i + numeroInicial) + ". Filme: " + filmes.get(i).getNome() + " - " + filmes.get(i).getGenero());
        }
    }

    private void exibirSeries(List<Serie> series, int numeroInicial) {
        System.out.println("\nSéries disponíveis:");
        for (int i = 0; i < series.size(); i++) {
            System.out.println((i + numeroInicial) + ". Série: " + series.get(i).getNome() + " - " + series.get(i).getGenero());
        }
    }

    private void exibirFilmes(List<Filme> filmes) {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Mostrar todos os filmes");
        System.out.println("2. Filtrar por gênero");
        System.out.println("0. Voltar para o menu");

        Scanner scanner = new Scanner(System.in);
        int opcaoFilmes = scanner.nextInt();
        scanner.nextLine();

        switch (opcaoFilmes) {
            case 1:
                System.out.println("Filmes disponíveis:");
                for (int i = 0; i < filmes.size(); i++) {
                    System.out.println((i + 1) + ". Filme: " + filmes.get(i).getNome() + " - " + filmes.get(i).getGenero());
                }
                break;

            case 2:
                System.out.print("Digite o gênero desejado: ");
                String generoFiltro = scanner.nextLine().toLowerCase();

                System.out.println("Filmes disponíveis do gênero '" + generoFiltro + "':");
                for (int i = 0; i < filmes.size(); i++) {
                    Filme filme = filmes.get(i);
                    if (filme.getGenero().toLowerCase().equals(generoFiltro)) {
                        System.out.println((i + 1) + ". Filme: " + filme.getNome() + " - " + filme.getGenero());
                    }
                }
                break;

            case 0:
                // Voltar para o menu principal
                break;

            default:
                System.out.println("Opção inválida.");
                break;
        }
    }


    private void exibirSeries(List<Serie> series) {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Mostrar todas as séries");
        System.out.println("2. Filtrar por gênero");
        System.out.println("0. Voltar para o menu");

        Scanner scanner = new Scanner(System.in);
        int opcaoSeries = scanner.nextInt();
        scanner.nextLine();

        switch (opcaoSeries) {
            case 1:
                System.out.println("\nSéries disponíveis:");
                for (int i = 0; i < series.size(); i++) {
                    System.out.println((i + 1) + ". Série: " + series.get(i).getNome() + " - " + series.get(i).getGenero());
                }
                break;

            case 2:
                System.out.print("Digite o gênero desejado: ");
                String generoFiltro = scanner.nextLine().toLowerCase();

                System.out.println("Séries disponíveis do gênero '" + generoFiltro + "':");
                for (int i = 0; i < series.size(); i++) {
                    Serie serie = series.get(i);
                    if (serie.getGenero().toLowerCase().equals(generoFiltro)) {
                        System.out.println((i + 1) + ". Série: " + serie.getNome() + " - " + serie.getGenero());
                    }
                }
                break;

            case 0:
                // Voltar para o menu principal
                break;

            default:
                System.out.println("Opção inválida.");
                break;
        }
    }


    public void assistirConteudo(int escolha) {
        if (escolha > 0 && escolha <= catalogo.getFilmes().size() + catalogo.getSeries().size()) {
            String nomeConteudo;
            if (escolha <= catalogo.getFilmes().size()) {
                nomeConteudo = catalogo.getFilmes().get(escolha - 1).getNome();
            } else {
                int indiceSerie = escolha - catalogo.getFilmes().size() - 1;
                nomeConteudo = catalogo.getSeries().get(indiceSerie).getNome();
            }
            System.out.println("Assistindo " + nomeConteudo + "...");
        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcaoCatalogo;

        CadastroLoginUsuario cadastroLogin = new CadastroLoginUsuario(10);

        while (true) {
            if (!cadastroLogin.isUsuarioLogado()) {
                System.out.println("Escolha uma opção:");
                System.out.println("1. Cadastrar Usuário");
                System.out.println("2. Fazer Login");
                System.out.println("3. Sair");

                int opcao = 0;

                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("Erro: Opção inválida. Deve ser um número inteiro.");
                    scanner.nextLine();
                    continue;
                }

                switch (opcao) {
                case 1:
                    System.out.print("Digite o nome de usuário: ");
                    String novoUsuario = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    String novaSenha = scanner.nextLine();
                    System.out.print("Digite o e-mail: ");
                    String novoEmail = scanner.nextLine();
                    cadastroLogin.cadastrarUsuario(novoUsuario, novaSenha, novoEmail);
                    cadastroLogin.salvarUsuariosEmArquivo();
                    break;

                    case 2:
                        System.out.print("Digite o nome de usuário ou e-mail: ");
                        String usuarioLogin = scanner.nextLine();
                        System.out.print("Digite a senha: ");
                        String senhaLogin = scanner.nextLine();
                        cadastroLogin.fazerLogin(usuarioLogin, senhaLogin);
                        break;

                    case 3:
                        System.out.println("Saindo...");
                        cadastroLogin.salvarUsuariosEmArquivo();
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } else {
                System.out.println("Escolha uma opção:");
                System.out.println("1. Alterar Senha");
                System.out.println("2. Alterar Nome");
                System.out.println("3. Alterar E-mail");
                System.out.println("4. Excluir Conta");
                System.out.println("5. Exibir Catálogo");
                System.out.println("6. Sair");

                int opcao = 0;

                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("Erro: Opção inválida. Deve ser um número inteiro.");
                    scanner.nextLine();
                    continue;
                }

                switch (opcao) {
                    case 1:
                        System.out.print("Digite a senha atual: ");
                        String senhaAtual = scanner.nextLine();
                        System.out.print("Digite a nova senha: ");
                        String novaSenha = scanner.nextLine();
                        cadastroLogin.alterarSenha(senhaAtual, novaSenha);
                        break;

                    case 2:
                        System.out.print("Digite o novo nome: ");
                        String novoNome = scanner.nextLine();
                        cadastroLogin.alterarNome(novoNome);
                        break;

                    case 3:
                        System.out.print("Digite o novo e-mail: ");
                        String novoEmail = scanner.nextLine();
                        cadastroLogin.alterarEmail(novoEmail);
                        break;

                    case 4:
                        System.out.print("Digite a senha para confirmar a exclusão da conta: ");
                        String senhaExcluir = scanner.nextLine();
                        cadastroLogin.excluirConta(senhaExcluir);
                        break;

                    case 5:
                        cadastroLogin.exibirCatalogo();

                        System.out.println("Escolha uma opção:");
                        System.out.println("0. Voltar para o menu");
                        System.out.println("6. Sair");

                        opcaoCatalogo = scanner.nextInt();
                        scanner.nextLine();

                        if (opcaoCatalogo == 0) {
                            break;
                        } else if (opcaoCatalogo == 6) {
                            System.out.println("Saindo...");
                            cadastroLogin.salvarUsuariosEmArquivo();
                            scanner.close();
                            System.exit(0);
                        } else {
                            cadastroLogin.assistirConteudo(opcaoCatalogo);

                            // Exibir apenas as opções de sair ou voltar ao menu
                            System.out.println("Escolha uma opção:");
                            System.out.println("0. Voltar para o menu");
                            System.out.println("6. Sair");

                            // Consumir a entrada do usuário sem processar a opção
                            scanner.nextLine();
                            break;
                        }

                    case 6:
                        System.out.println("Saindo...");
                        cadastroLogin.salvarUsuariosEmArquivo();
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }
        }
    }

	public int getTamanhoMaximo() {
		return tamanhoMaximo;
	}

	public void setTamanhoMaximo(int tamanhoMaximo) {
		this.tamanhoMaximo = tamanhoMaximo;
	}
}
