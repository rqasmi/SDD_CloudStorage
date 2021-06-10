package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

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

    public List<Credential> getUserCredentials(String username) {
        return credentialMapper.getUserCredentials(userService.getUser(username).getUserId());
    }

    public int createCredential(Credential credential, String username) {
        Integer userId = userService.getUser(username).getUserId();

        String encodedKey = generateSecretKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper
                .createCredential(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, userId));
    }

    public Credential getCredentialWithId(Integer id) {
        return credentialMapper.getCredentialWithId(id);
    }

    public int deleteCredential(Integer id) {
        return credentialMapper.deleteCredential(id);
    }

    public int updateCredential(Credential credential) {
        Credential cred = credentialMapper.getCredentialWithId(credential.getCredentialId());
        String encodedKey = cred.getKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.updateCredential(new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, null));
    }

    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }


}
