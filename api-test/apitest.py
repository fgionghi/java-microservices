import json
import requests
import time
import uuid

def get(url:str):
    g = requests.get(url)
    return g.status_code

def post(url:str, body:any):
    return None


myId = uuid.uuid4()
s = requests.Session()
s.headers.update({'api-token':str(myId)})

print("ID corrente: " + str(myId))

url = str(input("\nInserisci l'indirizzo a cui effettuare la richiesta: "))
nReq = int(input("Inserisci la quantità di richieste da effettuare: "))
maxTime = int(input("Inserisci il tempo di esecuzione [s]: "))
isGet = (str(input("Richiesta GET o POST? [g/p]: ").upper() == 'G'))

if(not isGet):
    body = json.loads(input("Incolla la stringa JSON contenente il body: "))

print()
for x in range(nReq):
    if(isGet):
        print(str(x + 1) + ". " + time.strftime('%Y-%m-%d %H:%M:%S',time.localtime()) + " - " + url + " - ExitCode = " + str(get(url)))
    time.sleep(maxTime / nReq)

