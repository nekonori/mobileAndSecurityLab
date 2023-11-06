## ElGamal Encryption Algorithm

ElGamal is a public-key encryption algorithm that was described by Taher Elgamal in 1985. It's based on the Diffie-Hellman key exchange.

**Key Generation:** In the key generation phase, a large prime number p and a primitive root of p, alpha chosen. The private key x is chosen randomly from the set {1, ..., p-2}, and the public key y is computed as y = alpha^x mod p. The public key is (p, alpha, y) and the private key is x.

**Encryption:** To encrypt a message m, a random number k from the set {1, ..., p-2} is chosen. The ciphertext is a pair (c1, c2), where c1 = alpha^k mod p and c2 = m\*y^k mod p.

**Decryption:** To decrypt the ciphertext (c1, c2), the message m is recovered by computing m = c2\*(c1^x)^-1 mod p.

ElGamal Decryption Algorithm
The decryption process in ElGamal involves using the recipient's private key to reverse the encryption process:

The recipient receives the encrypted message (c1, c2).
The recipient then computes m = c2\*(c1^x)^-1 mod p using their private key x. This gives them the original message m.
ElGamal encryption provides semantic security against chosen plaintext attacks. However, it's less commonly used than other public-key systems like RSA because it's less efficient: the ciphertext is twice as long as the plaintext.
