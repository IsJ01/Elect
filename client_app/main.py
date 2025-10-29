from fastapi.responses import FileResponse
import openpyxl
from fastapi.templating import Jinja2Templates
from fastapi.responses import HTMLResponse
from fastapi import Request
from fastapi import FastAPI, Depends
from sqlalchemy.orm import Session
import models, schemas
from database import SessionLocal, engine



app = FastAPI()
templates = Jinja2Templates(directory="templates")

models.Base.metadata.create_all(bind=engine)

# Подключение к БД
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@app.get("/web", response_class=HTMLResponse)
def web_clients(request: Request):
    return templates.TemplateResponse("index.html", {"request": request})



@app.get("/")
def root():
    return {"message": "База клиентов работает!"}


@app.post("/clients/")
def create_client(client: schemas.ClientCreate, db: Session = Depends(get_db)):
    db_client = models.Client(**client.dict())
    db.add(db_client)
    db.commit()
    db.refresh(db_client)
    return db_client


@app.get("/clients/")
def get_clients(db: Session = Depends(get_db)):
    return db.query(models.Client).all()



@app.get("/export/")
def export_clients(db: Session = Depends(get_db)):
    clients = db.query(models.Client).all()

    # Создаём книгу Excel
    wb = openpyxl.Workbook()
    ws = wb.active
    ws.title = "Клиенты"

    # Заголовки
    ws.append(["ID", "Имя", "Телефон", "Город", "Интерес"])

    # Данные
    for c in clients:
        ws.append([c.id, c.name, c.phone, c.city, c.interest])

    # Сохраняем файл
    file_path = "clients.xlsx"
    wb.save(file_path)

    # Отправляем файл пользователю
    return FileResponse(file_path, media_type='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', filename="clients.xlsx")
