package service;

import model.Colaborador;
import model.RegistroColheita;

public class Diferencial {
// Método estático para exibir o Ranking Top 5 e o mais eficiente
    public static void exibirRankingColaboradores(Colaborador[] equipe, int qtdColaboradores, RegistroColheita[] colheitas, int qtdColheitas) {

        // Validação: não tem nada para ranquear
        if (qtdColaboradores == 0 || qtdColheitas == 0) {
            System.out.println("\n[AVISO] Não há dados suficientes para gerar o ranking.");
            return; // Botão de ejetar
        }

        // ========================================================
        // PASSO 1: Somar os litros totais de cada colaborador
        // ========================================================

        double[] totalLitrosPorColaborador = new double[qtdColaboradores];
        int[] quantidadeLancamentos = new int[qtdColaboradores];

        for (int i = 0; i < qtdColheitas; i++) {
            // Acesso direto ao atributo, sem o método 'get'
            int matriculaDoRegistro = colheitas[i].matriculaFuncionario;

            // Descobre qual índice esse colaborador ocupa no vetor equipe[]
            for (int j = 0; j < qtdColaboradores; j++) {
                if (equipe[j].matricula == matriculaDoRegistro) {
                    totalLitrosPorColaborador[j] += colheitas[i].quantidadeLitros;
                    quantidadeLancamentos[j]++;
                    break; 
                }
            }
        }

        // ========================================================
        // PASSO 2: Ordenar para descobrir o Top 5
        // ========================================================

        // Guarda os índices na ordem que queremos (do maior para o menor)
        int limite = Math.min(5, qtdColaboradores); 
        int[] indicesOrdenados = new int[qtdColaboradores];

        for (int i = 0; i < qtdColaboradores; i++) {
            indicesOrdenados[i] = i;
        }

        // Algoritmo Selection Sort decrescente
        for (int i = 0; i < limite; i++) {
            int idxMaior = -1;

            for (int j = i; j < qtdColaboradores; j++) {
                if (idxMaior == -1 || totalLitrosPorColaborador[indicesOrdenados[j]] > totalLitrosPorColaborador[indicesOrdenados[idxMaior]]) {
                    idxMaior = j;
                }
            }

            // Troca o maior encontrado para a posição i
            int temp = indicesOrdenados[i];
            indicesOrdenados[i] = indicesOrdenados[idxMaior];
            indicesOrdenados[idxMaior] = temp;
        }

        // ========================================================
        // PASSO 3: Descobrir o funcionário mais eficiente
        // Critério: maior MÉDIA de litros por lançamento
        // ========================================================

        int indiceEficiente = -1;
        double maiorMedia = -1.0;

        for (int i = 0; i < qtdColaboradores; i++) {
            if (quantidadeLancamentos[i] > 0) {
                double media = totalLitrosPorColaborador[i] / quantidadeLancamentos[i];
                if (media > maiorMedia) {
                    maiorMedia = media;
                    indiceEficiente = i;
                }
            }
        }

        // ========================================================
        // PASSO 4: Exibir o relatório formatado
        // ========================================================

        System.out.println("\n=====================================================");
        System.out.println("           RANKING TOP 5 - QUINZENA ATUAL");
        System.out.println("=====================================================");

        String[] medalhas = {"1º [Ouro]  ", "2º [Prata] ", "3º [Bronze]", "4º         ", "5º         "};

        for (int pos = 0; pos < limite; pos++) {
            int idx = indicesOrdenados[pos];

            // Evita exibir colaboradores que não tiveram nenhuma colheita
            if (totalLitrosPorColaborador[idx] == 0) {
                System.out.println(medalhas[pos] + " (Sem registros na quinzena)");
                continue;
            }

            double mediaDiaria = 0.0;
            if (quantidadeLancamentos[idx] > 0) {
                mediaDiaria = totalLitrosPorColaborador[idx] / quantidadeLancamentos[idx];
            }

            System.out.println("-----------------------------------------------------");
            // Acesso direto aos atributos nome e matricula
            System.out.println(medalhas[pos] + " " + equipe[idx].nome + "  (Matrícula: " + equipe[idx].matricula + ")");
            System.out.printf("    Volume Colhido:   %.1f Litros%n",  totalLitrosPorColaborador[idx]);
            System.out.printf("    Qtd. Lançamentos: %d viagens%n",   quantidadeLancamentos[idx]);
            System.out.printf("    Média por viagem: %.2f Litros/viagem%n", mediaDiaria);
        }

        System.out.println("=====================================================");

        // Destaque: colaborador mais eficiente
        if (indiceEficiente != -1) {
            System.out.println("\n      ⭐ MAIS EFICIENTE DA QUINZENA ⭐");
            System.out.println("  " + equipe[indiceEficiente].nome + " (Matrícula: " + equipe[indiceEficiente].matricula + ")");
            System.out.printf("  Média absoluta: %.2f Litros por viagem (%d viagens no total)%n",
                    maiorMedia, quantidadeLancamentos[indiceEficiente]);
            System.out.println("=====================================================\n");
        }
    }
}
