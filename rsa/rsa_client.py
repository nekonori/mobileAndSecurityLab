import math
import socket


def gcd(a, h):
	temp = 0
	while(1):
		temp = a % h
		if (temp == 0):
			return h
		a = h
		h = temp


p = 3
q = 7
n = p*q
e = 2
phi = (p-1)*(q-1)

while (e < phi):
	if(gcd(e, phi) == 1):
		break
	else:
		e = e+1

k = 2
d = (1 + (k*phi))/e

def encrypt_message(message):
	res = ""
	for character in message:
		val = ord(character) - ord('a')
		c = pow(val, e)
		c = math.fmod(c, n)
		res += chr(int(c) + ord('a'))
	return res

def main():
	host = socket.gethostname()
	port = 5321
	client_socket = socket.socket()
	client_socket.connect((host, port))
	while True:
		message = input("Enter the plain text (or 'stop' to exit): ")
		if message == "stop":
			break
		encrypted_msg = encrypt_message(message)
		print("sending encrypted msg:", encrypted_msg)
		client_socket.send(bytes(encrypted_msg, 'utf-8'))
		# data = client_socket.recv(1024)
	client_socket.close()

if __name__ == "__main__":
	main()