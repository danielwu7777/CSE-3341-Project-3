class Factor {
	Id id;
	String identifier;
	int constant;
	Expr expr;
	boolean intOrRef;

	void parse() {
		if (Parser.scanner.currentToken() == Core.ID) {
			id = new Id();
			intOrRef = Parser.scanner.intOrRef(Parser.scanner.currentToken());
			identifier = Parser.scanner.getID();
			id.parse();
		} else if (Parser.scanner.currentToken() == Core.CONST) {
			constant = Parser.scanner.getCONST();
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.LPAREN) {
			Parser.scanner.nextToken();
			expr = new Expr();
			expr.parse();
			Parser.expectedToken(Core.RPAREN);
			Parser.scanner.nextToken();
		} else {
			System.out.println("ERROR: Expected ID, CONST, or LPAREN, recieved " + Parser.scanner.currentToken());
			System.exit(0);
		}
	}

	void semantic() {
		if (id != null) {
			id.semantic();
		} else if (expr != null) {
			expr.semantic();
		}
	}

	void print() {
		if (id != null) {
			id.print();
		} else if (expr != null) {
			System.out.print("(");
			expr.print();
			System.out.print(")");
		} else {
			System.out.print(constant);
		}
	}

	public int execute() {
		System.out.println(StaticReg.globVar.toString() + " this should be xg => 0");
		System.out.println(StackReg.localVar.toString());
		int result = 0;
		if (id != null) {
			if (intOrRef) {
				if (StaticReg.isGlobal(identifier)) {
					result = StaticReg.globVar.get(identifier + "g");
				} else {
					result = StackReg.localVar.peek().get(identifier);
				}
			} else {
				if (StaticReg.isGlobal(identifier)) {
					result = Heap.listInt.get(StaticReg.globVar.get(identifier + "g"));
				} else {
					result = Heap.listInt.get(StackReg.localVar.peek().get(identifier));
				}
			}
		} else if (expr != null) {
			result = expr.execute();
		} else {
			result = constant;
		}
		return result;
	}
}