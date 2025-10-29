import os

import uvicorn
from fastapi import FastAPI
from dotenv import load_dotenv
from fastapi.params import Depends

from fastapi.security import OAuth2PasswordBearer
from sqlalchemy.orm import Session
from sqlalchemy.sql.annotation import Annotated

from app.schemas import UserCreateDto
from app.services import UserService, generate_token

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


@app.post("/api/v1/sign-up")
async def sign_up(user: UserCreateDto,
                  user_service: UserService = Depends(),
                  db:Session = Depends(get_db)):
    new_user = user_service.create_user(db, user)
    return generate_token(new_user, SIGNING_KEY, ACCESS_TOKEN_EXPIRE_HOURS)

if __name__ == "__main__":
    uvicorn.run(app, host="localhost", port=8081)



