package br.com.bullcontrol.api.invoker;

import com.bullcontrol.sistema.Invoker;
import com.bullcontrol.sistema.InvokerRequest;
import com.bullcontrol.sistema.InvokerResponse;
import com.bullcontrol.sistema.ZipHandler;
import com.bullcontrol.sistema.domain.SessaoUsuario;
import com.bullcontrol.sistema.service.ServerService;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InvalidClassException;
import java.net.ConnectException;

@Slf4j
public class HessianInvoker implements Invoker {

    public Object invoke(String bullcontrolUrl, SessaoUsuario sessao, String serviceName, String methodName, Object[] params, Class<?>[] paramTypes) throws Throwable {
        if (methodName.equals("finalize")) return null;
        try {
            String url = bullcontrolUrl;

            HessianProxyFactory factory = new HessianProxyFactory();
            ServerService serverService = (ServerService) factory.create(ServerService.class, url + "/server");

            log.info("Invoking Bullcontrol service: {}, method: {}, params: {}", serviceName, methodName, params);
            InvokerRequest request = new InvokerRequest(sessao, serviceName, methodName, params, paramTypes);

            byte[] objBytes = (byte[]) serverService.executeMethod(ZipHandler.deflate(request));

            InvokerResponse response = ZipHandler.inflate(objBytes);
            if (response.isException()) throw response.getThrowable();
            return response.getResult();
        } catch (Throwable e) {
            if (e instanceof HessianRuntimeException){
                if (((HessianRuntimeException)e).getRootCause() instanceof ConnectException){
                    throw new RuntimeException("Error conecting server");
                }
            }
            Throwable cause = e.getCause() == null ? e : e.getCause();
            if (cause instanceof InvalidClassException){
                throw new RuntimeException("Bullcontrol server lib is outdated");
            } else if (cause instanceof IOException){
                throw new RuntimeException("Error connecting server");
            } else if (cause instanceof ClassNotFoundException) {
                throw new RuntimeException(String.format("Incomplete classpath detected. Class not found: %s", cause.getMessage()));
            }

            throw cause;
        }
    }
}
