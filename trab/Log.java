public class Log {
	public static double log(double valor, double base) {
		return Math.log10(valor) / Math.log10(base);
	}

	public static void main(String[] args) {
		System.out.println(log((double) 3, (double) 2));
	}
}
