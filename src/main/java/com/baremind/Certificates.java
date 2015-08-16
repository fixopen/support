package com.baremind;

import javax.security.cert.Certificate;
import javax.security.cert.CertificateEncodingException;
import javax.security.cert.CertificateException;
import java.security.*;

/**
 * Created by fixopen on 16/8/15.
 */
public class Certificates {
    public Certificate grant() {
        return new Certificate() {
            @Override
            public byte[] getEncoded() throws CertificateEncodingException {
                return new byte[0];
            }

            @Override
            public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {

            }

            @Override
            public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {

            }

            @Override
            public String toString() {
                return null;
            }

            @Override
            public PublicKey getPublicKey() {
                return null;
            }
        };
    }

    public void revoke(Certificate certificate) {
        //
    }

    public Certificate query() {
        return new Certificate() {
            @Override
            public byte[] getEncoded() throws CertificateEncodingException {
                return new byte[0];
            }

            @Override
            public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {

            }

            @Override
            public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {

            }

            @Override
            public String toString() {
                return null;
            }

            @Override
            public PublicKey getPublicKey() {
                return null;
            }
        };
    }
}
