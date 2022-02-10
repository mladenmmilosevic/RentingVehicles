package security;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

public class TokenSecurity {

   private static RsaJsonWebKey rsaJsonWebKey = null;
   private static String issuer = "energosoft.itss";
   private static int timeToExpire = 30;

   static {
      try {
         rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
      } catch (JoseException e) {
         e.printStackTrace();
      }
   }

   public static String generateJwtToken(String id) throws JoseException {
      rsaJsonWebKey.setKeyId("k1");

      JwtClaims claims = new JwtClaims();
      claims.setIssuer(issuer);
      claims.setExpirationTimeMinutesInTheFuture(timeToExpire);
      claims.setGeneratedJwtId();
      claims.setIssuedAtToNow();
      claims.setNotBeforeMinutesInThePast(2);
      claims.setClaim("id", id);

      JsonWebSignature jws = new JsonWebSignature();
      jws.setPayload(claims.toJson());
      jws.setKey(rsaJsonWebKey.getPrivateKey());

      jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
      jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
      String jwt = jws.getCompactSerialization();

      return jwt;
   }

   public static String validateJwtToken(String jwt) throws InvalidJwtException {
      JwtConsumer jwtConsumer = new JwtConsumerBuilder()
               .setRequireExpirationTime()
               .setMaxFutureValidityInMinutes(300)
               .setAllowedClockSkewInSeconds(30)
               .setExpectedIssuer(issuer)
               .setVerificationKey(rsaJsonWebKey.getKey())
               .build();

      JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);

      return jwtClaims.getClaimsMap().get("id").toString();
   }
}
