class Expr {
	Term term;
	Expr expr;
	int option;

	void parse() {
		term = new Term();
		term.parse();
		if (Parser.scanner.currentToken() == Core.ADD) {
			option = 1;
		} else if (Parser.scanner.currentToken() == Core.SUB) {
			option = 2;
		}
		if (option != 0) {
			Parser.scanner.nextToken();
			expr = new Expr();
			expr.parse();
		}
	}

	void semantic() {
		term.semantic();
		if (expr != null) {
			expr.semantic();
		}
	}

	void print() {
		term.print();
		if (option == 1) {
			System.out.print("+");
			expr.print();
		} else if (option == 2) {
			System.out.print("-");
			expr.print();
		}
	}

	public int execute() {
		int result = term.execute();
		if (option == 1) {
			result = result + expr.execute();
		} else if (option == 2) {
			result = result - expr.execute();
		}
		return result;
	}
}