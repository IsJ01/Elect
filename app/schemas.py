from pydantic import BaseModel


class UserCreateDto(BaseModel):
    username: str
    password: str