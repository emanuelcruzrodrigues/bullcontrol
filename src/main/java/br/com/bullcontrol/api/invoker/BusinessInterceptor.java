package br.com.bullcontrol.api.invoker;

import br.com.bullcontrol.api.config.BullcontrolApiConfiguration;
import br.com.bullcontrol.api.config.BullcontrolApiContext;
import br.com.bullcontrol.api.modulo.sistema.service.SessaoUsuarioApi;
import com.bullcontrol.sistema.Invoker;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Method;

public class BusinessInterceptor implements MethodInterceptor, Serializable {

    private String bullcontrolUrl;
    private String serviceName;
    private Class<?> serviceClass;
    private Invoker invoker;

    public BusinessInterceptor(String serviceName, Class<?> serviceClass) {
        this.serviceName = serviceName;
        this.serviceClass = serviceClass;

        try {
            this.invoker = new HessianInvoker();
        } catch (Throwable var6) {
            var6.printStackTrace();
        }

    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return this.invoker.invoke(getBullcontrolUrl(), SessaoUsuarioApi.getSessaoUsuario(), this.serviceName, method.getName(), args, method.getParameterTypes());
    }

    private String getBullcontrolUrl() {
        if (bullcontrolUrl == null) {
            BullcontrolApiConfiguration bullcontrolApiConfiguration = BullcontrolApiContext.getBean("bullcontrolApiConfiguration");
            bullcontrolUrl = bullcontrolApiConfiguration.getBullcontrol().getUrl();
        }
        return bullcontrolUrl;
    }

    private Object readResolve() {
        return Enhancer.create(this.serviceClass, this);
    }
}
