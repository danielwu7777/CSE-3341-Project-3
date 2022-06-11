import java.util.Map;

class Id {
	String identifier;
	
	void parse() {
		Parser.expectedToken(Core.ID);
		identifier = Parser.scanner.getID();
		Parser.scanner.nextToken();
	}
	
	// Called to check if the identifier has been declared
	void semantic() {
		if (Parser.nestedScopeCheck(identifier)==Core.ERROR) {
			System.out.println("ERROR: No matching declaration found: " + identifier);
			System.exit(0);
		}
	}
	
	//Called by IdList semantic functions to check for doubly declared variables
	void doublyDeclared() {
		if (Parser.currentScopeCheck(identifier)!=Core.ERROR) {
			System.out.println("ERROR: Doubly declared variable detected: " + identifier);
			System.exit(0);
		}
	}
	
	//Called by IdList semantic functions to add the variable to the scopes data structure in Parser
	void addToScopes(Core type) {
		Parser.scopes.peek().put(identifier, type);
	}
	
	//Called by Assign semantic function to check the declared type of the variable
	Core checkType() {
		return Parser.nestedScopeCheck(identifier);
	}
	
	void print() {
		System.out.print(identifier);
	}

	// intOrRef is true when id is int and globalOrLocal is true when id is a global variable
	void execute(boolean intOrRef, boolean globalOrLocal) {
		if (globalOrLocal) {
			if (intOrRef) {
				StaticReg.globVar.put(identifier, 0);
			}
			else {
				Heap.listInt.add(null);
				StaticReg.globVar.put(identifier, Heap.listInt.size() - 1);
			}
		}
		else {
			// If id is local, add to the map on the top of the stack
			Map<String, Integer> m = StackReg.localVar.pop();
			if (intOrRef) {
				m.put(identifier, 0);
			}
			else {
				Heap.listInt.add(null);
				m.put(identifier, Heap.listInt.size() - 1);
			}
			StackReg.localVar.add(m);
		}
	}
}