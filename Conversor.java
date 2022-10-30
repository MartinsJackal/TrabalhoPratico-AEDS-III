import java.text.NumberFormat;
import java.text.DecimalFormat;

public class Conversor // Método para formatar os valores (R$) para o estilo cotidiano.
{
	static NumberFormat formatavalores = new DecimalFormat("R$ #,##0.00");
    public static String floatParaString (float valor) { return formatavalores.format(valor); }
}