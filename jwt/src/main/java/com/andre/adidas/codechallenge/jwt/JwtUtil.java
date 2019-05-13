package com.andre.adidas.codechallenge.jwt;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Common helper methods to work with JWT
 */
@Slf4j
@Component
public class JwtUtil implements Serializable {
	public static final long serialVersionUID = -2630545757702775512L;
	public static final String CLAIM_KEY_ID = "id";
	public static final String CLAIM_KEY_AUTHORITIES = "authorities";

    @Value("${auth.secret}")
    private String secret;
    
	@Value("${auth.prefix}")
	private String tokenPrefix;
    
    @Value("${auth.expires}")
    private Long expiration;
    
    private String removePrefix(String token) {
    	return token.replace(tokenPrefix, "").trim();
	}

    @SneakyThrows
    public Long getUserIdFromToken(String token) {
    	token = removePrefix(token);
    	
        Long id = null;
        JWTClaimsSet claims = getClaimsFromToken(validate(token));
        id = Long.valueOf((Integer) claims.getIntegerClaim(CLAIM_KEY_ID));
        return id;
    }

    public String getUsernameFromToken(String token) throws ServletException {
    	JWTClaimsSet claims = getClaimsFromToken(validate(token));
        return claims.getSubject();
    }

    public Date getCreationDateFromToken(String token) {
    	JWTClaimsSet claims = getClaimsFromToken(validate(token));
    	
        return claims.getIssueTime();
    }

    public Date getExpirationDateFromToken(String token) {
        JWTClaimsSet claims = getClaimsFromToken(validate(token));
        
        return claims.getExpirationTime();
    }
    
    @SneakyThrows
    public JWTClaimsSet getClaimsFromToken(SignedJWT signedJWT) {
    	return signedJWT.getJWTClaimsSet();
	}

    @SneakyThrows
    public SignedJWT createSignedJWT(Authentication auth) {
        log.info("Starting to create the signed JWT");

        JwtUser user = (JwtUser) auth.getPrincipal();

        JWTClaimsSet jwtClaimSet = createJWTClaimSet(auth, user);

        KeyPair rsaKeys = generateKeyPair();

        log.info("Building JWK from the RSA Keys");

        JWK jwk = new RSAKey.Builder((RSAPublicKey) rsaKeys.getPublic()).keyID(UUID.randomUUID().toString()).build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .jwk(jwk)
                .type(JOSEObjectType.JWT)
                .build(), jwtClaimSet);

        log.info("Signing the token with the private RSA Key");

        RSASSASigner signer = new RSASSASigner(rsaKeys.getPrivate());

        signedJWT.sign(signer);

        log.info("Serialized token '{}'", signedJWT.serialize());

        return signedJWT;

    }

    private JWTClaimsSet createJWTClaimSet(Authentication auth, UserDetails userDetails) {
    	JwtUser applicationUser = (JwtUser) userDetails;
    	
        return new JWTClaimsSet.Builder()
                .subject(applicationUser.getUsername())
                .claim(CLAIM_KEY_AUTHORITIES, auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(toList()))
                .claim(CLAIM_KEY_ID, applicationUser.getId())
                .issuer("http://andre.com")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (expiration * 1000)))
                .build();
    }

    @SneakyThrows
    private KeyPair generateKeyPair() {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048);

        return generator.genKeyPair();
    }


    public String encryptToken(SignedJWT signedJWT) throws JOSEException {
        log.info("Starting the encryptToken method");

        DirectEncrypter directEncrypter = new DirectEncrypter(secret.getBytes());

        JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
                .contentType("JWT")
                .build(), new Payload(signedJWT));

        log.info("Encrypting token with system's private key");

        jweObject.encrypt(directEncrypter);

        log.info("Token encrypted");

        return jweObject.serialize();
    }

    @SneakyThrows
    public String decryptToken(String encryptedToken) {
        log.info("Decrypting token");

        JWEObject jweObject = JWEObject.parse(encryptedToken);

        DirectDecrypter directDecrypter = new DirectDecrypter(secret.getBytes());

        jweObject.decrypt(directDecrypter);

        log.info("Token decrypted, returning signed token . . . ");

        return jweObject.getPayload().toSignedJWT().serialize();
    }

    @SneakyThrows
    public void validateTokenSignature(String signedToken) {
        log.info("Starting method to validate token signature...");

        SignedJWT signedJWT = SignedJWT.parse(signedToken);

        log.info("Token Parsed! Retrieving public key from signed token");

        RSAKey publicKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());

        log.info("Public key retrieved, validating signature. . . ");

        if (!signedJWT.verify(new RSASSAVerifier(publicKey)))
            throw new AccessDeniedException("Invalid token signature!");

        log.info("The token has a valid signature");
    }
    
    @SneakyThrows
    public SignedJWT validate(String signedToken) {
    	signedToken = removePrefix(signedToken);
		validateTokenSignature(signedToken);
		
        return SignedJWT.parse(signedToken);
    }
}
