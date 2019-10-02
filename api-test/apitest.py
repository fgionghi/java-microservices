# JSON DI TEST
# {"title":"Test","category":"cat-1","description":"Descrizione di prova","availability":5,"price":10.5}
#
import json
import requests
import time
import uuid
import base64

def get(url:str):
    return s.get(url).status_code

def post(url:str, body:any):
    s.headers.update({"Content-Type":"application/json"});
    return s.post(url, data=json.dumps(body)).status_code


toEncode = {'token': str(uuid.uuid4()), 'max-call':10 }
encoded = base64.b64encode(bytes(json.dumps(toEncode),'utf-8'))


s = requests.Session()
s.headers.update({'api-token':str(encoded,'utf-8')})

print("ID corrente: " + str(encoded))

url = str(input("\nInserisci l'indirizzo a cui effettuare la richiesta: "))
nReq = int(input("Inserisci la quantità di richieste da effettuare: "))
maxTime = int(input("Inserisci il tempo di esecuzione [s]: "))
isGet:bool = (input("Richiesta GET o POST? [g/p]: ").upper() == 'G')

if(not isGet):
    body = json.loads(input("Incolla la stringa JSON contenente il body: "))

print()
for x in range(nReq):
    if(isGet):
        print(str(x + 1) + ". " + time.strftime('%Y-%m-%d %H:%M:%S',time.localtime()) + " - " + url + " - ExitCode = " + str(get(url)))
    else:
        print(str(x + 1) + ". " + time.strftime('%Y-%m-%d %H:%M:%S',time.localtime()) + " - " + url + " - ExitCode = " + str(post(url, body)))
    time.sleep(maxTime / nReq)

