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
                        in1.nextLine();
                        Funcoes.cadastro(RAF, RAF2, in1);
                        break;
                    case 2:
                        in1.nextLine();
                        Funcoes.ler(RAF, RAF2, in1);
                        break;
                    case 3: {
                        in1.nextLine();

                        System.out.printf("\tDigite o ID da conta a ser atualizada: "); // Pede o ID da conta
                        int idConta = in1.nextInt();

                        in1.nextLine();
 
                        System.out.printf("\tNome de usuário: "); // Pede o ID da conta
                        String nomeUsuario = in1.nextLine();

                        System.out.printf("\tCPF: ");
                        String cpf = in1.nextLine();

                        System.out.printf("\tNome: ");
                        String nome = in1.nextLine();

                        System.out.printf("\tNumero de emails: ");
                        int numeroEmails = in1.nextInt();

                        in1.nextLine();

                        String[] emails = new String[numeroEmails];

                        for (int i = 0; i < numeroEmails; i++) {
                            System.out.printf("\tEmail %d: ", i + 1);
                            emails[i] = in1.nextLine();
                        }

                        System.out.printf("\tSenha: ");
                        String senha = in1.nextLine();

                        System.out.printf("\tCidade: ");
                        String cidade = in1.nextLine();

                        System.out.printf("\tSaldo (utilize ','): ");
                        float saldo = in1.nextFloat();

                        System.out.printf("\tTransferências realizadas: ");
                        short transferenciasRealizadas = in1.nextShort();

                        Funcoes.atualizar(RAF, RAF2, in1, nomeUsuario, cpf, nome, numeroEmails, emails, senha, cidade, saldo,
                                transferenciasRealizadas, idConta);
                    }
                        break;
                    case 4:
                        Funcoes.transferencia(RAF, RAF2, in1);

                        break;
                    case 5: {
                        System.out.println("Digite o ID da conta que deseja excluir:");
                        in1.nextLine();
                        int idConta = in1.nextInt();
                        Funcoes.deletar(idConta, RAF, RAF2);
                    }
                        break;

                    case 6:
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