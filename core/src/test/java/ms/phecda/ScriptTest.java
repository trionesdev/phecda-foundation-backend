package ms.phecda;

import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptTest {

    ScriptEngineManager manager = new ScriptEngineManager();
    // 获取JavaScript引擎
    ScriptEngine engine = manager.getEngineByName("js");

    @Test
    public void ss() {
        try {
            Object result = engine.eval("  let a =2; return a; ");
            System.out.println(result);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

}
