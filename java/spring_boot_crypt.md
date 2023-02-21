# Spring Boot Encryption

## Dependencies

Gradle Kotlin:

```
dependencies {
    implementation(“org.springframework.boot:spring-boot-starter-security:2.7.2”)
}
```

## Encryption

Declare bean of the encrypter. "Standard" is 256-bit AES symmetric encryption.


```
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public BytesEncryptor aesBytesEncryptor() {
        String password = "hackme"; // should be kept in a secure place and not be shared
        String salt = "8560b4f4b3"; // should be hex-encoded with even number of chars
        return Encryptors.standard(password, salt);
    }

    //OR use TextEncryptor

    @Bean
    public TextEncryptor hexEncodingTextEncryptor() {
        String password = "hackme"; // should be kept in a secure place and not be shared
        String salt = "8560b4f4b3"; // should be hex-encoded with even number of chars
        return Encryptors.text(password, salt);
    }


    @Override
    public void run(String... args) throws Exception {
        BytesEncryptor bytesEncryptor = aesBytesEncryptor();

        byte[] inputBinaryData = {104, 121, 112, 101, 114, 115, 107, 105, 108, 108};
        byte[] encryptedData = bytesEncryptor.encrypt(inputBinaryData);
        byte[] decryptedData = bytesEncryptor.decrypt(encryptedData);

        System.out.println("Input data: " + new String(inputBinaryData));
        System.out.println("Encrypted data: " + new String(encryptedData));
        System.out.println("Decrypted data: " + new String(decryptedData));

        String inputData = "the owl is in the bush";
        String encryptedData = textEncryptor.encrypt(inputData);
        String decryptedData = textEncryptor.decrypt(encryptedData);

        System.out.println("Input data: " + inputData);
        System.out.println("Encrypted data: " + encryptedData);
        System.out.println("Decrypted data: " + decryptedData);
    }
}
```


## Password hashing


```
int strength = 7;
PasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(strength);

String rawPassword = "hackme";
String firstEncodedPassword = bCryptEncoder.encode(rawPassword);
String secondEncodedPassword = bCryptEncoder.encode(rawPassword);

System.out.println("First encoded password: " + firstEncodedPassword);
System.out.println("Second encoded password: " + secondEncodedPassword);
```

