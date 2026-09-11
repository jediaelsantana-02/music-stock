package com.desafio.java01;

import java.util.Objects;
import java.util.Scanner;

public class MusicStock {
    private static final int CAPACIDADE_MAXIMA_PRODUTOS = 100;
    private static final Scanner scanner = new Scanner(System.in);

    private static int[] codigos = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static String[] nomes = new String[CAPACIDADE_MAXIMA_PRODUTOS];
    private static double[] precos = new double[CAPACIDADE_MAXIMA_PRODUTOS];
    private static int[] quantidades = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static int indiceAtual = 0;


    public static void main(String[] args) {
        String opcao;

        do {
            opcao = menuPrincipal().toUpperCase().trim();

            switch (opcao) {
                case String s when s.equals("1") || s.contains("CADASTRAR") -> {
                    System.out.println("-> Executando: Cadastrar instrumento");
                    cadastrarInstrumento();
                }
                case String s when s.equals("2") || s.contains("LISTAR") -> {
                    System.out.println("-> Executando: Listar instrumentos");
                }
                case String s when s.equals("3") || s.contains("BUSCAR") -> {
                    System.out.println("-> Executando: Buscar instrumento");
                }
                case String s when s.equals("4") || s.contains("VENDA") -> {
                    System.out.println("-> Executando: Realizar venda");
                }
                case String s when s.equals("5") || s.contains("REPOR") || s.contains("ESTOQUE") && !s.contains("RELATÓRIO") -> {
                    System.out.println("-> Executando: Repor estoque");
                }
                case String s when s.equals("6") || s.contains("RELATÓRIO") || s.contains("RELATORIO") -> {
                    System.out.println("-> Executando: Relatório do estoque");
                }
                case String s when s.equals("7") || s.contains("MOVIMENTAÇÕES") -> {
                    System.out.println("-> Executando: MOVIMENTAÇÕES");
                }
                case String s when s.equals("0") || s.contains("SAIR") -> {
                    System.out.println("-> Saindo do sistema...");
                    scanner.close();
                    System.exit(0);
                }
                case null -> {
                    System.out.println("Entrada inválida (nula)!");
                }
                default -> {
                    System.out.println("Opção incorreta! Tente novamente.");
                }
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

    public static void cadastrarInstrumento() {
        System.out.println("=== Cadastro de Instrumento Musical ===");
        System.out.println("Informe os dados do produto:");

        try {
            verificarCapacidadeMaximaProduto();

            System.out.println("Código do produto:");
            int codigo = Integer.parseInt(scanner.nextLine());
            validarCodigoProduto(codigo);
            verificarCodigoProdutoDuplicado(codigo);

            System.out.println("Nome do produto:");
            String nome = scanner.nextLine().trim();
            validarNomeProduto(nome);

            System.out.println("Preço do produto:");
            double preco = Double.parseDouble(scanner.nextLine());
            validarPreco(preco);

            System.out.println("Quantidade inicial em estoque:");
            int quantidade = Integer.parseInt(scanner.nextLine());
            validarQuantidade(quantidade);

            inserirProduto(codigo, nome, preco, quantidade);

        } catch (NumberFormatException ex) {
            System.out.println("❌ Entrada inválida. Insira apenas números.");
        } catch (IllegalArgumentException ex) {
            System.err.println("Não é possível cadastrar o produto. " + ex.getMessage());
        }

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

    private static void inserirProduto(int codigo, String nome, double preco, int quantidade) {
        codigos[indiceAtual] = codigo;
        nomes[indiceAtual] = nome;
        precos[indiceAtual] = preco;
        quantidades[indiceAtual] = quantidade;
        indiceAtual++;
    }
}
