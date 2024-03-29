import java.util.HashMap;

class StmtSeq {
	Stmt stmt;
	StmtSeq ss;

	void parse() {
		if (Parser.scanner.currentToken() == Core.ID) {
			stmt = new Assign();
		} else if (Parser.scanner.currentToken() == Core.OUTPUT) {
			stmt = new Output();
		} else if (Parser.scanner.currentToken() == Core.IF) {
			stmt = new If();
		} else if (Parser.scanner.currentToken() == Core.WHILE) {
			stmt = new Loop();
		} else if (Parser.scanner.currentToken() == Core.INT || Parser.scanner.currentToken() == Core.REF) {
			stmt = new Decl();
		} else {
			System.out.println("ERROR: Bad start to statement: " + Parser.scanner.currentToken());
			System.exit(0);
		}
		stmt.parse();
		if ((Parser.scanner.currentToken() != Core.END)
				&& (Parser.scanner.currentToken() != Core.RBRACE)) {
			ss = new StmtSeq();
			ss.parse();
		}
	}

	void semantic() {
		stmt.semantic();
		if (ss != null) {
			ss.semantic();
		}
	}

	void print(int indent) {
		stmt.print(indent);
		if (ss != null) {
			ss.print(indent);
		}
	}

	void execute() {
		stmt.execute();
		if (ss != null) {
			StackReg.localVar.push(new HashMap<>());
			ss.execute();
			StackReg.localVar.pop();
		}
	}
}