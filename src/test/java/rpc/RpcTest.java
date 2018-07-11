package rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author: 温海林
 * 2018年07月11日17:38:58
 */
public class RpcTest {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                RpcExporter.exporter("localhost", 8999);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        RpcImporter<EchoService> importer = new RpcImporter<>();
        EchoService service = importer.importer(EchoServiceImpl.class, new InetSocketAddress("localhost", 8999));
        System.out.println(service.echo("Are you OK?"));
    }
}
