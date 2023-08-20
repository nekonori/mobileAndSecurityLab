public class AffineAttack {

    public static void main(String[] args) {
        String plainText = "AFFINE CIPHER";
        String cipherText = "UBBAHK CAPJKX";
        for (int a = 0; a < 100; a++) {
            for (int b = 0; b < 100; b++) {
                if (decryptCipher(cipherText, a, b).equals(plainText)) {
                    System.out.println("Key found");
                    System.out.println("a: " + a + " b: " + b);
                    System.out.println("Cipher text: " + cipherText);
                    System.out.println("Plain text: " + plainText);
                    return;
                }
            }
        }
    }

    static String decryptCipher(String cipher, int a, int b) {
        StringBuilder msg = new StringBuilder();
        int a_inv = 0;
        int flag = 0;

        for (int i = 0; i < 26; i++) {
            flag = (a * i) % 26;

            if (flag == 1) {
                a_inv = i;
            }
        }
        for (int i = 0; i < cipher.length(); i++) {
            if (cipher.charAt(i) != ' ') {
                msg.append((char) (((a_inv *
                        ((cipher.charAt(i) + 'A' - b)) % 26)) + 'A'));
            } else {
                msg.append(cipher.charAt(i));
            }
        }

        return msg.toString();
    }
}
