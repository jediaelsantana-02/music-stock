package com.desafio.java01;

import com.desafio.java01.exception.LimiteMovimentacoes;
import com.desafio.java01.exception.LimiteTentativasException;
import com.desafio.java01.exception.ProdutoNaoEncontradoException;
import com.desafio.java01.exception.QuantidadeIndisponivelException;

import java.util.Scanner;
import java.util.TreeMap;

public class MusicStock {
    private static final int MAXIMA_REPETICAO_CADASTRO_CAMPO = 5;
    private static final int CAPACIDADE_MAXIMA_PRODUTOS = 100;
    private static final int QUANTIDADE_MAXIMA_MOVIMENTACAO = 100;
    private static final String FORMATO_CABECALHO = "| %-6s | %-25s | %-10s | %-10s |%n";
    private static final String FORMATO_DADOS = "| %-6d | %-25s | %-10.2f | %-10d |%n";
    private static final Scanner scanner = new Scanner(System.in);

    private static final int[] codigos = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static final String[] nomes = new String[CAPACIDADE_MAXIMA_PRODUTOS];
    private static final double[] precos = new double[CAPACIDADE_MAXIMA_PRODUTOS];
    private static final int[] quantidades = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static final String[][] movimentacoesValores = new String[CAPACIDADE_MAXIMA_PRODUTOS][QUANTIDADE_MAXIMA_MOVIMENTACAO];

