package com.baremind.algorithm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.baremind.utils.ConfigManager;

import java.security.Security;

/**
 * Created by fixopen on 3/6/15.
 */
public class Securities {
    static {
        // 系统添加BC加密算法 以后系统中调用的算法都是BC的算法
        Security.addProvider(new BouncyCastleProvider());
    }

    public static final Signer signer = new Signer();
    public static final AESCrypto crypto = new AESCrypto();
    public static final CertificateManager certificateManager = new CertificateManager();
    public static final Digestor digestor = new Digestor();
    public static final IOCrypto externalCrypto = new IOCrypto();
    public static final KeyPairGenerator keyPairGenerator = new KeyPairGenerator();
    public static final KeyStoreManager keyStoreManager = new KeyStoreManager();
    public static final RandomGenerator randomGenerator = new RandomGenerator();
    public static final RSA lowLevelRSA = new RSA();
    public static final Watermark watermark = new Watermark();
    public static final Zip zip = new Zip();
    public static final Config config = ConfigManager.parseConfig("configFilePath");
}
