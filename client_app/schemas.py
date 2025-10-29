from pydantic import BaseModel
from typing import Optional

class ClientCreate(BaseModel):
    apartment: str
    profile: str
    services_used: str
    provider_rating: str
    provider_interest: str
    contact_time: str
    phone: str
    fair_price: str
    note: Optional[str] = ""
    latitude: Optional[float] = None
    longitude: Optional[float] = None
