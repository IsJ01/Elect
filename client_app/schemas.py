from pydantic import BaseModel

class ClientCreate(BaseModel):
    name: str
    phone: str
    city: str
    interest: str

class ClientOut(ClientCreate):
    id: int

    class Config:
        orm_mode = True