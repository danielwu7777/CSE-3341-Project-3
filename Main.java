class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		Scanner S = new Scanner(args[0]);
		Scanner dataS = new Scanner(args[1]);
		Parser.scanner = S;
		Parser.dataScanner = dataS;
		Program prog = new Program();

		prog.parse();

		prog.execute();
	}
}