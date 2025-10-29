import os

from fastapi import FastAPI
from dotenv import load_dotenv

from fastapi.security import OAuth2PasswordBearer, OAuth2PasswordRequestForm

load_dotenv("../.env")

SIGNING_KEY = os.getenv("SIGNING_KEY")
ACCESS_TOKEN_EXPIRE_HOURS = 3

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

app = FastAPI()




