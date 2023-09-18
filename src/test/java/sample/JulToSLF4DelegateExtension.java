package sample;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class JulToSLF4DelegateExtension implements BeforeAllCallback {

    static {
        // java.util.loggingの出力をSLF4Jへdelegate
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // beforeAllのコールバックを待たずにクラスがロードされた時点でSLF4Jへの
        // delegate処理を実行させるためのダミー実装
    }
}
