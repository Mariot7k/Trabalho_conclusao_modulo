import java.util.Scanner;
import model.Colaborador;
import model.Frota;
import model.RegistroColheita;
import model.Talhao;
import service.ArquivoService;
import service.GerarRelatorios;
import service.Diferencial;
import view.SeletorMenu;

public class FazendaEsperaca {
    public static void main(String[] args) {
        //Cria o sistema de arquivos
        ArquivoService.inicializarSistemaDeArquivos();

        boolean sistemaPronto = ArquivoService.inicializarSistemaDeArquivos();

        // Se a resposta for falsa (deu erro na pasta), aborta a missão!
        if (!sistemaPronto) {
            System.out.println("Encerrando o sistema por segurança para evitar perda de dados...");
            return; // O programa morre aqui. Não lê mais nada para baixo.
        }
        
        Scanner input = new Scanner(System.in);
        
        //Cria os vetores de objetos
        Colaborador[] equipe = new Colaborador[100];
        Frota[] frota = new Frota[100];
        Talhao[] talhoes = new Talhao[100];
        RegistroColheita[] colheitas = new RegistroColheita[500];

        // CARREGA OS DADOS
        // Atribuímos o retorno da função aos nossos contadores principais
        int qtdColaboradores = ArquivoService.carregarColaboradores(equipe);
        int qtdVeiculos = ArquivoService.carregarFrota(frota);
        int qtdTalhoes = ArquivoService.carregarTalhoes(talhoes);
        int qtdColheitas = ArquivoService.carregarColheitas(colheitas);

        System.out.println("Sistema carregado:");
        System.out.println("->  " + qtdColaboradores + " colaboradores prontos.");
        System.out.println("->  " + qtdVeiculos + " veiculos prontos.");
        System.out.println("->  " + qtdTalhoes + " talhões prontos.");
        System.out.println("->  " + qtdColheitas + " colheitas prontas.");
        
        int opcao = 0;
        while (opcao != 9) {
            opcao = SeletorMenu.funcaoMenu(input);

            switch (opcao) {
                case 1:
                    if (qtdColaboradores < equipe.length) {
                        System.out.println("\n--- Novo Cadastro de Colaborador ---");
                        
                        // Chama a função estática para criar 1 colaborador
                        Colaborador recemCadastrado = Colaborador.cadastrarColaborador(input, equipe, qtdColaboradores);
                        
                        // Guarda na posição atual e atualiza o contador principal da Main
                        equipe[qtdColaboradores] = recemCadastrado;
                        qtdColaboradores++; 
                        
                        System.out.println("\n-> Sucesso! Colaborador cadastrado.");
                        System.out.println("-> Total na equipe agora: " + qtdColaboradores + "/" + equipe.length);

                    } else {
                        // Se o vetor já estiver cheio
                        System.out.println("\n[ERRO] Espaço insuficiente no sistema!");
                        System.out.println("O limite máximo de " + equipe.length + " colaboradores já foi atingido.");
                    }
                    break;

                case 2:
                    // VALIDAÇÃO CRUCIAL: O vetor tem espaço para mais 1 cadastro?
                    if (qtdVeiculos < frota.length) {
                        System.out.println("\n--- Novo Cadastro de Veículo ---");
                        
                        // Chama a função estática para criar 1 veículo
                        Frota recemCadastrado = Frota.cadastrarVeiculo(input, frota, qtdVeiculos);
                        
                        // Guarda na posição atual e atualiza o contador principal da Main
                        frota[qtdVeiculos] = recemCadastrado;
                        qtdVeiculos++; 
                        
                        System.out.println("\n-> Sucesso! Veículo cadastrado.");
                        System.out.println("-> Total na frota agora: " + qtdVeiculos + "/" + frota.length);

                    } else {
                        // Se o vetor já estiver cheio
                        System.out.println("\n[ERRO] Espaço insuficiente no sistema!");
                        System.out.println("O limite máximo de " + frota.length + " veículos já foi atingido.");
                    }
                    break;

                    case 3:
                    // VALIDAÇÃO CRUCIAL: O vetor tem espaço para mais 1 cadastro?
                    if (qtdTalhoes < talhoes.length) {
                        System.out.println("\n--- Novo Cadastro de Talhão ---");
                        
                        // Chama a função estática para criar 1 talhão
                        Talhao recemCadastrado = Talhao.cadastrarTalhao(input, talhoes, qtdTalhoes);
                        
                        // Guarda na posição atual e atualiza o contador principal da Main
                        talhoes[qtdTalhoes] = recemCadastrado;
                        qtdTalhoes++; 
                        
                        System.out.println("\n-> Sucesso! Talhão cadastrado.");
                        System.out.println("-> Total de talhões agora: " + qtdTalhoes + "/" + talhoes.length);

                    } else {
                        // Se o vetor já estiver cheio
                        System.out.println("\n[ERRO] Espaço insuficiente no sistema!");
                        System.out.println("O limite máximo de " + talhoes.length + " talhões já foi atingido.");
                    }
                    break;

                    case 4:
                        if (qtdColheitas < colheitas.length) {
                            
                            // Verifica se há pelo menos 1 item cadastrado em cada categoria base para evitar travamento em loop infinito
                            if (qtdColaboradores == 0 || qtdVeiculos == 0 || qtdTalhoes == 0) {
                                System.out.println("[ERRO] Você precisa cadastrar pelo menos 1 funcionário, 1 trator e 1 talhão antes de registrar colheitas!");
                                break;
                            }

                            RegistroColheita novoLancamento = RegistroColheita.registrarEntrada(
                                input, 
                                equipe, qtdColaboradores, 
                                frota, qtdVeiculos, 
                                talhoes, qtdTalhoes
                            );
                            
                            if (novoLancamento != null) {
                                colheitas[qtdColheitas] = novoLancamento;
                                qtdColheitas++;
                                System.out.println("\n-> Registro de colheita salvo com sucesso!");
                            } 
                            // Se for null, ele apenas sai do 'case' silenciosamente e mostra o menu de novo
                        } else {
                            System.out.println("[ERRO] Memória cheia para novos registros de colheita.");
                        }
                        break;
                    
                    case 5:
                        // Chama a view para mostrar o menu e pegar a escolha do usuário
                        int tipoRelatorio = SeletorMenu.definirTipoRelatorio(input);

                        // Avalia qual relatório foi escolhido e chama o método correspondente
                        switch (tipoRelatorio) {
                            case 1:
                                // 1 - Acerto Geral
                                GerarRelatorios.exibirAcertoQuinzena(equipe, qtdColaboradores, colheitas, qtdColheitas);
                                break;
                                
                            case 2:
                                // 2 - Acerto Individual
                                System.out.print("\nDigite a matrícula do funcionário para a busca: ");
                                try {
                                    int matriculaBuscada = Integer.parseInt(input.nextLine());
                                    GerarRelatorios.exibirAcertoIndividual(equipe, qtdColaboradores, colheitas, qtdColheitas, matriculaBuscada);
                                } catch (NumberFormatException e) {
                                    System.out.println("[ERRO] Matrícula inválida! Digite apenas números.");
                                }
                                break;
                                
                            case 3:
                                // 3 - Fechamento do Talhão (Busca Individual)
                                System.out.print("\nDigite o ID do Talhão que deseja analisar: ");
                                try {
                                    int idTalhaoBusca = Integer.parseInt(input.nextLine());
                                    GerarRelatorios.exibirFechamentoTalhao(talhoes, qtdTalhoes, colheitas, qtdColheitas, idTalhaoBusca);
                                } catch (NumberFormatException e) {
                                    System.out.println("[ERRO] ID inválido! Digite apenas números inteiros.");
                                }
                                break;
                                
                            case 4:
                                // 4 - Relatório de Secagem (Destinos do Café)
                                GerarRelatorios.exibirRelatorioSecagem(colheitas, qtdColheitas);
                                break;
                            
                            case 5:
                                // 5 - Consultar Lista de Talhões
                                GerarRelatorios.listarTalhoes(talhoes, qtdTalhoes);
                                break;
                            
                            case 6:
                                // 6 - Consultar Lista da Frota
                                GerarRelatorios.listarFrota(frota, qtdVeiculos);
                                break;
                                
                            case 0:
                                // 0 - Voltar
                                System.out.println("\n-> Voltando ao menu principal...");
                                break;
                                
                            case -1:
                                // Erro de formatação capturado pelo catch no SeletorMenu
                                break;
                                
                            default:
                                System.out.println("\n[ERRO] Opção de relatório inexistente.");
                                break;
                        }
                        break;
                    
                    case 6:
                        Diferencial.exibirRankingColaboradores(equipe, qtdColaboradores, colheitas, qtdColheitas);
                        break;
                        
                    case 9:
                        System.out.println("Encerrando as atividades da Fazenda Esperança...");
                        System.out.println("\n[SISTEMA] Iniciando salvamento de dados...");
                        
                        // Chama a função mestre de salvamento passando todos os vetores e contadores
                        ArquivoService.salvarTodosOsDados(
                            equipe, qtdColaboradores, 
                            frota, qtdVeiculos, 
                            talhoes, qtdTalhoes, 
                            colheitas, qtdColheitas
                        );
                        
                        System.out.println("[SISTEMA] Salvamento concluído com sucesso!");
                        System.out.println("Até a próxima safra!");
                        break;
            }
        }
    }
}
