class Assign implements Stmt {
	// type is 1 if "new" assignment, 2 if "ref" assignment, 3 if "<expr>"
	// assignment
	int type;
	// assignTo is the id on the LHS of assignment
	Id assignTo;
	String identifierLeft;
	// assignFrom is the id on RHS of "ref" assignment
	Id assignFrom;
	String identifierRight;
	Expr expr;
	boolean intOrRef;

	public void parse() {
		assignTo = new Id();
		identifierLeft = Parser.scanner.getID();
		intOrRef = Parser.scanner.intOrRef(Parser.scanner.currentToken());
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
			identifierRight = Parser.scanner.getID();
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
		for (int i = 0; i < indent; i++) {
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
		boolean notInStackAndIsGlobalLeft = StackReg.search(identifierLeft) == -1 && StaticReg.isGlobal(identifierLeft);
		switch (type) {
			// input() assignment
			case 0:
				if (Parser.dataScanner.currentToken() == Core.EOS) {
					System.out.println("ERROR: No more integers to read in data file");
				} else {
					int input = Parser.dataScanner.getCONST();
					if (intOrRef) {
						if (notInStackAndIsGlobalLeft) {
							StaticReg.globVar.replace(identifierLeft + "g", input);
						} else {
							StackReg.searchAndReplaceConst(identifierLeft, input);
						}
					} else {
						if (notInStackAndIsGlobalLeft) {
							Heap.listInt.set(StaticReg.globVar.get(identifierLeft + "g"), input);
						} else {
							Heap.listInt.set(StackReg.search(identifierLeft), input);
						}
					}
					Parser.dataScanner.nextToken();
				}
				break;
			// "new class" assignment
			case 1:
				if (intOrRef) {
					if (notInStackAndIsGlobalLeft) {
						StaticReg.globVar.replace(identifierLeft + "g", 0);
					} else {
						StackReg.searchAndReplaceConst(identifierLeft, 0);
					}
				} else {
					Heap.listInt.add(null);
					StackReg.searchAndReplaceConst(identifierLeft, Heap.listInt.size() - 1);
				}
				break;
			// "share" assignment  
			case 2:  // fix this!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
				if (StackReg.search(identifierRight) == -1 && StaticReg.isGlobal(identifierRight)) {
					StaticReg.globVar.replace(identifierLeft + "g", StaticReg.globVar.get(identifierRight + "g"));
				} else {
					StackReg.searchAndReplaceConst(identifierLeft, StackReg.search(identifierRight));
				}
				break;
			// <expr>
			case 3:
				int exprResult = expr.execute();
				if (intOrRef) {
					if (notInStackAndIsGlobalLeft) {
						StaticReg.globVar.replace(identifierLeft + "g", exprResult);
					} else {
						StackReg.searchAndReplaceConst(identifierLeft, exprResult);
					}
				} else {
					if (notInStackAndIsGlobalLeft) {
						Heap.listInt.set(StaticReg.globVar.get(identifierLeft + "g"), exprResult);
					} else {
						Heap.listInt.set(StackReg.search(identifierLeft), exprResult);
					}
				}
				break;
		}
	}
}