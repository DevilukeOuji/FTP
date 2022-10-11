from ast import Break
import xmlrpc.client
import os 
proxy = xmlrpc.client.ServerProxy("http://localhost:8000/") 

def sendFile():
    path = input('Insira o nome do arquivo: ')
    abs_path = os.path.abspath(path)
    file_name = os.path.basename(path)
    print(file_name)
    if os.path.isfile(abs_path) or os.path.isdir(abs_path):
        with open(abs_path, "rb") as handle:
            proxy.receiveFile(file_name, xmlrpc.client.Binary(handle.read()))
    else: print('Arquivo não encontrado')
    print('Upload concluído')

def receiveFile():
    file_name = input('Insira o nome do arquivo: ')
    with open(file_name, "wb") as handle:
        handle.write(proxy.sendFile(file_name).data)
    print('Download concluído')

def listFiles():
    print(proxy.listFiles())

while True:
    print('''
    1-Upload
    2-Download
    3-Listar arquivos
    ''')
    option = int(input('Escolha uma opção:'))
    
    if option == 1: sendFile()
    elif option == 2: receiveFile()
    elif option == 3: listFiles()
    else: print('Escolha uma opção válida')
    
