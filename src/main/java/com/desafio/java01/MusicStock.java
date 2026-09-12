package com.desafio.java01;

import com.desafio.java01.exception.LimiteTentativasException;

import java.util.Scanner;

public class MusicStock {
    private static final int MAXIMA_REPETICAO_CADASTRO_CAMPO = 5;
    private static final int CAPACIDADE_MAXIMA_PRODUTOS = 100;
    private static final Scanner scanner = new Scanner(System.in);

    private static int[] codigos = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static String[] nomes = new String[CAPACIDADE_MAXIMA_PRODUTOS];
    private static double[] precos = new double[CAPACIDADE_MAXIMA_PRODUTOS];
    private static int[] quantidades = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static int indiceAtual = 0;


    private static void dbMusicStockInicial() {
        inserirProduto(1, "Bateria Odery", 5000.00, 9);
        inserirProduto(2, "Bateria Yamaha", 10000.00, 2);
        inserirProduto(3, "Teclado roland xps10", 4500, 12);
        inserirProduto(4, "Guitarra", 500.00, 7);
        inserirProduto(5, "Contrabaixo", 1600.00, 4);
    }

    public static void main(String[] args) {
        String opcao;

        dbMusicStockInicial();

        do {
            opcao = menuPrincipal().toUpperCase().trim();

            switch (opcao) {
                case "1" -> cadastrarProtuto();
                case "2" -> listarProdutos();
                case "3" -> System.out.println("-> Executando: Buscar instrumento");
                case "4" -> System.out.println("-> Executando: Realizar venda");
                case "5" -> System.out.println("-> Executando: Repor estoque");
                case "6" -> System.out.println("-> Executando: Relatório do estoque");
                case "7" -> System.out.println("-> Executando: MOVIMENTAÇÕES");
                case "0" -> {
                    System.out.println("-> Saindo do sistema...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção incorreta! Tente novamente.");
            }

        } while (true);
    }

    public static String menuPrincipal() {
        String[] menu = new String[8];
        menu[1] = "Cadastrar instrumento";
        menu[2] = "Listar instrumentos";
        menu[3] = "Buscar instrumento";
        menu[4] = "Realizar venda";
        menu[5] = "Repor estoque";
        menu[6] = "Relatório do estoque";
        menu[7] = "Movimentações";
        menu[0] = "Sair";

        System.out.println("--------------------------------------------------------------");

        System.out.println("OPÇÃO    DESCRIÇÃO ");
        for (int i = 1; i <= menu.length; i++) {
            int indice = i % menu.length;
            System.out.println("(" + indice + ")" + " — " + menu[indice]);
        }
        System.out.println("--------------------------------------------------------------");

        System.out.println("Digite uma das opções do MENU");
        return scanner.nextLine();
    }

    public static void cadastrarProtuto() {
        System.out.println("=== Cadastro de Instrumento Musical ===");
        System.out.println("Informe os dados do produto:");

        try {
            int codigo = obterCodigo();
            String nome = obterNome();
            double preco = obterPreco();
            int quantidade = obterQuantidade();

            inserirProduto(codigo, nome, preco, quantidade);

            System.out.println("✅ Produto cadastrado com sucesso!");

        } catch (LimiteTentativasException ex) {
            System.out.println("❌ Cadastro cancelado: " + ex.getMessage());
        }

    }

    private static void listarProdutos() {
        String formatoCabecalho = "| %-6s | %-25s | %-10s | %-10s |%n";
        String formatoDados = "| %-6d | %-25s | %-10.2f | %-10d |%n";

        System.out.println("+--------+---------------------------+------------+------------+");
        System.out.printf(formatoCabecalho, "CÓDIGO", "NOME", "PREÇO", "QUANTIDADE");
        System.out.println("+--------+---------------------------+------------+------------+");

        for (int i = 0; i < indiceAtual; i++) {
            System.out.printf(formatoDados, codigos[i], nomes[i], precos[i], quantidades[i]);
        }

        System.out.println("+--------+---------------------------+------------+------------+");
    }

    private static int obterCodigo() {
        int interacao = 0;

        while (true) {

            validarLimiteTentativas(interacao);

            try {
                System.out.println("Código do produto:");

                int codigo = Integer.parseInt(scanner.nextLine());
                validarCodigoProduto(codigo);
                verificarCodigoProdutoDuplicado(codigo);

                return codigo;
            } catch (NumberFormatException ex) {
                System.out.println("❌ Entrada inválida. Insira apenas números.");
                interacao++;
            } catch (IllegalArgumentException ex) {
                System.err.println("❌ " + ex.getMessage() + " Tentando novamente.");
                interacao++;
            }
        }
    }

    private static String obterNome() {
        int interacao = 0;

        while (true) {

            validarLimiteTentativas(interacao);

            try {
                System.out.println("Nome do produto:");
                String nomeProduto = scanner.nextLine().trim();
                validarNomeProduto(nomeProduto);

                return nomeProduto;

            } catch (IllegalArgumentException ex) {
                System.err.println("❌ " + ex.getMessage() + "Tentendo novamente");
                interacao++;
            }
        }
    }

    private static double obterPreco() {
        int interacao = 0;

        while (true) {

            validarLimiteTentativas(interacao);

            try {
                System.out.println("Preço do produto:");
                double preco = Double.parseDouble(scanner.nextLine());
                validarPreco(preco);

                return preco;

            } catch (NumberFormatException ex) {
                System.out.println("❌ Entrada inválida. Insira apenas números.");
                interacao++;
            } catch (IllegalArgumentException ex) {
                System.err.println("❌ " + ex.getMessage() + "Tentando novamente.");
                interacao++;
            }
        }
    }

    private static int obterQuantidade() {
        int interacao = 0;

        while (true) {

            validarLimiteTentativas(interacao);

            try {
                System.out.println("Quantidade inicial em estoque:");

                int quantidade = Integer.parseInt(scanner.nextLine());
                validarQuantidade(quantidade);

                return quantidade;

            } catch (NumberFormatException ex) {
                System.out.println("❌ Entrada inválida. Insira apenas números.");
                interacao++;

            } catch (IllegalArgumentException ex) {
                System.err.println("❌ " + ex.getMessage() + "Tentendo novamente");
                interacao++;

            }
        }
    }

    private static void inserirProduto(int codigo, String nome, double preco, int quantidade) {
        verificarCapacidadeMaximaProduto();

        codigos[indiceAtual] = codigo;
        nomes[indiceAtual] = nome;
        precos[indiceAtual] = preco;
        quantidades[indiceAtual] = quantidade;
        indiceAtual++;
    }

    private static void verificarCapacidadeMaximaProduto() {
        if (indiceAtual >= CAPACIDADE_MAXIMA_PRODUTOS) {
            throw new IllegalArgumentException("Capacidade máxima de produtos foi atingida.");
        }
    }

    private static void verificarCodigoProdutoDuplicado(int novoCodigoProduto) {
        for (int i = 0; i < indiceAtual; i++) {
            if (novoCodigoProduto == codigos[i]) {
                throw new IllegalArgumentException("Código do produto já está em uso.");
            }
        }
    }

    private static void validarCodigoProduto(int codigoProduto) {
        if (codigoProduto < 1) {
            throw new IllegalArgumentException("Código do produto deve ser maior que zero.");
        }
    }

    private static void validarNomeProduto(String nomeProduto) {
        if (nomeProduto.isBlank()) {
            throw new IllegalArgumentException("Nome está vazio.");
        }
    }

    private static void validarPreco(double precoProduto) {
        if (precoProduto <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero.");
        }
    }

    private static void validarQuantidade(int quantidadeProduto) {
        if (quantidadeProduto < 0) {
            throw new IllegalArgumentException("Quantidade inicial não pode ser negativa.");
        }
    }

    private static void validarLimiteTentativas(int interacao) {
        if (interacao >= MAXIMA_REPETICAO_CADASTRO_CAMPO) {
            throw new LimiteTentativasException();
        }
    }

}
