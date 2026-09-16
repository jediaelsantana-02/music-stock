package com.desafio.java01;

import com.desafio.java01.exception.*;

import java.util.Scanner;

public class MusicStock {

    // ==============================
    // CONSTANTES
    // ==============================
    private static final int MAXIMA_REPETICAO_CADASTRO_CAMPO = 3;
    private static final int CAPACIDADE_MAXIMA_PRODUTOS = 100;
    private static final int QUANTIDADE_MAXIMA_MOVIMENTACAO = 100;
    private static final String FORMATO_CABECALHO = "| %-6s | %-25s | %-10s | %-10s |%n";
    private static final String FORMATO_DADOS = "| %-6d | %-25s | %10.2f | %10d |%n";
    private static final String[] MENU_PRINCIPAL = {
            "Sair",
            "Cadastrar instrumento",
            "Listar instrumentos",
            "Buscar instrumento",
            "Realizar venda",
            "Repor estoque",
            "Relatório do estoque",
            "Movimentações"
    };


    // ==============================
    // DADOS DO SISTEMA
    // ==============================
    private static final Scanner scanner = new Scanner(System.in);

    private static final int[] codigos = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static final String[] nomes = new String[CAPACIDADE_MAXIMA_PRODUTOS];
    private static final double[] precos = new double[CAPACIDADE_MAXIMA_PRODUTOS];
    private static final int[] quantidades = new int[CAPACIDADE_MAXIMA_PRODUTOS];
    private static final String[][] movimentacoesValores = new String[CAPACIDADE_MAXIMA_PRODUTOS][QUANTIDADE_MAXIMA_MOVIMENTACAO];

    private static int indiceAtualProduto = 0;


    private static void carregarDadosIniciais() {
        inserirProduto(1, "Bateria Odery", 5000.00, 9);
        inserirProduto(2, "Bateria Yamaha", 10000.00, 2);
        inserirProduto(3, "Teclado roland xps10", 4500, 12);
        inserirProduto(4, "Guitarra", 500.00, 7);
        inserirProduto(5, "Contrabaixo", 1600.00, 4);
    }

    public static void main(String[] args) {

        carregarDadosIniciais();
        menuInicial();
    }


    // ==============================
    // MENU DO SISTEMA
    // ==============================
    public static void menuInicial() {
        int opcao;

        do {
            opcao = menuPrincipal();

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> listarProdutos();
                case 3 -> buscarProduto();
                case 4 -> realizarVenda();
                case 5 -> reporEstoque();
                case 6 -> relatorioEstoque();
                case 7 -> consultarMovimentacao();
                case 0 -> System.out.println("⏻ Encerrando sistema...");
                default -> System.out.println("❌ Opção incorreta! Tente novamente.");
            }

        } while (opcao > 0);

