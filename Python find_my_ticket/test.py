import requests, json, urllib
from tkinter import *
from datetime import datetime
from pygame import mixer


def internet_on():
    try:
        requests.get('http://216.58.192.142', timeout=5)
        return True
    except requests.ConnectionError:
        return False

#import winsound
#winsound.PlaySound("SystemExit", winsound.SND_ALIAS)


def select_place(user, coach, coaches):
    key = ""
    for value in coach['value']['places']:
        key = value
        break
    places = {0: {
        'ord': 0,
        'charline': "А",
        'wagon_num': coaches["coaches"][0]["num"],
        'wagon_class': coaches["coaches"][0]["coach_class"],
        'wagon_type': coaches["coaches"][0]["type"],
        'firstname': user.f_name,
        'lastname': user.l_name,
        'child': "",
        'stud': "",
        'bedding': 0,
        'place_num': coach['value']['places'][key][0]
    }}
    coach = {
        'from': user.st_from_id,
        'to': user.st_till_id,
        'train': user.my_train['num'],
        'date': user.dep_date.strftime('%Y-%m-%d'),
        'round_trip': 0,
        'places': places
    }
    r = requests.post('http://booking.uz.gov.ua/cart/add/', data=coach)
    print(r.status_code)
    print(requests.utils.dict_from_cookiejar(r.cookies))

def find_coach(user, coaches):
    data = {
        'station_id_from': user.st_from_id,
        'station_id_till': user.st_till_id,
        'train': user.my_train['num'],
        'coach_num': coaches['coaches'][0]['num'],
        'coach_class': coaches['coaches'][0]["coach_class"],
        'coach_type_id': coaches['coaches'][0]["coach_type_id"],
        'date_dep': user.my_train['from']['date'],
        'scheme_id': 0
    }
    r = requests.post('http://booking.uz.gov.ua/purchase/coach/', data=data)
    print(r.status_code)
    select_place(user, json.loads(r.text), coaches)

def find_coaches(user):
    data = {
        'station_id_from' : user.st_from_id,
        'station_id_till' : user.st_till_id,
        'train' : user.my_train['num'],
        'coach_type' : user.place_type,
        'model': user.my_train['model'],
        'date_dep': user.my_train['from']['date'],
        'round_trip': 0,
        'another_ec': 0
    }
    r = requests.post('http://booking.uz.gov.ua/purchase/coaches/', data=data)
    print(r.status_code)
    find_coach(user, json.loads(r.text))


def compare_place_type(all_trains_val, search_place, user):
    if search_place:
        for element in all_trains_val:
            for types in element['types']:
                if types['id'] == search_place:
                    print(types)
                    user.my_train = element
                    return True
    else:
        print(all_trains_val)
        return True
    return False


def find_place(user, all_trains):
    print("Шукаю " + user.place_type + " Осання перевірка - " + datetime.now().strftime("%H:%M:%S"))
    print(all_trains)
    if isinstance(all_trains['value'], str):
        print(all_trains['value'])
        return True
    else:
        if compare_place_type(all_trains['value'], user.place_type, user):
            mixer.init()
            mixer.music.load('vopli_vidopljasova_-_strivaj__parovoze_bonus_(zvukoff.ru).mp3')
            mixer.music.play()
            if user.f_name and user.l_name:
                find_coaches(user)
            return False
        else:
            return True


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
    return find_place(user, json.loads(r.text))

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
        user.f_name = my_fild.first_name.get()
        user.l_name = my_fild.last_name.get()
        if get_all_places(user):
            root.after(int(60000 * float(my_fild.r_time.get())), try_find_ticket, my_fild)

class EntryFild:
    def __init__(self):
        self.label = Label(root, text="New Label", font="Arial 48", bg="green")
        self.label.place(relx=0.5, rely=0.5, anchor="center")
        self.r_time = Entry(root, width="5")
        self.r_time.insert(END, 2)
        self.r_time.pack()
        self.station_from = Entry(root, width="40")
        self.station_from.insert(END, "Тернопіль")
        self.station_from.pack()
        self.station_till = Entry(root, width="40")
        self.station_till.insert(END, "Київ")
        self.station_till.pack()
        self.dep_date = Entry(root, width="10")
        self.dep_date.insert(END, datetime.now().strftime("%d.%m.%Y"))
        self.dep_date.pack()
        self.place_type = Entry(root, width="5")
        self.place_type.insert(END, "П")
        self.place_type.pack()
        self.first_name = Entry(root, width="40")
        self.first_name.pack()
        self.last_name = Entry(root, width="40")
        self.last_name.pack()
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
        self.my_train = {}

s = requests.Session()

root = Tk()
my_fild = EntryFild()
if internet_on():
    my_button = FindButton()
else:
    print('"Інернет си скінчив))"')
root.mainloop()