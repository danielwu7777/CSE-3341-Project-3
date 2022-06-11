class Assign implements Stmt {
	// type is 1 if "new" assignment, 2 if "ref" assignment, 3 if "<expr>" assignment
	int type;
	// assignTo is the id on the LHS of assignment
	Id assignTo;
	// assignFrom is the id on RHS of "ref" assignment
	Id assignFrom;
	Expr expr;
	
	public void parse() {
		assignTo = new Id();
		assignTo.parse();
		Parser.expectedToken(Core.ASSIGN);
		Parser.scanner.nextToken();
		if (Parser.scanner.currentToken() == Core.INPUT) {
			type = 0;
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.LPAREN);
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.RPAREN);
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.NEW) {
			type = 1;
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.CLASS);
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.SHARE) {
			type = 2;
			Parser.scanner.nextToken();
			assignFrom = new Id();
			assignFrom.parse();
		} else {
			type = 3;
			expr = new Expr();
			expr.parse();
		}
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void semantic() {
		assignTo.semantic();
		if (type == 0) {
			// Nothing to check here
		} else if (type == 1) {
			if (assignTo.checkType() != Core.REF) {
				System.out.println("ERROR: int variable used in new assignment");
				System.exit(0);
			}
		} else if (type == 2) {
			if (assignTo.checkType() != Core.REF) {
				System.out.println("ERROR: int variable used in ref assignment");
				System.exit(0);
			}
			if (assignFrom.checkType() != Core.REF) {
				System.out.println("ERROR: int variable used in ref assignment");
				System.exit(0);
			}
		} else {
			expr.semantic();
		}
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		assignTo.print();
		System.out.print("=");
		if (type == 0) {
			System.out.print("input()");
		} else if (type == 1) {
			System.out.print("new class");
		} else if (type == 2) {
			System.out.print("share ");
			assignFrom.print();
		} else {
			expr.print();
		}
		System.out.println(";");
	}

	public void execute() {
		if (assignTo.checkType() == Core.INT) {
			assignTo.execute(true, false);
		}
		else {
			assignTo.execute(false, false);
		}
		if (type == 0) {
			// get input for RHS
		}
		else if (type == 1) {
			//Heap.listInt.add(e)
		}
		
	}

	
}