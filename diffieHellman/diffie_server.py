import socket

def prime_checker(p):
    # Checks If the number entered is a Prime Number or not
    if p < 1:
        return -1
    elif p > 1:
        if p == 2:
            return 1
        for i in range(2, p):
            if p % i == 0:
                return -1
            return 1
 
 
def primitive_check(g, p, L):
    # Checks If The Entered Number Is A Primitive Root Or Not
    for i in range(1, p):
        L.append(pow(g, i) % p)
    for i in range(1, p):
        if L.count(i) > 1:
            L.clear()
            return -1
        return 1

def get_string_from_bytes(bin_string):
    #  print("got", bin_string)
     return (str(bin_string))[2:-1]

def main():
    host = socket.gethostname()
    port = 5322
    server_socket = socket.socket()
    server_socket.bind((host, port))
    server_socket.listen(2)
    print("Server is listening")
    conn, address = server_socket.accept()
    print("Connection from: " + str(address))

    data = conn.recv(1024)
    P = int(get_string_from_bytes(data))
    print("Received P:", P)
    
    data = conn.recv(1024)
    G = int(get_string_from_bytes(data))
    print("Received G:", G)
    
    data = conn.recv(1024)
    get_string_from_bytes(data)
    print("x1 was received in client")

    x2 = int(input("Enter private key of server:"))
    y2 = pow(G, x2)
    conn.send(bytes(str(y2), 'utf-8'))

    print("y2 was sent to client")
    data = conn.recv(1024)
    y1 = int(get_string_from_bytes(data))
    print("y1 received from client")
    k2 = pow(y1, x2) % P
    print("Shared key", k2)
    conn.close()

if __name__ == "__main__":
	main()