    private static int indiceAtualProduto = 0;


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
                case "3" -> buscarProduto();
                case "4" -> realizarVenda();
                case "5" -> reporEstoque();
                case "6" -> relatorioEstoque();
                case "7" -> consultarMovimentacao();
                case "0" -> {
                    System.out.println("-> Saindo do sistema...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção incorreta! Tente novamente.");
            }

        } while (true);
    }

    // ----------------------------------------- EXIBICAO -----------------------------------------

    private static String menuPrincipal() {
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

    private static void cadastrarProtuto() {
        System.out.println("=== Cadastro de Instrumento Musical ===");
        System.out.println("Informe os dados do produto:");

        try {
            int codigo = obterCodigo();
            String nome = obterNome();
            double preco = obterPreco();
            int quantidade = obterQuantidadeCadastro();

            inserirProduto(codigo, nome, preco, quantidade);

            System.out.println("✅ Produto cadastrado com sucesso!");

        } catch (LimiteTentativasException ex) {
            System.out.println("❌ Cadastro cancelado: " + ex.getMessage());
        }

    }

    private static void listarProdutos() {

        System.out.println("+--------+---------------------------+------------+------------+");
        System.out.printf(FORMATO_CABECALHO, "CÓDIGO", "NOME", "PREÇO", "QUANTIDADE");
        System.out.println("+--------+---------------------------+------------+------------+");

        for (int i = 0; i < indiceAtualProduto; i++) {
            System.out.printf(FORMATO_DADOS, codigos[i], nomes[i], precos[i], quantidades[i]);
        }

        System.out.println("+--------+---------------------------+------------+------------+");
    }

    private static void buscarProduto() {

        try {
            System.out.println("Código do produto:");

            int codigo = Integer.parseInt(scanner.nextLine());
            validarCodigoProduto(codigo);

            int indice = buscarProdutoPorCodigo(codigo);

            System.out.println("+--------+---------------------------+------------+------------+");
            System.out.printf(FORMATO_CABECALHO, "CÓDIGO", "NOME", "PREÇO", "QUANTIDADE");
            System.out.println("+--------+---------------------------+------------+------------+");

            System.out.printf(FORMATO_DADOS, codigos[indice], nomes[indice], precos[indice], quantidades[indice]);

            System.out.println("+--------+---------------------------+------------+------------+");

        } catch (ProdutoNaoEncontradoException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void realizarVenda() {

        try {
            System.out.println("Código do produto:");

            int codigo = Integer.parseInt(scanner.nextLine());
            validarCodigoProduto(codigo);

            int indice = buscarProdutoPorCodigo(codigo);
            int quantidade = obterQuantidade();

            if (quantidade > quantidades[indice]) {
                throw new QuantidadeIndisponivelException("Quantidade maior que a disponível.");
            }

            double valorTotal = quantidade * precos[indice];

            efetivarVenda(indice, quantidade);


            System.out.println("+--------+---------------------------+------------+------------+");
            System.out.printf(FORMATO_CABECALHO, "CÓDIGO", "NOME", "VALOR TOTAL", "QUANTIDADE");
            System.out.println("+--------+---------------------------+------------+------------+");

            System.out.printf(FORMATO_DADOS, codigos[indice], nomes[indice], valorTotal, quantidade);

            System.out.println("+--------+---------------------------+------------+------------+");

            System.out.println("✅ Venda realizada com sucesso!");

        } catch (ProdutoNaoEncontradoException ex) {
            System.out.println(ex.getMessage());
        } catch (QuantidadeIndisponivelException ex) {
            System.out.println("Estoque insuficiente. " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Informação inválida.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Venda não realizada. " + ex.getMessage());
        }
    }

    private static void reporEstoque() {

        try {
            System.out.println("Código do produto:");

            int codigo = Integer.parseInt(scanner.nextLine());
            validarCodigoProduto(codigo);

            int indice = buscarProdutoPorCodigo(codigo);
            int quantidade = obterQuantidade();

            reporQuantidadeProduto(indice, quantidade);

            System.out.println("✅ Estoque atualizado com sucesso!");

        } catch (ProdutoNaoEncontradoException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Informação inválida.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Estoque não adicionado. " + ex.getMessage());
        }
    }

    private static void consultarMovimentacao() {

        try {
            System.out.println("Código do produto:");

            int codigo = Integer.parseInt(scanner.nextLine());
            validarCodigoProduto(codigo);

            int indice = buscarProdutoPorCodigo(codigo);

            System.out.println("+----------------+");
            System.out.printf("| %-14s |%n", "MOVIMENTAÇÕES");
            System.out.println("+----------------+");

            for (String movimentacao : movimentacoesValores[indice]) {

                if (movimentacao != null) {
                    System.out.printf("| %-14s |%n", movimentacao);
                }

            }

            System.out.println("+----------------+");

        } catch (ProdutoNaoEncontradoException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Informação inválida.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Consulta não realizada. " + ex.getMessage());
        }

    }

    private static void relatorioEstoque() {
        String linha = "+------+----------+-------------------------+------------+------------+------------------+";
        String cabecalho = "| %-4s | %-8s | %-23s | %-10s | %-10s | %-16s |%n";
        String formatoDados = "| %4d | %-8s | %-23s | %10.2f | %10d | %16.2f |%n";

        System.out.println("==== Resumo Geral ====");
        System.out.println("Quantidade de produtos cadastrados\t\t: " + indiceAtualProduto);
        System.out.println("Total de itens em estoque\t\t\t\t: " + getTotalItensEstoque());
        System.out.println("Valor total do estoque\t\t\t\t\t: " + getValorTotalEstoque());
        System.out.println("Produto de maior preço\t\t\t\t\t: " + getProdutoMaiorPreco());
        System.out.println("Produto de menor preço\t\t\t\t\t: " + getProdutoMenorPreco());
        System.out.println("Produto com menor quantidade disponível : " + getMenorQuantidade());

        System.out.println("==== Detalhamento dos Produtos ====");

        System.out.println(linha);

        System.out.printf(cabecalho, "Nº", "Código", "Nome", "Preço (R$)", "Quantidade", "Valor Total (R$)");

        System.out.println(linha);

        for (int i = 0; i < indiceAtualProduto; i++) {

            double valorTotal = precos[i] * quantidades[i];

            System.out.printf(formatoDados, i + 1, codigos[i], nomes[i], precos[i], quantidades[i], valorTotal);
        }

        System.out.println(linha);

    }
    // ----------------------------------------- IMPLEMENTACAO        -----------------------------------------

    private static int buscarProdutoPorCodigo(int codigo) {

        for (int i = 0; i < indiceAtualProduto; i++) {
            if (codigo == codigos[i]) {
                return i;
            }
        }

        throw new ProdutoNaoEncontradoException("Produto não cadastrado.");
    }

    private static int obterCodigo() {
        int tentativas = 0;

        while (true) {

            validarLimiteTentativas(tentativas);

            try {
                System.out.println("Código do produto:");

                int codigo = Integer.parseInt(scanner.nextLine());
                validarCodigoProduto(codigo);
                verificarCodigoProdutoDuplicado(codigo);

                return codigo;
            } catch (NumberFormatException ex) {
                System.out.println("❌ Entrada inválida. Insira apenas números.");
                tentativas++;
            } catch (IllegalArgumentException ex) {
                System.err.println("❌ " + ex.getMessage() + " Tentando novamente.");
                tentativas++;
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
                System.err.println("❌ " + ex.getMessage() + "Tentando novamente");
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

    private static int obterQuantidadeCadastro() {
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
                System.err.println("❌ " + ex.getMessage() + "Tentando novamente");
                interacao++;

            }
        }
    }

    private static int obterQuantidade() {
        System.out.println("Quantidade:");

        int quantidade = Integer.parseInt(scanner.nextLine());
        validarQuantidadeMaiorQueZero(quantidade);

        return quantidade;
    }

    private static void inserirProduto(int codigo, String nome, double preco, int quantidade) {
        verificarCapacidadeMaximaProduto();

        codigos[indiceAtualProduto] = codigo;
        nomes[indiceAtualProduto] = nome;
        precos[indiceAtualProduto] = preco;
        quantidades[indiceAtualProduto] = quantidade;

        indiceAtualProduto++;
    }

    private static void verificarCapacidadeMaximaProduto() {
        if (indiceAtualProduto >= CAPACIDADE_MAXIMA_PRODUTOS) {
            throw new IllegalArgumentException("Capacidade máxima de produtos foi atingida.");
        }
    }

    private static void verificarCodigoProdutoDuplicado(int novoCodigoProduto) {
        for (int i = 0; i < indiceAtualProduto; i++) {
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

    private static void validarQuantidadeMaiorQueZero(int quantidadeProduto) {
        if (quantidadeProduto <= 0) {
            throw new IllegalArgumentException("A quantidade precisa ser maior que zero.");
        }
    }

    private static void validarLimiteTentativas(int interacao) {
        if (interacao >= MAXIMA_REPETICAO_CADASTRO_CAMPO) {
            throw new LimiteTentativasException();
        }
    }

    private static void efetivarVenda(int indice, int quantidade) {
        registrarMovimentacao(indice, "-" + quantidade);
        quantidades[indice] -= quantidade;
    }

    private static void reporQuantidadeProduto(int indice, int quantidade) {
        registrarMovimentacao(indice, "+" + quantidade);
        quantidades[indice] += quantidade;
    }

    // -------------------------------------- Movimentacao ------------------------------
    private static void registrarMovimentacao(int indiceProduto, String movimentacao) {

        for (int i = 0; i < movimentacoesValores[indiceProduto].length; i++) {
            if (movimentacoesValores[indiceProduto][i] == null
                    || movimentacoesValores[indiceProduto][i].isBlank()) {
                movimentacoesValores[indiceProduto][i] = movimentacao;
                break;
            } else if (i == movimentacoesValores[indiceProduto].length - 1) {
                throw new LimiteMovimentacoes("Não é possível cadastrar novas movimentações.");
            }
        }

    }

    private static int getTotalItensEstoque() {
        int totalItens = 0;

        for (int i = 0; i < indiceAtualProduto; i++) {
            totalItens += quantidades[i];
        }

        return totalItens;
    }

    private static String getValorTotalEstoque() {
        double valorTotal = 0;

        for (int i = 0; i < indiceAtualProduto; i++) {
            valorTotal += (quantidades[i] * precos[i]);
        }

        return "R$ " + valorTotal;
    }

    private static String getProdutoMaiorPreco() {
        int indiceMaior = 0;

        for (int i = 1; i < indiceAtualProduto; i++) {
            if (precos[i] > precos[indiceMaior]) {
                indiceMaior = i;
            }
        }

        return nomes[indiceMaior] + " (R$ " + precos[indiceMaior] + ")";
    }

    private static String getProdutoMenorPreco() {
        int indiceMenor = 0;

        for (int i = 1; i < indiceAtualProduto; i++) {
            if (precos[i] < precos[indiceMenor]) {
                indiceMenor = i;
            }
        }

        return nomes[indiceMenor] + " (R$ " + precos[indiceMenor] + ")";
    }

    private static String getMenorQuantidade() {
        int indiceMenorQuantidade = 0;

        for (int i = 1; i < indiceAtualProduto; i++) {
            if (quantidades[i] < quantidades[indiceMenorQuantidade]) {
                indiceMenorQuantidade = i;
            }
        }

        return nomes[indiceMenorQuantidade] + " (" + quantidades[indiceMenorQuantidade] + " unidades)";
    }

// -------------------------------- Ultils --------------------------


}
