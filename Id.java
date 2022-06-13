class Id {
	String identifier;
	boolean intOrRef;

	void parse() {
		Parser.expectedToken(Core.ID);
		identifier = Parser.scanner.getID();
		intOrRef = Parser.scanner.intOrRef(Parser.scanner.currentToken());
		Parser.scanner.nextToken();
	}

	// Called to check if the identifier has been declared
	void semantic() {
		if (Parser.nestedScopeCheck(identifier) == Core.ERROR) {
			System.out.println("ERROR: No matching declaration found: " + identifier);
			System.exit(0);
		}
	}

	// Called by IdList semantic functions to check for doubly declared variables
	void doublyDeclared() {
		if (Parser.currentScopeCheck(identifier) != Core.ERROR) {
			System.out.println("ERROR: Doubly declared variable detected: " + identifier);
			System.exit(0);
		}
	}

	// Called by IdList semantic functions to add the variable to the scopes data
	// structure in Parser
	void addToScopes(Core type) {
		Parser.scopes.peek().put(identifier, type);
	}

	// Called by Assign semantic function to check the declared type of the variable
	Core checkType() {
		return Parser.nestedScopeCheck(identifier);
	}

	void print() {
		System.out.print(identifier);
	}

	// If id is global,
	// append g after identifier in static region map for future scope checking
	public void execute() {
		if (StaticReg.inDeclSeq) {
			if (intOrRef) {
				StaticReg.globVar.put(identifier + "g", 0);
			} else {
				StaticReg.globVar.put(identifier + "g", null);
			}
		} else {
			if (intOrRef) {
				StackReg.localVar.peek().put(identifier, 0);
			} else {
				StackReg.localVar.peek().put(identifier, null);
			}
		}
	}
}