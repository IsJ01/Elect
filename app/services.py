import os
from datetime import timezone, datetime, timedelta

import jwt
from sqlalchemy.orm import Session

from app.schemas import UserCreateDto
from .models import User
from dotenv import load_dotenv


def generate_token(user: User, signing_key: str, hours: int):
    claims = {
        "username": user.username,
        "is_superuser": user.is_superuser,
    }
    expire = datetime.now(timezone.utc) + timedelta(hours=15)
    claims["exp"] = expire
    encoded_jwt = jwt.encode(claims, signing_key, algorithm="HS256")
    return encoded_jwt


class UserService:
    @staticmethod
    def get_by_username(db: Session, username: str):
        return db.query(User).filter(User.username == username).first()

    @staticmethod
    def get_by_email(db: Session, email: str):
        return db.query(User).filter(User.email == email).first()

    def create_user(self, db: Session, user_data: UserCreateDto, is_superuser: bool = False):

        if self.get_by_username(db, user_data.username):
            raise ValueError("User with this username already exists")

        db_user = User(
            username=user_data.username,
            is_superuser=is_superuser
        )
        db_user.set_password(user_data.password)

        db.add(db_user)
        db.commit()
        db.refresh(db_user)
        return db_user

    def create_superuser(self, db: Session, username: str, password: str):
        user_data = UserCreateDto(
            username=username,
            password=password,
        )
        return self.create_user(db, user_data, is_superuser=True)

    @staticmethod
    def get_superusers_count(db: Session) -> int:
        return db.query(User).filter(User.is_superuser == True).count()

    def ensure_superuser_exists(self, db: Session):
        if self.get_superusers_count(db) > 0:
            return

        username = os.getenv("ADMIN_NAME")
        password = os.getenv("ADMIN_PASS")

        if self.get_by_username(db, username):
            user = self.get_by_username(db, username)
            user.is_superuser = True
            db.commit()
            return

        try:
            self.create_superuser(
                db=db,
                username=username,
                password=password
            )

        except Exception as e:
            print(f"❌ Error creating superuser: {e}")