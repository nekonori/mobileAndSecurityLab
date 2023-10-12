# Python program to illustrate ElGamal encryption
import socket
import random
from math import pow

a = random.randint(2, 10)


def gcd(a, b):
    if a < b:
        return gcd(b, a)
    elif a % b == 0:
        return b
    else:
        return gcd(b, a % b)


# Generating large random numbers
def gen_key(q):
    key = random.randint(1, q)
    while gcd(q, key) != 1:
        key = random.randint(1, q)

    return key


# Modular exponentiation
def power(a, b, c):
    x = 1
    y = a

    while b > 0:
        if b % 2 != 0:
            x = (x * y) % c
        y = (y * y) % c
        b = int(b / 2)

    return x % c


# Asymmetric encryption
def encrypt(msg, q, h, g):
    en_msg = []

    k = gen_key(q)  # Private key for sender
    s = power(h, k, q)
    p = power(g, k, q)

    for i in range(0, len(msg)):
        en_msg.append(msg[i])

    print("g^k used : ", p)
    print("g^ak used : ", s)
    for i in range(0, len(en_msg)):
        en_msg[i] = s * ord(en_msg[i])

    return en_msg, p


def decrypt(en_msg, p, key, q):
    dr_msg = []
    h = power(p, key, q)
    for i in range(0, len(en_msg)):
        dr_msg.append(chr(int(en_msg[i] / h)))

    return dr_msg


def get_string_from_byte_string(byte_string):
    return (str(byte_string))[2:-1]


# Driver code
def main():
    host = socket.gethostname()
    port = 5000
    client_socket = socket.socket()
    client_socket.connect((host, port))
    print("Connected to server")

    data = client_socket.recv(1024)
    q = int(get_string_from_byte_string(data))

    data = client_socket.recv(1024)
    g = int(get_string_from_byte_string(data))

    data = client_socket.recv(1024)
    h = int(get_string_from_byte_string(data))

    while True:
        msg = input("Enter message to encrypt: ")
        en_msg, p = encrypt(msg, q, h, g)
        print("Sending encrypted Message: ", en_msg)

        client_socket.send(str(en_msg).encode())
        client_socket.send(str(p).encode())


if __name__ == "__main__":
    main()