        scanner.close();
    }

    private static int menuPrincipal() {

        System.out.println("--------------------------------------------------------------");
        System.out.println("OPÇÃO    DESCRIÇÃO");

        for (int i = 1; i < MENU_PRINCIPAL.length; i++) {
            System.out.printf("(%d) — %s%n", i, MENU_PRINCIPAL[i]);
        }

        System.out.printf("(0) - %s%n", MENU_PRINCIPAL[0]);
        System.out.println("--------------------------------------------------------------");

        return obterOpcaoMenuInicial();
    }

    // ==============================
    // FUNCIONALIDADES
    // ==============================
    private static void listarProdutos() {

        System.out.println("+--------+---------------------------+------------+------------+");
        System.out.printf(FORMATO_CABECALHO, "Código", "Nome", "Preço (R$)", "Quantidade");
        System.out.println("+--------+---------------------------+------------+------------+");

        for (int i = 0; i < indiceAtualProduto; i++) {
            System.out.printf(FORMATO_DADOS, codigos[i], nomes[i], precos[i], quantidades[i]);
        }

        System.out.println("+--------+---------------------------+------------+------------+");
    }

    private static void buscarProduto() {

        try {
            int codigo = obterCodigoParaBusca();
            int indice = buscarIndiceProdutoPorCodigo(codigo);

            System.out.println("+--------+---------------------------+------------+------------+");
            System.out.printf(FORMATO_CABECALHO, "Código", "Nome", "Preço (R$)", "Quantidade");
            System.out.println("+--------+---------------------------+------------+------------+");

            System.out.printf(FORMATO_DADOS, codigos[indice], nomes[indice], precos[indice], quantidades[indice]);

            System.out.println("+--------+---------------------------+------------+------------+");

        } catch (ProdutoNaoEncontradoException ex) {
            System.out.println("\uD83D\uDD0D " + ex.getMessage());
        } catch (LimiteTentativasException ex) {
            System.out.println("❌ " + ex.getMessage());
        }

    }


    // ==============================
    // ENTRADA DE DADOS
    // ==============================
    private static int obterOpcaoMenuInicial() {
        while (true) {
            try {
                System.out.println("Escolha uma das operação");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Opção incorreta. Digite apenas números.");
            }
        }
    }

    private static int obterCodigo(boolean isCadastrar) {
        int tentativas = 0;

        while (true) {

            try {
                validarLimiteTentativas(tentativas);

                System.out.println("Código do produto:");
                int codigo = Integer.parseInt(scanner.nextLine());

                validarCodigoMaiorQueZero(codigo);

                if (isCadastrar) {
                    validarCodigoDuplicado(codigo);
                }

                return codigo;
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite apenas números.");
                tentativas++;
            } catch (CodigoProdutoInvalidoException e) {
                System.out.println("❌ " + e.getMessage());
                tentativas++;
            } catch (CodigoDuplicadoException e) {
                System.out.println(e.getMessage());
            } catch (LimiteTentativasException e) {
                throw e;
            }

        }
    }

    private static int obterCodigoParaBusca() {
        return obterCodigo(false);
    }

    private static int obterCodigoParaCadastro() {
        return obterCodigo(true);
    }

    // ==============================
    // VALIDAÇÕES
    // ==============================
    private static void validarCodigoMaiorQueZero(int codigoProduto) {
        if (codigoProduto < 1) {
            throw new CodigoProdutoInvalidoException("Código do produto deve ser maior que zero.");
        }
    }

    private static void validarCodigoDuplicado(int novoCodigoProduto) {
        for (int i = 0; i < indiceAtualProduto; i++) {
            if (novoCodigoProduto == codigos[i]) {
                throw new CodigoDuplicadoException("❌ Código do produto já está em uso.");
            }
        }
    }

    private static void validarLimiteTentativas(int repeticao) {
        if (repeticao >= MAXIMA_REPETICAO_CADASTRO_CAMPO) {
            throw new LimiteTentativasException("Você excedeu o número máximo de tentativas.");
        }
    }

    private static int buscarIndiceProdutoPorCodigo(int codigo) {

        for (int i = 0; i < indiceAtualProduto; i++) {
            if (codigo == codigos[i]) {
                return i;
            }
        }

        throw new ProdutoNaoEncontradoException("Produto não cadastrado.");
    }


    private static void cadastrarProduto() {
        System.out.println("=== Cadastro de Instrumento Musical ===");
        System.out.println("Informe os dados do produto:");

        try {
            int codigo = obterCodigo(true);
            String nome = obterNome();
            double preco = obterPreco();
            int quantidade = obterQuantidadeCadastro();

            inserirProduto(codigo, nome, preco, quantidade);

            System.out.println("✅ Produto cadastrado com sucesso!");

        } catch (LimiteTentativasException ex) {
            System.out.println("❌ Cadastro cancelado: " + ex.getMessage());
        }

    }


    private static void realizarVenda() {

        try {
            System.out.println("Código do produto:");

            int codigo = Integer.parseInt(scanner.nextLine());
            validarCodigoMaiorQueZero(codigo);

            int indice = buscarIndiceProdutoPorCodigo(codigo);
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
            validarCodigoMaiorQueZero(codigo);

            int indice = buscarIndiceProdutoPorCodigo(codigo);
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
            validarCodigoMaiorQueZero(codigo);

            int indice = buscarIndiceProdutoPorCodigo(codigo);

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

        if (indiceAtualProduto == 0) {
            System.out.println("Nenhum produto cadastrado");
        } else {
            System.out.println("==== Resumo Geral ====");
            System.out.println("Quantidade de produtos cadastrados\t\t: " + indiceAtualProduto);
            System.out.println("Total de itens em estoque\t\t\t\t: " + getTotalItensEstoque());
            System.out.println("Valor total do estoque\t\t\t\t\t: R$ " + getValorTotalEstoque());
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


    }
// ----------------------------------------- IMPLEMENTACAO        -----------------------------------------


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

    private static double getValorTotalEstoque() {
        double valorTotal = 0;

        for (int i = 0; i < indiceAtualProduto; i++) {
            valorTotal += (quantidades[i] * precos[i]);
        }

        return valorTotal;
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
