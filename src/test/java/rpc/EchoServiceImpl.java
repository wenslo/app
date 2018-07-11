package rpc;

/**
 * @author: 温海林
 * 2018年07月11日16:54:56
 */
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(final String ping) {
        return ping != null ? ping + " --> I am ok." : "I am ok.";
    }
}
