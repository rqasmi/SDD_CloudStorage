package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

/**
 * Implements the credential service create, read, update or delete
 * information about credentials
 */
@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    /**
     * Gathers a list of all credentials of a given user
     * @param username The username of the user to get the credentials for
     * @return a list of all credentials for the given user
     */
    public List<Credential> getUserCredentials(String username) {
        return credentialMapper.getUserCredentials(userService.getUser(username).getUserId());
    }

    /**
     * Creates a credential with an encrypted password for the given user
     * @param credential The credential to create a new credential object from
     * @param username A username to get the corresponding user for
     * @return id of the new credential
     */
    public int createCredential(Credential credential, String username) {
        Integer userId = userService.getUser(username).getUserId();

        String encodedKey = generateSecretKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper
                .createCredential(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, userId));
    }

    /**
     * Gets credential by its id
     * @param id the id of the credential
     * @return the requested credential
     */
    public Credential getCredentialWithId(Integer id) {
        return credentialMapper.getCredentialWithId(id);
    }

    /**
     * Deletes a given credential by id
     * @param id the id of the credential to delete
     * @return the number of deleted rows
     */
    public int deleteCredential(Integer id) {
        return credentialMapper.deleteCredential(id);
    }

    /**
     * Updates a credential
     * @param credential A credential object, which contains updated information of the credential
     * @return the number of rows updated in the CREDENTIAL table
     */
    public int updateCredential(Credential credential) {
        Credential cred = credentialMapper.getCredentialWithId(credential.getCredentialId());
        String encodedKey = cred.getKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.updateCredential(new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, null));
    }

    /**
     * Generates a secret key for the encryption of passwords
     * @return an encoded string secret key
     */
    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
