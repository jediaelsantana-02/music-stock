# 🎵 Music Stock

Sistema de gerenciamento de estoque de instrumentos musicais desenvolvido em **Java**, como exercício prático para treinamento dos fundamentos da linguagem.

O projeto simula um sistema de estoque utilizando **arrays unidimensionais paralelos** para armazenar os dados dos produtos e uma **matriz bidimensional** para registrar as movimentações de entrada e saída.

---

## 📋 Sobre o projeto

O **Music Stock** permite cadastrar instrumentos musicais, consultar produtos, realizar vendas, repor o estoque e consultar relatórios e movimentações.

O principal objetivo do projeto é praticar conceitos fundamentais de Java, como:

* Variáveis e constantes
* Arrays
* Matrizes
* Estruturas de repetição
* Estruturas condicionais
* `switch`
* Métodos
* Tratamento de exceções
* Exceções personalizadas
* Entrada de dados com `Scanner`
* Validação de dados
* Organização de código
* Manipulação de dados em memória

---

## 🚀 Funcionalidades

O sistema possui as seguintes opções:

### 0 - Sair

Encerra o sistema.

### 1 - Cadastrar instrumento

Permite cadastrar um novo instrumento informando:

* Código
* Nome
* Preço
* Quantidade inicial

O sistema realiza validações para impedir:

* Código inválido
* Código duplicado
* Nome vazio
* Preço inválido
* Quantidade inválida
* Excesso de tentativas
* Cadastro acima da capacidade máxima do sistema

---

### 2 - Listar instrumentos

Exibe todos os instrumentos cadastrados em formato de tabela.

Exemplo:

```text
| Código | Nome                      | Preço      | Quantidade  |
| 1      | Bateria Odery             | 5000.00    | 9           |
| 2      | Bateria Yamaha            | 10000.00   | 2           |
```

---

### 3 - Buscar instrumento

Permite localizar um instrumento através do seu código.

Caso o produto não seja encontrado, o sistema informa o usuário através de uma exceção personalizada.

---

### 4 - Realizar venda

Permite realizar uma saída de estoque.

O sistema:

1. Solicita o código do instrumento.
2. Localiza o produto.
3. Solicita a quantidade.
4. Verifica se existe quantidade suficiente em estoque.
5. Calcula o valor total da venda.
6. Atualiza a quantidade disponível.
7. Registra a movimentação de saída.

As vendas são registradas com o sinal `-`.

Exemplo:

```text
-2
```

---

### 5 - Repor estoque

Permite adicionar produtos ao estoque.

O sistema:

1. Solicita o código do instrumento.
2. Localiza o produto.
3. Solicita a quantidade.
4. Atualiza o estoque.
5. Registra a movimentação de entrada.

As reposições são registradas com o sinal `+`.

Exemplo:

```text
+5
```

---

### 6 - Relatório do estoque

Apresenta informações gerais sobre o estoque, incluindo:

* Quantidade de produtos cadastrados
* Total de itens em estoque
* Valor total do estoque
* Produto com maior preço
* Produto com menor preço
* Produto com menor quantidade disponível

Também apresenta os produtos cadastrados em formato de tabela.

---

### 7 - Movimentações

Permite consultar as movimentações de um determinado instrumento.

As movimentações são armazenadas em uma matriz bidimensional.

Exemplo:

```text
Código do produto: 1

Movimentações:
+5
-2
+3
-1
```

Onde:

* `+` representa entrada/reposição
* `-` representa saída/venda

---

## 🗃️ Estrutura dos dados

O projeto utiliza arrays paralelos para armazenar as informações dos instrumentos.

```text
codigos[]
nomes[]
precos[]
quantidades[]
```

Cada posição representa o mesmo produto nos diferentes arrays.

Por exemplo:

```text
Índice     Código       Nome             Preço       Quantidade
   0          1         Bateria Odery     5000.00          9
   1          2         Bateria Yamaha   10000.00          2
```

Também é utilizada uma matriz bidimensional para armazenar as movimentações:

```text
movimentacoesValores[][]
```

Cada linha da matriz representa um produto e cada coluna representa uma movimentação daquele produto.

---

## 🧠 Conceitos de Java praticados

Durante o desenvolvimento foram praticados diversos fundamentos da linguagem Java.

### Arrays

Utilização de arrays para armazenar os dados dos produtos.

### Matrizes

Utilização de uma matriz bidimensional para controlar as movimentações de estoque.

### Métodos

O sistema foi dividido em métodos responsáveis por diferentes operações, como:

