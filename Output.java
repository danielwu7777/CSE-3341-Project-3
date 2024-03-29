class Output implements Stmt {
	Expr expr;
	
	public void parse() {
		Parser.expectedToken(Core.OUTPUT);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.LPAREN);
		Parser.scanner.nextToken();
		expr = new Expr();
		expr.parse();
		Parser.expectedToken(Core.RPAREN);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void semantic() {
		expr.semantic();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("output(");
		expr.print();
		System.out.println(");");
	}

	public void execute() {
		int exprResult = expr.execute();
		System.out.println(exprResult);
	}
}