package service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import model.Colaborador;
import model.Frota;
import model.RegistroColheita;
import model.Talhao;

public class ArquivoService {
    
    // Constante para o caminho da pasta, facilitando a manutenção
    private static final String PASTA = "banco_de_dados/";

    // Método principal para ser chamado logo no início da sua Main
    public static boolean inicializarSistemaDeArquivos() {
        File diretorio = new File("banco_de_dados");
        
        if (!diretorio.exists()) {
            boolean pastaCriada = diretorio.mkdir();
            
            if (pastaCriada) {
                System.out.println("-> Pasta 'banco_de_dados' criada com sucesso.");
            } else {
                System.out.println("[ERRO CRÍTICO] Falha ao criar a pasta 'banco_de_dados'!");
                System.out.println("Verifique as permissões. O sistema não pode continuar sem salvar.");
                
                return false; // Avisa a Main que a inicialização FALHOU
            }
        }

        // Tenta criar os arquivos
        criarArquivoSeNaoExistir(PASTA + "colaboradores.csv");
        criarArquivoSeNaoExistir(PASTA + "frota.csv");
        criarArquivoSeNaoExistir(PASTA + "talhoes.csv");
        criarArquivoSeNaoExistir(PASTA + "colheitas.csv");
        
        return true; // Avisa a Main que a inicialização foi um SUCESSO
    }

    // Método auxiliar privado para não repetir código
    private static void criarArquivoSeNaoExistir(String caminho) {
        try {
            File arquivo = new File(caminho);
            if (!arquivo.exists()) {
                arquivo.createNewFile(); // Cria o arquivo em branco
            }
        } catch (IOException e) {
            System.out.println("[ERRO CRÍTICO] Falha ao criar o arquivo: " + caminho);
            System.out.println("Detalhe: " + e.getMessage());
        }
    }

    public static void salvarTodosOsDados(
            Colaborador[] equipe, int qtdColab,
            Frota[] frota, int qtdFrota,
            Talhao[] talhoes, int qtdTalhoes,
            RegistroColheita[] colheitas, int qtdColheitas) {
        
        salvarColaboradores(equipe, qtdColab);
        salvarFrota(frota, qtdFrota);
        salvarTalhoes(talhoes, qtdTalhoes);
        salvarColheitas(colheitas, qtdColheitas);
    }