* Cadastro
* Listagem
* Busca
* Venda
* Reposição
* Relatórios
* Validações
* Movimentações

### Estruturas de repetição

Utilização de:

* `for`
* `while`
* `do while`
* `for-each`

### Estruturas condicionais

Utilização de:

* `if`
* `else`
* `switch`

### Tratamento de exceções

Utilização de `try/catch` para tratar situações inválidas durante a execução do programa.

### Exceções personalizadas

O projeto possui exceções próprias para representar situações específicas do sistema.

Entre elas:

* `LimiteTentativasException`
* `ProdutoNaoEncontradoException`
* `QuantidadeIndisponivelException`
* `QuantidadeProdutoInvalidoException`
* `CapacidadeMaximaProdutosException`
* `LimiteMovimentacoesException`

---

## ✅ Validações

O sistema possui diversas validações para garantir a integridade dos dados.

### Código do produto

* Não pode ser menor ou igual a zero.
* Não pode ser duplicado.

### Nome

* Não pode ser vazio ou composto apenas por espaços.

### Preço

* Deve ser maior que zero.

### Quantidade

* Deve ser maior que zero.
* Durante uma venda, não pode ser maior que a quantidade disponível em estoque.

### Capacidade do estoque

O sistema possui capacidade máxima de:

```text
100 produtos
```

### Movimentações

Cada produto pode possuir até:

```text
100 movimentações
```

### Tentativas

Algumas entradas possuem limite de:

```text
3 tentativas
```

---

## 📊 Dados iniciais

Para facilitar os testes do sistema, alguns instrumentos são carregados inicialmente:

| Código | Produto              |        Preço | Quantidade |
| -----: | -------------------- | -----------: | ---------: |
|      1 | Bateria Odery        |  R$ 5.000,00 |          9 |
|      2 | Bateria Yamaha       | R$ 10.000,00 |          2 |
|      3 | Teclado Roland XPS10 |  R$ 4.500,00 |         12 |
|      4 | Guitarra             |    R$ 500,00 |          7 |
|      5 | Contrabaixo          |  R$ 1.600,00 |          4 |

Esses dados são utilizados principalmente para facilitar a execução e os testes das funcionalidades.

---

## 🖥️ Exemplo do menu

```text
================================
       MUSIC STOCK
================================

0 - Sair
1 - Cadastrar instrumento
2 - Listar instrumentos
3 - Buscar instrumento
4 - Realizar venda
5 - Repor estoque
6 - Relatório do estoque
7 - Movimentações

Escolha uma opção:
```

---

## 📁 Organização do código

Mesmo sendo um projeto desenvolvido em uma única classe como parte do exercício, o código foi organizado em seções para facilitar a leitura e manutenção.

```text
MusicStock
│
├── Constantes
├── Dados do sistema
├── Inicialização / Main
├── Menu do sistema
├── Funcionalidades
├── Relatórios
├── Entrada de dados
├── Validações
├── Operações sobre os dados
└── Movimentações
```

As exceções personalizadas ficam separadas em seu próprio pacote:

```text
com.desafio.java01.exception
```

---

## 🎯 Objetivo do projeto

O objetivo principal deste projeto é consolidar os conhecimentos fundamentais de Java através da construção de uma aplicação prática.

O projeto foi desenvolvido como um exercício de aprendizado, buscando aplicar conceitos de programação em uma situação próxima de um sistema real de controle de estoque.

---

## 🔮 Possíveis evoluções

Como exercício de fundamentos, o projeto atualmente utiliza arrays e uma única classe principal.

Como possíveis evoluções futuras, podem ser aplicados conceitos mais avançados, como:

* Criação de uma classe `Produto`
* Programação Orientada a Objetos
* Encapsulamento
* Utilização de `List` e outras Collections
* Separação de responsabilidades
* Persistência em banco de dados
* Testes automatizados com JUnit
* Criação de uma API REST
* Interface gráfica ou aplicação web
* Controle de usuários e permissões

---

## 🛠️ Tecnologias

* Java
* IntelliJ IDEA
* Maven
* Git
* GitHub

---

## 📌 Status do projeto

🚧 **Projeto desenvolvido para fins de estudo e prática de Java.**

O foco atual é consolidar os fundamentos da linguagem antes de evoluir a aplicação para uma arquitetura orientada a objetos.

---

## 👨‍💻 Autor

**Jediael Santana**

Projeto desenvolvido como parte dos estudos e desafios práticos de Java.
