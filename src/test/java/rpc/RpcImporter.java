package rpc;

import org.assertj.core.internal.cglib.proxy.Proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author: 温海林
 * 2018年07月11日17:13:43
 */
public class RpcImporter<S> {
    @SuppressWarnings("unchecked")
    S importer(final Class<?> serviceClass, final InetSocketAddress address) {
        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[]{
                serviceClass.getInterfaces()[0]
        }, (proxy, method, args) -> {
            Socket socket = null;
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            try {
                socket = new Socket();
                socket.connect(address);
                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeUTF(serviceClass.getName());
                output.writeUTF(method.getName());
                output.writeObject(method.getParameterTypes());
                output.writeObject(args);
                input = new ObjectInputStream(socket.getInputStream());
                return input.readObject();
            } finally {
                if (socket != null) {
                    socket.close();
                }
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            }
        });
    }
}
