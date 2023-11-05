import socket
import math


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

def decrypt(encryptedText):
	encryptedText = encryptedText[2:-1]
	ans = ""
	for character in encryptedText:
		c = ord(character) - ord('a')
		m = pow(c, d)
		m = math.fmod(m, n)
		m = int(m)
		ans += chr(m + ord('a'))
	return ans

def main():
	host = socket.gethostname()
	port = 5321
	server_socket = socket.socket()
	server_socket.bind((host, port))
	server_socket.listen(2)
	print("Server is listening")
	conn, address = server_socket.accept()
	print("Connection from: " + str(address))
	while True:
		data = conn.recv(1024)
		if not data:
			break
		print("Received encrypted text from client: " + str(data))

        
		decr_data = decrypt(str(data))
		print("decrypted message: ", decr_data)
	conn.close()

if __name__ == "__main__":
	main()
