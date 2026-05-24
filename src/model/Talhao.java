package model;
import java.util.Scanner;

public class Talhao {
    public int idTalhao;
    public String nome; 
    public String variedaCafe;
    public double estimativaProducao;

    // Método estático de fabricação que valida a duplicidade do ID (Chave Primária)
    public static Talhao cadastrarTalhao(Scanner input, Talhao[] talhoes, int qtdTalhoes) {
        Talhao novoTalhao = new Talhao();

        System.out.println("\n=== NOVO CADASTRO DE TALHÃO ===");

        // 1. Entrada e Validação do ID do Talhão (Chave Primária com Proteção de Tipo)
        boolean idDuplicado;
        do {
            idDuplicado = false; // Reseta a flag
            System.out.print("Digite o código/ID do talhão (apenas números): ");
            
            try {
                novoTalhao.idTalhao = Integer.parseInt(input.nextLine());

                // Varre o vetor de talhões até onde existem dados cadastrados (Busca Linear de Duplicidade)
                for (int i = 0; i < qtdTalhoes; i++) {
                    if (talhoes[i].idTalhao == novoTalhao.idTalhao) {
                        idDuplicado = true;
                        System.out.println("[ERRO] Este código de talhão já está cadastrado (" + talhoes[i].nome + ")!");
                        System.out.println("Por favor, digite um código diferente.\n");
                        break; // Interrompe o for ao achar a primeira duplicidade
                    }
                }

            } catch (NumberFormatException e) {
                // Captura o erro caso o usuário digite letras, espaços ou símbolos
                System.out.println("[ERRO] Entrada inválida! Digite apenas números inteiros (Ex: 1, 2, 10).\n");
                idDuplicado = true; // Força a repetição do laço do-while
            }

        } while (idDuplicado);

        // 2. Entrada do Nome do Talhão
        System.out.print("Digite o nome/identificação do talhão: ");
        novoTalhao.nome = input.nextLine().trim();

        // 3. Entrada da Variedade de Café
        System.out.print("Digite a variedade do café (ex: Catuaí, Mundo Novo): ");
        novoTalhao.variedaCafe = input.nextLine().trim();

        // 4. Entrada e Validação da Estimativa de Produção com Proteção contra Erros de Tipo
        do {
            System.out.print("Digite a estimativa de produção (em litros): ");
            try {
                // Lê a linha inteira como texto e tenta converter para double
                novoTalhao.estimativaProducao = Double.parseDouble(input.nextLine());

                if (novoTalhao.estimativaProducao <= 0) {
                    System.out.println("[ERRO] A estimativa de produção deve ser maior que zero!\n");
                }
                
            } catch (NumberFormatException e) {
                // Captura o erro caso o usuário digite letras, símbolos ou use vírgula em vez de ponto
                System.out.println("[ERRO] Entrada inválida! Digite um número decimal válido (Ex: 5000.00 ou 4500)!\n");
                
                // Força o valor para 0 para garantir que o laço do-while continue rodando
                novoTalhao.estimativaProducao = 0;
            }
        } while (novoTalhao.estimativaProducao <= 0);

        System.out.println("-> Talhão '" + novoTalhao.nome + "' cadastrado com sucesso!");

        return novoTalhao;
    }
}
