import java.util.Map;
import java.util.Stack;

class StackReg{
    // Stack of maps to store local variables in each scope
	static Stack<Map<String, Integer>> localVar = new Stack<>();
}
