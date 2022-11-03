import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    // O 'main' apenas recebe qual opção o usuário
    // deseja (pelo byte "opcao") e o redireciona
    // para o seriço desejadao, porém sua principal
    // funcionalidade é no controle do arquivo criado.
    {
        Scanner in1 = new Scanner(System.in);
        int opcao = 0;

        try {
            RandomAccessFile RAF = new RandomAccessFile("dados.db", "rw");
            RandomAccessFile RAF2 = new RandomAccessFile("indices.db", "rw");

            do {
                Menus.menuInicial();
                System.out.print("Opção:");
                opcao = in1.nextInt();
                switch (opcao) {
                    case 1:
                        // CADASTRO
                        in1.nextLine();
                        Funcoes.cadastro(RAF, RAF2, in1);
                        break;
                    case 2:
                        // LEITURA
                        in1.nextLine();
                        Funcoes.ler(RAF, RAF2, in1);
                        break;
                    case 3: {
                        // ATUALIZACAO
                        in1.nextLine();

                        System.out.printf("\tDigite o ID da conta a ser atualizada: "); // Pede o ID da conta
                        int idConta = in1.nextInt();

                        boolean contaAtualizada = Funcoes.receberDadosParaAtualizarConta(RAF, RAF2, in1, idConta);

                        if (contaAtualizada) {
                            Menus.contaAtualizada(idConta);
                        }
                    }
                        break;
                    case 4: {
                        // TRANSFERENCIA
                        boolean transferenciaRealizada = Funcoes.transferencia(RAF, RAF2, in1);
                        if (transferenciaRealizada) {
                            Menus.transferenciaRealizada();
                        }
                    }
                        break;
                    case 5: {
                        // EXCLUSÃO
                        System.out.println("Digite o ID da conta que deseja excluir:");
                        in1.nextLine();
                        int idConta = in1.nextInt();
                        boolean contaDeletada = Funcoes.deletar(idConta, RAF, RAF2);

                        if (contaDeletada) {
                            Menus.contaDeletada(); // Exibe mensagem de conta deletada
                        }
                    }
                        break;
                    case 6: {
                        // MENU LZW
                        Menus.menuLZW(Funcoes.ler(RAF, RAF2, in1).toString());
                        
                    }
                        break;
                    case 7:
                        System.out.printf("\n\tPrograma finalizado.\n");
                        System.exit(0);
                        break;
                    default:
                        System.out.printf("\n\tOpção inválida.\n");
                        Menus.menuInicial();
                        break;

                }
            } while (opcao != 6);
            RAF.close();
            RAF2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        in1.close();// erro 937 resolvido
    }
}