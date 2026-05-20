package model;
import java.util.Scanner;

public class Frota {
    public String placa;
    public double capacidadeMax;

    // Método estático de fabricação que valida a duplicidade (Chave Primária)
    public static Frota cadastrarVeiculo(Scanner input, Frota[] frota, int qtdVeiculos) {
        Frota novoVeiculo = new Frota();

        System.out.println("\n=== NOVO CADASTRO DE VEÍCULO ===");

        // 1. Entrada e Validação da Placa (Chave Primária)
        boolean placaDuplicada;
        do {
            placaDuplicada = false; // Reseta a flag
            System.out.print("Digite a placa do veículo (ex: ABC1D23): ");
            // Padroniza para maiúsculas e remove espaços extras
            novoVeiculo.placa = input.nextLine().toUpperCase().trim();

            // Varre o vetor da frota até onde existem dados cadastrados
            for (int i = 0; i < qtdVeiculos; i++) {
                if (frota[i].placa.equals(novoVeiculo.placa)) {
                    placaDuplicada = true;
                    System.out.println("[ERRO] Esta placa já está cadastrada na frota!");
                    System.out.println("Por favor, digite uma placa diferente.\n");
                    break; // Interrompe o for ao achar a primeira duplicidade
                }
            }
        } while (placaDuplicada);

        // 2. Entrada e Validação da Capacidade Máxima
        do {
            System.out.print("Digite a capacidade máxima da carreta (em litros): ");
            novoVeiculo.capacidadeMax = input.nextDouble();
            input.nextLine(); // Limpeza crucial do buffer do teclado após ler double

            if (novoVeiculo.capacidadeMax <= 0) {
                System.out.println("[ERRO] A capacidade deve ser maior que zero!\n");
            }
        } while (novoVeiculo.capacidadeMax <= 0);

        System.out.println("-> Veículo de placa " + novoVeiculo.placa + " cadastrado com sucesso!");

        return novoVeiculo;
    }
}
