import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class FSMStateHandler4 implements Callable<Boolean>, IFSMStateHandler {

    private final FSMContext ctx;
    private final ExecutorService executor;

    public FSMStateHandler4(FSMContext ctx, ExecutorService executor) {
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
        return this.state4();
    }

    private boolean state4() throws IOException {
        ctx.lexer.getChar();

        if (ctx.lexer.getInputChar() == ' ' ||
                ctx.lexer.getInputChar() >= '\t' && ctx.lexer.getInputChar() <= '\r') {
            ctx.fsmReturn = true;
            ctx.nextState = 1;
            return true;
        }

        switch (ctx.lexer.getInputChar()) {
            case ',':
            case ']':
            case '}':
                ctx.lexer.ungetChar();
                ctx.fsmReturn = true;
                ctx.nextState = 1;
                return true;

            case '.':
                ctx.lexer.getStringBuilder().append((char) ctx.lexer.getInputChar());
                ctx.nextState = 5;
                return true;

            case 'e':
            case 'E':
                ctx.lexer.getStringBuilder().append((char) ctx.lexer.getInputChar());
                ctx.nextState = 7;
                return true;

            default:
                return false;
        }
    }

    @Override
    public Future<Boolean> submitTask() {
        return executor.submit(this);
    }
}
