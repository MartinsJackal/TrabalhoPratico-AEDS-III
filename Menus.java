import java.util.List;
import java.util.Scanner;

public class Menus {
    // Visando melhor leitura do código, todos os menus impressos
    // se encontram nesta classe que apenas exibe ao usuário as informações
    // necessárias.
    public static void menuInicial() {

        System.out.printf("\n");
        System.out.printf("\t.---------------------------------------.\n");
        System.out.printf("\t|            Cash3!            \n");
        System.out.printf("\t|---------------------------------------|\n");
        System.out.printf("\t|       » Escolha uma das opções «      |\n");
        System.out.printf("\t|---------------------------------------|\n");
        System.out.printf("\t| 1 - Criar uma nova conta              |\n");
        System.out.printf("\t| 2 - Ler conta                         |\n");
        System.out.printf("\t| 3 - Atualizar conta                   |\n");
        System.out.printf("\t| 4 - Transferir                        |\n");
        System.out.printf("\t| 5 - Deletar conta                     |\n");
        System.out.printf("\t| 6 - Menu LZW                          |\n");
        System.out.printf("\t| 7 - Sair                              |\n");
        System.out.printf("\t'---------------------------------------'\n");
        System.out.printf("\n");
    }

    public static void menuNovaConta() {
        System.out.printf("\n");
        System.out.printf("\t.----------------------.\n");
        System.out.printf("\t|      Nova Conta      |\n");
        System.out.printf("\t'----------------------'\n");
        System.out.printf("\n");
    }

    public static void menuTransferencia() {
        System.out.printf("\n");
        System.out.printf("\t.---------------------------.\n");
        System.out.printf("\t|       Transferência       |\n");
        System.out.printf("\t'---------------------------'\n");
        System.out.printf("\n");
    }

    public static void menuAtualizacao() {
        System.out.printf("\n");
        System.out.printf("\t.---------------------------.\n");
        System.out.printf("\t|      Atualizar conta      |\n");
        System.out.printf("\t'---------------------------'\n");
        System.out.printf("\n");
    }

    public static void menuDeletarConta() {
        System.out.printf("\n");
        System.out.printf("\t.---------------------------.\n");
        System.out.printf("\t|       Deletar conta       |\n");
        System.out.printf("\t'---------------------------'\n");
        System.out.printf("\n");
    }

    public static void contaEncontrada() {
        System.out.printf("\n");
        System.out.printf("\t.----------------------------.\n");
        System.out.printf("\t|       Conta encontrada     |\n");
        System.out.printf("\t'----------------------------'\n");
        System.out.printf("\n");
    }

    public static void contaAtualizada(int idConta) {
        System.out.printf("\n");
        System.out.printf("\t.--------------------------------------------.\n");
        System.out.printf("\t|       Conta %d atualizada com sucesso!     |\n", idConta);
        System.out.printf("\t'--------------------------------------------'\n");
        System.out.printf("\n");
    }

    public static void contaCriada(int idConta) {
        System.out.printf("\n");
        System.out.printf("\t.----------------------------------------.\n");
        System.out.printf("\t|       Conta %d criada com sucesso!     |\n", idConta);
        System.out.printf("\t'----------------------------------------'\n");
        System.out.printf("\n");
    }

    public static void transferenciaRealizada() {
        System.out.printf("\n");
        System.out.printf("\t.------------------------------------------------.\n");
        System.out.printf("\t|       Transferência realizada com sucesso!     |\n");
        System.out.printf("\t'------------------------------------------------'\n");
        System.out.printf("\n");
    }

    public static void contaNaoEncontrada() {
        System.out.printf("\n");
        System.out.printf("\t.---------------------------------.\n");
        System.out.printf("\t|       Conta não encontrada!     |\n");
        System.out.printf("\t'---------------------------------'\n");
        System.out.printf("\n");
    }

    public static void saldoInsuficiente() {
        System.out.printf("\n");
        System.out.printf("\t.------------------------------.\n");
        System.out.printf("\t|       Saldo insuficiente     |\n");
        System.out.printf("\t'------------------------------'\n");
        System.out.printf("\n");
    }

    public static void contaDeletada() {
        System.out.printf("\n");
        System.out.printf("\t.----------------------------------------.\n");
        System.out.printf("\t|       Conta deletada com sucesso!      |\n");
        System.out.printf("\t'----------------------------------------'\n");
        System.out.printf("\n");
    }

    public static void menuLZW() {
        byte in1;
        Scanner in = new Scanner(System.in);
		do {
            System.out.printf("\n");
            System.out.printf("\t.---------------------------\n");
            System.out.printf("\t|       Menu LZW            |\n");
            System.out.printf("\t'---------------------------\n");
            System.out.printf("\t|      1 - Compactação      |\n");
            System.out.printf("\t|      2 - Descompactação   |\n");
            System.out.printf("\t|      3 - Sair             |\n");
            System.out.printf("\t'---------------------------\n");
            System.out.printf("\n");
    
			System.out.println("Qual seria a operação a ser feita?");
			in1 = in.nextByte();
			switch(in1){
				case 1:{
					//Compactação
                    List<Integer> compressão = Funcoes.codificador("geekific-geekific");
                    System.out.println(compressão);
				}
				break;
				case 2:{
					//Descompactação
                    List<Integer> compressão = Funcoes.codificador("geekific-geekific");
					String descompressão = Funcoes.decodificador(compressão);
                    System.out.println(descompressão);
				}
				break;
                case 3:{
                    System.out.println("Voltando ao menu inicial...");
                    Menus.menuInicial();
                }
                break;
                default: {
                    System.out.println("Opção invalida!");
                }
            }
        } while (in1 > 3);
        in.close();
    }
}