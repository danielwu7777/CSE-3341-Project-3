class Cmpr {
	Expr expr1;
	Expr expr2;
	int option;

	void parse() {
		expr1 = new Expr();
		expr1.parse();
		if (Parser.scanner.currentToken() == Core.EQUAL) {
			option = 0;
		} else if (Parser.scanner.currentToken() == Core.LESS) {
			option = 1;
		} else if (Parser.scanner.currentToken() == Core.LESSEQUAL) {
			option = 2;
		} else {
			System.out.println("ERROR: Expected EQUAL, LESS, or LESSEQUAL, recieved " + Parser.scanner.currentToken());
			System.exit(0);
		}
		Parser.scanner.nextToken();
		expr2 = new Expr();
		expr2.parse();
	}

	void semantic() {
		expr1.semantic();
		expr2.semantic();
	}

	void print() {
		expr1.print();
		switch (option) {
			case 0:
				System.out.print("==");
				break;
			case 1:
				System.out.print("<");
				break;
			case 2:
				System.out.print("<=");
				break;
		}
		expr2.print();
	}

	public boolean execute() {
		boolean b = false;
		switch (option) {
			case 0:
				b = expr1.execute() == expr2.execute();
				break;
			case 1:
				b = expr1.execute() < expr2.execute();
				break;
			case 2:
				b = expr1.execute() <= expr2.execute();
				break;
		}
		return b;
	}
}