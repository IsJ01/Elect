import os

from fastapi import FastAPI
from dotenv import load_dotenv

from fastapi.security import OAuth2PasswordBearer

from app.services import UserService

from app.database import get_db

load_dotenv()

SIGNING_KEY = os.getenv("SIGNING_KEY")

ACCESS_TOKEN_EXPIRE_HOURS = 3

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

app = FastAPI()

@app.on_event("startup")
def startup_event():

    db = next(get_db())
    user_service = UserService()

    try:
        user_service.ensure_superuser_exists(db)
    except Exception as e:
        print(f"❌ Error during startup: {e}")
    finally:
        db.close()


