import requests, json
from tkinter import *
from datetime import datetime

def find_place(user, all_trains):
    print("Шукаю... Осання перевірка - " + datetime.now().strftime("%H:%M:%S"))
    if isinstance(all_trains['value'], str):
        print(all_trains['value'])
    print(all_trains['value'])

def get_all_places(user):
    print("=============Шукаю вльні місця============")
    data = {
        'station_id_from': user.st_from_id,
        'station_id_till': user.st_till_id,
        'station_from': user.st_from,
        'station_till': user.st_till,
        'date_dep': user.dep_date.strftime('%d.%m.%Y'),
        'time_dep': user.dep_date.strftime('%H:%M'),
        'time_dep_till': '',
        'another_ec': '',
        'search': ''
    }
    r = requests.post('http://booking.uz.gov.ua/purchase/search/', data=data)
    print(r.status_code)
    find_place(user, json.loads(r.text))

def get_station_id(name):
    r = requests.get('http://booking.uz.gov.ua/purchase/station/', params={'term': name})
    print(r.status_code)
    print(r.text)
    obj = json.loads(r.text)
    print(obj[0]['value'])
    return obj[0]['value']

def try_find_ticket(my_fild):
    if my_fild.simple_not_empty() == False:
        print("Заповніть пусті рядки")
        return False
    else:
        user = Pasenger(my_fild.station_from.get(), my_fild.station_till.get(), my_fild.dep_date.get(),
                        my_fild.place_type.get())
        get_all_places(user)

class EntryFild:
    def __init__(self):
        self.station_from = Entry(root, width="40")
        self.station_from.pack()
        self.station_till = Entry(root, width="40")
        self.station_till.pack()
        self.dep_date = Entry(root, width="10")
        self.dep_date.insert(END, datetime.now().strftime("%d.%m.%Y"))
        self.dep_date.pack()
        self.place_type = Entry(root, width="5")
        self.place_type.insert(END, "П")
        self.place_type.pack()
    def simple_not_empty(self):
        if self.station_from.get() and self.station_till.get() and self.dep_date.get() and self.place_type.get():
            return True
        else:
            return False

class FindButton:
    def __init__(self):
        self.but = Button(root)
        self.but['text'] = "Шукати"
        self.but.pack()
        self.but.bind("<Button-1>", self.action_find)
        self.but.bind('<Return>', self.action_find)
    def action_find(self, event):
        try_find_ticket(my_fild)

class Pasenger :
    def __init__(self, st_from, st_till, dep_date, place_type):
        self.st_from_id = get_station_id(st_from)
        self.st_from = st_from
        self.st_till_id = get_station_id(st_till)
        self.st_till = st_till
        self.dep_date = datetime.strptime(dep_date, '%d.%m.%Y')
        self.place_type = place_type
        self.f_name = ""
        self.l_name = ""

s = requests.Session()


root = Tk()
my_fild = EntryFild()
my_button = FindButton()
root.mainloop()