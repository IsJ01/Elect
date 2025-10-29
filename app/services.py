from datetime import timezone, datetime, timedelta

import jwt

from models import User

class JwtService:

    def generate_token(self, user: User, signing_key: str, hours: int):
        claims = {
            "username": user.username,
            "is_superuser": user.is_superuser,
        }
        expire = datetime.now(timezone.utc) + timedelta(hours=15)
        claims["exp"] = expire
        encoded_jwt = jwt.encode(claims, signing_key, algorithm="HS256")
        return encoded_jwt