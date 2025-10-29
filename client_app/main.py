from fastapi import FastAPI, Request, Depends
from fastapi.responses import RedirectResponse, HTMLResponse
from fastapi.responses import HTMLResponse, FileResponse
from fastapi.templating import Jinja2Templates
from sqlalchemy.orm import Session
from database import SessionLocal, engine
import models, schemas
import openpyxl

app = FastAPI()
templates = Jinja2Templates(directory="templates")

models.Base.metadata.create_all(bind=engine)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

# Главная страница
@app.get("/web", response_class=HTMLResponse)
def web_clients(request: Request):
    return templates.TemplateResponse("index.html", {"request": request})

@app.get("/")
def root():
    # Перенаправляем пользователя на /web
    return RedirectResponse(url="/web")

# Получение всех клиентов
@app.get("/clients/")
def read_clients(db: Session = Depends(get_db)):
    return db.query(models.Client).all()

# Добавление клиента
@app.post("/clients/")
def create_client(client: schemas.ClientCreate, db: Session = Depends(get_db)):
    db_client = models.Client(**client.dict())
    db.add(db_client)
    db.commit()
    db.refresh(db_client)
    return db_client

# Экспорт в Excel
@app.get("/export/")
def export_clients(db: Session = Depends(get_db)):
    clients = db.query(models.Client).all()
    wb = openpyxl.Workbook()
    ws = wb.active
    ws.title = "Клиенты"
    ws.append([
        "ID","Квартира","Портрет","Услуги","Оценка провайдера",
        "Интерес к услугам","Удобное время","Телефон","Желаемая цена","Примечание",
        "Широта","Долгота"
    ])
    for c in clients:
        ws.append([
            c.id, c.apartment, c.profile, c.services_used, c.provider_rating,
            c.provider_interest, c.contact_time, c.phone, c.fair_price, c.note,
            c.latitude, c.longitude
        ])
    file_path = "clients.xlsx"
    wb.save(file_path)
    return FileResponse(file_path, media_type='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', filename="clients.xlsx")


