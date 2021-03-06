import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class FSMStateHandler2 implements Callable<Boolean>, IFSMStateHandler {

    private final FSMContext ctx;
    private final ExecutorService executor;

    public FSMStateHandler2(FSMContext ctx, ExecutorService executor) {
        this.ctx = ctx;
        this.executor = executor;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Boolean call() throws Exception {
        return this.state2();
    }

    private boolean state2() throws IOException {
        ctx.lexer.getChar();

        if (ctx.lexer.getInputChar() >= '1' && ctx.lexer.getInputChar() <= '9') {
            ctx.lexer.getStringBuilder().append((char) ctx.lexer.getInputChar());
            ctx.nextState = 3;
            return true;
        }

        if (ctx.lexer.getInputChar() == '0') {
            ctx.lexer.getStringBuilder().append((char) ctx.lexer.getInputChar());
            ctx.nextState = 4;
            return true;
        }
        return false;
    }

    @Override
    public Future<Boolean> submitTask() {
        return executor.submit(this);
    }
}
