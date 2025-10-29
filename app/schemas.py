from pydantic import BaseModel


class UserCreateDto(BaseModel):
    username: str
    password: str


class TokenResponse(BaseModel):
    token: str