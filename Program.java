import java.util.*;

class Program {
	DeclSeq ds;
	StmtSeq ss;

	void parse() {
		Parser.expectedToken(Core.PROGRAM);
		Parser.scanner.nextToken();
		if (Parser.scanner.currentToken() != Core.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
		Parser.expectedToken(Core.BEGIN);
		Parser.scanner.nextToken();
		ss = new StmtSeq();
		ss.parse();
		Parser.expectedToken(Core.END);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.EOS);
	}

	void semantic() {
		Parser.scopes = new Stack<HashMap<String, Core>>();
		Parser.scopes.push(new HashMap<String, Core>());
		if (ds != null) {
			ds.semantic();
		}
		Parser.scopes.push(new HashMap<String, Core>());
		ss.semantic();
		Parser.scopes.pop();
	}

	void print() {
		System.out.println("program");
		if (ds != null) {
			ds.print(1);
		}
		System.out.println("begin");
		ss.print(1);
		System.out.println("end");
	}

	void execute() {
		if (ds != null) {
			ds.execute();
		}
		ss.execute();
	}
}