    private static void salvarColaboradores(Colaborador[] equipe, int qtd) {
        // O PrintWriter envelopa o FileWriter de forma elegante
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA + "colaboradores.csv", false))) {
            
            writer.println("Matricula;Nome;TipoContrato"); // Cabeçalho
            
            for (int i = 0; i < qtd; i++) {
                // O println já pula a linha automaticamente para o próximo registro
                writer.println(equipe[i].matricula + ";" + equipe[i].nome + ";" + equipe[i].tipoContrato);
            }
            
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível salvar os Colaboradores: " + e.getMessage());
        }
    }

    private static void salvarFrota(Frota[] frota, int qtd) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA + "frota.csv", false))) {
            
            writer.println("Placa;CapacidadeMax");
            
            for (int i = 0; i < qtd; i++) {
                writer.println(frota[i].placa + ";" + frota[i].capacidadeMax);
            }
            
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível salvar a Frota: " + e.getMessage());
        }
    }

    private static void salvarTalhoes(Talhao[] talhoes, int qtd) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA + "talhoes.csv", false))) {
            
            writer.println("ID;Nome;Variedade;EstimativaProducao");
            
            for (int i = 0; i < qtd; i++) {
                writer.println(talhoes[i].idTalhao + ";" + talhoes[i].nome + ";" + talhoes[i].variedaCafe + ";" + talhoes[i].estimativaProducao);
            }
            
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível salvar os Talhões: " + e.getMessage());
        }
    }

    private static void salvarColheitas(RegistroColheita[] colheitas, int qtd) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASTA + "colheitas.csv", false))) {
            
            writer.println("Data;MatriculaFuncionario;IDTalhao;PlacaTrator;Litros;Destino");
            
            for (int i = 0; i < qtd; i++) {
                writer.println(colheitas[i].data + ";" + 
                               colheitas[i].matriculaFuncionario + ";" + 
                               colheitas[i].idTalhao + ";" + 
                               colheitas[i].placaVeiculo + ";" + 
                               colheitas[i].quantidadeLitros + ";" + 
                               colheitas[i].destino);
            }
            
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível salvar as Colheitas: " + e.getMessage());
        }
    }

    // ==========================================
    // MÉTODOS DE LEITURA (CARGA)
    // ==========================================

    // Carrega os colaboradores do CSV para o vetor e retorna a quantidade lida
    public static int carregarColaboradores(Colaborador[] equipe) {
        int contador = 0;
        try {
            File arquivo = new File(PASTA + "colaboradores.csv");
            Scanner leitor = new Scanner(arquivo);

            // Pula a primeira linha (cabeçalho)
            if (leitor.hasNextLine()) leitor.nextLine();

            while (leitor.hasNextLine() && contador < equipe.length) {
                String linha = leitor.nextLine();
                String[] dados = linha.split(";");

                Colaborador c = new Colaborador();
                c.matricula = Integer.parseInt(dados[0]);
                c.nome = dados[1];
                c.tipoContrato = dados[2];

                equipe[contador] = c;
                contador++;
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("[AVISO] Arquivo de colaboradores não encontrado. Iniciando vazio.");
        }
        return contador;
    }

    public static int carregarFrota(Frota[] frota) {
        int contador = 0;
        try {
            File arquivo = new File(PASTA + "frota.csv");
            Scanner leitor = new Scanner(arquivo);

            if (leitor.hasNextLine()) leitor.nextLine();

            while (leitor.hasNextLine() && contador < frota.length) {
                String linha = leitor.nextLine();
                String[] dados = linha.split(";");

                Frota f = new Frota();
                f.placa = dados[0];
                f.capacidadeMax = Double.parseDouble(dados[1]);

                frota[contador] = f;
                contador++;
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("[AVISO] Arquivo de frota não encontrado.");
        }
        return contador;
    }

    public static int carregarTalhoes(Talhao[] talhoes) {
        int contador = 0;
        try {
            File arquivo = new File(PASTA + "talhoes.csv");
            Scanner leitor = new Scanner(arquivo);

            if (leitor.hasNextLine()) leitor.nextLine();

            while (leitor.hasNextLine() && contador < talhoes.length) {
                String linha = leitor.nextLine();
                String[] dados = linha.split(";");

                Talhao t = new Talhao();
                t.idTalhao = Integer.parseInt(dados[0]);
                t.nome = dados[1];
                t.variedaCafe = dados[2];
                t.estimativaProducao = Double.parseDouble(dados[3]);

                talhoes[contador] = t;
                contador++;
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("[AVISO] Arquivo de talhões não encontrado.");
        }
        return contador;
    }

    public static int carregarColheitas(RegistroColheita[] colheitas) {
        int contador = 0;
        try {
            File arquivo = new File(PASTA + "colheitas.csv");
            Scanner leitor = new Scanner(arquivo);

            if (leitor.hasNextLine()) leitor.nextLine();

            while (leitor.hasNextLine() && contador < colheitas.length) {
                String linha = leitor.nextLine();
                String[] dados = linha.split(";");

                RegistroColheita rc = new RegistroColheita();
                rc.data = dados[0];
                rc.matriculaFuncionario = Integer.parseInt(dados[1]);
                rc.idTalhao = Integer.parseInt(dados[2]);
                rc.placaVeiculo = dados[3];
                rc.quantidadeLitros = Double.parseDouble(dados[4]);
                rc.destino = dados[5];

                colheitas[contador] = rc;
                contador++;
            }
            leitor.close();
        } catch (FileNotFoundException e) {
            System.out.println("[AVISO] Arquivo de colheitas não encontrado.");
        }
        return contador;
    }
}


