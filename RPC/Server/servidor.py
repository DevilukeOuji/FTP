from xmlrpc.server import SimpleXMLRPCServer
import xmlrpc.client
import os

def sendFile(file_name):
    path = './Files/' + file_name
    if os.path.isfile(path) or os.path.isdir(path):
        with open(path, "rb") as handle:
            return xmlrpc.client.Binary(handle.read())
    else: print('Arquivo não encontrado')

def receiveFile(file_name, data):
    with open('./Files/' + file_name, "wb") as handle:
        handle.write(data.data)
    return True


def listFiles():
    return [file for file in os.listdir('./Files/') if os.path.isfile('./Files/' + file)]

server = SimpleXMLRPCServer(("localhost", 8000))
print("Listening on port 8000...")
server.register_function(sendFile, 'sendFile')
server.register_function(receiveFile, 'receiveFile')
server.register_function(listFiles, 'listFiles')


server.serve_forever()