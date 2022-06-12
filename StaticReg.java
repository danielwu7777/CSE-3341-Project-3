import java.util.HashMap;
import java.util.Map;

class StaticReg {
    // Map to store global variables in <decl-seq>
    static Map<String, Integer> globVar = new HashMap<>();

    // Returns true when the variable is in global scope
    static public boolean isGlobal(String identifier) {
        boolean b = false;
        if (globVar.containsKey(identifier + "g")) {
            b = true;
        }
        return b;
    }

    // Used for initial declarations in <decl-seq>
    static boolean inDeclSeq;
}
