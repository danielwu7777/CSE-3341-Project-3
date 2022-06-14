import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class StackReg {
    // Stack of maps to store local variables in each scope
    static Stack<Map<String, Integer>> localVar = new Stack<>();

    // Searches stack for given key and replaces the value with given constant value
    static public void searchAndReplaceConst(String key, Integer value) {
        Stack<Map<String, Integer>> copyOfStack = new Stack<>();
        copyOfStack.addAll(localVar);
        Map<String, Integer> m = copyOfStack.pop();
        while (!m.containsKey(key)) {
            m = copyOfStack.pop();
        }
        Map<String, Integer> newMap = new HashMap<>() {
            {
                put(key, value);
            }
        };
        localVar.set(localVar.indexOf(m), newMap);
    }

    // Searches stack for given key and returns the value
    static public int search(String key) {
        Stack<Map<String, Integer>> copyOfStack = new Stack<>();
        copyOfStack.addAll(localVar);
        Map<String, Integer> m = copyOfStack.pop();
        while (!m.containsKey(key)) {
            m = copyOfStack.pop();
        }
        return m.get(key);
    }
}
