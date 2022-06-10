class Decl implements Stmt {
	DeclInt declInt;
	DeclClass declClass;
	
	public void parse() {
		if (Parser.scanner.currentToken() == Core.INT) {
			declInt = new DeclInt();
			declInt.parse();
		} else {
			declClass = new DeclClass();
			declClass.parse();
		}
	}
	
	public void semantic() {
		if (declInt != null) {
			declInt.semantic();
		} else {
			declClass.semantic();
		}
	}
	
	public void print(int indent) {
		if (declInt != null) {
			declInt.print(indent);
		} else {
			declClass.print(indent);
		}
	}

	void execute() {
		if (declInt != null) {
			declInt.execute();
		} else {
			declClass.execute();
		}
    }
}